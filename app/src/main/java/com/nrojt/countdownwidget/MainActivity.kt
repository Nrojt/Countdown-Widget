package com.nrojt.countdownwidget

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.glance.action.actionStartActivity
import com.nrojt.countdownwidget.data.CountdownRepository
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val repository = CountdownRepository(this)
        setContent {
            Text("Hello, world!")
            MainScreen(repository)
        }
    }
}

@Composable
fun MainScreen(repository: CountdownRepository) {
    val scope = rememberCoroutineScope()
    var widgetDataList: List<CountdownWidgetParams> = listOf()

    LaunchedEffect(key1 = repository) {
        scope.launch { widgetDataList = repository.getAllWidgetData()}
        Log.d("MainScreen2", "widgetDataList size: ${widgetDataList.size}")
    }


    // TODO maybe use a LazyColumn/recyclerview instead?

    Log.d("MainScreen", "widgetDataList size: ${widgetDataList.size}")

    // looping through the widget data list
    for (widgetData in widgetDataList) {
        WidgetCard(widgetData, repository)
    }
}

@Composable
fun WidgetCard(widgetData : CountdownWidgetParams, repository: CountdownRepository) {
    Text(widgetData.title)
    Text(widgetData.datetime)
    Text("id: ${widgetData.id}")
    Button(onClick = {
        actionStartActivity<CountdownWidgetConfigureActivity>()
    }) {
        Text("Configure")
    }
    val scope = rememberCoroutineScope()
    Button(onClick = { scope.launch{repository.deleteWidgetData(widgetData)} }) {
        Text("Delete")
    }
}

