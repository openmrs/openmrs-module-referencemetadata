package org.openmrs.module.referencemetadata.reports;

import org.junit.Assert;
import org.openmrs.module.referencemetadata.reporting.reports.ListOfPatientsForAProvider;
import org.openmrs.module.reporting.dataset.SimpleDataSet;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.reporting.report.ReportData;
import org.openmrs.module.reporting.report.manager.ReportManager;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;

import static org.hamcrest.Matchers.contains;

public class ListOfPatientsForAProviderTest extends ReportManagerTest {

    @Autowired
    ListOfPatientsForAProvider listOfPatientsForAProvider;

    public ReportManager getReportManager() {
        return listOfPatientsForAProvider;
    }

    public EvaluationContext getEvaluationContext() {
        EvaluationContext context = new EvaluationContext();
        context.addParameterValue("providerUuid", "f6803750-6940-49e5-a5ee-afe599feda6d");
        return context;
    }

    public void verifyData(ReportData data) {
        Timestamp timestamp = Timestamp.valueOf("2017-06-18 22:19:48");
        SimpleDataSet dataSet = (SimpleDataSet) data.getDataSets().values().iterator().next();

        Assert.assertThat(dataSet.getRows(), contains(hasData("OpenMRS_ID", "1000EE")));
        Assert.assertThat(dataSet.getRows(), contains(hasData("Given_Name", "Rafal")));
        Assert.assertThat(dataSet.getRows(), contains(hasData("Family_Name", "Korytkowski")));
        Assert.assertThat(dataSet.getRows(), contains(hasData("EncounterDate", timestamp)));
    }
}
