package com.lnkj.weather

import com.billy.cc.core.component.CC
import com.billy.cc.core.component.CCResult
import com.billy.cc.core.component.CCUtil
import com.billy.cc.core.component.IComponent
import com.lnkj.library_base.router.WeatherComponent.WEATHER_ACTION_OPEN_MAIN_ACTIVITY
import com.lnkj.library_base.router.WeatherComponent.WEATHER_ACTION_OPEN_PRIVACY_POLICY
import com.lnkj.library_base.router.WeatherComponent.WEATHER_ACTION_OPEN_USER_AGREEMENT
import com.lnkj.library_base.router.WeatherComponent.WEATHER_COMPONENT_NAME
import com.lnkj.weather.ui.main.MainActivity
import com.lnkj.weather.ui.settings.PrivacyPolicyActivity

/**
 * @创建者 MuFeng-T
 * @创建时间 2020/3/13 21:25
 * @描述
 */
class ModuleComponent : IComponent {
    override fun onCall(cc: CC?): Boolean {
        when (cc?.actionName) {
            WEATHER_ACTION_OPEN_MAIN_ACTIVITY -> {
                openMainActivity(cc)
            }
            WEATHER_ACTION_OPEN_PRIVACY_POLICY -> {
                openPrivacyPolicyActivity(cc)
            }
            WEATHER_ACTION_OPEN_USER_AGREEMENT -> {
                openUserAgreementActivity(cc)
            }
        }
        return false
    }

    private fun openMainActivity(cc: CC) {
        CCUtil.navigateTo(cc, MainActivity::class.java)
        CC.sendCCResult(cc.callId, CCResult.success())
    }

    private fun openPrivacyPolicyActivity(cc: CC) {
        CCUtil.navigateTo(cc, PrivacyPolicyActivity::class.java)
        CC.sendCCResult(cc.callId, CCResult.success())
    }

    private fun openUserAgreementActivity(cc: CC) {
        CCUtil.navigateTo(cc, PrivacyPolicyActivity::class.java)
        CC.sendCCResult(cc.callId, CCResult.success())

    }

    override fun getName(): String {
        return WEATHER_COMPONENT_NAME
    }
}