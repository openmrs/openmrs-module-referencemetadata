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
public class ListOfDiagnosis extends BaseReportManager {

	private static final String DATA_SET_UUID = "500261e9-0e0c-4e66-95dd-1f67bce0b699";

	public ListOfDiagnosis() {
	}

	@Override
	public String getUuid() {
		return "e451ae04-4881-11e7-a919-92ebcb67fe33";
	}

	@Override
	public String getName() {
		return "List of Diagnosis";
	}

	@Override
	public String getDescription() {
		return "List all diagnosis's for a given date range along with the count";
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

		SqlDataSetDefinition sqlDataDef = new SqlDataSetDefinition();
		sqlDataDef.setUuid(DATA_SET_UUID);
		sqlDataDef.setName(getName());
		sqlDataDef.addParameters(getParameters());
		sqlDataDef.setSqlQuery(getSQLQuery());

		reportDef.addDataSetDefinition("listOfDiagnosis", Mapped.mapStraightThrough(sqlDataDef));


		return reportDef;
	}

	@Override
	public List<ReportDesign> constructReportDesigns(ReportDefinition reportDefinition) {
		List<ReportDesign> l = new ArrayList<ReportDesign>();
		l.add(ReportManagerUtil.createExcelDesign("ba5a2bd8-68b6-4dac-aef5-ea88d0b327f5", reportDefinition));
		return l;
	}

	@Override
	public String getVersion() {
		return "1.0";
	}

	private String getSQLQuery(){
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("SELECT cn.name, count(*) as count ");
		stringBuilder.append("FROM  obs obs INNER JOIN concept_name cn ON obs.value_coded= cn.concept_id ");
		stringBuilder.append("WHERE obs.concept_id = ( SELECT c.concept_id FROM concept c WHERE c.uuid='1284AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA' ) ");
		stringBuilder.append("AND cn.locale='en' ");
		stringBuilder.append("AND cn.locale_preferred = '1' ");
		stringBuilder.append("AND obs.date_created >= :startDate ");
		stringBuilder.append("AND obs.date_created <= :endDate ");
		stringBuilder.append("group by obs.value_coded, cn.name ");
		stringBuilder.append("order by count(*) desc ;");

		return stringBuilder.toString();
	}
}
