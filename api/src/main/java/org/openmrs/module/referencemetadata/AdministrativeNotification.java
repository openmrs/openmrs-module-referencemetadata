package org.openmrs.module.referencemetadata;

import java.util.Collection;

import org.openmrs.Concept;
import org.openmrs.api.context.Context;

public class AdministrativeNotification {
	
	public void notify_duplicate_concepts() {
	Collection<Concept> concepts = Context.getConceptService().getConceptsByName("YES");
	
	 if (concepts.size() == 2) {
		 
		 
	 } else {
		
	 }
}
}
