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
import org.openmrs.module.referencemetadata.reporting.reports.ListOfUsers;
import org.openmrs.module.reporting.dataset.SimpleDataSet;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.reporting.report.ReportData;
import org.openmrs.module.reporting.report.manager.ReportManager;
import org.springframework.beans.factory.annotation.Autowired;

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
//      HOW TO CHECK FOR MULTIPLE ROWS?
/*		SimpleDataSet dataSet = (SimpleDataSet) data.getDataSets().values().iterator().next();
		Assert.assertThat(dataSet.getRows(), contains(hasData("USERNAME", null)));
		Assert.assertThat(dataSet.getRows(), contains(hasData("USERNAME", "admin")));
		Assert.assertThat(dataSet.getRows(), contains(hasData("USERNAME", "butch")));

		Assert.assertThat(dataSet.getRows(), contains(hasData("UUID", "A4F30A1B-5EB9-11DF-A648-37A07F9C90FB")));
		Assert.assertThat(dataSet.getRows(), contains(hasData("UUID", "1010d442-e134-11de-babe-001e378eb67e")));
		Assert.assertThat(dataSet.getRows(), contains(hasData("UUID", "c98a1558-e131-11de-babe-001e378eb67e")));*/
	}
}
