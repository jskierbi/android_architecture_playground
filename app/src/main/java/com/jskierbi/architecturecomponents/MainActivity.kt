package com.jskierbi.architecturecomponents

import android.arch.lifecycle.*
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v7.app.AlertDialog
import com.jskierbi.architecturecomponents.ui.UserActivity
import com.jskierbi.commons.logd
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.disposables.Disposables
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

fun <T> Flowable<T>.subscribe(dispose: Lifecycle.Event,
                              next: (T) -> Unit,
                              error: (Throwable) -> Unit = {},
                              complete: () -> Unit = {}) {

}

class MainActivity : LifecycleActivity() {

//  val svc = UserPresenceService(this)

  //  var loadDataDisposable = Disposables.empty()!!
  var dialogDisposable = Disposables.empty()!!
  var loadDataDisposable = Disposables.empty()!!

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    viewModel<DataViewModel>().apply {
      if (param == null) param = "Default value"
      param?.let { subscribeLoadData(it) }
    }
    uiBtnLoadData1.setOnClickListener { subscribeLoadData("Param 1") }
    uiBtnLoadData2.setOnClickListener { subscribeLoadData("Param 2") }
    uiRoomPlayground.setOnClickListener { startActivity(Intent(applicationContext, UserActivity::class.java)) }
  }

  override fun onDestroy() {
    dialogDisposable.dispose()
    loadDataDisposable.dispose()
    super.onDestroy()
  }

  fun subscribeLoadData(param: String) {
    // Show spinner
    viewModel<DataViewModel>().param = param
    loadDataDisposable.dispose()
    loadDataDisposable = viewModel<AsyncOpRegistryViewModel>().loadData(param)
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe({ next ->
        // Hide spinner
        logd(">>> Got next emission! $next")
        uiTextView.text = next.name
      }, { error ->
        // Hide spinner
        val dialog = AlertDialog.Builder(this).apply {
          title = "Fuckup!"
          setOnCancelListener { finish() }
          setPositiveButton("Retry") { _, _ -> subscribeLoadData(param) }
          setNegativeButton("Cancel") { _, _ -> finish() }
        }.show()
        dialogDisposable.dispose()
        dialogDisposable = Disposables.fromAction { dialog.dismiss() }
      })
//      .disposeOn(this, Lifecycle.Event.ON_STOP) // Be careful!!!
  }
}

inline fun <reified T : ViewModel> FragmentActivity.viewModel() = ViewModelProviders.of(this).get(T::class.java)

fun Disposable.disposeOn(lifecycleOwner: LifecycleOwner, event: Lifecycle.Event) {
  lifecycleOwner.lifecycle.addObserver(object : LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun disposeOnStop() {
      if (event == Lifecycle.Event.ON_STOP) dispose()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun disposeOnDestroy() {
      if (event == Lifecycle.Event.ON_DESTROY) dispose()
    }
  })
}
