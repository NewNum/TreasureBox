package com.huxh.treasurebox.baselib

import android.app.Application
import android.content.ContextWrapper
import com.huxh.treasurebox.BuildConfig
import com.huxh.treasurebox.baselib.utils.*
import com.tencent.bugly.crashreport.CrashReport
import com.tencent.smtt.sdk.QbSdk

lateinit var mApplication: Application

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        mApplication = this
        registerActivityLifecycleCallbacks(activityManager)
        CrashReport.initCrashReport(applicationContext, "0591bae31b", BuildConfig.DEBUG);
        //多语言设置
        registerActivityLifecycleCallbacks(MultiLanguageUtils.callbacks)
        //x5内核初始化接口
        QbSdk.initX5Environment(applicationContext, object : QbSdk.PreInitCallback {
            override fun onViewInitFinished(arg0: Boolean) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                LogUtil.d(" onViewInitFinished is $arg0")
            }

            override fun onCoreInitFinished() {}
        })
    }

}


object AppContext : ContextWrapper(mApplication)//ContextWrapper对Context上下文进行包装(装饰者模式)

const val TAG = "Box"