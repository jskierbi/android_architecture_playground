package com.jskierbi.architecturecomponents.modules.user

import com.jskierbi.architecturecomponents.ArchitectureComponentsApplication
import com.jskierbi.commons.logd
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory
import java.sql.SQLIntegrityConstraintViolationException

class UserManager(val application: ArchitectureComponentsApplication) {

  private val retrofit: Retrofit = Retrofit.Builder()
      .baseUrl("https://private-ec18c-lemmings.apiary-mock.com")
      .addConverterFactory(JacksonConverterFactory.create())
      .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
      .build()
  private val userService: UserApiService = retrofit.create(UserApiService::class.java)
  val userDao = application.exampleDatabase.userDao()

  fun findAllUsers(): Flowable<List<User>> {
    return userDao.findAllUsers()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
  }

  fun persistUser(user: User) {
    Single.create<Long> {
      userDao.insertUser(user)
    }.subscribeOn(Schedulers.io())
        .subscribe()
  }

  fun persistUsers(users: MutableList<User>) {
    try {
      userDao.insertUsers(users)
    } catch (exception: SQLIntegrityConstraintViolationException) {
      userDao.updateUsers(users)
    }
  }

  fun getUsers(): Single<MutableList<User>> {
    return userService.getUsers()
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.io())
        .doOnNext {
          persistUsers(it)
          logd("success")
        }
        .observeOn(AndroidSchedulers.mainThread())
        .singleOrError()
  }

}