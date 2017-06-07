package com.jskierbi.architecturecomponents

import android.arch.lifecycle.ViewModel
import com.jskierbi.architecturecomponents.modules.user_service.BookModel
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposables
import io.reactivex.processors.BehaviorProcessor
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * Created by jakub on 07.06.17.
 */
class AsyncOpRegistryViewModel : ViewModel() {

  private val bookProcessor = BehaviorProcessor.create<BookModel>()
  val bookFlowable: Flowable<BookModel> = bookProcessor

  private var loadDataDisposable = Disposables.empty()!!

  private var internalParam: String? = null
  fun loadData(param: String): Flowable<BookModel> {
    if (this.internalParam != param) { // No fucking way!!!!
      this.internalParam = param
      loadDataDisposable.dispose()
      loadDataDisposable = Flowable.interval(1, TimeUnit.SECONDS)
        .map { BookModel("$param#$it", "Author") }
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
          { next -> bookProcessor.onNext(next) },
          { error -> bookProcessor.onError(error) },
          { bookProcessor.onComplete() }
        )
    }
    return bookFlowable
  }

  fun cancel() {
    loadDataDisposable.dispose()
  }

  override fun onCleared() {
    loadDataDisposable.dispose()
  }
}