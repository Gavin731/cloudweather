package com.mufeng.sociallibrary.entity.platform

import com.mufeng.sociallibrary.config.PlatformType

/**
 * description:支付宝的配置实体类
 *@date 2019/7/15
 *@author: yzy.
 */
data class AliPlatConfigBean (
  override val name: PlatformType, // 平台类型
  override var appkey:String?
): PlatformConfig