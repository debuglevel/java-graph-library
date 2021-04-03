package de.debuglevel.graphlibrary

import mu.KotlinLogging

/**
 * A graph containing vertices connected by edges.
 */
class Graph<T : Any> {
    private val logger = KotlinLogging.logger {}

    private val vertices = mutableSetOf<Vertex<T>>()
    private val edges = mutableSetOf<Edge<T>>()

    /**
     * Add a vertex to the graph.
     */
    fun addVertex(vertex: Vertex<T>) {
        vertices.add(vertex)
    }

    /**
     * Add an edge
     */
    fun addEdge(edge: Edge<T>) {
        edges.add(edge)
        edge.start.outEdges.add(edge)
        edge.end.inEdges.add(edge)
    }

    /**
     * Remove an edge
     */
    fun removeEdge(edge: Edge<T>) {
        edges.remove(edge)
        edge.start.outEdges.remove(edge)
        edge.end.inEdges.remove(edge)
    }

    /**
     * Gat all edges
     */
    fun getEdges(): Set<Edge<T>> {
        return edges.toSet()
    }

    /**
     * Get all vertices
     */
    fun getVertices(): Set<Vertex<T>> {
        return vertices.toSet()
    }

    /**
     * Get a string representation of the graph
     */
    override fun toString(): String {
        var s = ""
        vertices.map { vertex ->
            s += vertex.text + " → "
            edges
                .filter { e -> e.start == vertex }
                .map { it.end }
                .joinToString("|")
            "$s\n"
        }
        return s
    }
}
