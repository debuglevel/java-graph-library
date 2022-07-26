package de.debuglevel.graphlibrary

import mu.KotlinLogging

/**
 * A graph contains [Vertices](Vertex) connected by [Edge]s.
 */
class Graph<T : Any> {
    private val logger = KotlinLogging.logger {}

    private val _vertices = mutableSetOf<Vertex<T>>()
    private val _edges = mutableSetOf<Edge<T>>()

    /**
     * Adds a [Vertex] to the [Graph].
     */
    fun addVertex(vertex: Vertex<T>) {
        _vertices.add(vertex)
    }

    /**
     * Adds an [Edge].
     */
    fun addEdge(edge: Edge<T>) {
        _edges.add(edge)
        edge.start.outEdges.add(edge)
        edge.end.inEdges.add(edge)
    }

    /**
     * Removes an [Edge].
     */
    fun removeEdge(edge: Edge<T>) {
        _edges.remove(edge)
        edge.start.outEdges.remove(edge)
        edge.end.inEdges.remove(edge)
    }

    /**
     * Gat all [Edge]s.
     */
    val edges: Set<Edge<T>>
        get() {
            return _edges.toSet()
        }

    /**
     * Gets all [Vertices](Vertex).
     */
    val vertices: Set<Vertex<T>>
        get() {
            return _vertices.toSet()
        }

    /**
     * Gets a string representation of the [Graph].
     */
    override fun toString(): String {
        var s = ""
        _vertices.map { vertex ->
            s += vertex.text + " → "
            _edges
                .filter { edge -> edge.start == vertex }
                .map { edge -> edge.end }
                .joinToString("|")
            "$s\n"
        }
        return s
    }
}
