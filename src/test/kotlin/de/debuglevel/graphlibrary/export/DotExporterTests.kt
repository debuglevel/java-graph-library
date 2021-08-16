package de.debuglevel.graphlibrary.export

import de.debuglevel.graphlibrary.*
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class DotExporterTests {
    @Test
    fun generate() {
        // Arrange
        val graph = Graph<String>()
        val vertex1 =
            Vertex(content = "Vertex1", text = "Vertex1", tooltip = "Tooltip1", color = Color.Blue, Shape.Ellipse)
        val vertex2 =
            Vertex(content = "Vertex2", text = "Vertex2", tooltip = "Tooltip2", color = Color.Red, Shape.Rectangle)
        val vertices = listOf(vertex1, vertex2)
        vertices.forEach { graph.addVertex(it) }

        val edge1to2 = Edge(vertex1, vertex2)
        val allEdges = listOf(edge1to2)
        allEdges.forEach { graph.addEdge(it) }

        // Act
        val dot = DotExporter.generate(graph)

        // Assert
        Assertions.assertThat(dot).containsPattern(" -> ")
        Assertions.assertThat(dot).containsPattern("label.*Vertex1")
        Assertions.assertThat(dot).containsPattern("label.*Vertex2")
        Assertions.assertThat(dot).containsPattern("tooltip.*Tooltip1")
        Assertions.assertThat(dot).containsPattern("tooltip.*Tooltip2")
        Assertions.assertThat(dot).containsPattern("shape.*ellipse")
        Assertions.assertThat(dot).containsPattern("shape.*rectangle")
        Assertions.assertThat(dot).containsPattern("fillcolor.*red")
        Assertions.assertThat(dot).containsPattern("fillcolor.*blue")
    }
}