package com.jskierbi.architecturecomponents.core.persistence

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.jskierbi.architecturecomponents.modules.user.BillingAddress
import com.jskierbi.architecturecomponents.modules.user.User

@Database(entities = arrayOf(User::class, BillingAddress::class), version = 1)
abstract class ExampleDatabase: RoomDatabase() {

}