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

import org.openmrs.module.reporting.ReportingConstants;
import org.openmrs.module.reporting.dataset.definition.SqlDataSetDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.reporting.report.ReportDesign;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.openmrs.module.reporting.report.manager.BaseReportManager;
import org.openmrs.module.reporting.report.manager.ReportManagerUtil;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ListOfNewPatientRegistrations extends BaseReportManager {

	private static final String DATA_SET_UUID = "17f7658c-c915-4c32-adad-df3799855569";

	public ListOfNewPatientRegistrations() {
	}

	@Override
	public String getUuid() {
		return "e451a9d6-4881-11e7-a919-92ebcb67fe33";
	}

	@Override
	public String getName() {
		return "List of New Patient Registrations";
	}

	@Override
	public String getDescription() {
		return "List all patients who have registered within since a given date";
	}

	@Override
	public List<Parameter> getParameters() {
		List<Parameter> parameterArrayList = new ArrayList<Parameter>();
		parameterArrayList.add(ReportingConstants.START_DATE_PARAMETER);
		return parameterArrayList;
	}

	@Override
	public ReportDefinition constructReportDefinition() {
		ReportDefinition reportDef = new ReportDefinition();
		reportDef.setUuid(getUuid());
		reportDef.setName(getName());
		reportDef.setDescription(getDescription());
		reportDef.setParameters(getParameters());

		SqlDataSetDefinition sqlDataDef = new SqlDataSetDefinition();
		sqlDataDef.setUuid(DATA_SET_UUID);
		sqlDataDef.setName(getName());
		sqlDataDef.addParameters(getParameters());
		sqlDataDef.setSqlQuery(getSQLQuery());

		reportDef.addDataSetDefinition("newPatientRegistrations", Mapped.mapStraightThrough(sqlDataDef));


		return reportDef;
	}

	@Override
	public List<ReportDesign> constructReportDesigns(ReportDefinition reportDefinition) {
		List<ReportDesign> l = new ArrayList<ReportDesign>();
		l.add(ReportManagerUtil.createExcelDesign("c410998d-b71e-428b-8e5c-f88536c83761", reportDefinition));
		return l;
	}

	@Override
	public String getVersion() {
		return "1.0";
	}

	private String getSQLQuery(){
		StringBuilder stringBuilder = new StringBuilder();
/*		stringBuilder.append("select * ");
		stringBuilder.append("from patient ");
		stringBuilder.append("where date_created >= :startDate; ");*/
		stringBuilder.append("SELECT pi.identifier, pn.given_name, pn.family_name, p.date_created ");
		stringBuilder.append("FROM patient p INNER JOIN person_name pn ");
		stringBuilder.append("ON p.patient_id = pn.person_id ");
		stringBuilder.append("INNER JOIN patient_identifier pi ");
		stringBuilder.append("ON p.patient_id = pi.patient_id ");
		stringBuilder.append("WHERE p.date_created >= :startDate ");
		stringBuilder.append("ORDER BY p.patient_id desc ");

		return stringBuilder.toString();
	}
}
