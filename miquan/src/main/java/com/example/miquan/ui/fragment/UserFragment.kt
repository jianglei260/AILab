package com.example.miquan.ui.fragment

import android.app.AlertDialog
import android.app.Fragment
import android.content.DialogInterface
import android.content.res.ColorStateList
import android.databinding.Observable
import android.databinding.ObservableBoolean
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.support.v7.widget.AppCompatImageView
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import com.example.miquan.R
import com.example.miquan.data.ResouceRepository
import com.example.miquan.data.UserRepository
import com.example.miquan.data.server.RetrofitProvider
import com.example.miquan.extensions.appcompatImageView
import com.example.miquan.extensions.simpleDraweeView
import com.example.miquan.model.User
import com.example.miquan.ui.activitiy.ChargeActivity
import com.example.miquan.ui.activitiy.LoginActivity
import com.example.miquan.upgrade.AppUpdateTool
import com.example.miquan.upgrade.VersionInfo
import com.facebook.drawee.generic.RoundingParams
import com.facebook.drawee.view.SimpleDraweeView
import kotlinx.android.synthetic.main.toast_view.view.*
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.*
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.text.SimpleDateFormat
import java.util.*

class UserFragment : Fragment() {
    val loadding = ObservableBoolean(false)

    companion object {
        fun newInstance(): UserFragment {
            return UserFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val root = UserFragmentUI().createView(AnkoContext.Companion.create(activity, this, false))
        initUser()
        loadding.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                find<ProgressBar>(UserFragmentUI.LOADDING).visibility = if (loadding.get()) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
            }
        })
        return root
    }

    fun initUser() {
        async(kotlinx.coroutines.experimental.android.UI) {
            val result = bg {
                UserRepository.instance.getCurrentUser(activity)
            }
            showUser(result.await())
        }
    }

    fun showUser(user: User?) {
        Log.d("user login:", user.toString())
        val idTextView = view.find<TextView>(UserFragmentUI.USER_ID_TEXT)
        val vipTextView = view.find<TextView>(UserFragmentUI.USER_COIN_TEXT)
        val vipFlagImage = view.find<AppCompatImageView>(UserFragmentUI.VIP_FLAG_IMAGE)
        val headImage = view.find<SimpleDraweeView>(UserFragmentUI.USER_IMAGE)
        if (user != null) {
            idTextView.text = user.phone
            idTextView.onClick { }
            headImage.imageURI = Uri.parse("res:///" + R.drawable.ic_logo)
            val dateMills = user.vipData.toLong();
            val date = Date(dateMills)
            val today = Date();
            if (date.before(today) && user.type == 2) {
                vipTextView.text = "会员已过期"
                vipFlagImage.visibility = View.VISIBLE
                vipFlagImage.foregroundTintList = ColorStateList.valueOf(Color.parseColor("#bdbdbd"))
            } else {
                //todo
                vipFlagImage.visibility = View.GONE
                vipTextView.text = ""
            }
            if (date.after(today)) {
                vipFlagImage.visibility = View.VISIBLE
                vipFlagImage.foregroundTintList = ColorStateList.valueOf(Color.parseColor("#f5bb47"))
                val format = SimpleDateFormat("yyyy-mm-dd")
                vipTextView.setText("会员有效期至：" + format.format(date))
            }
        } else {
            headImage.imageURI = Uri.parse("res:///" + R.drawable.ic_m)
            idTextView.text = "点击登录"
            vipFlagImage.visibility = View.GONE
            vipTextView.text = ""
            idTextView.onClick {
                startActivity<LoginActivity>()
            }
        }
    }

    fun checkUpdate() {
        loadding.set(true)
        val tool = AppUpdateTool.Builder(activity).setRequestUrl(RetrofitProvider.BASE_URl + "/v1/version").setAutoUpdate(true).build()
        tool.checkUpdate(object : AppUpdateTool.UpdateCallback {
            override fun needUpdate(needUpdate: Boolean, versionInfo: VersionInfo) {
                activity.runOnUiThread {
                    loadding.set(false)
                    if (needUpdate) {
                        showNeedUpdate(tool, versionInfo)
                    } else {
                        showNewest()
                    }
                }
            }

            override fun onFailure() {
                activity.runOnUiThread {
                    loadding.set(false)
                    showNewest()
                }
            }
        })

    }

    fun showNeedUpdate(tool: AppUpdateTool, versionInfo: VersionInfo) {
        val dialog = AlertDialog.Builder(activity).setMessage(versionInfo.getFeature()).setTitle(R.string.update_new_version).setPositiveButton(R.string.update_download, DialogInterface.OnClickListener { dialog, which ->
            dialog.dismiss()
            tool.doUpdate(versionInfo, false)
        }).setNegativeButton(R.string.update_cancel, DialogInterface.OnClickListener { dialog, which -> dialog.dismiss() }).show()
    }

    fun showNewest() {
        val dialog = AlertDialog.Builder(activity).setMessage(R.string.update_newest_version).setPositiveButton(R.string.dialog_sure, DialogInterface.OnClickListener { dialog, which -> dialog.dismiss() }).show()
    }

    fun logout() {
        val dialog = AlertDialog.Builder(activity).setMessage("是否退出当前用户登录").setPositiveButton(R.string.dialog_sure, DialogInterface.OnClickListener { dialog, which ->
            dialog.dismiss()
            doLogout()
        }).setNegativeButton("取消", DialogInterface.OnClickListener { dialog, which ->
            dialog.dismiss()
        }).show()

    }

    fun doLogout() {
        loadding.set(true)
        async(kotlinx.coroutines.experimental.android.UI) {
            val result = bg {
                UserRepository.instance.logout(activity)
            }
            val resources = updateLogout(result.await())
        }
    }

    fun updateLogout(success: Boolean) {
        loadding.set(false)
        if (success) {
            initUser()
        }
    }

}

