package de.debuglevel.graphlibrary.export

import de.debuglevel.graphlibrary.Graph
import guru.nidi.graphviz.engine.Format
import guru.nidi.graphviz.engine.Graphviz
import mu.KotlinLogging
import java.io.OutputStream

/**
 * Renders a [Graph] via a Java GraphViz port.
 */
object GraphvizExporter {
    private val logger = KotlinLogging.logger {}

    /**
     * Renders a [dot] graph definition into the [outputStream] in the specified [format].
     */
    fun render(dot: String, outputStream: OutputStream, format: Format) {
        logger.debug { "Rendering graph visualization to OutputStream '$outputStream'..." }

        val graph = guru.nidi.graphviz.parse.Parser().read(dot)
        Graphviz
            .fromGraph(graph)
            .render(format)
            .toOutputStream(outputStream)

        logger.debug { "Rendering graph visualization done." }
    }
}