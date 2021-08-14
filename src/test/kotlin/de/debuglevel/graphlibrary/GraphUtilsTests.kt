package de.debuglevel.graphlibrary

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class GraphUtilsTests {
    @Test
    fun `direct path exists`() {
        // Arrange
        val graph = TestDataProvider.getTwoStepGraph()

        // Act
        val pathExist = GraphUtils.pathExists(
            start = graph.vertices.single { it.content == "Vertex1" },
            end = graph.vertices.single { it.content == "Vertex2" },
            ignoredEdge = null
        )

        // Assert
        Assertions.assertThat(pathExist).isTrue
    }

    @Test
    fun `indirect path exists`() {
        // Arrange
        val graph = TestDataProvider.getTwoStepGraph()

        // Act
        val pathExist = GraphUtils.pathExists(
            start = graph.vertices.single { it.content == "Vertex1" },
            end = graph.vertices.single { it.content == "Vertex3" },
            ignoredEdge = null
        )

        // Assert
        Assertions.assertThat(pathExist).isTrue
    }

    @Test
    fun `indirect path exists ignoring direct edge`() {
        // Arrange
        val graph = TestDataProvider.getMinimalGraph()
        val directEdge = graph.edges.single { it.start.content == "Vertex1" && it.end.content == "Vertex3" }

        // Act
        val pathExist = GraphUtils.pathExists(
            start = graph.vertices.single { it.content == "Vertex1" },
            end = graph.vertices.single { it.content == "Vertex3" },
            ignoredEdge = directEdge
        )

        // Assert
        Assertions.assertThat(pathExist).isTrue
    }

    @Test
    fun `get start vertices`() {
        // Arrange
        val graph = TestDataProvider.getTwoStartsGraph()
        val vertex1 = graph.vertices.single { it.content == "Vertex1" }
        val vertex4 = graph.vertices.single { it.content == "Vertex4" }
        val startVertices = listOf(vertex1, vertex4)

        // Act
        val foundStartVertices = GraphUtils.getStartVertices(graph)

        // Assert
        Assertions.assertThat(foundStartVertices).containsExactlyInAnyOrder(*startVertices.toTypedArray())
    }

    @Test
    fun `get end vertices`() {
        // Arrange
        val graph = TestDataProvider.getTwoEndsGraph()
        val vertex3 = graph.vertices.single { it.content == "Vertex3" }
        val vertex4 = graph.vertices.single { it.content == "Vertex4" }
        val endVertices = listOf(vertex3, vertex4)

        // Act
        val foundEndVertices = GraphUtils.getEndVertices(graph)
        foundEndVertices.forEach { println(it) }

        // Assert
        Assertions.assertThat(foundEndVertices).containsExactlyInAnyOrder(*endVertices.toTypedArray())
    }

    @Test
    fun `populate orders`() {
        // Arrange
        val graph = TestDataProvider.getTwoStartsGraph()
        val vertex1 = graph.vertices.single { it.content == "Vertex1" }
        val vertex2 = graph.vertices.single { it.content == "Vertex2" }
        val vertex3 = graph.vertices.single { it.content == "Vertex3" }

        // Act
        GraphUtils.populateOrders(graph)

        // Assert
        Assertions.assertThat(vertex1.order).isLessThan(vertex2.order)
        Assertions.assertThat(vertex1.scaledOrder).isLessThan(vertex2.scaledOrder)
        Assertions.assertThat(vertex2.order).isLessThan(vertex3.order)
        Assertions.assertThat(vertex2.scaledOrder).isLessThan(vertex3.scaledOrder)
    }

    @Test
    fun `find cycles in partly cyclic graph`() {
        // Arrange
        val graph = TestDataProvider.getPartCyclicGraph()

        // Act
        val cyclesFound = GraphUtils.findCycles(graph)

        // Assert
        Assertions.assertThat(cyclesFound).isTrue
    }

    @Test
    fun `find cycles in fully cyclic graph`() {
        // Arrange
        val graph = TestDataProvider.getFullCyclicGraph()

        // Act
        val cyclesFound = GraphUtils.findCycles(graph)

        // Assert
        Assertions.assertThat(cyclesFound).isTrue
    }

    @Test
    fun `find cycles in non-cyclic graph`() {
        // Arrange
        val graph = TestDataProvider.getTwoStartsGraph()

        // Act
        val cyclesFound = GraphUtils.findCycles(graph)

        // Assert
        Assertions.assertThat(cyclesFound).isFalse
    }
}