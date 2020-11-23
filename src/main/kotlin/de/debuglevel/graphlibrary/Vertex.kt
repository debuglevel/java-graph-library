package de.debuglevel.graphlibrary

data class Vertex<T>(
    val content: T,
    val color: Color,
    val shape: Shape,
    val tooltip: String,
    val text: String
) {
    val outEdges = hashSetOf<Edge<T>>()
    val inEdges = hashSetOf<Edge<T>>()
    override fun toString() = text
}
