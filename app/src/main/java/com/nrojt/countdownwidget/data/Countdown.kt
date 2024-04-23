package com.nrojt.countdownwidget.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "countdown")
data class Countdown(
    @PrimaryKey(autoGenerate = false)
    val id : String,

    val title : String,
    val datetime : LocalDateTime,
    val backgroundImageURI : String,
)
