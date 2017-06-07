package com.jskierbi.architecturecomponents.modules.user_service

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent
import com.jskierbi.commons.logd

/**
 * Created by jakub on 07.06.17.
 */
class UserPresenceService(lifecycleOwner: LifecycleOwner) : LifecycleObserver {

  private val lifecycle = lifecycleOwner.lifecycle.apply {
    addObserver(this@UserPresenceService)
  }

  @OnLifecycleEvent(Lifecycle.Event.ON_START)
  fun onStart() {
    logd(">>> Manager: onStart()")
  }

  @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
  fun onStop() {
    logd(">>> Managerr: onStop()")
  }

  fun checkState() {
    logd("Current state: ${lifecycle.currentState}")
  }

  @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
  fun onDestroy() {
    logd(">>> Manager: onDestroy()")
  }
}
