package com.networknt.schema.format;

import java.net.URI;

/**
 * Format for iri.
 */
public class IriFormat extends AbstractRFC3987Format {
    @Override
    protected boolean validateIri(URI uri) {
        return uri.isAbsolute();
    }

    @Override
    public String getName() {
        return "iri";
    }
    
    @Override
    public String getMessageKey() {
        return "format.iri";
    }
}
