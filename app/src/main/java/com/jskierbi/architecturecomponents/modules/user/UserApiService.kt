package com.jskierbi.architecturecomponents.modules.user

import io.reactivex.Observable
import retrofit2.http.GET

interface UserApiService {

  @GET("users")
  fun getUsers(): Observable<List<User>>

}