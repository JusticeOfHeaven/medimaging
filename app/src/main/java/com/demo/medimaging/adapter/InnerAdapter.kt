package com.demo.medimaging.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.demo.medimaging.R
import com.demo.medimaging.bean.CommonInnerBean
import com.hello.lib_net.util.GlideApp
import kotlinx.android.synthetic.main.item_common_inner.view.*

class InnerAdapter(var mContext: Context, var data: List<CommonInnerBean>) :
    RecyclerView.Adapter<InnerAdapter.InnerViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.item_common_inner, null, false)
        return InnerViewHolder(view)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: InnerViewHolder, position: Int) {
        GlideApp.with(mContext)
            .load(data[position].url)
            .into(holder.imageView)

        holder.name.text = "body_anatom"
        holder.size.text = "165MB"
        holder.download.setOnClickListener {
            Toast.makeText(mContext, "开始下载", Toast.LENGTH_LONG).show()
        }
    }

    inner class InnerViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {
        var imageView = mView.image_view
        var name = mView.tv_name
        var size = mView.tv_size
        var download = mView.tv_download
    }

}
