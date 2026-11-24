package com.networknt.schema.format;

import java.net.URI;

/**
 * {@link Format} for RFC 3987 Internationalized Resource Identifiers (IRIs).
 * <p>
 * Allows the non-ASCII characters RFC 3987 allows ({@code ucschar}, and {@code iprivate} in the query), and rejects
 * bidirectional formatting characters anywhere in the value. Subclasses check the rest in {@link #validateIri(URI)}.
 */
public abstract class AbstractRFC3987Format extends AbstractRFC3986Format {
    @Override
    protected boolean validate(URI uri) {
        return !containsBidiFormatting(uri.toString()) && validateIri(uri);
    }

    /**
     * Determines if the iri matches the format. Called only for a value without bidirectional formatting
     * characters.
     *
     * @param uri the iri to match
     * @return true if matches
     */
    protected abstract boolean validateIri(URI uri);

    @Override
    protected final boolean isInternationalized() {
        return true;
    }
}
