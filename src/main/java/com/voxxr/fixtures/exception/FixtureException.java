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

package com.voxxr.fixtures.exception;

/**
 * @author Anthony Howell <anthonyhowell@gmail.com>
 */
public class FixtureException extends Exception {

    private static final long serialVersionUID = 3812848911540694234L;


    public FixtureException() {}

    public FixtureException(String msg) {
        super(msg);
    }

    public FixtureException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public FixtureException(Throwable cause) {
        super(cause);
    }

    public FixtureException(String msg, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(msg, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("\nFixtureException {");
        str.append("\n    Type: " + getClass().getSimpleName());
        str.append("\n    Message: " + getLocalizedMessage());

        if (getCause() != null) {
            str.append("\n    Cause: " + getCause());
        }

        str.append("\n}");

        return str.toString();
    }

}
