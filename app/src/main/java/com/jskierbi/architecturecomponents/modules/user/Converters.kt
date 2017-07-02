package com.jskierbi.architecturecomponents.modules.user

import android.arch.persistence.room.TypeConverter
import java.util.*

class DateConverter {

  @TypeConverter
  fun fromTimestamp(timestamp: Long): Date {
    return Date(timestamp)
  }

  @TypeConverter
  fun dateToTimestamp(date: Date): Long {
    return date.time
  }
}