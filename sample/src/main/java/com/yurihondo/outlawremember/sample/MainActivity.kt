package com.yurihondo.outlawremember.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yurihondo.outlawremember.sample.ui.theme.OutlawrememberTheme
import java.util.Date

class MainActivity : ComponentActivity(), SavedOutlawStateRegistryOwner {

    override val savedOutlawStateRegistry: SavedOutlawStateRegistry = SavedOutlawStateRegistryImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OutlawSaveableStateProvider(
                id = this::class.java.simpleName,
                owner = this,
                content = @Composable { App() }
            )
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        savedOutlawStateRegistry.performSave()
    }
}

@Composable
private fun App() {
    OutlawrememberTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            MainContent()
        }
    }
}

@Composable
private fun ListItem(
    modifier: Modifier = Modifier,
    index: Int,
    onClickItem: () -> Unit
) {
    Box(
        modifier = modifier
            .clickable(onClick = onClickItem)
            .fillMaxWidth()
            .height(50.dp)
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = "$index - Click me!",
        )
    }
}

@Composable
private fun MainContent(
    modifier: Modifier = Modifier,
) {
    var flag by remember { mutableStateOf(true) }
    if (flag) {
        SwitchableScreen(
            modifier = modifier,
            name = "Screen1",
            onSwitchRequested = { flag = !flag }
        )
    } else {
        SwitchableScreen(
            modifier = modifier,
            name = "Screen2",
            onSwitchRequested = { flag = !flag }
        )
    }
}

@Composable
private fun SwitchableScreen(
    modifier: Modifier = Modifier,
    name: String,
    onSwitchRequested: () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        var text by rememberOutlaw {
            mutableStateOf("$name: TimeStamp: ${Date().time}")
        }

        Text(text = text)
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Button(onClick = { text = "$name: TimeStamp: ${Date().time}" }) {
                Text(text = "UPDATE")
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(onClick = onSwitchRequested) {
                Text(text = "Switch")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    OutlawrememberTheme {
        ListItem(
            index = 0,
            onClickItem = {}
        )
    }
}