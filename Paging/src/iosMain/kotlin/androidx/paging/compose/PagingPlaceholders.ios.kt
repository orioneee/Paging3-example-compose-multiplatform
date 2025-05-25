package androidx.paging.compose

internal actual fun getPagingPlaceholderKey(index: Int): Any = PagingPlaceholderKey(index)

class PagingPlaceholderKey(private val index: Int)
