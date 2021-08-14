package de.debuglevel.graphlibrary

object TestDataProvider {
    fun getMinimalGraph(): Graph<String> {
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
        allEdges.forEach { graph.addEdge(it) }

        return graph
    }
}