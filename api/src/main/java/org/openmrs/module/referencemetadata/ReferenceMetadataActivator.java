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


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.ConceptClass;
import org.openmrs.ConceptName;
import org.openmrs.GlobalProperty;
import org.openmrs.PatientIdentifierType;
import org.openmrs.Role;
import org.openmrs.api.AdministrationService;
import org.openmrs.api.ConceptService;
import org.openmrs.api.PatientService;
import org.openmrs.api.UserService;
import org.openmrs.api.context.Context;
import org.openmrs.module.BaseModuleActivator;
import org.openmrs.module.ModuleException;
import org.openmrs.module.dataexchange.DataImporter;
import org.openmrs.module.emrapi.EmrApiConstants;
import org.openmrs.module.emrapi.utils.MetadataUtil;
import org.openmrs.module.idgen.AutoGenerationOption;
import org.openmrs.module.idgen.SequentialIdentifierGenerator;
import org.openmrs.module.idgen.service.IdentifierSourceService;
import org.openmrs.module.idgen.validator.LuhnMod30IdentifierValidator;
import org.openmrs.module.metadatadeploy.api.MetadataDeployService;
import org.openmrs.module.metadatadeploy.bundle.MetadataBundle;
import org.openmrs.module.metadatamapping.MetadataSource;
import org.openmrs.module.metadatamapping.MetadataTermMapping;
import org.openmrs.module.metadatamapping.api.MetadataMappingService;
import org.openmrs.module.reporting.report.manager.ReportManager;
import org.openmrs.module.reporting.report.manager.ReportManagerUtil;

/**
 * This class contains the logic that is run every time this module is either started or stopped.
 */
public class ReferenceMetadataActivator extends BaseModuleActivator {

	protected Log log = LogFactory.getLog(getClass());

    @Override
    public void started() {
        setupOpenmrsId(Context.getAdministrationService(), Context.getPatientService(), Context.getService(IdentifierSourceService.class), Context.getService(MetadataMappingService.class));

        installConcepts();

        // preferred approach, using Metadata Deploy module
        deployMetadataPackages(Context.getService(MetadataDeployService.class));

        // deprecated approach, using Metadata Sharing module
        installMetadataPackages();

		setupFullAPILevelPrivilegesOnApplicationRoles();
        log.info("Started Reference Metadata module");

	    //setup built-in reports from #org.openmrs.module.referenceapplication.reports
	    ReportManagerUtil.setupAllReports(ReportManager.class);
    }

	private void installConcepts() {
		GlobalProperty installedVersion = Context.getAdministrationService().getGlobalPropertyObject(ReferenceMetadataConstants.INSTALLED_VERSION_GP);
        if (installedVersion == null) {
        	installedVersion = new GlobalProperty(ReferenceMetadataConstants.INSTALLED_VERSION_GP, "0");
        }
        
        if (Integer.valueOf(installedVersion.getPropertyValue()) < ReferenceMetadataConstants.METADATA_VERSION) {
        	ConceptService conceptService = Context.getConceptService();
        	
        	//CIEL comes with a number of items that need to be used instead
        	ConceptClass frequency = conceptService.getConceptClassByUuid("8e071bfe-520c-44c0-a89b-538e9129b42a");
        	if (frequency != null) {
        		if (!frequency.getId().equals(23)) { //Try to purge, if ID is different than in CIEL
        			conceptService.purgeConceptClass(frequency);
        		}
        	}
        	Context.flushSession(); //Flush so that purges are not deferred until after data import
        	
        	DataImporter dataImporter = Context.getRegisteredComponent("dataImporter", DataImporter.class);
            dataImporter.importData("Reference_Application_Concepts-22.xml");
            dataImporter.importData("Reference_Application_Diagnoses-10.xml");
            dataImporter.importData("Reference_Application_Order_Entry_and_Allergies_Concepts-17.xml");
            
            //1.11 requires building the index for the newly added concepts.
            //Without doing so, cs.getConceptByClassName() will return an empty list.
            //We use reflection such that we do not blow up versions before 1.11
            try {
            	Method method = Context.class.getMethod("updateSearchIndexForType", new Class[]{Class.class});
            	method.invoke(null, new Object[] {ConceptName.class});
            }
            catch (NoSuchMethodException ex) {
            	//this must be a version before 1.11
            }
            catch (InvocationTargetException ex) {
            	log.error("Failed to update search index", ex);
            }
            catch (IllegalAccessException ex) {
            	log.error("Failed to update search index", ex);
            }
            
            installedVersion.setPropertyValue(ReferenceMetadataConstants.METADATA_VERSION.toString());
        }
        
        Context.getAdministrationService().saveGlobalProperty(installedVersion);
	}

    public void deployMetadataPackages(MetadataDeployService service) {
        MetadataBundle rolesAndPrivileges = Context.getRegisteredComponent("referenceApplicationRolesAndPrivileges", MetadataBundle.class);
        service.installBundles(Arrays.asList(rolesAndPrivileges));
    }

	public void installMetadataPackages() {
        try {
            MetadataUtil.setupStandardMetadata(getClass().getClassLoader());
        }
        catch (Exception e) {
            throw new ModuleException("Failed to load MDS packages", e);
        }
    }

    public void setupOpenmrsId(AdministrationService administrationService, PatientService patientService, IdentifierSourceService identifierSourceService, MetadataMappingService metadataMappingService) {

        PatientIdentifierType openmrsIdType = patientService.getPatientIdentifierTypeByUuid(ReferenceMetadataConstants.OPENMRS_ID_UUID);
        if (openmrsIdType == null) {
            openmrsIdType = new PatientIdentifierType();
            openmrsIdType.setName(ReferenceMetadataConstants.OPENMRS_ID_NAME);
            openmrsIdType.setDescription(ReferenceMetadataConstants.OPENMRS_ID_DESCRIPION);
            
            try {
	            Method method = openmrsIdType.getClass().getMethod("setCheckDigit", new Class[] { boolean.class });
	            method.invoke(openmrsIdType, true);
            }
            catch (Exception ex) {
            	//ignore when running on platform 2.0 which removed this method
            }
            
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
	        openmrsIdGenerator.setMinLength(6);
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

        MetadataTermMapping identifierTypeMapping = metadataMappingService.getMetadataTermMapping(EmrApiConstants.EMR_METADATA_SOURCE_NAME, EmrApiConstants.PRIMARY_IDENTIFIER_TYPE);
        //overwrite if not set yet
        if(!openmrsIdType.getUuid().equals(identifierTypeMapping.getMetadataUuid())){
            identifierTypeMapping.setMappedObject(openmrsIdType);
            metadataMappingService.saveMetadataTermMapping(identifierTypeMapping);
        }
    }

	public void setupFullAPILevelPrivilegesOnApplicationRoles() {
		UserService userService = Context.getUserService();
		Role fullAPILevelRole = userService.getRoleByUuid(EmrApiConstants.PRIVILEGE_LEVEL_FULL_UUID);

		if (fullAPILevelRole == null) {
			return; //This is the case when running tests due to EMRAPI activator not being run.
		}

		for (Role role : Context.getUserService().getAllRoles()) {
			if (role.getName().startsWith("Application:")) {
				role.getInheritedRoles().add(fullAPILevelRole);
				userService.saveRole(role);
			}
		}

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
