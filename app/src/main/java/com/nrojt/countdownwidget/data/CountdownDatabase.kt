package com.nrojt.countdownwidget.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Countdown::class], version = 1)
abstract class CountdownDatabase : RoomDatabase() {
    abstract val dao : CountdownDao
}