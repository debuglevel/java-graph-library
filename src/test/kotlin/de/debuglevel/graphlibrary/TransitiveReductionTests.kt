package de.debuglevel.graphlibrary

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TransitiveReductionTests {

    @Test
    fun `transitive reduce graph`() {
        // Arrange
        val graph = Graph<String>()
        val vertex1 = Vertex(content = "Vertex1", color = Color.Blue, Shape.Ellipse, tooltip = "", text = "Vertex1")
        val vertex2 = Vertex(content = "Vertex2", color = Color.Blue, Shape.Ellipse, tooltip = "", text = "Vertex2")
        val vertex3 = Vertex(content = "Vertex3", color = Color.Blue, Shape.Ellipse, tooltip = "", text = "Vertex3")
        val vertices = listOf(vertex1, vertex2, vertex3)
        vertices.forEach { graph.addVertex(it) }

        val edge1to2 = Edge(vertex1, vertex2)
        val edge2to3 = Edge(vertex2, vertex3)
        val edge1to3 = Edge(vertex1, vertex3)
        val allEdges = listOf(edge1to2, edge1to3, edge2to3)
        val remainingEdges = listOf(edge1to2, edge2to3)
        allEdges.forEach { graph.addEdge(it) }

        // Act
        TransitiveReduction.reduce(graph)

        // Assert
        assertThat(graph.vertices).containsExactlyInAnyOrder(*vertices.toTypedArray())
        assertThat(graph.edges).containsExactlyInAnyOrder(*remainingEdges.toTypedArray())
        assertThat(graph.edges).doesNotContain(edge1to3)
    }
}