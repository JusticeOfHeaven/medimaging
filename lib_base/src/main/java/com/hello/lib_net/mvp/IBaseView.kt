package com.hello.lib_net.mvp

import android.content.Context
import androidx.lifecycle.Lifecycle

/**
 * Create by jzhan on 2019/7/11
 */
interface IBaseView<T : IBasePresenter> {

    fun getPresenter(): T

    fun getContext(): Context

    /**
     * 判断Activity、Fragment是否回收
     */
    fun isRecycled(): Boolean

    fun getLifecycle(): Lifecycle

}