package com.hello.lib_net.adapter

import androidx.annotation.IdRes

/**
 * Create by jzhan
 *
 *
 */
abstract class LoadMoreView {

    companion object {
        const val STATUS_DEFAULT = 1
        const val STATUS_LOADING = 2
        const val STATUS_FAIL = 3
        const val STATUS_END = 4
    }

    var mLoadMoreStatus: Int = STATUS_DEFAULT
        get() = field
        set(value) {
            field = value
        }
    /**
     * no more data is hidden
     * true for no more data
     */
    var mLoadMoreEndGone = false

    fun convert(holder: BaseViewHolder) {
        when (mLoadMoreStatus) {
            STATUS_DEFAULT -> {
            }
            STATUS_LOADING -> {
            }
            STATUS_FAIL -> {
            }
            STATUS_END -> {
            }

        }
    }

    @IdRes
    abstract fun getLoadingViewId(): Int

    fun isLoadEndMoreGone(): Boolean {
        if (getLoadingViewId() == 0) {
            return true
        }
        return mLoadMoreEndGone
    }
}