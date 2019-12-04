package com.demo.medimaging.bean

import androidx.annotation.Keep

/**
 * Create by jzhan at 2019-12-04
 *
 */
@Keep
class CommonBean {
    var title: String = ""
    var data = arrayListOf<CommonInnerBean>()
}