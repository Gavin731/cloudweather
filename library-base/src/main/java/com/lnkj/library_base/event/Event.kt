package com.lnkj.library_base.event

/**
 * @创建者 MuFeng-T
 * @创建时间 2020/3/25 14:19
 * @描述
 */
object EventKey {
    /**
     * 关闭语音播报
     */
    const val EVENT_STOP_VOICE_ANNOUNCEMENTS = "event_stop_voice_announcements"
    /**
     * 选择或添加城市
     */
    const val EVENT_CHOOSE_ACC_CITY = "event_choose_acc_city"
    /**
     * 删除城市
     */
    const val EVENT_CHOOSE_REMOVE_CITY = "event_choose_remove_city"

    /**
     * 切换城市
     */
    const val EVENT_CHANGE_CITY = "event_change_city"

    /**
     * 传递 切换到小时详情的第几个
     */
    const val EVENT_CHOOSE_HOUR_DETAILS_INDEX = "EVENT_CHOOSE_HOUR_DETAILS_INDEX"

    /**
     * 切换自动定位状态
     */
    const val EVENT_CHANGE_AUTO_LOCATION_STATUS = "event_change_auto_location_status"

    /**
     * 空气质量分享
     */
    const val EVENT_AIR_SHARE = "EVENT_AIR_SHARE"
    /**
     * 分钟改变通知
     */
    const val EVENT_MINUTE_CHANGE = "EVENT_MINUTE_CHANGE"
}