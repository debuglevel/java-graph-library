package de.debuglevel.graphlibrary

import mu.KotlinLogging

object GraphUtils {
    private val logger = KotlinLogging.logger {}

    /**
     * Whether another path between start and end (other than the given ignoredEdge) exists.
     */
    fun <T : Any> pathExists(start: Vertex<T>, end: Vertex<T>, ignoredEdge: Edge<T>) =
        findVertex(start, end, ignoredEdge)

    /**
     * Walk tree (starting from "start") to find vertex "breakCondition".
     */
    private fun <T : Any> findVertex(
        start: Vertex<T>,
        breakCondition: Vertex<T>,
        ignoredEdge: Edge<T>?
    ): Boolean {
        val descendants =
            when {
                // only filter if ignoredEdge is not null (i.e. we are on the first level of the recursive tree). Saves about 50% time.
                ignoredEdge != null -> start.outEdges
                    .filter { it !== ignoredEdge }
                    .map { it.end }
                else -> start.outEdges
                    .map { it.end }
            }

        return if (descendants.contains(breakCondition)) {
            true
        } else {
            descendants.any {
                // ignoredEdge is replaced by null here, as it is only relevant in the first call level of the recursion
                    descendant ->
                findVertex(descendant, breakCondition, null)
            }
        }
    }

    /**
     * Get all vertices which have no incoming edge
     */
    fun <T : Any> getStartVertices(graph: Graph<T>): List<Vertex<T>> {
        logger.trace { "Getting start vertices for graph..." }
        val startVertices = graph.getVertices()
            .filter { it.inEdges.count() == 0 }
        logger.trace { "Got ${startVertices.count()} start vertices for graph" }
        return startVertices
    }

    /**
     * Get all vertices which have no outgoing edge
     */
    fun <T : Any> getEndVertices(graph: Graph<T>): List<Vertex<T>> {
        logger.trace { "Getting end vertices for graph..." }
        val endVertices = graph.getVertices()
            .filter { it.outEdges.count() == 0 }
        logger.trace { "Got ${endVertices.count()} end vertices for graph" }
        return endVertices
    }

    /**
     * Assign ascending numbers from start vertices to end vertices.
     */
    fun <T : Any> populateOrders(graph: Graph<T>) {
        logger.trace { "Populating orders for graph..." }
        val startVertices = getStartVertices(graph)
        startVertices.forEach { vertex -> populateOrders(vertex, 0) }

        val maximumOrder = graph.getVertices().maxOf { it.order!! }
        graph.getVertices().forEach { it.scaledOrder = it.order!!.toDouble() / maximumOrder }

        logger.trace { "Populated orders for graph" }
    }

    fun <T : Any> populateOrders(vertex: Vertex<T>, order: Int) {
        if (vertex.order == null || vertex.order!! < order) {
            logger.trace { "Populating order for vertex '$vertex' with '$order'..." }
            vertex.order = order
        } else {
            logger.trace { "Skipping populating order for vertex '$vertex' with '$order' because it is already higher (${vertex.order})..." }
        }

        val successors = vertex.outEdges.map { edge -> edge.end }
        successors.forEach { succeedingVertex -> populateOrders(succeedingVertex, order + 1) }
    }

    fun <T : Any> findCycles(graph: Graph<T>) {
        logger.trace { "Finding cycles in graph..." }
        val startVertices = getStartVertices(graph) // TODO: might not find isolated cycles or cycles at the start
        startVertices.forEach { vertex -> findCycles(vertex, listOf()) }
        logger.trace { "Found cycles in graph" }
    }

    /**
     * Search for cycles
     */
    fun <T : Any> findCycles(vertex: Vertex<T>, visitedVertices: List<Vertex<T>>): Boolean {
        val newVisitedVertices = listOf(*visitedVertices.toTypedArray(), vertex)

        if (visitedVertices.contains(vertex)) {
            logger.info {
                "Found cycle in graph while visiting '$vertex' in this path: ${
                    newVisitedVertices.joinToString(
                        "→"
                    )
                }"
            }
            // return true if cycle is found
            return true
        }

        val successors = vertex.outEdges.map { edge -> edge.end }
        successors.forEach { succeedingVertex -> findCycles(succeedingVertex, newVisitedVertices) }
        return false
    }
}