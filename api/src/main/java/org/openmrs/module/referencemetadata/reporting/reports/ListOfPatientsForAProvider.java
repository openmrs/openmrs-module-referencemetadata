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
public class ListOfPatientsForAProvider extends ReferenceApplicationReportManager {

    private static final String DATA_SET_UUID = "00fceb85-dcc8-42fc-baf7-0485516d19f5";
    public static final Parameter PROVIDER_UUID_PARAMETER = new Parameter("providerUuid", "Provider UUID", String.class);

    public ListOfPatientsForAProvider() {
    }

    @Override
    public String getUuid() {
        return "e451ae04-4881-11e7-a919-92ebcb67fe45";
    }

    @Override
    public String getName() {
        return "List of Patients for this Provider";
    }

    @Override
    public String getDescription() {
        return "List all patients who have worked with the given provider";
    }

    @Override
    public List<Parameter> getParameters() {
        List<Parameter> parameterArrayList = new ArrayList<Parameter>();
        parameterArrayList.add(PROVIDER_UUID_PARAMETER);
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

        reportDef.addDataSetDefinition("listOfPatientsForProvider", Mapped.mapStraightThrough(sqlDataDef));

        return reportDef;
    }

    @Override
    public List<ReportDesign> constructReportDesigns(ReportDefinition reportDefinition) {
        List<ReportDesign> l = new ArrayList<ReportDesign>();
        l.add(ReportManagerUtil.createExcelDesign("08f66d83-4558-4a09-a23b-4aa7695ab365", reportDefinition));
        return l;
    }

    @Override
    public String getVersion() {
        return "1.0";
    }

    private String getSQLQuery(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("select max(per.person_id) as PersonId, max(per.uuid) as PersonUuid, ");
        stringBuilder.append("max(patIden.identifier) as OpenMRS_ID, ");
        stringBuilder.append("max(pname.given_name) as Given_Name, max(pname.family_name) as Family_Name, ");
        stringBuilder.append("max(enc.encounter_datetime) as EncounterDate ");
        stringBuilder.append("from encounter enc ");
        stringBuilder.append("INNER JOIN patient pat on enc.patient_id = pat.patient_id ");
        stringBuilder.append("INNER JOIN person per on enc.patient_id = per.person_id ");
        stringBuilder.append("INNER JOIN person_name pname on pname.person_id = pat.patient_id ");
        stringBuilder.append("INNER JOIN patient_identifier patIden on patIden.patient_id = pat.patient_id ");
        stringBuilder.append("INNER JOIN encounter_provider encProvider on encProvider.encounter_id = enc.encounter_id ");
        stringBuilder.append("INNER JOIN provider pro on pro.provider_id = encProvider.provider_id ");
        stringBuilder.append("WHERE pro.uuid=:providerUuid ");
        stringBuilder.append("group by pat.patient_id limit 0,10; ");
        return stringBuilder.toString();
    }
}
