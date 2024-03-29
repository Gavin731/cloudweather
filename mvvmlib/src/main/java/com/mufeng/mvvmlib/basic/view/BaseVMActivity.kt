package com.mufeng.mvvmlib.basic.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.gyf.immersionbar.ImmersionBar
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.impl.LoadingPopupView
import com.lxj.xpopup.interfaces.SimpleCallback
import com.mufeng.mvvmlib.basic.BasicViewModel
import com.mufeng.mvvmlib.basic.UIChange
import com.mufeng.mvvmlib.basic.eventObserver
import com.mufeng.mvvmlib.http.ApiException
import com.mufeng.mvvmlib.util.WeatherDayTimeManager
import com.mufeng.mvvmlib.utilcode.ext.fillIntentArguments
import com.mufeng.mvvmlib.utilcode.utils.toast
import com.mufeng.mvvmlib.widget.StatefulLayout

/**
 * @创建者 MuFeng-T
 * @创建时间 2019/10/14 22:39
 * @描述
 */
abstract class BaseVMActivity<VM : BasicViewModel, VB : ViewDataBinding> : AppCompatActivity() {

    protected lateinit var binding: VB

    abstract val viewModel: VM
    abstract val layoutResId: Int

    lateinit var loadingView: LoadingPopupView
    var statefulLayout: StatefulLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ImmersionBar.with(this)
            .statusBarDarkFont(WeatherDayTimeManager.instance.getIsDayTime(), 0.2f).init()

        binding = DataBindingUtil.setContentView(this, layoutResId)
        startObserve()
        binding.lifecycleOwner = this
        loadingView = XPopup.Builder(this)
                .dismissOnTouchOutside(false)
                .setPopupCallback(SimpleCallback())
                .hasShadowBg(false)
                .asLoading()

        initView(savedInstanceState)
        initData()
    }

    open fun startObserve() {
        viewModel.apply {

            uiChange.eventObserver(this@BaseVMActivity) {
                when (it) {
                    is UIChange.ToastEvent -> toast(it.msg)
                    is UIChange.FinishEvent -> this@BaseVMActivity.finish()
                    is UIChange.IntentEvent -> startActivity(it)
                }
            }

            apiException.eventObserver(this@BaseVMActivity) {
                if (it is ApiException) {
                    onError(it)
                }
            }

            apiLoading.eventObserver(this@BaseVMActivity) {
                if (it) {
                    showLoading()
                } else {
                    hideLoading()
                }
            }

        }
    }

    open fun showLoading() {
        loadingView.show()
    }

    open fun hideLoading() {
        loadingView.dismiss()
    }

    private fun startActivity(intentEvent: UIChange.IntentEvent) {
        val intent = Intent(this@BaseVMActivity, intentEvent.clzz)
        if (intentEvent.params.isNotEmpty()) {
            intent.fillIntentArguments(*intentEvent.params)
        }
        startActivity(intent)
        if (intentEvent.isFinished) {
            finish()
        }
    }

    abstract fun initView(savedInstanceState: Bundle?)

    abstract fun initData()

    open fun onError(e: ApiException) {
        toast(e.displayMessage)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.unbind()
    }

}