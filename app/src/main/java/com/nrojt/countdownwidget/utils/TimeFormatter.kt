package com.nrojt.countdownwidget.utils

import java.time.LocalDateTime
import java.time.Duration

class TimeFormatter {
    fun formatTimeTill(targetDateTime: String): String {
        val targetDateTimeConverted : LocalDateTime = LocalDateTime.parse(targetDateTime)
        val now = LocalDateTime.now()
        val duration = Duration.between(now, targetDateTimeConverted)

        val days = duration.toDays()
        val hours = duration.toHours() % 24
        val minutes = duration.toMinutes() % 60

        // Not that clean, might fix at some point
        return when {
            days > 0 -> String.format("%d days", days)
            hours > 0 -> String.format("%d hours", hours)
            else -> String.format("%d minutes", minutes)
        }
    }
}