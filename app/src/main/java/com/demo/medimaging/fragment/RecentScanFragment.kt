package com.demo.medimaging.fragment

import androidx.recyclerview.widget.LinearLayoutManager
import com.demo.medimaging.adapter.CommonAdapter
import com.demo.medimaging.R
import com.demo.medimaging.bean.CommonBean
import com.hello.lib_net.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_recent_scan.*

/**
 * Create by jzhan at 2019-12-04
 *
 */
class RecentScanFragment :BaseFragment(){
    override fun getLayoutId(): Int = R.layout.fragment_recent_scan

    override fun initView() {
        recycler_view.layoutManager = LinearLayoutManager(requireContext())
        val list = arrayListOf<CommonBean>()
        list.add(CommonBean())
        list.add(CommonBean())
        list.add(CommonBean())
        recycler_view.adapter = CommonAdapter(requireContext(),list)
    }

}