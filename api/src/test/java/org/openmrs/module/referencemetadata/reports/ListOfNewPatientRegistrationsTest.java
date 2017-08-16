/*
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
package org.openmrs.module.referencemetadata.reports;

import org.junit.Assert;
import org.openmrs.module.referencemetadata.reporting.reports.ListOfNewPatientRegistrations;
import org.openmrs.module.reporting.common.DateUtil;
import org.openmrs.module.reporting.dataset.SimpleDataSet;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.reporting.report.ReportData;
import org.openmrs.module.reporting.report.manager.ReportManager;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;

import static org.hamcrest.Matchers.contains;

public class ListOfNewPatientRegistrationsTest extends ReportManagerTest {

	@Autowired
	ListOfNewPatientRegistrations listOfNewPatientRegistrations;

	public ReportManager getReportManager() {
		return listOfNewPatientRegistrations;
	}

	public EvaluationContext getEvaluationContext() {
		EvaluationContext context = new EvaluationContext();
		context.addParameterValue("startDate", DateUtil.getDateTime(2017,6,17));
		return context;
	}

	public void verifyData(ReportData data) {
		Timestamp timestamp = Timestamp.valueOf("2017-10-15 08:26:51.0");
		SimpleDataSet dataSet = (SimpleDataSet) data.getDataSets().values().iterator().next();

		Assert.assertThat(dataSet.getRows(), contains(hasData("OpenMRS_ID", "1000EE")));
		Assert.assertThat(dataSet.getRows(), contains(hasData("Given_Name", "Rafal")));
		Assert.assertThat(dataSet.getRows(), contains(hasData("Family_Name", "Korytkowski")));
		Assert.assertThat(dataSet.getRows(), contains(hasData("Created", timestamp)));
	}
}
