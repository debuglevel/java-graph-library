package de.debuglevel.graphlibrary

object TestDataProvider {
    /**
     * A -> B -> C
     *  `------>´
     */
    fun getMinimalGraph(): Graph<String> {
        val graph = getTwoStepGraph()
        val vertex1 = graph.vertices.single { it.content == "Vertex1" }
        val vertex3 = graph.vertices.single { it.content == "Vertex3" }

        val edge1to3 = Edge(vertex1, vertex3)
        graph.addEdge(edge1to3)

        return graph
    }

    /**
     * A -> B -> C
     * D ->´
     */
    fun getTwoStartsGraph(): Graph<String> {
        val graph = getTwoStepGraph()
        val vertex2 = graph.vertices.single { it.content == "Vertex2" }
        val vertex4 = Vertex(content = "Vertex4", color = Color.Blue, Shape.Ellipse, tooltip = "", text = "Vertex4")
        graph.addVertex(vertex4)

        val edge4to2 = Edge(vertex4, vertex2)
        graph.addEdge(edge4to2)

        return graph
    }

    /**
     * A -> B -> C
     *      `--> D
     */
    fun getTwoEndsGraph(): Graph<String> {
        val graph = getTwoStepGraph()
        val vertex2 = graph.vertices.single { it.content == "Vertex2" }
        val vertex4 = Vertex(content = "Vertex4", color = Color.Blue, Shape.Ellipse, tooltip = "", text = "Vertex4")
        graph.addVertex(vertex4)

        val edge2to4 = Edge(vertex2, vertex4)
        graph.addEdge(edge2to4)

        return graph
    }

    /**
     * A -> B -> C
     *      `<--´
     */
    fun getPartCyclicGraph(): Graph<String> {
        val graph = getTwoStepGraph()
        val vertex2 = graph.vertices.single { it.content == "Vertex2" }
        val vertex3 = graph.vertices.single { it.content == "Vertex3" }

        val edge3to2 = Edge(vertex3, vertex2)
        graph.addEdge(edge3to2)

        return graph
    }

    /**
     * A -> B -> C
     *  `<------´
     */
    fun getFullCyclicGraph(): Graph<String> {
        val graph = getTwoStepGraph()
        val vertex1 = graph.vertices.single { it.content == "Vertex1" }
        val vertex3 = graph.vertices.single { it.content == "Vertex3" }

        val edge3to1 = Edge(vertex3, vertex1)
        graph.addEdge(edge3to1)

        return graph
    }

    /**
     * A -> B -> C
     */
    fun getTwoStepGraph(): Graph<String> {
        val graph = Graph<String>()
        val vertex1 = Vertex(content = "Vertex1", color = Color.Blue, Shape.Ellipse, tooltip = "", text = "Vertex1")
        val vertex2 = Vertex(content = "Vertex2", color = Color.Blue, Shape.Ellipse, tooltip = "", text = "Vertex2")
        val vertex3 = Vertex(content = "Vertex3", color = Color.Blue, Shape.Ellipse, tooltip = "", text = "Vertex3")
        val vertices = listOf(vertex1, vertex2, vertex3)
        vertices.forEach { graph.addVertex(it) }

        val edge1to2 = Edge(vertex1, vertex2)
        val edge2to3 = Edge(vertex2, vertex3)
        val allEdges = listOf(edge1to2, edge2to3)
        allEdges.forEach { graph.addEdge(it) }

        return graph
    }
}