package com.nrojt.countdownwidget.data

import android.content.Context
import com.nrojt.countdownwidget.CountdownWidgetParams
import com.nrojt.countdownwidget.R
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

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
                .setDatetime("2025-01-01T00:00:00")
                .setBackgroundImage(defaultBackgroundImageUri)
                .build()
            // save the new widget data
            saveWidgetData(newWidgetData)
            return newWidgetData
        }

        return widgetData
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
}