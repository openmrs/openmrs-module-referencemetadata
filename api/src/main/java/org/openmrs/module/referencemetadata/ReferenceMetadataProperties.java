package org.openmrs.module.referencemetadata;

import org.openmrs.EncounterType;
import org.openmrs.VisitType;
import org.openmrs.module.emrapi.utils.ModuleProperties;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component("referenceMetadataProperties")
public class ReferenceMetadataProperties extends ModuleProperties {

    public static final String FACILITY_VISIT_TYPE_UUID = "7b0f5697-27e3-40c4-8bae-f4049abfb4ed";

    public static final String ADMISSION_ENCOUNTER_TYPE_UUID = "e22e39fd-7db2-45e7-80f1-60fa0d5a4378";
    public static final String DISCHARGE_ENCOUNTER_TYPE_UUID = "181820aa-88c9-479b-9077-af92f5364329";
    public static final String TRANSFER_ENCOUNTER_TYPE_UUID = "7b68d557-85ef-4fc8-b767-4fa4f5eb5c23";
    public static final String CHECK_IN_ENCOUNTER_TYPE_UUID = "ca3aed11-1aa4-42a1-b85c-8332fc8001fc";
    public static final String CHECK_OUT_ENCOUNTER_TYPE_UUID = "25a042b2-60bc-4940-a909-debd098b7d82";
    public static final String ICPC_DIAGNOSIS_CATEGORIES_CONCEPT_UUID = "160167AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";

    public VisitType getFacilityVisitType() {
        return getVisitTypeByUuid(FACILITY_VISIT_TYPE_UUID, true);
    }

    public EncounterType getAdmissionEncounterType() {
        return getEncounterTypeByUuid(ADMISSION_ENCOUNTER_TYPE_UUID, true);
    }

    public EncounterType getDischargeEncounterType() {
        return getEncounterTypeByUuid(DISCHARGE_ENCOUNTER_TYPE_UUID, true);
    }

    public EncounterType getTransferEncounterType() {
        return getEncounterTypeByUuid(TRANSFER_ENCOUNTER_TYPE_UUID, true);
    }

    public EncounterType getCheckInEncounterType() {
        return getEncounterTypeByUuid(CHECK_IN_ENCOUNTER_TYPE_UUID, true);
    }

    public EncounterType getCheckOutEncounterType() {
        return getEncounterTypeByUuid(CHECK_OUT_ENCOUNTER_TYPE_UUID, true);
    }

}
