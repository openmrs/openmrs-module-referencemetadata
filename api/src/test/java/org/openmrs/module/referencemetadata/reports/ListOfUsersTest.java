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
import org.openmrs.module.referencemetadata.reporting.reports.ListOfUsers;
import org.openmrs.module.reporting.dataset.DataSetRow;
import org.openmrs.module.reporting.dataset.SimpleDataSet;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.reporting.report.ReportData;
import org.openmrs.module.reporting.report.manager.ReportManager;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;

import static org.hamcrest.Matchers.contains;

public class ListOfUsersTest extends ReportManagerTest {

	@Autowired
	ListOfUsers listOfUsers;

	@Override
	public ReportManager getReportManager() {
		return listOfUsers;
	}

	@Override
	public EvaluationContext getEvaluationContext() {
		EvaluationContext context = new EvaluationContext();
		context.addParameterValue("retired", Boolean.FALSE);
		return context;
	}

	public void verifyData(ReportData data) {
		SimpleDataSet dataSet = (SimpleDataSet) data.getDataSets().values().iterator().next();
		Matcher<DataSetRow>[] dataSetRowMatcher = new Matcher[5];
		dataSetRowMatcher[0] = hasData("User", null);
		dataSetRowMatcher[1] = hasData("User", "admin");
		dataSetRowMatcher[2] = hasData("User", "clerk");
		dataSetRowMatcher[3] = hasData("User", "doctor");
		dataSetRowMatcher[4] = hasData("User", "butch");
		Assert.assertThat(dataSet.getRows(), contains(dataSetRowMatcher));

		dataSetRowMatcher[0] = hasData("Created", Timestamp.valueOf("2005-01-01 00:00:00.0"));
		dataSetRowMatcher[1] = hasData("Created", Timestamp.valueOf("2005-01-01 00:00:00.0"));
		dataSetRowMatcher[2] = hasData("Created", Timestamp.valueOf("2017-05-18 22:49:32"));
		dataSetRowMatcher[3] = hasData("Created", Timestamp.valueOf("2017-05-18 22:49:32"));
		dataSetRowMatcher[4] = hasData("Created", Timestamp.valueOf("2008-08-15 15:57:09.0"));
		Assert.assertThat(dataSet.getRows(), contains(dataSetRowMatcher));

	}
}
