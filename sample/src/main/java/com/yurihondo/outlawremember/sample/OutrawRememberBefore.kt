package com.yurihondo.outlawremember.sample

import androidx.compose.runtime.saveable.SaveableStateRegistry

fun DisposableSavedOutlawStateRegistry(
    id: String,
    owner: SavedOutlawStateRegistryOwner,
): DisposableSavedOutlawStateRegistry {
    val key = "${DisposableSavedOutlawStateRegistry::class.java.simpleName}:$id"
    val registry = owner.savedOutlawStateRegistry
    val saveableStateRegistry = SaveableStateRegistry(registry.consumeRestoredStateForKey(key)) {
        // If you want to restrict the data to be stored, implement its process here.
        // In this sample, there are no restrictions, so true is returned.
        true
    }
    registry.registerSavedStateProvider(key) {
        saveableStateRegistry.performSave()
    }
    return DisposableSavedOutlawStateRegistry(saveableStateRegistry) {
        registry.unregisterSavedStateProvider(key)
    }
}

class DisposableSavedOutlawStateRegistry(
    saveableStateRegistry: SaveableStateRegistry,
    private val onDispose: () -> Unit
) : SaveableStateRegistry by saveableStateRegistry {
    fun dispose() {
        onDispose()
    }
}