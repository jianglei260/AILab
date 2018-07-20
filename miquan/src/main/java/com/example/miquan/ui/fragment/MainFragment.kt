package com.example.miquan.ui.fragment

import android.app.Fragment
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.support.v7.widget.StaggeredGridLayoutManager.VERTICAL
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.miquan.R
import com.example.miquan.data.ResouceRepository
import com.example.miquan.data.server.RemoteDataSource
import org.jetbrains.anko.*
import com.example.miquan.extensions.*
import com.example.miquan.model.Resource
import com.example.miquan.ui.activitiy.DetailActivity
import com.example.miquan.ui.adapter.MainListAdapter
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.recyclerview.v7.recyclerView

class MainFragment : Fragment() {
    companion object {
        fun newInstance(): MainFragment {
            return MainFragment()
        }
    }

    var loadPageNum = 0

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val root = MainFragmentUI().createView(AnkoContext.Companion.create(activity, this, false));
        val recyclerView: RecyclerView = root.find(MainFragmentUI.recyler_view)
        recyclerView.adapter = MainListAdapter(ArrayList()) {
            startDetailActivity(it)
        }
        recyclerView.layoutManager = StaggeredGridLayoutManager(2, VERTICAL)
        loadData(loadPageNum)
        return root
    }

    fun startDetailActivity(resource: Resource) {
        startActivity<DetailActivity>(DetailActivity.ID to resource.id)
    }

    suspend fun getData(page: Int): List<Resource>? {
        return RemoteDataSource.instance.resouces(page)
    }

    fun loadData(page: Int, clear: Boolean = false) {
        async(UI) {
            val result = bg {
                ResouceRepository.instance.resouces(page)
            }
            val resources = showData(result.await(), clear)
        }

    }

    fun showData(resources: List<Resource>?, clear: Boolean = false) {
        val adapter = view.find<RecyclerView>(MainFragmentUI.recyler_view).adapter as MainListAdapter

        if (resources != null) {
            if (clear) {
                adapter.resources.clear()
                adapter.notifyDataSetChanged()
                view.find<SmartRefreshLayout>(MainFragmentUI.swip_refresh_layout).finishRefresh()
            } else {
                view.find<SmartRefreshLayout>(MainFragmentUI.swip_refresh_layout).finishLoadmore()
            }
            val start = adapter.resources.size
            adapter.resources.addAll(resources)
            adapter.notifyItemInserted(start)
            loadPageNum++
        } else {
            toast(R.string.load_data_error)
        }
    }
}

class MainFragmentUI : AnkoComponent<MainFragment> {
    companion object {
        val header_layout = 1;
        val swip_refresh_layout = 2
        val recyler_view = 3
    }

    override fun createView(ui: AnkoContext<MainFragment>): View = with(ui) {
        relativeLayout {
            smartRefreshLayout {
                id = swip_refresh_layout
                setRefreshHeader(ClassicsHeader(ctx))
                setRefreshFooter(ClassicsFooter(ctx))
                setOnLoadmoreListener {
                    ui.owner.loadData(ui.owner.loadPageNum)
                }
                setOnRefreshListener {
                    ui.owner.loadPageNum = 0
                    ui.owner.loadData(ui.owner.loadPageNum, true)
                }
                recyclerView {
                    id = recyler_view
                }.layoutParams = SmartRefreshLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            }.lparams(width = matchParent, height = matchParent) {
                //                below(header_layout)
            }
//            relativeLayout {
//                id = header_layout
//                textView(R.string.app_name)
//            }.lparams(width = matchParent, height = dip(49))
        }
    }
}