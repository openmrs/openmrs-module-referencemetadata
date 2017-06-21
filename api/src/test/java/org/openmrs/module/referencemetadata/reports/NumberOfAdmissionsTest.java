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
import org.openmrs.Location;
import org.openmrs.module.referencemetadata.reporting.reports.NumberOfAdmissions;
import org.openmrs.module.reporting.dataset.SimpleDataSet;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.reporting.report.ReportData;
import org.openmrs.module.reporting.report.manager.ReportManager;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.Matchers.contains;

public class NumberOfAdmissionsTest extends ReportManagerTest {

	@Autowired
	NumberOfAdmissions numberOfAdmissions;

	public ReportManager getReportManager() {
		return numberOfAdmissions;
	}

	public EvaluationContext getEvaluationContext() {
		EvaluationContext context = new EvaluationContext();
		context.addParameterValue("location", new Location(Location.LOCATION_UNKNOWN));
		return context;
	}

	public void verifyData(ReportData data) {
		SimpleDataSet dataSet = (SimpleDataSet) data.getDataSets().values().iterator().next();
		Assert.assertThat(dataSet.getRows(), contains(hasData("TOTAL", 1L)));
	}
}