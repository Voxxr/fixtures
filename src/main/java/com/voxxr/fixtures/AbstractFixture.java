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



import com.voxxr.fixtures.exception.ReferenceOutOfBoundsException;

import java.awt.*;
import java.text.Normalizer;

/**
 * @author Anthony Howell <anthonyhowell@gmail.com>
 */
abstract public class AbstractFixture implements Fixture, SharedFixture {

    protected ReferenceRepository referenceRepository;

    public void setReferenceRepository(ReferenceRepository referenceRepository) {
        this.referenceRepository = referenceRepository;
    }

    public void setReference(String name, Object obj) {
        referenceRepository.setReference(name, obj);
    }

    public Object getReference(String name) throws ReferenceOutOfBoundsException {
        return referenceRepository.getReference(name);
    }

    public boolean hasReference(String name) {
        return referenceRepository.hasReference(name);
    }

    public String normalizeName(String name) {
        String normalized = Normalizer.normalize(name, Normalizer.Form.NFKD)
                .replaceAll("[^\\p{ASCII}]+", "")
                .replaceAll("(?:[^\\w+]|\\s\\+)+", "-")
                .replaceAll("^-|-$", "");
        return normalized;
    }

}
