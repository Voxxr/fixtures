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

package com.voxxr.fixtures;


import com.voxxr.fixtures.exception.FixtureException;
import com.voxxr.fixtures.exception.InvalidFixtureException;
import com.voxxr.fixtures.sort.DependencySorter;
import com.voxxr.fixtures.sort.OrderSorter;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * Fixture loader.
 *
 * Given a set of fixtures, this loader will assess them to determine if they need to be sorted
 * into a particular order for execution. Once sorting has been completed, color iteration
 * is used to load each fixture.
 *
 * @author Anthony Howell <anthonyhowell@gmail.com>
 */
public class FixtureLoader {

    private ReferenceRepository referenceRepository;

    // Raw fixture list (provided)
    private List<Fixture> fixtures = new LinkedList<>();

    // Sorted fixture list (computed)
    private LinkedList<Fixture> sortedFixtures = new LinkedList<>();

    private OrderSorter orderSorter = new OrderSorter();

    private DependencySorter dependencySorter = new DependencySorter();

    public FixtureLoader(List<Fixture> fixtures) throws InvalidFixtureException {
        for (Fixture fixture : fixtures) {
            addFixture(fixture);
        }

        this.referenceRepository = new ReferenceRepository();
    }

    public void addFixture(Fixture fixture) throws InvalidFixtureException {
        if (fixtureIsOrdered(fixture) && fixtureIsDependent(fixture)) {
            throw new InvalidFixtureException(fixture.getClass().getName());
        }

        fixtures.add(fixture);
    }

    public void load() throws FixtureException {
        fixtures.stream()
                .filter(f -> !f.getClass().isInstance(OrderSorter.class))
                .filter(f -> !f.getClass().isInstance(DependentFixture.class))
                .forEach(sortedFixtures::add);

        if (orderSorter.shouldSort(fixtures)) {
            sortedFixtures = (LinkedList<Fixture>) orderSorter.sort(fixtures);
        }

        if (dependencySorter.shouldSort(sortedFixtures)) {
            sortedFixtures = (LinkedList<Fixture>) dependencySorter.sort(sortedFixtures);
        }

        sortedFixtures.forEach(this::loadFixture);
    }

    private void loadFixture(Fixture fixture) {
        // Determine if fixture wants the reference repository
        if (AbstractFixture.class.isInstance(fixture)) {
            ((AbstractFixture) fixture).setReferenceRepository(referenceRepository);
        }

        fixture.load();
    }

    public boolean hasFixture(Class<? extends Fixture> fixture) {
        return (fixtures != null) && (fixtures.size() > 0) && (fixtures.stream().anyMatch(fixture::isInstance));
    }

    public Optional<Fixture> getFixture(Class<? extends Fixture> fixture) {
        return fixtures.stream().filter(fixture::isInstance).findFirst();
    }

    private boolean fixtureIsOrdered(Fixture fixture) {
        return OrderedFixture.class.isAssignableFrom(fixture.getClass());
    }

    private boolean fixtureIsDependent(Fixture fixture) {
        return DependentFixture.class.isAssignableFrom(fixture.getClass());
    }

    public ReferenceRepository getReferenceRepository() {
        return referenceRepository;
    }

}
