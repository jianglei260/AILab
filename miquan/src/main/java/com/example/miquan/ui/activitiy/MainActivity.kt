package com.example.miquan.ui.activitiy

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.view.Menu
import android.view.MenuItem
import com.example.miquan.R
import com.example.miquan.base.BaseActivity
import com.example.miquan.extensions.bottomNavigationView
import com.example.miquan.ui.fragment.MainFragment
import com.example.miquan.ui.fragment.UserFragment
import me.relex.photodraweeview.PhotoDraweeView
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class MainActivity : BaseActivity() {
    val mainFragment = MainFragment.newInstance()
    val userFragment = UserFragment.newInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mainUI = MainActivityUI()
        mainUI.setContentView(this)
        fragmentManager.beginTransaction().add(MainActivityUI.container, mainFragment).add(MainActivityUI.container, userFragment).hide(userFragment).commit()
        find<BottomNavigationView>(MainActivityUI.bottom_navigation_view).setOnNavigationItemSelectedListener {
            if (it.itemId == R.id.main) {
                fragmentManager.beginTransaction().show(mainFragment).hide(userFragment).commit()
            } else {
                fragmentManager.beginTransaction().show(userFragment).hide(mainFragment).commit()
            }
            return@setOnNavigationItemSelectedListener true
        }
        registeEventAction(LoginActivity.ACTION_USER_LOGIN)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.action_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            R.id.action_sacn_code -> {
//                toast("qr code")
//            }
//        }
        return super.onOptionsItemSelected(item)
    }

    override fun onEvent(action: String?) {
        super.onEvent(action)
        if (action.equals(LoginActivity.ACTION_USER_LOGIN)) {
            if (userFragment != null) {
                userFragment.initUser()
            }
        }
    }
}

class MainActivityUI : AnkoComponent<MainActivity> {
    companion object {
        val container: Int = 1
        val bottom_navigation_view = 2
    }

    override fun createView(ui: AnkoContext<MainActivity>) = ui.apply {
        relativeLayout {
            bottomNavigationView {
                id = bottom_navigation_view
                inflateMenu(R.menu.bottom_menu)
            }.lparams {
                alignParentBottom()
            }
            frameLayout {
                id = container
            }.lparams(width = matchParent, height = matchParent) {
                above(bottom_navigation_view)
            }
            view {
                backgroundColor = R.color.gray
            }.lparams(width = matchParent, height = 1) {
                below(container)
            }
        }
    }.view
}