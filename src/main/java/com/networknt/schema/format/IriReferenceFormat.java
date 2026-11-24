package com.networknt.schema.format;

import java.net.URI;

/**
 * Format for iri-reference.
 */
public class IriReferenceFormat extends AbstractRFC3987Format {
    @Override
    protected boolean validateIri(URI uri) {
        return true;
    }

    @Override
    public String getName() {
        return "iri-reference";
    }
    
    @Override
    public String getMessageKey() {
        return "format.iri-reference";
    }

}
