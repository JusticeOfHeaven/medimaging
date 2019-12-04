package com.hello.lib_net.mvp

import android.content.Context

/**
 * Create by jzhan on 2019/7/11
 */
abstract class BasePresenter<T : IBaseView<*>>() : IBasePresenter {

    lateinit var mContext: Context
    lateinit var view: T

    constructor(view: T) : this() {
        this.view = view
        this.mContext = view.getContext()

    }

}