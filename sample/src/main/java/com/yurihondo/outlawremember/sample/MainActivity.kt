package com.yurihondo.outlawremember.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yurihondo.outlawremember.sample.ui.theme.OutlawrememberTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
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
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .padding(horizontal = 16.dp),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            Text(text = "Hello World!")
                        }
                    }

                }
            }
        }
    }
}

@Composable
fun ListItem(
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