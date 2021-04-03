package de.debuglevel.graphlibrary.export

import guru.nidi.graphviz.engine.Format
import guru.nidi.graphviz.engine.Graphviz
import mu.KotlinLogging
import java.io.File

/**
 * Renders the graph via a Java graphviz port
 */
object GraphvizExporter {
    private val logger = KotlinLogging.logger {}

    /**
     * Renders a dot graph definition to a media file.
     * @param dot the graphviz dot source
     * @param outputFile the destination media file
     * @param format media format to render to
     */
    fun render(dot: String, outputFile: File, format: Format) {
        logger.debug { "Rendering graph visualization to file '$outputFile'..." }

        val graph = guru.nidi.graphviz.parse.Parser.read(dot)
        Graphviz
            .fromGraph(graph)
            .render(format)
            .toFile(outputFile)

        logger.debug { "Rendering graph visualization done." }
    }
}