package com.rolf.day25

import com.rolf.Day
import com.rolf.util.EdgeType
import com.rolf.util.Graph
import com.rolf.util.Vertex
import com.rolf.util.splitLine

fun main() {
    Day25().run()
}

class Day25 : Day() {
    override fun solve1(lines: List<String>) {
        // Visualize with graph.py to find the cuts
        val cuts = setOf(
            "jxb" to "ksq",
            "ksq" to "jxb",
            "nqq" to "pxp",
            "pxp" to "nqq",
            "kns" to "dct",
            "dct" to "kns",
        )


        // Build the graph again without the cuts
        val graph = Graph<Void>()
        for (line in lines) {
            val (from, toList) = splitLine(line, ": ")
            val tos = splitLine(toList, " ")
            graph.addVertex(Vertex(from))
            for (to in tos) {
                val pair = from to to
                if (!cuts.contains(pair)) {
                    graph.addVertex(Vertex(to))
                    graph.addEdge(from, to, edgeType = EdgeType.UNDIRECTED)
                }
            }
        }

        // Look for cliques with size 3 and more to remove from the vertices to inspect
        val edgesToInspect = graph.vertices().map { it.id }.toMutableSet()
        println(edgesToInspect.size)
        for (clique in graph.largestCliques()) {
            if (clique.size >= 3) {
                edgesToInspect.removeAll(clique)
            }
        }
        println(edgesToInspect.size)

        for (vertex in graph.vertices()) {
            val neighbours = graph.neighbours(vertex.id)
            println("${vertex.id}: ${neighbours.size}")
        }

        val connectedComponents = graph.findConnectedComponents()
        assert(connectedComponents.size == 2)
        println(connectedComponents[0].size * connectedComponents[1].size)
    }

    override fun solve2(lines: List<String>) {
    }
}
