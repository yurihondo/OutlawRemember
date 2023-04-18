package com.yurihondo.outlawremember.sample

fun DisposableOutlawSavedStateRegistry(
    id: String,
    owner: SavedOutlawStateRegistryOwner,
): DisposableSaveableOutlawStateRegistry {
    val key = "${SaveableOutlawStateRegistry::class.java.simpleName}:$id"
    val registry = owner.savedOutlawStateRegistry
    val saveableStateRegistry =
        SaveableOutlawStateRegistry(registry.consumeRestoredStateForKey(key))
    registry.registerSavedStateProvider(key) {
        saveableStateRegistry.performSave()
    }
    return DisposableSaveableOutlawStateRegistry(saveableStateRegistry) {
        registry.unregisterSavedStateProvider(key)
    }
}

class DisposableSaveableOutlawStateRegistry(
    saveableStateRegistry: SaveableOutlawStateRegistry,
    private val onDispose: () -> Unit
) : SaveableOutlawStateRegistry by saveableStateRegistry {
    fun dispose() {
        onDispose()
    }
}
