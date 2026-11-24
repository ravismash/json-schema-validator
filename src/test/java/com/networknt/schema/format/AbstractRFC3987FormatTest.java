/*
 * Copyright (c) 2026 the original author or authors.
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
package com.networknt.schema.format;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * Tests that a subclass of {@link AbstractRFC3987Format} gets the RFC 3987 rules without repeating them.
 */
class AbstractRFC3987FormatTest {
    private static final Format FORMAT = new AbstractRFC3987Format() {
        @Override
        protected boolean validateIri(URI uri) {
            return true;
        }

        @Override
        public String getName() {
            return "test-iri";
        }
    };

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "http://ƒøø.ßår/ | true",
            "http://â@example.com/ | true",
            "http://ex\u202Eample/ | false",
            "http://example.com/a\u2066b | false",
            "/a\u200Eb | false",
            "http://example.com/a%E2%80%AEb | true",
    })
    void subclass(String value, boolean valid) {
        assertEquals(valid, FORMAT.matches(null, value), value);
    }
}
