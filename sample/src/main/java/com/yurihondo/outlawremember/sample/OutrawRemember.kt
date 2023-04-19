package com.yurihondo.outlawremember.sample

import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.*

private const val MaxSupportedRadix = 36

@Composable
fun <T : Any> rememberOutlaw(
    vararg inputs: Any?,
    saver: Saver<T, out Any> = autoSaver(),
    key: String? = null,
    init: () -> T
): T {
    val finalKey = if (key.isNullOrEmpty()) {
        currentCompositeKeyHash.toString(MaxSupportedRadix)
    } else {
        key
    }

    @Suppress("UNCHECKED_CAST")
    (saver as Saver<T, Any>)

    val registry = LocalSaveableOutlawStateRegistry.current
    val value = remember(*inputs) {
        val restored = registry?.consumeRestored(finalKey)?.let {
            saver.restore(it)
        }
        restored ?: init()
    }

    if (registry != null) {
        val saverState = rememberUpdatedState(saver)
        val valueState = rememberUpdatedState(value)
        DisposableEffect(registry, finalKey) {
            val valueProvider = {
                with(saverState.value) {
                    SaverScope { true }.save(valueState.value)
                }
            }
            val entry = registry.registerProvider(finalKey, valueProvider)
            onDispose { entry.unregister() }
        }
    }
    return value
}