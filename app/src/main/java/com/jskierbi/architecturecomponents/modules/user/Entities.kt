package com.jskierbi.architecturecomponents.modules.user

import android.arch.persistence.room.*

@Entity(tableName = "users", indices = arrayOf(Index(value = "user_name", unique = true)))
data class User(@PrimaryKey val id: Long,
                @ColumnInfo(name = "user_name") val name: String,
                @ColumnInfo(name = "user_password") val password: String)

@Entity(tableName = "user billing_address",
        foreignKeys = arrayOf(ForeignKey(
            entity = User::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("user_id"))
        )
)
data class BillingAddress(@PrimaryKey val id: Long,
                val name: String,
                val city: String,
                @ColumnInfo(name = "house_number") val houseNumber: String,
                @ColumnInfo(name = "zip_code") val zipCode: String,
                @ColumnInfo(name = "user_id") val userId: Long
)