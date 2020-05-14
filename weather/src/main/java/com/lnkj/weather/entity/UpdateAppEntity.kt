package com.lnkj.weather.entity

import com.google.gson.annotations.SerializedName

/**
 * @ClassName: UpdateAppEntity
 * @Description:
 * @Author: Pekon
 * @CreateDate: 2020/5/14 11:01
 * @UpdateUser: 更新者：
 * @UpdateDate: 2020/5/14 11:01
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
data class UpdateAppEntity(
    @SerializedName("old_version")
    var oldVersion: String?,
    var version: String?,
    var url: String?
)