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

import java.util.*;

/**
 *  A  -->  D  -
 *   \          \
 *     ---       G ----
 *         \           \
 *  B  -->  E  ------>  H
 *                     /
 *     C  ---->  F  --
 *
 *   I       J
 *
 *
 * If the edges in a graph have a direction, the graph is called a directed graph. The arrows shown in the figure above
 * depict the direction of the edges. We also include two vertices here to represent isolated objects that don't depend
 * on other objects.
 *
 * The arrows in the figure represent evaluation direction. For example, to evaluate E, we must first evaluate A and B.
 * To evaluate H, we might wanted a sorted collection like this:
 *
 * ADBECFDGH or ADGBECFH
 *
 * There are many other possible orderings, depending on the approach you take to implement the topological sort.
 *
 * @author Anthony Howell <anthonyhowell@gmail.com>
 */
public class DirectedGraph<T> implements Iterable<T> {

    // Key is a node
    // Value is a set of nodes connected by outgoing edges from the key
    private final Map<T, Set<T>> graph = new HashMap<>();


    public boolean addNode(T node) {
        if (graph.containsKey(node)) {
            return false;
        }

        graph.put(node, new HashSet<>());
        return true;
    }

    public void addNodes(Collection<T> nodes) {
        nodes.forEach(this::addNode);
    }

    public boolean nodeExists(T node) {
        return graph.containsKey(node);
    }

    public void addEdge(T src, T dest) {
        validateSourceAndDestinationNodes(src, dest);
        // Add the edge by adding the destination node in to the outgoing edges
        graph.get(src).add(dest);
    }

    public void removeEdge(T src, T dest) {
        validateSourceAndDestinationNodes(src, dest);
        graph.get(src).remove(dest);
    }

    public boolean hasEdge(T src, T dest) {
        validateSourceAndDestinationNodes(src, dest);
        return graph.get(src).contains(dest);
    }

    public Set<T> edgesFrom(T node) {
        // Check that the node exists
        Set<T> edges = graph.get(node);
        if (edges == null) {
            throw new NoSuchElementException("Source node does not exist");
        }

        return Collections.unmodifiableSet(edges);
    }

    public Iterator<T> iterator() {
        return graph.keySet().iterator();
    }

    public int size() {
        return graph.size();
    }

    public boolean isEmpty() {
        return graph.isEmpty();
    }

    private void validateSourceAndDestinationNodes(T src, T dest) {
        if (!graph.containsKey(src) || !graph.containsKey(dest)) {
            throw new NoSuchElementException("Both src and dest nodes must be in the graph");
        }
    }



}
