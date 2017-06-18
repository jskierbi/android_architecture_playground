package com.jskierbi.architecturecomponents.core.persistence

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities = arrayOf(), version = 1)
abstract class ExampleDatabase: RoomDatabase() {

}