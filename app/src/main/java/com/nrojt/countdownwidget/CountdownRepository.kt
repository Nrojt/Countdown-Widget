package com.nrojt.countdownwidget

import android.content.Context
import java.time.LocalDateTime

class CountdownRepository(private val context : Context) {
    private val sharedPreferences = context.getSharedPreferences("countdownWidget", Context.MODE_PRIVATE)

    fun getCountdownData(countdownWidgetId: String): LocalDateTime {
        sharedPreferences.getString(countdownWidgetId + "datetime", null)?.let {
            return LocalDateTime.parse(it)
        }
        return LocalDateTime.of(2030, 1, 1, 0, 0)
    }

    fun getBackgroundImage(countdownWidgetId: String): Int{
        sharedPreferences.getString(countdownWidgetId + "backgroundImage", null)?.let {
            return it.toInt()
        }
        return 0 //TODO sample image
    }

    fun saveCountdownData(dateTime: LocalDateTime, countdownWidgetId: String) {
        sharedPreferences.edit().putString(countdownWidgetId + "datetime", dateTime.toString()).apply()
    }

    fun saveBackgroundImage(imageId: Int, countdownWidgetId: String) {
        sharedPreferences.edit().putString(countdownWidgetId + "backgroundImage", imageId.toString()).apply()
    }
}