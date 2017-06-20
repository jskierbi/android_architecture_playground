package com.jskierbi.architecturecomponents

import android.app.Application
import android.arch.persistence.room.Room
import com.jskierbi.architecturecomponents.core.persistence.ExampleDatabase

class ArchitectureComponentsApplication : Application() {

  val exampleDatabase: ExampleDatabase by lazy { Room.databaseBuilder(applicationContext, ExampleDatabase::class.java, "architecture-components").build() }

}