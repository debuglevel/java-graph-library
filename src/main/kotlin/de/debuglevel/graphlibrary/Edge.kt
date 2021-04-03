package de.debuglevel.graphlibrary

/**
 * An edge connecting two vertices.
 */
data class Edge<T>(
    val start: Vertex<T>,
    val end: Vertex<T>
) {
    override fun toString() = "$start -> $end"
}
