package de.debuglevel.graphlibrary.export

import de.debuglevel.graphlibrary.Graph
import de.debuglevel.graphlibrary.Vertex
import mu.KotlinLogging

/**
 * Exports a [Graph] to a GraphViz DOT file.
 */
object DotExporter {
    private val logger = KotlinLogging.logger {}

    /**
     * Generates the DOT content of a [graph].
     */
    fun generate(graph: Graph<*>): String {
        logger.debug { "Generating GraphViz DOT source..." }

        var s = " digraph graphname {\n"

        for (vertex in graph.vertices) {
            val attributesString = buildAttributes(vertex)
            s += "${vertex.hashCode()}[$attributesString];\n"
        }

        for (edge in graph.edges) {
            s += "${edge.start.hashCode()} -> ${edge.end.hashCode()};\n"
        }

        s += "}\n"

        logger.debug { "Generated GraphViz DOT source" }
        return s
    }

    private fun buildAttributes(vertex: Vertex<out Any>): String {
        val attributes = mapOf(
            "label" to vertex.text,
            "fillcolor" to vertex.color.graphvizValue,
            "style" to "filled",
            "shape" to vertex.shape.graphvizValue,
            "tooltip" to "${vertex.tooltip}"
        )

        val attributesString = attributes
            .filter { it.value.isNotEmpty() }
            .map { "${it.key}=\"${escape(it.value)}\"" }
            .joinToString(",")

        return attributesString
    }

    /**
     * Escapes strings according to <https://graphviz.org/doc/info/lang.html> which
     * is actually only that a `"` must be `\"` instead. Nothing further has to be
     * escaped, and even `\\` remains `\\`.
     */
    private fun escape(str: String): String {
        return str.replace("\"", "\\\"")
    }
}