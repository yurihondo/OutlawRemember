package com.yurihondo.outlawremember.sample

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect

@Composable
internal fun OutlawSaveableStateProvider(
    id: String,
    owner: SavedOutlawStateRegistryOwner,
    content: @Composable () -> Unit
) {
    val saveableStateRegistry = DisposableOutlawSavedStateRegistry(id, owner)
    CompositionLocalProvider(LocalSaveableOutlawStateRegistry provides saveableStateRegistry) {
        content()
    }
    DisposableEffect(id, saveableStateRegistry) {
        onDispose {
            saveableStateRegistry.dispose()
        }
    }
}
