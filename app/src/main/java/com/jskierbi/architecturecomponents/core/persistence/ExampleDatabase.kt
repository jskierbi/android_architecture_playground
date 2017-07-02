package com.jskierbi.architecturecomponents.core.persistence

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.jskierbi.architecturecomponents.modules.user.BillingAddress
import com.jskierbi.architecturecomponents.modules.user.DateConverter
import com.jskierbi.architecturecomponents.modules.user.User
import com.jskierbi.architecturecomponents.modules.user.UserDao

@TypeConverters(DateConverter::class)
@Database(entities = arrayOf(User::class, BillingAddress::class), version = 2)
abstract class ExampleDatabase: RoomDatabase() {
  abstract fun userDao(): UserDao
}