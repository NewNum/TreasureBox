package com.huxh.treasurebox.baselib.base.data


open class BaseResponse<T>(var data: T?) {

    companion object {
        const val SUCCESS = 0
    }

    var code: Int = -1
    var msg: String = ""
}