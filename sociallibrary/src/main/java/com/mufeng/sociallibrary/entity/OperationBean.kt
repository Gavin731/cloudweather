package com.mufeng.sociallibrary.entity

import android.content.Context
import com.mufeng.sociallibrary.callback.OperationCallback
import com.mufeng.sociallibrary.config.OperationType
import com.mufeng.sociallibrary.config.PlatformType
import com.mufeng.sociallibrary.entity.content.OperationContent

/**
 * description: 第三方平台操作的实体
 *@date 2019/7/15
 *@author: yzy.
 */
data class OperationBean(
  var operationContext: Context,        // 操作上下文
  var operationPlat: PlatformType,      // 平台类型
  var operationType: OperationType,     // 操作类型
  var operationCallback: OperationCallback,   // 回调
  var operationContent: OperationContent? = null  // 平台内容
)