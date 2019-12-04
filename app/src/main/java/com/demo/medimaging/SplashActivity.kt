package com.demo.medimaging

import android.content.Intent
import com.hello.lib_net.base.BaseActivity

/**
 * Create by jzhan at 2019-12-04
 *
 */
class SplashActivity:BaseActivity() {
    override fun initView() {
        val thread = Thread(Runnable {
            Thread.sleep(1000)
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        })
        thread.start()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_splash
    }

}