package de.debuglevel.graphlibrary

import mu.KotlinLogging

object TransitiveReduction {
    private val logger = KotlinLogging.logger {}

    /**
     * Performs a transitive reduction on the [graph].
     * All [Edge]s which provide shortcuts by bypassing a [Vertex] will be removed.
     * A [graph] with as few [Edge]s as possible is the result.
     * May stuck in a loop or throw an OverflowException if the [graph] is cyclic.
     */
    fun <T : Any> reduce(graph: Graph<T>) {
        logger.debug { "Performing transitive reduction on graph..." }

        for (edge in graph.edges) {
            if (GraphUtils.pathExists(edge.start, edge.end, edge)) {
                graph.removeEdge(edge)
                logger.debug { "Removed superseded edge: $edge" }
            }
        }

        logger.debug { "Performed transitive reduction on graph." }
    }
}