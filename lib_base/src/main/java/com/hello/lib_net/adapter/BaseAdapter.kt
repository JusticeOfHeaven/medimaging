package com.hello.lib_net.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import java.lang.NullPointerException

/**
 * Create by jzhan
 *
 * RecyclerView.Adapter 的基类
 */
@Suppress("NAME_SHADOWING", "PrivatePropertyName", "MemberVisibilityCanBePrivate")
abstract class BaseAdapter<T> : RecyclerView.Adapter<BaseViewHolder> {

    private var layoutResId: Int
    private var mHeaderLayout: LinearLayout? = null
    private var mFooterLayout: LinearLayout? = null
    private var mEmptyLayout: LinearLayout? = null
    private val mLoadMoreView: LoadMoreView = SimpleLoadMoreView()

    private var mData: ArrayList<T> = arrayListOf()
    private var mContext: Context? = null
    private var mRequestLoadMoreListener: RequestLoadMoreListener? = null
    private var mLoadMoreEnable: Boolean = false
    private var mNextLoadEnable: Boolean = false
    private var mLoading: Boolean = false
    private var mAutoLoadMoreSize = 1

    private val HEADER_VIEW = 0x00000111
    private val LOADING_VIEW = 0x00000222
    private val FOOTER_VIEW = 0x00000333
    private val EMPTY_VIEW = 0x00000555

    private var mRecyclerView: RecyclerView? = null

    constructor() : this(0, arrayListOf())
    constructor(@LayoutRes layoutResId: Int) : this(layoutResId, arrayListOf()) {}
    constructor(@LayoutRes layoutResId: Int, data: ArrayList<T>) {
        this.layoutResId = layoutResId
        this.mData.addAll(data)
    }


