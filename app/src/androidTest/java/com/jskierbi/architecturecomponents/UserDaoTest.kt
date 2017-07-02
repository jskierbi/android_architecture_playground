package com.jskierbi.architecturecomponents

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.persistence.room.Room
import android.database.sqlite.SQLiteConstraintException
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.jskierbi.architecturecomponents.core.persistence.ExampleDatabase
import com.jskierbi.architecturecomponents.modules.user.User
import com.jskierbi.architecturecomponents.modules.user.UserInformation
import org.junit.*
import org.junit.rules.ExpectedException
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserDaoTest {

  @JvmField
  @Rule
  val instantTaskExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()
  @JvmField
  @Rule
  var thrown = ExpectedException.none()!!

  val user: User = User().apply {
    id = 1
    name = "john_doe"
    password = "nemo"
    userInformation = UserInformation("John", "Doe", 20)
  }

  lateinit var database: ExampleDatabase

  @Before
  fun setupDatabase() {
    database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(), ExampleDatabase::class.java)
        .allowMainThreadQueries()
        .build()
  }

  @After
  fun closeDatabase() {
    database.close()
  }

  @Test
  fun getEmptyUserList() {
    database.userDao().findAllUsers().test().assertValue {
      it.isEmpty()
    }
  }

  @Test
  fun autoIncrement() {
    user.id  = 0
    val id: Long = database.userDao().insertUser(user)
    Assert.assertNotEquals(0, id)
    user.id = id
    database.userDao().deleteUser(user)
  }

  @Test
  fun insertUser() {
    val id: Long = database.userDao().insertUser(user)
    Assert.assertNotEquals(0, id)
    database.userDao().findAllUsers().test().assertValue {
      it.isNotEmpty() && it.first().id == id && it.first().name.equals(user.name)
    }
  }

  @Test
  fun insertUserRollback() {
    database.beginTransaction()
    database.userDao().insertUser(user)
    user.name = "foo_bar"
    thrown.expect(SQLiteConstraintException::class.java)
    thrown.expectMessage("UNIQUE constraint failed: users.id")
    database.userDao().insertUser(user)
    database.endTransaction()
    database.userDao().findAllUsers().test().assertValue {
      it.isEmpty()
    }
  }

  @Test
  fun insertUserReplace() {
    database.userDao().insertUserReplace(user)
    user.name = "foo_bar"
    database.userDao().insertUserReplace(user)
    database.userDao().findAllUsers().test().assertValue {
      it.isNotEmpty() && it.first().name == user.name
    }
  }

  @Test
  fun insertUserIgnore() {
    database.userDao().insertUserIgnore(user)
    user.name = "foo_bar"
    database.userDao().insertUserIgnore(user)
    database.userDao().findAllUsers().test().assertValue {
      it.isNotEmpty() && it.first().name == "john_doe"
    }
  }

  @Test
  fun findSingleUser() {
    val id: Long = database.userDao().insertUser(user)
    database.userDao().findUserById(id).test().assertValue {
      it.id == id && it.name == user.name
    }
  }

  @Test
  fun deleteUser() {
    database.userDao().insertUser(user)
    val rowCount: Int = database.userDao().deleteUser(user)
    Assert.assertEquals(rowCount, 1)
    database.userDao().findAllUsers().test().assertValue {
      it.isEmpty()
    }
  }

}