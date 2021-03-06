package com.huxh.treasurebox.baselib.base.viewmodel

import androidx.lifecycle.*
import com.huxh.treasurebox.baselib.utils.LogUtil
import com.tencent.smtt.export.external.interfaces.NetworkException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.io.IOException

open class BaseViewModel : ViewModel() {

    val mStateLiveData = MutableLiveData<StateActionEvent>()//通用事件模型驱动(如：显示对话框、取消对话框、错误提示)

    fun launch(block: suspend CoroutineScope.() -> Unit) {//使用协程封装统一的网络请求处理
        viewModelScope.launch {
            //ViewModel自带的viewModelScope.launch,会在页面销毁的时候自动取消请求,有效封装内存泄露
            try {
                mStateLiveData.value = LoadState
                block()
                mStateLiveData.value = SuccessState
            } catch (e: Exception) {
                LogUtil.d(e.message ?: e.javaClass.simpleName)
                mStateLiveData.value = ErrorState(
                    if (e is IOException) {
                        NET_WORK_EXCEPTION
                    } else {
                        UNKNOWN_EXCEPTION
                    }
                )
            }
        }
    }

    fun <T> emit(block: suspend LiveDataScope<T>.() -> T): LiveData<T> = liveData {
        try {
            mStateLiveData.value = LoadState
            emit(block())
            mStateLiveData.value = SuccessState
        } catch (e: Exception) {
            LogUtil.d(e.message ?: e.javaClass.simpleName)
            mStateLiveData.value = ErrorState(
                if (e is IOException) {
                    NET_WORK_EXCEPTION
                } else {
                    UNKNOWN_EXCEPTION
                }
            )
        }
    }

    companion object {
        const val NET_WORK_EXCEPTION = "NET_WORK_EXCEPTION"

        const val UNKNOWN_EXCEPTION = "UNKNOWN_EXCEPTION"

    }
}