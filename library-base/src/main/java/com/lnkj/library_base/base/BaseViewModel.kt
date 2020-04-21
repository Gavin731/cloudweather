package com.lnkj.library_base.base

import com.lnkj.library_base.exception.BusinessException
import com.mufeng.mvvmlib.basic.BasicViewModel
import com.mufeng.mvvmlib.basic.Event

/**
 * @创建者 MuFeng-T
 * @创建时间 2020/3/17 9:11
 * @描述
 */

open class BaseViewModel: BasicViewModel() {

    fun<T> BasicViewModel.request(apiDSL: DslViewModel<T>.() -> Unit){
        val dsl = DslViewModel<T>()
        apiDSL<BaseBean<T>> {
            onStart {
                val override = dsl.apply(apiDSL).onStart?.invoke()
                if (override == null || !override) {
                    onApiStart()
                }
                override
            }
            onRequest {
                dsl.apply(apiDSL).request()
            }
            onResponse {
                if (it.isSuccess()) {
                    dsl.apply(apiDSL).onResponse?.invoke(it.data)
                }else{

                    apiException.value = Event(BusinessException(it))
                }
            }

            onError {error ->
                val override = dsl.apply(apiDSL).onError?.invoke(error)
                if (override == null || !override) {
                    onApiError(error)
                }
                override
            }

            onFinally {
                val override = dsl.apply(apiDSL).onFinally?.invoke()
                if (override == null || !override) {
                    onApiFinally()
                }
                override
            }

        }
    }

    class DslViewModel<T>{
        internal lateinit var request: suspend () -> BaseBean<T>

        internal var onStart: (() -> Boolean?)? = null

        internal var onResponse: ((T?) -> Unit)? = null

        internal var onError: ((Exception) -> Boolean?)? = null

        internal var onFinally: (() -> Boolean?)? = null

        infix fun onStart(onStart: (() -> Boolean?)?) {
            this.onStart = onStart
        }

        infix fun onRequest(request: suspend () -> BaseBean<T>) {
            this.request = request
        }

        infix fun onResponse(onResponse: ((T?) -> Unit)?) {
            this.onResponse = onResponse
        }

        infix fun onError(onError: ((Exception) -> Boolean?)?) {
            this.onError = onError
        }

        infix fun onFinally(onFinally: (() -> Boolean?)?) {
            this.onFinally = onFinally
        }
    }
}