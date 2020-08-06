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
public class NumberOfVisits extends BaseReportManager {

	private static final String DATA_SET_UUID = "439828be-a96a-437a-af4d-7db1c03a259f";

	public NumberOfVisits() {
	}

	@Override
	public String getUuid() {
		return "9667a78e-4881-11e7-a919-92ebcb67fe33";
	}

	@Override
	public String getName() {
		return "Number of Visits";
	}

	@Override
	public String getDescription() {
		return "Number of visits in a given date range";
	}

	@Override
	public List<Parameter> getParameters() {
		List<Parameter> parameterArrayList = new ArrayList<Parameter>();
		parameterArrayList.add(ReportingConstants.START_DATE_PARAMETER);
		parameterArrayList.add(ReportingConstants.END_DATE_PARAMETER);
		parameterArrayList.add(ReportingConstants.LOCATION_PARAMETER);
		parameterArrayList.add(new Parameter("activeVisits", "Include Active Visits", Boolean.class));
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

		reportDef.addDataSetDefinition("Visit Count", Mapped.mapStraightThrough(sqlDataDef));


		return reportDef;
	}

	@Override
	public List<ReportDesign> constructReportDesigns(ReportDefinition reportDefinition) {
		List<ReportDesign> l = new ArrayList<ReportDesign>();
		l.add(ReportManagerUtil.createExcelDesign("dba7cde5-32de-463a-8647-acc917fe5484", reportDefinition));
		return l;
	}

	@Override
	public String getVersion() {
		return "1.0";
	}

	private String getSQLQuery(){
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("SELECT max(vt.name) as VisitType ,count(v.visit_id) as Count FROM visit v ");
		stringBuilder.append("INNER JOIN visit_type vt ON v.visit_type_id=vt.visit_type_id ");
		stringBuilder.append("WHERE v.location_id = :location ");
		stringBuilder.append("AND v.date_started >= :startDate ");
		stringBuilder.append("AND (v.date_stopped <= :endDate OR True = :activeVisits) ");
		stringBuilder.append("GROUP BY vt.visit_type_id ;");

		return stringBuilder.toString();
	}
}
