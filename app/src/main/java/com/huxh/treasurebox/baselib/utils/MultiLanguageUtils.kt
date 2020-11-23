package com.huxh.treasurebox.baselib.utils

import android.app.Activity
import android.app.Application.ActivityLifecycleCallbacks
import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.os.LocaleList
import android.text.TextUtils
import androidx.core.os.ConfigurationCompat
import androidx.core.os.LocaleListCompat
import java.util.*

/**
 * Todo 多语言设置
 * 来自：https://blog.csdn.net/m0_38074457/article/details/84993366
 * 使用步骤：
 * 1、Application中onCreate添加registerActivityLifecycleCallbacks(MultiLanguageUtils.callbacks);
 * @Override
 * protected void attachBaseContext(Context base) {
 * //系统语言等设置发生改变时会调用此方法，需要要重置app语言
 * super.attachBaseContext(MultiLanguageUtils.attachBaseContext(base));
 * }
 * 2、改变应用语言调用MultiLanguageUtils.changeLanguage(activity,type,type);
 */
//public final static String SP_LANGUAGE="SP_LANGUAGE";
//public final static String SP_COUNTRY="SP_COUNTRY";
object MultiLanguageUtils {
    /**
     * TODO 1、 修改应用内语言设置
     * @param language    语言  zh/en
     * @param area      地区
     */
    fun changeLanguage(context: Context, language: String?, area: String?) {
        if (TextUtils.isEmpty(language) && TextUtils.isEmpty(area)) {
            //如果语言和地区都是空，那么跟随系统s
            val sp = SPUtil(context)
            sp.putString(Constant.SP.LANGUAGE_STRING, "")
            sp.putString(Constant.SP.COUNTRY, "")
        } else {
            //不为空，那么修改app语言，并true是把语言信息保存到sp中，false是不保存到sp中
            val newLocale = Locale(language, area)
            changeAppLanguage(context, newLocale, true)
        }
        // 重启应用
//        AppManager.getAppManager().finishAllActivity();
//        IntentUtils.toActivity(context,MainActivity.Class,true);
    }

    /**
     * TODO 2、更改应用语言
     * @param locale      语言地区
     * @param persistence 是否持久化
     */
    fun changeAppLanguage(context: Context, locale: Locale, persistence: Boolean) {
        val resources = context.resources
        val metrics = resources.displayMetrics
        val configuration = resources.configuration
        setLanguage(context, locale, configuration)
        resources.updateConfiguration(configuration, metrics)
        if (persistence) {
            saveLanguageSetting(context, locale)
        }
    }

    private fun setLanguage(context: Context, locale: Locale?, configuration: Configuration) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            configuration.setLocale(locale)
            configuration.setLocales(LocaleList(locale))
            context.createConfigurationContext(configuration)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLocale(locale)
        } else {
            configuration.locale = locale
        }
    }

    /**
     * TODO 3、 跟随系统语言
     */
    fun attachBaseContext(context: Context): Context {
        val sp = SPUtil(context)
        val spLanguage = sp.getString(Constant.SP.LANGUAGE_STRING, "")
        val spCountry = sp.getString(Constant.SP.COUNTRY, "")
        val resources = context.resources
        val dm = resources.displayMetrics
        val configuration = resources.configuration
        val appLocale = getAppLocale(context)
        //如果本地有语言信息，以本地为主，如果本地没有使用默认Locale
        var locale: Locale? = null
        locale = if (!TextUtils.isEmpty(spLanguage) && !TextUtils.isEmpty(spCountry)) {
            if (isSameLocal(appLocale, spLanguage!!, spCountry!!)) {
                appLocale
            } else {
                Locale(spLanguage, spCountry)
            }
        } else {
            appLocale
        }
        setLanguage(context, locale, configuration)
        resources.updateConfiguration(configuration, dm)
        return context
    }

    /**
     * 判断sp中和app中的多语言信息是否相同
     */
    fun isSameWithSetting(context: Context): Boolean {
        val locale = getAppLocale(context)
        val language = locale.language
        val country = locale.country
        val sp = SPUtil(context)
        val sp_language = sp.getString(Constant.SP.LANGUAGE_STRING, "")
        val sp_country = sp.getString(Constant.SP.COUNTRY, "")
        return language == sp_language && country == sp_country
    }

    /**
     * 判断应用于系统语言是否相同
     */
    fun isSameLocal(appLocale: Locale, sp_language: String, sp_country: String): Boolean {
        val appLanguage = appLocale.language
        val appCountry = appLocale.country
        return appLanguage == sp_language && appCountry == sp_country
    }

    /**
     * 保存多语言信息到sp中
     */
    fun saveLanguageSetting(context: Context, locale: Locale) {
        val sp = SPUtil(context)
        sp.putString(Constant.SP.LANGUAGE_STRING, locale.language)
        sp.putString(Constant.SP.COUNTRY, locale.country)
    }

    /**
     * 获取应用语言
     */
    fun getAppLocale(context: Context): Locale {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            context.resources.configuration.locales[0]
        } else {
            context.resources.configuration.locale
        }
    }

    /**
     * 获取系统语言
     */
    val systemLanguage: LocaleListCompat
        get() {
            val configuration = Resources.getSystem().configuration
            return ConfigurationCompat.getLocales(configuration)
        }

    //注册Activity生命周期监听回调，此部分一定加上，因为有些版本不加的话多语言切换不回来
    //registerActivityLifecycleCallbacks(callbacks);
    var callbacks: ActivityLifecycleCallbacks = object : ActivityLifecycleCallbacks {
        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            val sp = SPUtil(activity)
            val language = sp.getString(Constant.SP.LANGUAGE_STRING, "")
            val country = sp.getString(Constant.SP.COUNTRY, "")
            if (!TextUtils.isEmpty(language) && !TextUtils.isEmpty(country)) {
                //强制修改应用语言
                if (!isSameWithSetting(activity)) {
                    val locale = Locale(language, country)
                    changeAppLanguage(activity, locale, false)
                }
            }
        }

        override fun onActivityStarted(activity: Activity) {}
        override fun onActivityResumed(activity: Activity) {}
        override fun onActivityPaused(activity: Activity) {}
        override fun onActivityStopped(activity: Activity) {}
        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
        override fun onActivityDestroyed(activity: Activity) {}
    }
}