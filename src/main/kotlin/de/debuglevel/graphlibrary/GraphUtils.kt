package de.debuglevel.graphlibrary

import mu.KotlinLogging

object GraphUtils {
    private val logger = KotlinLogging.logger {}

    /**
     * Whether a path between start and end (other than the ignoredEdge, if given) exists.
     */
    fun <T : Any> pathExists(start: Vertex<T>, end: Vertex<T>, ignoredEdge: Edge<T>?) =
        findVertex(start, end, ignoredEdge)

    /**
     * Walk tree (starting from "start") to find [Vertex] "breakCondition".
     */
    private fun <T : Any> findVertex(
        start: Vertex<T>,
        breakCondition: Vertex<T>,
        ignoredEdge: Edge<T>?
    ): Boolean {
        val descendants =
            when {
                // Only filter if ignoredEdge is not null (i.e. we are on the first level of the recursive tree). Saves about 50% time.
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
     * Gets all [Vertices](Vertex) which have no incoming [Edge].
     */
    fun <T : Any> getStartVertices(graph: Graph<T>): List<Vertex<T>> {
        logger.trace { "Getting start vertices for graph..." }
        val startVertices = graph.vertices
            .filter { it.inEdges.isEmpty() }
        logger.trace { "Got ${startVertices.count()} start vertices for graph" }
        return startVertices
    }

    /**
     * Gets all [Vertices](Vertex) which have no outgoing [Edge].
     */
    fun <T : Any> getEndVertices(graph: Graph<T>): List<Vertex<T>> {
        logger.trace { "Getting end vertices for graph..." }
        val endVertices = graph.vertices
            .filter { it.outEdges.isEmpty() }
        logger.trace { "Got ${endVertices.count()} end vertices for graph" }
        return endVertices
    }

    /**
     * Assigns ascending numbers from start to end [Vertices](Vertex).
     */
    fun <T : Any> populateOrders(graph: Graph<T>) {
        logger.trace { "Populating orders for graph..." }
        val startVertices = getStartVertices(graph)
        startVertices.forEach { vertex -> populateOrders(vertex, 0) }

        val maximumOrder = graph.vertices.maxOf { it.order!! }
        graph.vertices.forEach { it.scaledOrder = it.order!!.toDouble() / maximumOrder }

        logger.trace { "Populated orders for graph" }
    }

    private fun <T : Any> populateOrders(vertex: Vertex<T>, order: Int) {
        if (vertex.order == null || vertex.order!! < order) {
            logger.trace { "Populating order for vertex '$vertex' with '$order'..." }
            vertex.order = order
        } else {
            logger.trace { "Skipping populating order for vertex '$vertex' with '$order' because it is already higher (${vertex.order})..." }
        }

        val successors = vertex.outEdges.map { edge -> edge.end }
        successors.forEach { succeedingVertex -> populateOrders(succeedingVertex, order + 1) }
    }

    /**
     * Searches for cycles.
     */
    fun <T : Any> findCycles(graph: Graph<T>): Boolean {
        logger.trace { "Searching cycles in graph..." }
        var startVertices =
            getStartVertices(graph) // TODO: Might not find isolated cycles or graphs that only consist of 1 circle

        if (startVertices.isEmpty() && graph.vertices.any()) {
            // of no start vertex is found but the graph actually has vertices, just pick a random one
            // (although this is probably always due to a cyclic graph; could return true therefore as a shortcut)
            startVertices = listOf(graph.vertices.random())
        }

        val cyclesFound = startVertices.map { vertex -> findCycles(vertex, listOf()) }
            .any { it }
        logger.trace { "Searched cycles in graph: $cyclesFound" }
        return cyclesFound
    }

    private fun <T : Any> findCycles(vertex: Vertex<T>, visitedVertices: List<Vertex<T>>): Boolean {
        val newVisitedVertices = listOf(*visitedVertices.toTypedArray(), vertex)

        // Check if current visited vertex was already visited before
        if (visitedVertices.contains(vertex)) {
            logger.info {
                "Found cycle in graph while visiting '$vertex' in this path: ${
                    newVisitedVertices.joinToString("→")
                }"
            }
            // Return true if cycle is found
            return true
        }

        val successors = vertex.outEdges.map { edge -> edge.end }
        val cyclesFound = successors
            .map { succeedingVertex -> findCycles(succeedingVertex, newVisitedVertices) }
            .any { it }
        return cyclesFound
    }
}