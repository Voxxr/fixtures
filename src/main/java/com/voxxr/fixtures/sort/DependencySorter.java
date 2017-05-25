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


import com.voxxr.fixtures.DependentFixture;
import com.voxxr.fixtures.Fixture;
import com.voxxr.fixtures.exception.SortException;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * @author Anthony Howell <anthonyhowell@gmail.com>
 */
public class DependencySorter extends AbstractSorter implements FixtureSorter {

    @Override
    public boolean shouldSort(List<Fixture> fixtures) {
        Optional<Fixture> fixture = fixtures.stream()
                .filter(DependentFixture.class::isInstance)
                .findFirst();

        return (fixture.isPresent()) ? true : false;
    }

    @Override
    public List<Fixture> sort(List<Fixture> fixtures) throws SortException {
        LinkedList<Fixture> sortedFixtures = new LinkedList<>();
        fixtures.forEach(sortedFixtures::add);

        DirectedGraph<Fixture> graph = new DirectedGraph<>();

        // It is possible to perform an ordered sort and a dependency sort. In the event an ordered
        // sort has been performed, dependent fixtures were also included in that sort. We'll want
        // to remove them from the sorted list so the dependency sort can resolve their execution
        // order properly (prevents duplicates in the final sort).
        Iterator<Fixture> iterator = sortedFixtures.iterator();
        while (iterator.hasNext()) {
            if (fixtureIsDependent(iterator.next())) {
                iterator.remove();
            }
        }

        for (Fixture fixture : fixtures) {
            // Don't reprocess a fixture more than once
            if (sortedFixtures.contains(fixture)) continue;

            // If the fixture is not dependent, well skipt trying to check its dependencies
            if (!fixtureIsDependent(fixture)) continue;

            // Add the fixture as a root node
            graph.addNode(fixture);

            // For every dependency, add a node, and its edge
            for (Class depClass : ((DependentFixture) fixture).getDependencies()) {
                Fixture dep = findFixtureByClass(depClass, fixtures);

                if (dep == null) {
                    throw new SortException("Dependency not found (" + depClass.getName() + ")");
                }

                if (!graph.nodeExists(dep)) {
                    graph.addNode(dep);
                }

                graph.addEdge(dep, fixture);
            }
        }

        LinkedList<Fixture> graphedFixtures = (LinkedList<Fixture>) TopologicalSort.sort(graph);

        graphedFixtures
                .stream()
                .filter(fixture -> !sortedFixtures.contains(fixture))
                .forEach(sortedFixtures::add);

        return sortedFixtures;
    }

    private Fixture findFixtureByClass(Class clazz, List<Fixture> fixtures) {
        for (Fixture fixture : fixtures) {
            if (clazz.isInstance(fixture)) {
                return fixture;
            }
        }

        return null;
    }

}
