package com.jskierbi.architecturecomponents

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.db.framework.FrameworkSQLiteOpenHelperFactory
import android.arch.persistence.room.testing.MigrationTestHelper
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.jskierbi.architecturecomponents.core.persistence.ExampleDatabase
import com.jskierbi.architecturecomponents.core.persistence.Migration_1_2
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

const val TEST_DB_NAME: String = "migration-test"

@RunWith(AndroidJUnit4::class)
open class MigrationTest {

  @JvmField
  @Rule
  val migrationTestHelper: MigrationTestHelper = MigrationTestHelper(
      InstrumentationRegistry.getInstrumentation(),
      ExampleDatabase::class.java.canonicalName,
      FrameworkSQLiteOpenHelperFactory()
      )

  @Test
  fun migrate1To2() {

    var database: SupportSQLiteDatabase = migrationTestHelper.createDatabase(TEST_DB_NAME, 1)

    database.execSQL("INSERT INTO users(user_name,user_password,first_name,last_name) VALUES ('john_doe','319f4d26e3c536b5dd871bb2c52e3178','John','Doe')")
    database.execSQL("INSERT INTO users(user_name,user_password,first_name,last_name) VALUES ('john_doe2','319f4d26e3c536b5dd871bb2c52e3178','John2','Doe2')")

    database.close()

    database = migrationTestHelper.runMigrationsAndValidate(TEST_DB_NAME, 2, true, Migration_1_2())

    Assert.assertTrue(database.version == 2)

  }


}