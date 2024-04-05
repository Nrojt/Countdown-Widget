package com.nrojt.countdownwidget

import java.time.LocalDateTime

class CountdownRepository(private var countdownWidgetId: Int) {

    fun getCountdownData(): LocalDateTime {
        // TODO load data from local database or something?
        return LocalDateTime.of(2025, 1, 1, 0, 0)
    }

    fun getBackgroundImage(): Int {
        // TODO load data from local database or something?
        return R.drawable.example_appwidget_preview
    }
}