package de.debuglevel.graphlibrary.export

import guru.nidi.graphviz.engine.Format
import guru.nidi.graphviz.engine.Graphviz
import mu.KotlinLogging
import java.io.OutputStream

/**
 * Renders the graph via a Java graphviz port
 */
object GraphvizExporter {
    private val logger = KotlinLogging.logger {}

    /**
     * Renders a dot graph definition to a media file.
     * @param dot the graphviz dot source
     * @param outputStream OutputStream to write into
     * @param format media format to render to
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