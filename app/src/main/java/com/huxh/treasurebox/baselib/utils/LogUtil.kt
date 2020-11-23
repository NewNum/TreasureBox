package com.huxh.treasurebox.baselib.utils

import android.content.Intent
import android.util.Log
import com.huxh.treasurebox.baselib.TAG
import java.lang.StringBuilder
import java.util.*

object LogUtil {

    fun d(info: String) {
        if (!isReleaseVersion()) {
            show(info)
        }
    }

    fun releaseLog(info: String) {
        Log.d(TAG, info)
    }

    fun logTime(msg: String) {
        val isReleaseVersion = isReleaseVersion()
        if (!isReleaseVersion) {
            val calendar = Calendar.getInstance()
            val hour: Int = calendar.get(Calendar.HOUR)
            val minute: Int = calendar.get(Calendar.MINUTE)
            val second: Int = calendar.get(Calendar.SECOND)
            val millisecond: Int = calendar.get(Calendar.MILLISECOND)
            d("current_time : ${hour}点${minute}分${second}秒${millisecond}毫秒; msg =${msg}")
        }
    }

    fun logIntent(intent: Intent) {
        val bundle = intent.extras
        if (bundle?.keySet() == null) {
            return
        }
        val sb = StringBuilder()
        sb.append("intent:[")
        for (key in bundle.keySet()) {
            sb.append("Key=${key},content=${bundle.get(key)};")
        }
        sb.append("]")
        d(sb.toString())
    }

    // 使用Log来显示调试信息,因为log在实现上每个message有4k字符长度限制
    // 所以这里使用自己分节的方式来输出足够长度的message
    private fun show(str: String) {
        val string = str.trim { it <= ' ' }
        var index = 0
        val maxLength = 3000
        while (index < string.length) {
            // java的字符不允许指定超过总的长度end
            Log.d(TAG, if (string.length <= index + maxLength) {
                string.substring(index)
            } else {
                string.substring(index, index + maxLength)
            }.trim { it <= ' ' })
            index += maxLength
        }
    }
}