package org.openmrs.module.referencemetadata.metadatasharing;

import org.openmrs.ConceptSource;
import org.openmrs.api.ConceptService;
import org.openmrs.module.metadatasharing.resolver.Resolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Since we run in MIRROR import mode, MDS isn't checking for possible matches.
 */
@Component("referencemetadata.ConceptSourceByNameResolver")
public class ConceptSourceByNameResolver extends Resolver<ConceptSource> {

    @Autowired
    ConceptService conceptService;

    @Override
    public ConceptSource getExactMatch(ConceptSource incoming) {
        ConceptSource existing = conceptService.getConceptSourceByName(incoming.getName());
        return existing;
    }

    @Override
    public ConceptSource getPossibleMatch(ConceptSource incoming) {
        return null;
    }
}
