package com.huxh.treasurebox.baselib.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.text.TextUtils

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.huxh.treasurebox.BuildConfig
import com.huxh.treasurebox.baselib.AppContext
import com.huxh.treasurebox.baselib.base.data.BaseResponse
import com.huxh.treasurebox.baselib.storage.Preference
import com.google.gson.Gson
import java.io.File
import java.io.InputStream

fun <T> toJson(t: T): String {
    return Gson().toJson(t)
}

inline fun <reified T> fromJson(json: String): T {
    return Gson().fromJson<T>(json, T::class.java)
}

fun getStatusBarHeight(context: Context): Int {
    var result = 0
    val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
    if (resourceId > 0) {
        result = context.resources.getDimensionPixelSize(resourceId)
    }
    return result
}

fun isChinaLanguage(): Boolean {
    val language by Preference<String>(Constant.SP.LANGUAGE_STRING, "")
    return !TextUtils.equals("en", language)
}

fun isReleaseVersion(): Boolean {
    return TextUtils.equals(BuildConfig.BUILD_TYPE, "release")
}


/**
 * 复制内容到剪切板
 *
 * @param copyStr
 * @return
 */
fun copyToClipboard(context: Context, copyStr: String?): Boolean {
    return try {
        //获取剪贴板管理器
        val cm = context.getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager
        // 创建普通字符型ClipData
        val mClipData = ClipData.newPlainText("Label", copyStr)
        // 将ClipData内容放到系统剪贴板里。
        cm?.setPrimaryClip(mClipData)
        true
    } catch (e: Exception) {
        false
    }
}

fun callPhone(context: Context, phone: String) {
    context.startActivity(Intent(Intent.ACTION_DIAL).setData(Uri.parse("tel:$phone")))
}


val Float.px: Int
    get() {
        val scale = AppContext.resources.displayMetrics.density//获得当前屏幕密度
        return (this * scale + 0.5f).toInt()
    }

val Float.dp: Int
    get() {
        val scale = AppContext.resources.displayMetrics.density//获得当前屏幕密度
        return (this / scale + 0.5f).toInt()
    }

fun debug(block: (() -> Unit)) {
    if (BuildConfig.DEBUG) block.invoke()
}


fun InputStream.saveToFile(file: String) = use { input ->
    File(file).outputStream().use { output ->
        input.copyTo(output)
    }
}

fun <T> LiveData<BaseResponse<T>>.observeWithCheck(
    fragment: Fragment,
    owner: LifecycleOwner,
    block: (BaseResponse<T>) -> Unit,
) {
    observeWithCheck(fragment.requireContext(), owner, block)
}

/*fun <T> LiveData<BaseResponse<T>>.observeWithCheck(
    activity: Activity,
    owner: LifecycleOwner,
    block: (BaseResponse<T>) -> Unit,
) {
    observeWithCheck(activity, owner, block)
}*/

fun <T> LiveData<BaseResponse<T>>.observeWithCheck(
    context: Context,
    owner: LifecycleOwner,
    block: (BaseResponse<T>) -> Unit,
) {
    observe(owner) {
        checkCode(context, it)
        block.invoke(it)
    }
}

fun checkCode(context: Context, response: BaseResponse<*>?) {
    if (response == null) return
}

val activityManager = ActivityManager()