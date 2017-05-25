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


import com.voxxr.fixtures.Fixture;
import com.voxxr.fixtures.OrderedFixture;
import com.voxxr.fixtures.exception.SortException;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * @author Anthony Howell <anthonyhowell@gmail.com>
 */
public class OrderSorter extends AbstractSorter implements FixtureSorter {

    @Override
    public boolean shouldSort(List<Fixture> fixtures) {
        Optional<Fixture> fixture = fixtures.stream()
                .filter(OrderedFixture.class::isInstance)
                .findFirst();

        return (fixture.isPresent()) ? true : false;
    }

    @Override
    public List<Fixture> sort(List<Fixture> fixtures) throws SortException {
        List<Fixture> sortedFixtures = new LinkedList<>();

        fixtures.forEach(sortedFixtures::add);

        Collections.sort(sortedFixtures, (Fixture o1, Fixture o2) -> {
            if (fixtureIsOrdered(o1) && fixtureIsDependent(o2)) {
                if (((OrderedFixture) o1).getOrder() == ((OrderedFixture) o2).getOrder()) {
                    return 0;
                }
                return (((OrderedFixture) o1).getOrder() < ((OrderedFixture) o2).getOrder()) ? -1 : 1;
            } else if (fixtureIsOrdered(o1)) {
                return ((OrderedFixture) o1).getOrder() == 0 ? 0 : 1;
            } else if (fixtureIsOrdered(o2)) {
                return ((OrderedFixture) o2).getOrder() == 0 ? 0 : -1;
            }

            return 0;
        });

        return sortedFixtures;
    }

}
