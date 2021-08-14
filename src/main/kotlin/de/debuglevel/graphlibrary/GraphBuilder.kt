package de.debuglevel.graphlibrary

import mu.KotlinLogging
import kotlin.system.measureTimeMillis

/**
 * Builds a graph by retrieving information through a NodeInformationRetriever
 */
class GraphBuilder<T : Any>(
    private val nodeInformationRetriever: NodeInformationRetriever<T>
) {
    private val logger = KotlinLogging.logger {}

    /**
     * Build the graph
     */
    fun build(
        nodes: List<T>,
        transitiveReduction: Boolean
    ): Graph<T> {
        logger.debug { "Creating graph..." }

        val graph = Graph<T>()

        // build all vertices (defined by the given list of nodes)
        val vertices = nodes.map { node ->
            val color = nodeInformationRetriever.getColor(node)
            val shape = nodeInformationRetriever.getShape(node)
            val tooltip: String = nodeInformationRetriever.getTooltip(node)
            val text: String = nodeInformationRetriever.getText(node)

            val vertex = Vertex(node, text, tooltip, color, shape)
            graph.addVertex(vertex)
            vertex
        }

        // add edges between vertices (defined by the NodeInformationRetriever which knows how to get predecessors and successors)
        for (vertex in vertices) {
            nodeInformationRetriever.getPrecedingVertices(vertex, vertices)
                .forEach { precedingVertex -> graph.addEdge(Edge(precedingVertex, vertex)) }

            nodeInformationRetriever.getSucceedingVertices(vertex, vertices)
                .forEach { succeedingVertex -> graph.addEdge(Edge(vertex, succeedingVertex)) }
        }

        // remove redundant edges (if wanted)
        if (transitiveReduction) {
            val duration = measureTimeMillis { TransitiveReduction.reduce(graph) }
            logger.debug { "Removing superseded edges took ${duration}ms" }
        }

        logger.debug { "Created graph" }
        return graph
    }
}