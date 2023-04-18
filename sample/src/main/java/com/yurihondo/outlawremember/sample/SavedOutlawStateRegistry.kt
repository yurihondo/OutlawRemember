package com.yurihondo.outlawremember.sample

interface SavedOutlawStateRegistry {
    fun registerSavedStateProvider(key: String, doSave: () -> Map<String, List<Any?>>)
    fun unregisterSavedStateProvider(key: String)
    fun consumeRestoredStateForKey(key: String): Map<String, List<Any?>>?
    fun performSave()
}

object SavedOutlawStateRegistryImpl : SavedOutlawStateRegistry {

    private val stateMap = mutableMapOf<String, Map<String, List<Any?>>>()
    private val providers = mutableMapOf<String, () -> Map<String, List<Any?>>>()

    override fun registerSavedStateProvider(key: String, doSave: () -> Map<String, List<Any?>>) {
        providers[key] = doSave
    }

    override fun unregisterSavedStateProvider(key: String) {
        providers.remove(key)
    }

    override fun consumeRestoredStateForKey(key: String): Map<String, List<Any?>>? {
        return stateMap.remove(key)?.let { return it }
    }

    override fun performSave() {
        providers.forEach { provider -> stateMap[provider.key] = provider.value.invoke() }
    }
}
