package com.hello.lib_net.base

import android.content.ComponentCallbacks
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * Create by jzhan on 2019/7/11
 */
abstract class BaseActivity : AppCompatActivity(){

    companion object {
        @JvmStatic
        private var sNoncompatDensity: Float = 0f
        @JvmStatic
        private var sNoncompatScaleDensity: Float = 0f
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        setCustomDensity()
        initView()
    }

    abstract fun initView()

    abstract fun getLayoutId(): Int

    private fun setCustomDensity() {
        val appDisplayMeyrics = resources.displayMetrics
        if (sNoncompatDensity == 0f) {
            sNoncompatDensity = appDisplayMeyrics.density
            sNoncompatScaleDensity = appDisplayMeyrics.scaledDensity

            registerComponentCallbacks(object : ComponentCallbacks {
                override fun onLowMemory() {

                }

                override fun onConfigurationChanged(newConfig: Configuration?) {
                    if (newConfig != null && newConfig.fontScale > 0) {
                        sNoncompatScaleDensity = resources.displayMetrics.scaledDensity
                    }
                }

            })

            val targetDensity = appDisplayMeyrics.widthPixels / 360f
            val targetScaleDensity = targetDensity * (sNoncompatScaleDensity / sNoncompatDensity)
            val targetDensityDpi = (160 * targetDensity).toInt()

            appDisplayMeyrics.density = targetDensity
            appDisplayMeyrics.scaledDensity = targetScaleDensity
            appDisplayMeyrics.densityDpi = targetDensityDpi

        }
    }

}