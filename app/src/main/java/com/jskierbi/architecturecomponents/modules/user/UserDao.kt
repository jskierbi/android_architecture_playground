package com.jskierbi.architecturecomponents.modules.user

import android.arch.persistence.room.*

@Dao
interface UserDao {

  @Insert(onConflict = OnConflictStrategy.IGNORE)
  fun insertUser(user: User): Long

  @Insert(onConflict = OnConflictStrategy.IGNORE)
  fun insertUsers(users: List<User>): List<Long>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertUserAndBuddy(user: User, buddy: User)

  @Insert(onConflict = OnConflictStrategy.ROLLBACK)
  fun insertUserAndBuddies(user: User, buddies: List<User>)

  @Update(onConflict = OnConflictStrategy.IGNORE)
  fun updateUser(user: User): Int

  @Update(onConflict = OnConflictStrategy.REPLACE)
  fun updateUsers(users: List<User>)

  @Delete()
  fun deleteUser(user: User): Int

  @Delete()
  fun deleteUsers(users: List<User>)

  @Query("SELECT * FROM users")
  fun findAllUsers(): List<User>

  @Query("SELECT first_name, last_name FROM users")
  fun findUserInformation(): List<UserInformation>

}