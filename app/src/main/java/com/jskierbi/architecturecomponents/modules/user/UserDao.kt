package com.jskierbi.architecturecomponents.modules.user

import android.arch.persistence.room.*
import io.reactivex.Flowable

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
  fun findAllUsers(): Flowable<List<User>>

  @Query("SELECT first_name, last_name, age FROM users")
  fun findUserInformation(): Flowable<List<UserInformation>>

//  @Query("SELECT * FROM users WHERE id = :id LIMIT 1")
//  fun findUserById(id: Int): Flowable<User>

}