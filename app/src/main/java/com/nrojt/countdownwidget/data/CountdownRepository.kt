package com.nrojt.countdownwidget.data

import android.content.Context
import com.nrojt.countdownwidget.CountdownWidgetParams
import com.nrojt.countdownwidget.R
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.ThreadLocalRandom

class CountdownRepository(context : Context) {
    private val dataStore = context.countdownWidgetParamDataStore

    suspend fun getWidgetData(countdownWidgetId: String): CountdownWidgetParams {
        val widgetData : CountdownWidgetParams? = dataStore.data
            .map { countdownWidgetParams ->
                if (countdownWidgetParams.id == countdownWidgetId) countdownWidgetParams else null
            }
            .first()

        // check if the widget data is null
        if (widgetData == null) {
            // if it is, create a new widget data with the default values
            val defaultBackgroundImageUri = "android.resource://com.nrojt.countdownwidget/" + R.drawable.example_appwidget_preview
            // if it is, create a new widget data with the default values
            val newWidgetData = CountdownWidgetParams.newBuilder()
                .setId(countdownWidgetId)
                .setDatetime(getRandomDateTime())
                .setBackgroundImage(defaultBackgroundImageUri)
                .setTitle("Countdown")
                .build()
            // save the new widget data
            saveWidgetData(newWidgetData)
            return newWidgetData
        }

        return widgetData
    }

    suspend fun getAllWidgetData(): List<CountdownWidgetParams> {
        return dataStore.data.map { countdownWidgetParams ->
            countdownWidgetParams
        }.toList()
    }

    suspend fun saveWidgetData(widgetData : CountdownWidgetParams) {
        // using the datastore to save the date string
        dataStore.updateData { currentData ->
            currentData.toBuilder()
                .setId(widgetData.id)
                .setDatetime(widgetData.datetime)
                .setBackgroundImage(widgetData.backgroundImage)
                .build()
        }
    }

    suspend fun updateWidgetData(widgetData : CountdownWidgetParams) {
        // using the datastore to save the date string
        dataStore.updateData { currentData ->
            currentData.toBuilder()
                .setId(widgetData.id)
                .setDatetime(widgetData.datetime)
                .setBackgroundImage(widgetData.backgroundImage)
                .build()
        }
    }

    suspend fun deleteWidgetData(widgetData : CountdownWidgetParams) {
        // using the datastore to delete the widget data
            dataStore.updateData { currentData ->
                if (currentData.id == widgetData.id) {
                    currentData.toBuilder().clear().build()
                } else {
                    currentData
                }
        }
    }
}

private fun getRandomDateTime(): String {
    val start = LocalDateTime.now()
    val end = LocalDateTime.of(2030, 12, 31, 23, 59)

    val startSeconds = start.toEpochSecond(java.time.ZoneOffset.UTC)
    val endSeconds = end.toEpochSecond(java.time.ZoneOffset.UTC)
    val randomSeconds = ThreadLocalRandom.current().nextLong(startSeconds, endSeconds)

    val randomDateTime = LocalDateTime.ofEpochSecond(randomSeconds, 0, java.time.ZoneOffset.UTC)
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")

    return randomDateTime.format(formatter)
}