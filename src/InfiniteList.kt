fun <T> infiniteListOf(vararg elements: T): InfiniteList<T> = InfiniteList(elements.toList())

class InfiniteList<T>(val list: List<T>): List<T> by list {
    override fun get(index: Int): T = list[index % size]
}

fun <T> mutableInfiniteListOf(vararg elements: T): MutableInfiniteList<T> = MutableInfiniteList(elements.toMutableList())

class MutableInfiniteList<T>(val list: MutableList<T>): MutableList<T> by list {
    override fun get(index: Int): T = list[index % size]
    override fun set(index: Int, element: T): T {
        list[index % size] = element
        return element
    }
}