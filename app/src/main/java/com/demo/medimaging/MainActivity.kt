package com.demo.medimaging

import androidx.fragment.app.Fragment
import com.demo.medimaging.fragment.MainFragment
import com.demo.medimaging.fragment.RecentScanFragment
import com.hello.lib_net.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private var fragments = arrayListOf<Fragment>()
    private val recentScanFragment by lazy { RecentScanFragment() }
    private val mainFragment by lazy { MainFragment() }

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun initView() {

        fragments.add(recentScanFragment)
        fragments.add(mainFragment)

        indicator_view.bindViewPager(view_pager, fragments, supportFragmentManager)
    }

}
