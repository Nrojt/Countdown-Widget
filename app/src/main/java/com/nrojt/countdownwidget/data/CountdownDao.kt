package com.nrojt.countdownwidget.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CountdownDao {
    @Insert
    fun insert(countdown: Countdown)
    @Update
    fun update(countdown: Countdown)
    @Delete
    fun delete(countdown: Countdown)

    @Query("SELECT * FROM countdown WHERE id = :id")
    fun getById(id: String): Countdown

    @Query("SELECT * FROM countdown ORDER BY datetime ASC")
    fun getAll(): Flow<List<Countdown>>

}