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

import java.util.ArrayList;
import java.util.List;

import org.openmrs.module.reporting.dataset.definition.SqlDataSetDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.reporting.report.ReportDesign;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.openmrs.module.reporting.report.manager.ReportManagerUtil;
import org.springframework.stereotype.Component;

@Component
public class ListOfProviders extends ReferenceApplicationReportManager {

	private static final String DATA_SET_UUID = "bae94c6f-59ac-44e0-afa9-f8f8729d90b0";

	public ListOfProviders() {
	}

	@Override
	public String getUuid() {
		return "d3950ea8-4881-11e7-a919-92ebcb67fe33";
	}

	@Override
	public String getName() {
		return "List of Providers";
	}

	@Override
	public String getDescription() {
		return "List all providers grouped by active and inactive providers";
	}

	@Override
	public List<Parameter> getParameters() {
		List<Parameter> parameterArrayList = new ArrayList<Parameter>();
		parameterArrayList.add(new Parameter("retired", "Retired Users", Boolean.class));
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

		reportDef.addDataSetDefinition("listOfProviders", Mapped.mapStraightThrough(sqlDataDef));


		return reportDef;
	}

	@Override
	public List<ReportDesign> constructReportDesigns(ReportDefinition reportDefinition) {
		List<ReportDesign> l = new ArrayList<ReportDesign>();
		l.add(ReportManagerUtil.createExcelDesign("15b5f44e-70a3-4eaf-b916-3bb6d9ef591c", reportDefinition));
		return l;
	}

	@Override
	public String getVersion() {
		return "1.0";
	}

	private String getSQLQuery(){
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("SELECT pro.uuid AS ProviderUuid, pro.identifier AS Provider, ");
		stringBuilder.append("pname.family_name as FamilyName, pname.given_name as GivenName, ");
		stringBuilder.append("role.name AS RoleName, pro.date_created AS Created ");
        stringBuilder.append("FROM provider pro ");
        stringBuilder.append("INNER JOIN providermanagement_provider_role role ");
        stringBuilder.append("ON pro.provider_role_id = role.provider_role_id ");
		stringBuilder.append("INNER JOIN person_name pname on  pname.person_id = pro.person_id ");
        stringBuilder.append("WHERE pro.retired = :retired; ");
		return stringBuilder.toString();
	}
}
