package com.jskierbi.commons

import android.util.Log

/**
 * Created by q on 01/10/16.
 */
fun Any.logd(msg: String) = Log.d(getTag(), msg).unit

fun Any.logd(msg: String, throwable: Throwable) = Log.d(getTag(), msg, throwable).unit

fun Any.logw(msg: String) = Log.w(getTag(), msg).unit
fun Any.logw(throwable: Throwable) = Log.w(getTag(), throwable.message, throwable).unit
fun Any.logw(msg: String, throwable: Throwable) = Log.w(getTag(), msg, throwable).unit

fun Any.loge(msg: String) = Log.e(getTag(), msg).unit
fun Any.loge(throwable: Throwable) = Log.e(getTag(), throwable.message, throwable).unit
fun Any.loge(msg: String, throwable: Throwable) = Log.e(getTag(), msg, throwable).unit

private fun getTag() = Thread.currentThread().stackTrace.let { stack ->
  "(${stack[4].fileName}:${stack[4].lineNumber}) ${stack[4].methodName}()"
}

val Any?.unit: Unit get() = Unit