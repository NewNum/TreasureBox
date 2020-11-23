package com.huxh.treasurebox.baselib.utils

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle

class ActivityManager : Application.ActivityLifecycleCallbacks {

    private val activitys = mutableListOf<Activity>()

    private var isEdit = false

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        activitys.add(activity)
    }

    override fun onActivityStarted(activity: Activity) {}

    override fun onActivityResumed(activity: Activity) {}

    override fun onActivityPaused(activity: Activity) {}

    override fun onActivityStopped(activity: Activity) {}

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}

    override fun onActivityDestroyed(activity: Activity) {
        if (!isEdit) {
            activitys.remove(activity)
        }
    }

    fun startToNewActivity(context: Context, clazz: Class<out Activity>?) {
        isEdit = true
        for (activity in activitys) {
            activity.finish()
        }
        activitys.clear()
        isEdit = false
        if (clazz != null) {
            context.startActivity(Intent(context, clazz).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            })
        }
    }

}