class UserFragmentUI : AnkoComponent<UserFragment> {
    companion object {
        val USER_ID_TEXT = 1
        val USER_COIN_TEXT = 2
        val VIP_FLAG_IMAGE = 3
        val LEFT_IMAGE = 4
        val RIGHT_IMAGE = 5
        val USER_IMAGE = 6
        val LOADDING = 7
    }

    override fun createView(ui: AnkoContext<UserFragment>): View = with(ui) {
        relativeLayout {
            scrollView {
                verticalLayout {
                    relativeLayout {
                        backgroundColor = Color.WHITE
                        simpleDraweeView {
                            id = USER_IMAGE
                            imageURI = Uri.parse("res:///" + R.drawable.ic_m)
                            hierarchy.roundingParams = RoundingParams.asCircle()
                        }.lparams(width = dip(56), height = dip(56)) {
                            leftMargin = dip(16)
                            centerVertically()
                        }
                        appcompatImageView {
                            id = VIP_FLAG_IMAGE
                            imageResource = R.drawable.ic_vip
                            visibility = GONE
                        }.lparams(width = dip(12), height = dip(12)) {
                            topMargin = dip(32)
                            leftMargin = dip(60)
                        }
                        verticalLayout {
                            textView("user id") {
                                textSize = sp(4).toFloat()
                                id = USER_ID_TEXT
                            }.lparams(width = wrapContent, height = wrapContent) {
                            }
                            textView("非会员") {
                                //                            textSize = sp(12).toFloat()
                                id = USER_COIN_TEXT
                            }.lparams(width = wrapContent, height = wrapContent) {
                            }
                        }.lparams(width = wrapContent, height = wrapContent) {
                            leftMargin = dip(12)
                            rightOf(USER_IMAGE)
                            centerVertically()
                        }

                    }.lparams(width = matchParent, height = dip(120)) {
                        bottomMargin = dip(4)
                    }
                    //charge layout begin
                    relativeLayout {
                        backgroundColor = Color.WHITE
                        onClick {
                            startActivity<ChargeActivity>()
                        }
                        val leftImage = imageView {
                            id = LEFT_IMAGE
                            imageResource = R.drawable.ic_charge
                        }.lparams(width = dip(24), height = dip(24)) {
                            leftMargin = dip(16)
                            centerVertically()
                        }
                        val text = textView {
                            text = "充值"
                        }.lparams(width = wrapContent, height = dip(24)) {
                            centerVertically()
                            leftMargin = dip(16)
                            rightOf(leftImage)
                        }
                        val rightImage = imageView {
                            id = RIGHT_IMAGE
                            imageResource = R.drawable.ic_detail
                        }.lparams(width = dip(24), height = dip(24)) {
                            alignParentRight()
                            rightMargin = dip(16)
                            centerVertically()
                        }

                    }.lparams(width = matchParent, height = dip(40)) {
                        bottomMargin = dip(1)
                    }
                    //charge layout end
                    //charge layout begin
                    relativeLayout {
                        backgroundColor = Color.WHITE
                        val leftImage = imageView {
                            id = LEFT_IMAGE
                            imageResource = R.drawable.ic_update
                        }.lparams(width = dip(24), height = dip(24)) {
                            leftMargin = dip(16)
                            centerVertically()
                        }
                        val text = textView {
                            text = "检查更新"
                        }.lparams(width = wrapContent, height = dip(24)) {
                            centerVertically()
                            leftMargin = dip(16)
                            rightOf(leftImage)
                        }
                        val rightImage = imageView {
                            id = RIGHT_IMAGE
                            imageResource = R.drawable.ic_detail
                        }.lparams(width = dip(24), height = dip(24)) {
                            alignParentRight()
                            rightMargin = dip(16)
                            centerVertically()
                        }
                        onClick {
                            ui.owner.checkUpdate()
                        }
                    }.lparams(width = matchParent, height = dip(40)) {
                        bottomMargin = dip(1)
                    }
                    //charge layout end
                    //charge layout begin
                    relativeLayout {
                        backgroundColor = Color.WHITE
                        val text = textView {
                            text = "退出登录"
                            gravity = Gravity.CENTER
                        }.lparams(width = wrapContent, height = dip(24)) {
                            centerInParent()
                        }
                        onClick {
                            ui.owner.logout()
                        }
                    }.lparams(width = matchParent, height = dip(40)) {
                        topMargin = dip(16)
                    }
                    //charge layout end
                }
            }.lparams(width = matchParent, height = matchParent)
            progressBar {
                id = LOADDING
                visibility = if (owner.loadding.get()) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
            }.lparams(width = dip(24), height = dip(24)) {
                centerInParent()
            }

        }

    }
}