package com.hello.lib_net.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Create by jzhan
 */
open class BaseViewHolder(mView:View):RecyclerView.ViewHolder(mView) {

    var contentView:View

    init {
        contentView = mView
    }
}