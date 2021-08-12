package de.debuglevel.graphlibrary

import mu.KotlinLogging

/**
 * A graph containing vertices connected by edges.
 */
class Graph<T : Any> {
    private val logger = KotlinLogging.logger {}

    private val _vertices = mutableSetOf<Vertex<T>>()
    private val _edges = mutableSetOf<Edge<T>>()

    /**
     * Add a vertex to the graph.
     */
    fun addVertex(vertex: Vertex<T>) {
        _vertices.add(vertex)
    }

    /**
     * Add an edge
     */
    fun addEdge(edge: Edge<T>) {
        _edges.add(edge)
        edge.start.outEdges.add(edge)
        edge.end.inEdges.add(edge)
    }

    /**
     * Remove an edge
     */
    fun removeEdge(edge: Edge<T>) {
        _edges.remove(edge)
        edge.start.outEdges.remove(edge)
        edge.end.inEdges.remove(edge)
    }

    /**
     * Gat all edges
     */
    val edges: Set<Edge<T>>
        get() {
            return _edges.toSet()
        }

    /**
     * Get all vertices
     */
    val vertices: Set<Vertex<T>>
        get() {
            return _vertices.toSet()
        }

    /**
     * Get a string representation of the graph
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
