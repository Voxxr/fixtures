/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.voxxr.fixtures.sort;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @author Anthony Howell <anthonyhowell@gmail.com>
 */
public class TopologicalSort {

    public static <T> List<T> sort(DirectedGraph<T> graph) {
        DirectedGraph<T> reversedGraph = reverseGraph(graph);

        List<T> result = new LinkedList<>();
        Set<T> visited = new HashSet<>();

        /**
         * We also maintain a third set consisting of all the nodes that have been
         * fully expanded. If the graph contains a cycle, then we can detect this by noting that a
         * node has been explored but not fully expanded.
         */
        Set<T> expanded = new HashSet<>();

        // Fire off a Depth-First search from each node in the graph
        for (T node : reversedGraph) {
            explore(node, reversedGraph, result, visited, expanded);
        }

        return result;
    }

    /**
     * Recursively performs a Depth-First search from the specified node, marking all nodes
     * encountered by the search.
     *
     * @param node      The node to begin the search from
     * @param graph     The graph in which to perform the search
     * @param result    A list holding the topological sort of the graph
     * @param visited   A set of nodes that have already been visited
     * @param expanded  A set of nodes that have been fully expanded
     * @param <T>
     */
    private static <T> void explore(T node, DirectedGraph<T> graph, List<T> result, Set<T> visited, Set<T> expanded) {
        if (visited.contains(node)) {
            // If this node has already been expanded, then its already ben assigned a position in the final
            // topological sort and we don't need to explore it again
            if (expanded.contains(node)) return;

            // If it hasn't been expanded, it means we've just found a node that is currently being explored,
            // and therefore is part of a cycle. In this case, we should report an error.
            throw new IllegalArgumentException("A cycle was detected within the graph when exploring node(" + node.toString() + ")");
        }

        visited.add(node);

        // Recursively explore all predecessors of this node
        for (T predecessor  : graph.edgesFrom(node)) {
            explore(predecessor, graph, result, visited, expanded);
        }

        result.add(node);
        expanded.add(node);
    }

    private static <T> DirectedGraph<T> reverseGraph(DirectedGraph<T> graph) {
        DirectedGraph<T> result = new DirectedGraph<>();

        // Add all the nodes from the original graph
        for (T node : graph) {
            result.addNode(node);
        }

        // Scan over all the edges in the graph, adding their reverse to the reversed graph
        for (T node : graph) {
            for (T endpoint : graph.edgesFrom(node)) {
                result.addEdge(endpoint, node);
            }
        }

        return result;
    }

}
