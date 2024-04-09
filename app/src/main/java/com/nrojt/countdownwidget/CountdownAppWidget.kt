package com.nrojt.countdownwidget

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import androidx.glance.Button
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.ImageProvider
import androidx.glance.action.actionStartActivity
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import androidx.glance.text.Text
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
        val repository = remember { CountdownRepository(context) }
        val updater = remember { CountdownWidgetUpdater(context, id) }
        val countdownWidgetID = id.toString()

        val timeFormatter = TimeFormatter()
        val targetDateTime = repository.getCountdownData(countdownWidgetID)
        val timeTill = timeFormatter.formatTimeTill(targetDateTime)

        Column(
            modifier = GlanceModifier.fillMaxSize().background(imageProvider = ImageProvider(
                repository.getBackgroundImage(countdownWidgetID)
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