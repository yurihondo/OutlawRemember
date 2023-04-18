package com.yurihondo.outlawremember.sample

import androidx.compose.runtime.staticCompositionLocalOf

val LocalSaveableOutlawStateRegistry =
    staticCompositionLocalOf<SaveableOutlawStateRegistry?> { null }

interface SaveableOutlawStateRegistry {
    interface Entry {
        fun unregister()
    }

    fun consumeRestored(key: String): Any?
    fun registerProvider(key: String, valueProvider: () -> Any?): Entry
    fun performSave(): Map<String, List<Any?>>
}

fun SaveableOutlawStateRegistry(
    restored: Map<String, List<Any?>>?,
): SaveableOutlawStateRegistry = SaveableOutlawStateRegistryImpl(restored)

class SaveableOutlawStateRegistryImpl(
    restored: Map<String, List<Any?>>?,
) : SaveableOutlawStateRegistry {

    private val restored = restored?.toMutableMap() ?: mutableMapOf()
    private val valueProviders = mutableMapOf<String, MutableList<() -> Any?>>()

    override fun consumeRestored(key: String): Any? {
        val list = restored.remove(key)
        return if (list != null && list.isNotEmpty()) {
            if (list.size > 1) {
                restored[key] = list.subList(1, list.size)
            }
            list[0]
        } else {
            null
        }
    }

    override fun registerProvider(
        key: String,
        valueProvider: () -> Any?
    ): SaveableOutlawStateRegistry.Entry {
        require(key.isNotBlank()) { "Registered key is empty or blank" }
        @Suppress("UNCHECKED_CAST")
        valueProviders.getOrPut(key) { mutableListOf() }.add(valueProvider)
        return object : SaveableOutlawStateRegistry.Entry {
            override fun unregister() {
                val list = valueProviders.remove(key)
                list?.remove(valueProvider)
                if (list != null && list.isNotEmpty()) {
                    // if there are other providers for this key return list back to the map
                    valueProviders[key] = list
                }
            }
        }
    }

    override fun performSave(): Map<String, List<Any?>> {
        val map = restored.toMutableMap()
        valueProviders.forEach { (key, list) ->
            if (list.size == 1) {
                val value = list[0].invoke()
                if (value != null) {
                    map[key] = arrayListOf<Any?>(value)
                }
            } else {
                map[key] = List(list.size) { index ->
                    val value = list[index].invoke()
                    value
                }
            }
        }
        return map
    }
}
