package com.nrojt.countdownwidget

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.glance.Button
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.action.actionStartActivity
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.ImageProvider
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import androidx.glance.text.Text
import com.nrojt.countdownwidget.data.CountdownRepository
import com.nrojt.countdownwidget.utils.TimeFormatter

class CountdownAppWidget : GlanceAppWidget() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        // https://developer.android.com/develop/ui/compose/glance/create-app-widget
        // https://www.youtube.com/watch?v=bhrN7yFG0D4
        // In this method, load data needed to render the AppWidget.
        // Use `withContext` to switch to another thread for long running
        // operations.

        provideContent {
            // create your AppWidget here
            CountdownWidgetContent(context, id)
        }
    }

    @Composable
    private fun CountdownWidgetContent(context: Context, id: GlanceId) {
        val repository = CountdownRepository(context)
        val updater = remember { CountdownWidgetUpdater(context, id) }
        val countdownWidgetID = id.toString()

        // Remember the widget data once fetched
        var widgetData by remember(countdownWidgetID) { mutableStateOf<CountdownWidgetParams?>(null) }

        // Use LaunchedEffect to launch a coroutine
        LaunchedEffect(key1 = countdownWidgetID) {
            // Fetch the widget data in a coroutine
            val data = repository.getWidgetData(countdownWidgetID)
            widgetData = data
        }

        // If data is null, show a loading indicator or placeholder
        if (widgetData == null) {
            Column {
                Text("Loading...", modifier = GlanceModifier.padding(12.dp))
            }
        } else {

            val timeFormatter = TimeFormatter()
            val targetDateTime = widgetData!!.datetime
            val timeTill = timeFormatter.formatTimeTill(targetDateTime)

            val backgroundUri = Uri.parse(widgetData!!.backgroundImage)

            // Composable UI code using the retrieved data
            Column(
                modifier = GlanceModifier.fillMaxSize().background(
                    imageProvider = ImageProvider(
                        backgroundUri
                    )
                ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(timeTill, modifier = GlanceModifier.padding(12.dp))
                Row(horizontalAlignment = Alignment.CenterHorizontally) {
                    Button(
                        text = "Edit",
                        onClick = actionStartActivity<MainActivity>()
                    )
                }
                Spacer(modifier = GlanceModifier.padding(12.dp))
            }
        }
    }
}