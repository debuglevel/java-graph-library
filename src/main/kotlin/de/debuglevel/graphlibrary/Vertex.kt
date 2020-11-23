package de.debuglevel.graphlibrary

data class Vertex<T>(
    val content: T,
    val color: Color,
    val shape: Shape,
    val tooltip: String,
    val text: String,
    var order: Int? = null,
    var scaledOrder: Double? = null
) {
    val outEdges = hashSetOf<Edge<T>>()
    val inEdges = hashSetOf<Edge<T>>()
    override fun toString() = text
}
