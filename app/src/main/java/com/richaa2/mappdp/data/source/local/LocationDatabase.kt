package com.richaa2.mappdp.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [LocationInfoEntity::class], version = 1)
abstract class LocationDatabase : RoomDatabase() {
    abstract fun locationDao(): LocationDao
}