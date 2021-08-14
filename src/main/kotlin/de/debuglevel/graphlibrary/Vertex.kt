package de.debuglevel.graphlibrary

/**
 * A vertex/node containing an object of type T
 * @param T the type of the content
 * @param color the background color of the vertex when visualized
 * @param shape the shape of the vertex when visualized
 * @param tooltip tooltip of the vertex when rendered in appropriate format
 * @param text text to place in the vertex
 */
data class Vertex<T>(
    val content: T,
    val text: String,
    val tooltip: String?,
    val color: Color = Color.Gray,
    val shape: Shape = Shape.Rectangle,
    var order: Int? = null,
    var scaledOrder: Double? = null
) {
    val outEdges = hashSetOf<Edge<T>>()
    val inEdges = hashSetOf<Edge<T>>()
    override fun toString() = text
}
