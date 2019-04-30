package com.common.basic

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import com.common.utils.DisplayManager
import com.common.utils.ScreenAdapter
import kotlin.properties.Delegates

/**
 * description:
 * author:  周昇辰
 * date:2019/4/1
 */

open class BaseApplication : Application() {

    companion object {
        var context:Context by Delegates.notNull()
    }
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
    override fun onCreate() {
        super.onCreate()
        context=applicationContext
        ScreenAdapter.initAppDensity(this)
        DisplayManager.init(this)
    }
}