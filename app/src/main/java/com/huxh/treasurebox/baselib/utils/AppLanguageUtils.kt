package com.huxh.treasurebox.baselib.utils

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.os.LocaleList
import android.text.TextUtils
import java.util.*

/**
 * @author YanLu
 * @since 17/5/12
 */
object AppLanguageUtils {
    private val mAllLanguages by lazy {
        arrayListOf<Locale>(
            Locale.CHINESE,
            Locale.ENGLISH
        )
    }

    fun changeAppLanguage(context: Context, newLanguage: Int): Context {
        val resources = context.resources
        val configuration = resources.configuration

        // app locale
        val locale = getLocaleByLanguage(newLanguage)

        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.N -> {
                configuration.setLocales(LocaleList(locale))
            }
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 -> {
                configuration.setLocale(locale)
            }
            else -> {
                configuration.locale = locale
            }
        }

        // updateConfiguration
        return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N_MR1) {
            val dm = resources.displayMetrics
            resources.updateConfiguration(configuration, dm)
            context
        } else {
            context.createConfigurationContext(configuration)
        }
    }

    private fun isSupportLanguage(language: Int): Boolean {
        return language < mAllLanguages.size
    }

    fun getSupportLanguage(language: Int): Int {
        return if (isSupportLanguage(language)) {
            language
        } else 0
    }

    /**
     * 获取指定语言的locale信息，如果指定语言不存在[.mAllLanguages]，返回本机语言，如果本机语言不是语言集合中的一种[.mAllLanguages]，返回英语
     *
     * @param language language
     * @return
     */
    fun getLocaleByLanguage(language: Int): Locale {
        return if (isSupportLanguage(language)) {
            mAllLanguages[language]
        } else {
            Locale.CHINESE
        }
    }

    fun attachBaseContext(context: Context, language: Int): Context {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            updateResources(context, language)
        } else {
            context
        }
    }

    @TargetApi(Build.VERSION_CODES.N)
    private fun updateResources(context: Context, language: Int): Context {
        val resources = context.resources
        val locale = getLocaleByLanguage(language)
        val configuration = resources.configuration
        configuration.setLocale(locale)
        configuration.setLocales(LocaleList(locale))
        return context.createConfigurationContext(configuration)
    }
}