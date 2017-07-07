/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.referencemetadata.reporting.reports;

import org.openmrs.module.referencemetadata.reporting.definition.library.ReferenceApplicationCohortDefinitionLibrary;
import org.openmrs.module.referencemetadata.reporting.definition.library.ReferenceApplicationPatientDataDefinitionLibrary;
import org.openmrs.module.reporting.ReportingConstants;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.data.patient.definition.PatientDataDefinition;
import org.openmrs.module.reporting.data.patient.library.BuiltInPatientDataLibrary;
import org.openmrs.module.reporting.dataset.definition.PatientDataSetDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.reporting.report.ReportDesign;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.openmrs.module.reporting.report.manager.BaseReportManager;
import org.openmrs.module.reporting.report.manager.ReportManagerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class NumberOfPatientRegistrations extends BaseReportManager {

	private static final String DATA_SET_UUID = "cc96c6d5-1adf-4fb6-bc72-284f1822a9f3";

	@Autowired
	private BuiltInPatientDataLibrary builtInPatientData;

	@Autowired
	private ReferenceApplicationCohortDefinitionLibrary referenceApplicationCohortDefinitionLibrary;

	@Autowired
	private ReferenceApplicationPatientDataDefinitionLibrary referenceApplicationPatientDataDefinitionLibrary;

	public NumberOfPatientRegistrations() {
	}

	@Override
	public String getUuid() {
		return "d71b5170-5283-11e7-b114-b2f933d5fe66";
	}

	@Override
	public String getName() {
		return "Number of Patient Registrations";
	}

	@Override
	public String getDescription() {
		return "Number of patient registrations for a given date period";
	}

	@Override
	public List<Parameter> getParameters() {
		List<Parameter> parameterArrayList = new ArrayList<Parameter>();
		parameterArrayList.add(ReportingConstants.START_DATE_PARAMETER);
		parameterArrayList.add(ReportingConstants.END_DATE_PARAMETER);
		return parameterArrayList;
	}

	@Override
	public ReportDefinition constructReportDefinition() {

		ReportDefinition reportDef = new ReportDefinition();
		reportDef.setUuid(getUuid());
		reportDef.setName(getName());
		reportDef.setDescription(getDescription());
		reportDef.setParameters(getParameters());

		addDataSet(reportDef, "New Patient Registrations Data set");

		return reportDef;
	}

	private void addDataSet(ReportDefinition rd, String dataSetKey){

		PatientDataSetDefinition dataSet = new PatientDataSetDefinition();
		dataSet.setUuid(DATA_SET_UUID);
		dataSet.setName(getName());
		dataSet.setParameters(getParameters());

		CohortDefinition cohortDefinition = referenceApplicationCohortDefinitionLibrary.getActivePatientRegistrationsByDatePeriod();
		dataSet.addRowFilter(Mapped.mapStraightThrough(cohortDefinition));

		rd.addDataSetDefinition(dataSetKey, Mapped.mapStraightThrough(dataSet));

		addColumn(dataSet, "Registered DateTime", referenceApplicationPatientDataDefinitionLibrary.getDateCreated());
		addColumn(dataSet, "OpenMRS ID", referenceApplicationPatientDataDefinitionLibrary.getOpenmrsId());
		addColumn(dataSet, "Given Name", builtInPatientData.getPreferredGivenName());
		addColumn(dataSet, "Family Name", builtInPatientData.getPreferredFamilyName());
		addColumn(dataSet, "Gender", builtInPatientData.getGender());
	}

	private void addColumn(PatientDataSetDefinition dsd, String columnName, PatientDataDefinition pdd) {
		dsd.addColumn(columnName, pdd, Mapped.straightThroughMappings(pdd));
	}

	@Override
	public List<ReportDesign> constructReportDesigns(ReportDefinition reportDefinition) {
		List<ReportDesign> l = new ArrayList<ReportDesign>();
		l.add(ReportManagerUtil.createExcelDesign("0e36f8b5-8635-4f2c-bf7c-a19a22c38816", reportDefinition));
		return l;
	}

	@Override
	public String getVersion() {
		return "1.0";
	}

}
