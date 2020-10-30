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

import org.openmrs.module.reporting.ReportingConstants;
import org.openmrs.module.reporting.dataset.definition.SqlDataSetDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.reporting.report.ReportDesign;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.openmrs.module.reporting.report.manager.ReportManagerUtil;
import org.springframework.stereotype.Component;

@Component
public class NumberOfVisitNotes extends ReferenceApplicationReportManager {

	private static final String DATA_SET_UUID = "ec726fb5-fee8-43c5-b85f-034d165c2fb3";

	public NumberOfVisitNotes() {
	}

	@Override
	public String getUuid() {
		return "9667ac52-4881-11e7-a919-92ebcb67fe33";
	}

	@Override
	public String getName() {
		return "Number of Visit Notes";
	}

	@Override
	public String getDescription() {
		return "Number of active visit notes for a given time period";
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

		reportDef.addDataSetDefinition("Visit Note Count", Mapped.mapStraightThrough(sqlDataDef));


		return reportDef;
	}

	@Override
	public List<ReportDesign> constructReportDesigns(ReportDefinition reportDefinition) {
		List<ReportDesign> l = new ArrayList<ReportDesign>();
		l.add(ReportManagerUtil.createExcelDesign("740eb793-621e-43f5-8f6b-06ebca3ed4df", reportDefinition));
		return l;
	}

	@Override
	public String getVersion() {
		return "1.0";
	}

	private String getSQLQuery(){
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("select max(vt.name) as VisitType, count(e.encounter_id) as Count from encounter e ");
		stringBuilder.append("INNER JOIN visit v on e.visit_id = v.visit_id ");
		stringBuilder.append("INNER JOIN visit_type vt on v.visit_type_id = vt.visit_type_id ");
		stringBuilder.append("where e.encounter_type = (select et.encounter_type_id  ");
		stringBuilder.append("from encounter_type et  ");
		stringBuilder.append("where et.uuid = 'd7151f82-c1f3-4152-a605-2f9ea7414a79')  ");
		stringBuilder.append("AND v.date_started >= :startDate ");
		stringBuilder.append("AND (v.date_stopped <= :endDate OR True = :activeVisits) ");
		stringBuilder.append("and e.location_id = :location ");
		stringBuilder.append("group by v.visit_type_id; ");

		return stringBuilder.toString();
	}
}
