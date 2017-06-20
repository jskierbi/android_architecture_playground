package com.jskierbi.architecturecomponents.modules.user

import android.arch.persistence.room.*

@Entity(tableName = "users", indices = arrayOf(Index(value = "user_name", unique = true)))
data class User(@PrimaryKey(autoGenerate = true) var id: Long = -1,
                @ColumnInfo(name = "user_name") var name: String = "",
                @ColumnInfo(name = "user_password") var password: String = "",
                @Embedded var userInformation: UserInformation = UserInformation())

data class UserInformation(@ColumnInfo(name = "first_name") var firstName: String = "", @ColumnInfo(name = "last_name") var lastName: String = "")

@Entity(tableName = "user_billing_address",
    foreignKeys = arrayOf(ForeignKey(
        entity = User::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("user_id"),
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE)
    )
)
data class BillingAddress(@PrimaryKey(autoGenerate = true) var id: Long = -1,
                          var state: String = "",
                          var city: String = "",
                          var street: String = "",
                          @ColumnInfo(name = "house_number") var houseNumber: String = "",
                          @ColumnInfo(name = "zip_code") var zipCode: String = "",
                          @ColumnInfo(name = "user_id") var userId: Long = -1
)