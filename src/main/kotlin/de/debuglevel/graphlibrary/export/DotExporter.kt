package de.debuglevel.graphlibrary.export

import de.debuglevel.graphlibrary.Graph
import de.debuglevel.graphlibrary.Vertex
import mu.KotlinLogging

/**
 * Exports the graph to a graphviz dot file
 */
object DotExporter {
    private val logger = KotlinLogging.logger {}

    /**
     * Generate the dot content.
     */
    fun generate(graph: Graph<*>): String {
        logger.debug { "Generating GraphViz dot source..." }

        var s = " digraph graphname {\n"

        for (vertex in graph.vertices) {
            val attributesString = builtAttributes(vertex)
            s += "${vertex.hashCode()}[$attributesString];\n"
        }

        for (edge in graph.edges) {
            s += "${edge.start.hashCode()} -> ${edge.end.hashCode()};\n"
        }

        s += "}\n"

        logger.debug { "Generating GraphViz dot source done." }
        return s
    }

    private fun builtAttributes(vertex: Vertex<out Any>): String {
        val attributes = mapOf(
            "label" to "\"${vertex.text}\"",
            "fillcolor" to vertex.color.graphvizValue,
            "style" to "filled",
            "shape" to vertex.shape.graphvizValue,
            "tooltip" to "${vertex.tooltip}"
        )

        val attributesString = attributes
            .filter { it.value.isNotEmpty() }
            .map { "${it.key}=${it.value}" }
            .joinToString(",")

        return attributesString
    }
}