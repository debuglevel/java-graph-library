package de.debuglevel.graphlibrary

/**
 * A [Vertex]/node containing an object of type [T].
 * When visualized, the following attributes are used:
 * A [text] is placed inside the [Vertex]. It has a background [color] and a [shape].
 * A [tooltip] may be displayed on mouse hover (e.g. when using SVG).
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
