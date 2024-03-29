package com.mufeng.mvvmlib.basic

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mufeng.mvvmlib.http.ErrorHandler.Companion.handlerException
import com.mufeng.mvvmlib.http.handler.RequestViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * @创建者 MuFeng-T
 * @创建时间 2019/10/14 22:20
 * @描述
 */

open class BasicViewModel : RequestViewModel() {

    open val apiException: MutableLiveData<Event<Throwable>> = MutableLiveData()
    open val apiLoading: MutableLiveData<Event<Boolean>> = MutableLiveData()

    /**
     * 界面事件处理
     */
    private val _uiChange = MutableLiveData<Event<UIChange>>()
    val uiChange: LiveData<Event<UIChange>>
        get() = _uiChange

    /**
     * 吐司一条信息
     * @param msg String
     */
    protected fun toastEvent(msg: String) {
        _uiChange.value = Event(UIChange.ToastEvent(msg))
    }

    /**
     * 结束界面
     */
    fun finishEvent() {
        _uiChange.value = Event(UIChange.FinishEvent)
    }

    /**
     * 界面跳转
     * @param clzz Class<out AppCompatActivity> 目标Activity
     * @param isFinished Boolean    是否结束当前界面
     * @param params Array<out Pair<String, Any?>> 传递参数
     */
    fun startActivity(
            clzz: Class<out AppCompatActivity>,
            isFinished: Boolean = false,
            vararg params: Pair<String, Any?>
    ) {
        _uiChange.value = Event(UIChange.IntentEvent(clzz, isFinished, params))
    }

    override fun onApiStart() {
        apiLoading.value = Event(true)
    }

    override fun onApiError(e: Throwable?) {
        apiLoading.value = Event(false)
        val exception = handlerException(e)
        apiException.value = Event(exception)
    }

    override fun onApiFinally() {
        apiLoading.value = Event(false)
    }

    fun launch(
        tryBlock: suspend CoroutineScope.() -> Unit,
        catchBlock: suspend CoroutineScope.(Throwable) -> Unit = {},
        finallyBlock: suspend CoroutineScope.() -> Unit = {}
    ){
        viewModelScope.launch(Dispatchers.Main){
            try {
                tryBlock()
            } catch (e: Exception) {
                catchBlock(e)
            } finally {
                finallyBlock()
            }
        }
    }

}