package com.lnkj.library_base.exception

import com.lnkj.library_base.base.BaseBean
import com.mufeng.mvvmlib.http.ApiException

/**
 * 业务级异常
 * @constructor
 */
class BusinessException : ApiException {
    constructor(bean: BaseBean<*>) : super(bean.code, Exception(bean.msg)){
        displayMessage = bean.msg
    }
    constructor(code: Int, msg: String) : super(code, Exception(msg)){
        displayMessage = msg
    }
}