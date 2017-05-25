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


import com.voxxr.fixtures.exception.ReferenceExistsException;
import com.voxxr.fixtures.exception.ReferenceOutOfBoundsException;

import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Anthony Howell <anthonyhowell@gmail.com>
 */
public class ReferenceRepository2 {

    private ConcurrentHashMap<String, Reference<?>> references = new ConcurrentHashMap<>();


    public ReferenceRepository2() {}

    public boolean hasReference(String name) {
        return this.references.containsKey(name);
    }

    public void addReference(String name, Reference<?> ref) throws ReferenceExistsException {
        if (hasReference(name)) {
            throw new ReferenceExistsException(name);
        }

        setReference(name, ref);
    }

    public void setReference(String name, Reference<?> ref) {
        this.references.put(name, ref);
    }

    public Reference<?> getReference(String name) throws ReferenceOutOfBoundsException {
        if (!hasReference(name)) {
            throw new ReferenceOutOfBoundsException(name);
        }

        return this.references.get(name);
    }

    public Enumeration<String> getReferenceNames() {
        return references.keys();
    }

    public ConcurrentHashMap<String, Reference<?>> getReferences() {
        return references;
    }

}
