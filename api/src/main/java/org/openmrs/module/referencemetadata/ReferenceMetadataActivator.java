/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.referencemetadata;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.GlobalProperty;
import org.openmrs.PatientIdentifierType;
import org.openmrs.api.AdministrationService;
import org.openmrs.api.PatientService;
import org.openmrs.api.context.Context;
import org.openmrs.module.BaseModuleActivator;
import org.openmrs.module.ModuleException;
import org.openmrs.module.ModuleFactory;
import org.openmrs.module.emrapi.EmrApiConstants;
import org.openmrs.module.emrapi.utils.MetadataUtil;
import org.openmrs.module.idgen.AutoGenerationOption;
import org.openmrs.module.idgen.SequentialIdentifierGenerator;
import org.openmrs.module.idgen.service.IdentifierSourceService;
import org.openmrs.module.idgen.validator.LuhnMod30IdentifierValidator;

/**
 * This class contains the logic that is run every time this module is either started or stopped.
 */
public class ReferenceMetadataActivator extends BaseModuleActivator {

	protected Log log = LogFactory.getLog(getClass());

    @Override
    public void started() {
        setupOpenmrsId(Context.getAdministrationService(), Context.getPatientService(), Context.getService(IdentifierSourceService.class));
        try {
            MetadataUtil.setupStandardMetadata(ModuleFactory.getModuleClassLoader("referencemetadata"));
        }
        catch (Exception e) {
            throw new ModuleException("Failed to load Visit Diagnosis concept package", e);
        }
    }

    public void setupOpenmrsId(AdministrationService administrationService, PatientService patientService, IdentifierSourceService identifierSourceService) {

        PatientIdentifierType openmrsIdType = patientService.getPatientIdentifierTypeByName(ReferenceMetadataConstants.OPENMRS_ID_NAME);
        if (openmrsIdType == null) {
            openmrsIdType = new PatientIdentifierType();
            openmrsIdType.setName(ReferenceMetadataConstants.OPENMRS_ID_NAME);
            openmrsIdType.setDescription(ReferenceMetadataConstants.OPENMRS_ID_DESCRIPION);
            openmrsIdType.setCheckDigit(true);
            openmrsIdType.setRequired(true);
            openmrsIdType.setValidator(LuhnMod30IdentifierValidator.class.getName());
            openmrsIdType.setUuid(ReferenceMetadataConstants.OPENMRS_ID_UUID);
            patientService.savePatientIdentifierType(openmrsIdType);
        }

        SequentialIdentifierGenerator openmrsIdGenerator = (SequentialIdentifierGenerator) identifierSourceService.getIdentifierSourceByUuid(ReferenceMetadataConstants.OPENMRS_ID_GENERATOR_UUID);
        if (openmrsIdGenerator == null) {
            openmrsIdGenerator = new SequentialIdentifierGenerator();
            openmrsIdGenerator.setIdentifierType(openmrsIdType);
            openmrsIdGenerator.setName(ReferenceMetadataConstants.OPENMRS_ID_GENERATOR_NAME);
            openmrsIdGenerator.setUuid(ReferenceMetadataConstants.OPENMRS_ID_GENERATOR_UUID);
            openmrsIdGenerator.setBaseCharacterSet(new LuhnMod30IdentifierValidator().getBaseCharacters());
            openmrsIdGenerator.setLength(6);
            openmrsIdGenerator.setFirstIdentifierBase("10000");
            identifierSourceService.saveIdentifierSource(openmrsIdGenerator);
        }

        AutoGenerationOption openmrsIdOptions = identifierSourceService.getAutoGenerationOption(openmrsIdType);
        if (openmrsIdOptions == null) {
            openmrsIdOptions = new AutoGenerationOption();
            openmrsIdOptions.setIdentifierType(openmrsIdType);
            openmrsIdOptions.setSource(openmrsIdGenerator);
            openmrsIdOptions.setManualEntryEnabled(false);
            openmrsIdOptions.setAutomaticGenerationEnabled(true);
            identifierSourceService.saveAutoGenerationOption(openmrsIdOptions);
        }

        setGlobalProperty(administrationService, EmrApiConstants.PRIMARY_IDENTIFIER_TYPE, ReferenceMetadataConstants.OPENMRS_ID_UUID);
    }

    private void setGlobalProperty(AdministrationService administrationService, String name, String value) {
        GlobalProperty gpObject = administrationService.getGlobalPropertyObject(name);
        if (gpObject == null) {
            gpObject = new GlobalProperty();
            gpObject.setProperty(name);
        }
        gpObject.setPropertyValue(value);
        administrationService.saveGlobalProperty(gpObject);
    }

}
