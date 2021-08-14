package de.debuglevel.graphlibrary

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TransitiveReductionTests {

    @Test
    fun `transitive reduce graph`() {
        // Arrange
        val graph = TestDataProvider.getMinimalGraph()
        val redundantEdge = graph.edges.single { it.start.content == "Vertex1" && it.end.content == "Vertex3" }
        val remainingEdges = graph.edges.minus(redundantEdge)

        // Act
        TransitiveReduction.reduce(graph)

        // Assert
        assertThat(graph.vertices).containsExactlyInAnyOrder(*graph.vertices.toTypedArray())
        assertThat(graph.edges).containsExactlyInAnyOrder(*remainingEdges.toTypedArray())
        assertThat(graph.edges).doesNotContain(redundantEdge)
    }
}