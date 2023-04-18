package com.yurihondo.outlawremember.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yurihondo.outlawremember.sample.ui.theme.OutlawrememberTheme
import java.util.*

class MainActivity : ComponentActivity(), SavedOutlawStateRegistryOwner {

    override val savedOutlawStateRegistry: SavedOutlawStateRegistry = SavedOutlawStateRegistryImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SaveableOutlawStateProvider(
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
            Column {
                LazyColumn(
                    modifier = Modifier.weight(1.0f),
                    state = rememberLazyListState(),
                ) {
                    items(100) {
                        ListItem(
                            index = it,
                            onClickItem = {}
                        )
                    }
                }
                Footer()
            }
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
private fun Footer(
    modifier: Modifier = Modifier,
) {
    var flag by remember { mutableStateOf(true) }

    if (flag) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(horizontal = 16.dp),
        ) {
            var text by rememberSaveable(
            ) {
                mutableStateOf("1: Time -> ${Date().time}")
            }
            Text(text = text)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Button(onClick = { text = "1: Time -> ${Date().time}" }) {
                    Text(text = "UPDATE")
                }
                Spacer(modifier = Modifier.width(16.dp))
                Button(onClick = { flag = !flag }) {
                    Text(text = "Change")
                }
            }
        }
    } else {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(horizontal = 16.dp),
        ) {
            var text by rememberSaveable(
            ) {
                mutableStateOf("2: Time -> ${Date().time}")
            }
            Text(text = text)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Button(onClick = { text = "2: Time -> ${Date().time}" }) {
                    Text(text = "UPDATE")
                }
                Spacer(modifier = Modifier.width(16.dp))
                Button(onClick = { flag = !flag }) {
                    Text(text = "Change")
                }
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