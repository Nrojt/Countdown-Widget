package com.nrojt.countdownwidget

import com.nrojt.countdownwidget.data.Countdown

sealed interface CountdownEvent {
    object SaveCountdown : CountdownEvent
    data class SetId(val id: String) : CountdownEvent
    data class SetTitle(val title: String) : CountdownEvent
    data class SetDateTime(val dateTime: String) : CountdownEvent
    data class SetBackgroundImageURI(val backgroundImageURI: String) : CountdownEvent
    object ShowDatePicker : CountdownEvent
    data class DeleteCountdown(val countdown: Countdown) : CountdownEvent
}