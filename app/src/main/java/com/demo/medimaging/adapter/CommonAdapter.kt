package com.demo.medimaging.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.demo.medimaging.R
import com.demo.medimaging.bean.CommonBean
import com.demo.medimaging.bean.CommonInnerBean
import kotlinx.android.synthetic.main.item_common.view.*

/**
 * Create by jzhan at 2019-12-04
 *
 */
class CommonAdapter(var mContext: Context, var data: List<CommonBean>) :
    RecyclerView.Adapter<CommonAdapter.CommonViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommonViewHolder {
        val inflate = LayoutInflater.from(mContext).inflate(R.layout.item_common, null, false)
        return CommonViewHolder(inflate)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: CommonViewHolder, position: Int) {
//        holder.title.text = data[position].title
        holder.title.text = "NEW"
        holder.recyclerView.layoutManager = GridLayoutManager(mContext, 2, LinearLayoutManager.VERTICAL,false)
//        holder.recyclerView.layoutManager = LinearLayoutManager(mContext)
        val list = data[position].data
        list.add(CommonInnerBean())
        list.add(CommonInnerBean())
        list.add(CommonInnerBean())
        list.add(CommonInnerBean())
        holder.recyclerView.adapter = InnerAdapter(mContext, list)
    }

    class CommonViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {
        var title = mView.tv_title
        var recyclerView = mView.recycler_view
    }

}