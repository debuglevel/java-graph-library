package de.debuglevel.graphlibrary

/**
 * A NodeInformationRetriever defines how to derive graph data from other data structures.
 */
interface NodeInformationRetriever<T> {
    fun getColor(node: T): Color
    fun getShape(node: T): Shape
    fun getTooltip(node: T): String
    fun getText(node: T): String
    fun getPrecedingVertices(vertex: Vertex<T>, allVertices: List<Vertex<T>>): List<Vertex<T>>
    fun getSucceedingVertices(vertex: Vertex<T>, allVertices: List<Vertex<T>>): List<Vertex<T>>
}
