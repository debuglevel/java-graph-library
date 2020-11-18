package de.debuglevel.graphlibrary

import mu.KotlinLogging

class Graph<T : Any> {
    private val logger = KotlinLogging.logger {}

    private val vertices = mutableSetOf<Vertex<T>>()
    private val edges = mutableSetOf<Edge<T>>()

    fun addVertex(vertex: Vertex<T>) {
        vertices.add(vertex)
    }

    fun addEdge(edge: Edge<T>) {
        edges.add(edge)
        edge.start.outEdges.add(edge)
        edge.end.inEdges.add(edge)
    }

    fun removeEdge(edge: Edge<T>) {
        edges.remove(edge)
        edge.start.outEdges.remove(edge)
        edge.end.inEdges.remove(edge)
    }

    fun getEdges(): Set<Edge<T>> {
        return edges.toSet()
    }

    fun getVertices(): Set<Vertex<T>> {
        return vertices.toSet()
    }

    override fun toString(): String {
        var s = ""
        vertices.map { vertex ->
            s += vertex.toString()
            edges
                .filter { e -> e.start == vertex }
                .forEach { e -> s += "  ${e.end}" }
            s
        }
        return s
    }
}
