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

import org.hamcrest.Matcher;
import org.junit.Assert;
import org.openmrs.module.referencemetadata.reporting.reports.ListOfDiagnosis;
import org.openmrs.module.reporting.common.DateUtil;
import org.openmrs.module.reporting.dataset.DataSetRow;
import org.openmrs.module.reporting.dataset.SimpleDataSet;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.reporting.report.ReportData;
import org.openmrs.module.reporting.report.manager.ReportManager;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.AssertTrue;

import static org.hamcrest.Matchers.contains;

public class ListOfDiagnosisTest  extends ReportManagerTest {

	@Autowired
	ListOfDiagnosis listOfDiagnosis;

	public ReportManager getReportManager() {
		return listOfDiagnosis;
	}

	public EvaluationContext getEvaluationContext() {
		EvaluationContext context = new EvaluationContext();
		context.addParameterValue("startDate", DateUtil.getDateTime(2016,6,17));
		context.addParameterValue("endDate", DateUtil.getDateTime(2018,6,20));
		return context;
	}

	public void verifyData(ReportData data) {
		SimpleDataSet dataSet = (SimpleDataSet) data.getDataSets().values().iterator().next();

		Matcher<DataSetRow>[] expectedValues = new Matcher[2];
		expectedValues[0] = hasData("NAME", "Vero");
		expectedValues[1] = hasData("NAME", "Myalgia");
		Assert.assertThat(dataSet.getRows(), contains(expectedValues));

		expectedValues[0] = hasData("COUNT", 2L);
		expectedValues[1] = hasData("COUNT", 1L);
		Assert.assertThat(dataSet.getRows(), contains(expectedValues));
	}
}
