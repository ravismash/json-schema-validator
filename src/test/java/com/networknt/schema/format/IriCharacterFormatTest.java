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

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * Tests that iri and iri-reference allow only the non-ASCII characters RFC 3987 allows: {@code ucschar}, and
 * {@code iprivate} in the query.
 */
class IriCharacterFormatTest {
    private static final Format IRI = new IriFormat();
    private static final Format IRI_REFERENCE = new IriReferenceFormat();

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "http://x/\u00E9?\u00E9#\u00E9 | true",
            // iprivate only in the query
            "http://x/?q=\uE000 | true",
            "http://x/?q=\uDB80\uDC00 | true",
            "http://x/\uE000 | false",
            "http://x/\uDB80\uDC00 | false",
            "http://x/?a#\uE000 | false",
            "http://\uE000.example/ | false",
            // noncharacters and lone surrogates
            "http://x/\uFDD0 | false",
            "http://x/?q=\uFFFE | false",
            "http://x/\uD800 | false",
            "about:#\uFFFF | false",
    })
    void iri(String value, boolean valid) {
        assertEquals(valid, IRI.matches(null, value), value);
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "?\uE000 | true",
            "#\uFFFE | false",
            "/a\uE000 | false",
            "/a\u00E9 | true",
    })
    void iriReference(String value, boolean valid) {
        assertEquals(valid, IRI_REFERENCE.matches(null, value), value);
    }
}
