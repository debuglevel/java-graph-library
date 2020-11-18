package de.debuglevel.graphlibrary

data class Edge<T>(
    val start: Vertex<T>,
    val end: Vertex<T>
) {
    override fun toString() = "$start -> $end"
}
