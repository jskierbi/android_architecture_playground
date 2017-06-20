package com.jskierbi.architecturecomponents.core.persistence

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.migration.Migration

class Migration_1_2: Migration(1, 2) {

  override fun migrate(database: SupportSQLiteDatabase?) {
    database?.execSQL("ALTER TABLE users ADD COLUMN IF NOT EXISTS age INT DEFAULT 0")
  }
}