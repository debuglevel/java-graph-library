package de.debuglevel.graphlibrary

/**
 * An edge connects two [Vertices](Vertex).
 */
data class Edge<T>(
    val start: Vertex<T>,
    val end: Vertex<T>
) {
    override fun toString() = "$start -> $end"
}
