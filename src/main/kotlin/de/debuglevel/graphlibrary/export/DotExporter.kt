package de.debuglevel.graphlibrary.export

import de.debuglevel.graphlibrary.Graph
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

        for (vertex in graph.getVertices()) {
            s += "${vertex.hashCode()}[label=\"${vertex.text}\",fillcolor=${vertex.color.graphvizValue},style=filled,shape=${vertex.shape.graphvizValue},tooltip=\"${vertex.tooltip}\"];\n"
        }

        for (edge in graph.getEdges()) {
            s += "${edge.start.hashCode()} -> ${edge.end.hashCode()};\n"
        }

        s += "}\n"

        logger.debug { "Generating GraphViz dot source done." }
        return s
    }
}