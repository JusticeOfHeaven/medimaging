package com.demo.medimaging.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.demo.medimaging.R

/**
 * Create by jzhan at 2019-12-04
 *
 */
class IndicatorView : View {
    // 圆之间的间隙
    private var gap: Int = 14
    //半径
    private var mRadius = 5f
    private var circlePaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    // 选中的position
    private var mSelectPosition = 0
    // 总数
    private var mCount = 3

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        setWillNotDraw(false)

        circlePaint.style = Paint.Style.FILL
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        for (i in 0 until mCount) {
            if (i == mSelectPosition) {
                circlePaint.color = Color.parseColor("#2D9CDB")
            } else {
                circlePaint.color = Color.parseColor("#C4C4C4")
            }
            if (mCount % 2 == 0) {
                // 是偶数
                canvas.drawCircle(
                    measuredWidth / 2f - (mRadius * 2 + gap) * (mCount / 2 - i) + mRadius + gap / 2,
                    measuredHeight / 2f,
                    mRadius,
                    circlePaint
                )
            } else {
                // 是奇数
                canvas.drawCircle(
                    measuredWidth / 2f - (mRadius * 2 + gap) * ((mCount - 1) / 2 - i),
                    measuredHeight / 2f,
                    mRadius,
                    circlePaint
                )
            }
        }

    }

    fun bindViewPager(viewPager: ViewPager, fragments: List<Fragment>,fm: FragmentManager) {
        mCount = fragments.size

        viewPager.adapter = object : FragmentPagerAdapter(fm) {
            override fun getItem(position: Int): Fragment {
                return fragments[position]
            }

            override fun getCount(): Int {
                return mCount
            }

        }

        viewPager.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                mSelectPosition = position
                invalidate()
            }
        })
    }
}