    fun bindToRecyclerView(recyclerView: RecyclerView) {
        mRecyclerView = recyclerView
        mRecyclerView?.adapter = this
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        var baseViewHolder: BaseViewHolder? = null
        mContext = parent.context

        when (viewType) {
            HEADER_VIEW -> {
                mHeaderLayout?.apply {
                    baseViewHolder = createBaseViewHolder(this)
                }
            }
            FOOTER_VIEW -> {
                mFooterLayout?.apply {
                    baseViewHolder = createBaseViewHolder(this)
                }
            }
            EMPTY_VIEW -> {
                mEmptyLayout?.apply {
                    baseViewHolder = createBaseViewHolder(this)
                }
            }
            LOADING_VIEW -> {
                baseViewHolder = getLoadingView(parent)
            }
        }

        return baseViewHolder ?: throw NullPointerException("BaseAdapter onCreateViewHolder return a null ViewHolder")
    }


    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        // don't move position
        autoLoadMore(position)
        when (holder.itemViewType) {
            HEADER_VIEW,
            EMPTY_VIEW,
            FOOTER_VIEW -> {
            }
            LOADING_VIEW -> {
                mLoadMoreView.convert(holder)
            }
            else -> {
                convert(holder, mData[holder.layoutPosition - getHeaderLayoutCount()])
            }
        }
    }

    private fun autoLoadMore(position: Int) {
        if (getLoadMoreViewCount() == 0) {
            return
        }
        // 说明还没到最后一个
        if (position < itemCount - mAutoLoadMoreSize) {
            return
        }
        // 说明正在请求当中或者有请求失败
        if (mLoadMoreView.mLoadMoreStatus != LoadMoreView.STATUS_DEFAULT) {
            return
        }
        mLoadMoreView.mLoadMoreStatus = LoadMoreView.STATUS_LOADING
        if (!mLoading) {
            mLoading = true
            if (mRecyclerView != null) {
                mRecyclerView?.post {
                    mRequestLoadMoreListener?.onLoadMoreRequested()
                }
            } else {
                mRequestLoadMoreListener?.onLoadMoreRequested()
            }
        }
    }

    override fun getItemCount(): Int {
        var count = 0
        if (getEmptyViewCount() == 1) {
            count = 1
            if (getHeaderLayoutCount() != 0) {
                count++
            }
            if (getFooterLayoutCount() != 0) {
                count++
            }
        } else {
            count = getHeaderLayoutCount() + mData.size + getFooterLayoutCount() + getLoadMoreViewCount()
        }
        return count
    }


    private fun getLoadingView(parent: ViewGroup): BaseViewHolder {
        val view = getItemView(mLoadMoreView.getLoadingViewId(), parent)
        val holder = createBaseViewHolder(view)
        holder.contentView.setOnClickListener {
            // 加载失败
            if (mLoadMoreView.mLoadMoreStatus == LoadMoreView.STATUS_FAIL) {
                mLoadMoreView.mLoadMoreStatus = LoadMoreView.STATUS_DEFAULT
                notifyItemChanged(getHeaderLayoutCount() + mData.size + getFooterLayoutCount())
            }
        }
        return holder
    }

    private fun getLoadMoreViewCount(): Int {
        if (mRequestLoadMoreListener == null || !mLoadMoreEnable) {
            return 0
        }
        if (!mNextLoadEnable && mLoadMoreView.isLoadEndMoreGone()) {
            return 0
        }
        if (mData.isEmpty()) {
            return 0
        }
        return 1
    }

    private fun getItemView(layoutResId: Int, parent: ViewGroup): View {
        return LayoutInflater.from(mContext).inflate(layoutResId, parent, false)
    }

    private fun createBaseViewHolder(view: View): BaseViewHolder {
        return BaseViewHolder(view)
    }

    /**
     * if addHeaderView will be return 1, if not will be return 0
     */
    private fun getHeaderLayoutCount(): Int {
        return if (mHeaderLayout == null || mHeaderLayout?.childCount == 0) 0 else 1
    }

    /**
     * if addFooterView will be return 1, if not will be return 0
     */
    private fun getFooterLayoutCount(): Int {
        return if (mFooterLayout == null || mFooterLayout?.childCount == 0) 0 else 1
    }

    fun addHeaderView(header: View) {
        addHeaderView(header, -1)
    }

    fun addHeaderView(header: View, index: Int) {
        addHeaderView(header, index, LinearLayout.VERTICAL)
    }

    fun addHeaderView(header: View, index: Int, orientation: Int): Int {
        if (mHeaderLayout == null) {
            mHeaderLayout = LinearLayout(header.context)
            when (orientation) {
                LinearLayout.VERTICAL -> {
                    mHeaderLayout?.orientation = orientation
                    mHeaderLayout?.layoutParams = LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                }
                LinearLayout.HORIZONTAL -> {
                    mHeaderLayout?.orientation = orientation
                    mHeaderLayout?.layoutParams = LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                }
            }
        }
        val childCount = mHeaderLayout?.childCount ?: 0
        val index = if (index < 0 || index > childCount) {
            childCount
        } else {
            index
        }
        mHeaderLayout?.addView(header, index)
        if (mHeaderLayout?.childCount == 1) {
            val position = getHeaderViewPosition()
            if (position != -1) {
                notifyItemInserted(position)
            }
        }
        return index
    }

    private fun getHeaderViewPosition(): Int {
//        if (getEmptyViewCount() == 1) {
//            if (mHeadAndEmptyEnable) {
//                return 0
//            }
//        }else{
//            return 0
//        }
//        return -1
        return 0
    }

    fun addFooterView(footer: View) {
        addFooterView(footer, -1)
    }

    fun addFooterView(footer: View, index: Int) {
        addFooterView(footer, index, LinearLayout.VERTICAL)
    }

    fun addFooterView(footer: View, index: Int, orientation: Int) {
        if (mFooterLayout == null) {
            mFooterLayout = LinearLayout(footer.context)
            mFooterLayout?.orientation = orientation
            mFooterLayout?.layoutParams = if (orientation == LinearLayout.VERTICAL) {
                LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            } else {
                LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT)
            }
        }
        val childCount = mFooterLayout?.childCount ?: 0
        val index = if (index < 0 || index > childCount) {
            childCount
        } else {
            index
        }
        mFooterLayout?.addView(footer, index)
        if (mFooterLayout?.childCount == 1) {
            val position = getFooterViewPosition()
            if (position != -1) {
                notifyItemInserted(position)
            }
        }
    }

    private fun getFooterViewPosition(): Int {
//        if (getEmptyViewCount() == 1) {
//            val position = 1
//
//        } else {
//            return getHeaderLayoutCount() + mData.size
//        }
//        return -1

        return getHeaderLayoutCount() + mData.size
    }

    private fun getEmptyViewCount(): Int {
        if (mEmptyLayout == null || mEmptyLayout?.childCount == 0) {
            return 0
        }
//        if (!mIsUseEmpty) {
//            return 0
//        }
        if (mData.isNotEmpty()) {
            return 0
        }
        return 1
    }

    interface RequestLoadMoreListener {
        fun onLoadMoreRequested()
    }

    abstract fun convert(holder: BaseViewHolder, t: T)
}