package de.debuglevel.graphlibrary.export

import guru.nidi.graphviz.engine.Format
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.io.ByteArrayOutputStream

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GraphvizExporterTests {
    data class TestFormat(val format: Format, val magicBytes: ByteArray)

    fun getFormats(): List<TestFormat> {
        return listOf(
            TestFormat(
                Format.PNG,
                byteArrayOf(0x89.toByte(), 0x50, 0x4E, 0x47, 0x0D, 0x0A, 0x1A, 0x0A)
            ),
            TestFormat(
                Format.SVG_STANDALONE,
                "<?xml".toByteArray()
            ),
            TestFormat(
                Format.SVG,
                "<svg".toByteArray()
            ),
        )
    }

    @ParameterizedTest
    @MethodSource("getFormats")
    fun `render to formats`(testFormat: TestFormat) {
        // Arrange
        val dot = "digraph { start -> end }"

        // Act
        val byteArrayOutputStream = ByteArrayOutputStream()
        GraphvizExporter.render(dot, byteArrayOutputStream, format = testFormat.format)
        val bytes = byteArrayOutputStream.toByteArray()

        // Assert
        Assertions.assertThat(bytes).startsWith(*testFormat.magicBytes)
    }
}