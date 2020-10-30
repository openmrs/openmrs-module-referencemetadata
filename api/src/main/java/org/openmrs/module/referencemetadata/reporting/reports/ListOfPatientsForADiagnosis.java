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
public class ListOfPatientsForADiagnosis extends ReferenceApplicationReportManager  {

    private static final String DATA_SET_UUID = "500261e9-0e0e-4e66-95dd-1f67bce0b699";
    public static final Parameter DIAGNOSIS_UUID_PARAMETER = new Parameter("diagnosisUuid", "Diagnosis UUID", String.class);

    public ListOfPatientsForADiagnosis() {
    }

    @Override
    public String getUuid() {
        return "e451ae04-4881-11e7-a919-92ebcb67fe24";
    }

    @Override
    public String getName() {
        return "List of Patients affected by this Diagnosis";
    }

    @Override
    public String getDescription() {
        return "List all Patients's for a selected Diagnosis";
    }

    @Override
    public List<Parameter> getParameters() {
        List<Parameter> parameterArrayList = new ArrayList<Parameter>();
        parameterArrayList.add(ReportingConstants.START_DATE_PARAMETER);
        parameterArrayList.add(ReportingConstants.END_DATE_PARAMETER);
        parameterArrayList.add(DIAGNOSIS_UUID_PARAMETER);
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

        reportDef.addDataSetDefinition("listOfPatientsForDiagnosis", Mapped.mapStraightThrough(sqlDataDef));

        return reportDef;
    }

    @Override
    public List<ReportDesign> constructReportDesigns(ReportDefinition reportDefinition) {
        List<ReportDesign> l = new ArrayList<ReportDesign>();
        l.add(ReportManagerUtil.createExcelDesign("ba5a2bd8-68b6-4dac-aef5-ea88d0b327t4", reportDefinition));
        return l;
    }

    @Override
    public String getVersion() {
        return "1.0";
    }

    private String getSQLQuery(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT max(obs.person_id) as PersonId, max(person.uuid) as PersonUuid, ");
        stringBuilder.append("(select identifier from patient_identifier where patient_id = max(encounter.patient_id) and preferred=true limit 0,1) as 'OpenMRS ID', ");
        stringBuilder.append("CONCAT(max(person_name.given_name),' ', max(person_name.middle_name), ' ', max(person_name.family_name)) as 'Patient Name', ");
        stringBuilder.append("IF(max(person.gender)='M', 'Male', 'Female') as Gender, ");
        stringBuilder.append("max(encounter.encounter_datetime) as 'Encounter Date', count(*) as 'Total Encounters' ");
        stringBuilder.append("FROM  obs obs INNER JOIN concept_name cn ON obs.value_coded= cn.concept_id ");
        stringBuilder.append("INNER JOIN encounter encounter ON encounter.encounter_id = obs.encounter_id ");
        stringBuilder.append("INNER JOIN person person ON person.person_id = obs.person_id ");
        stringBuilder.append("INNER JOIN person_name person_name on person.person_id = person_name.person_id ");
        stringBuilder.append("WHERE cn.locale='en' ");
        stringBuilder.append("AND cn.locale_preferred = '1' ");
        stringBuilder.append("AND cn.uuid = :diagnosisUuid ");
        stringBuilder.append("AND obs.date_created >= :startDate ");
        stringBuilder.append("AND obs.date_created <= :endDate ");
        stringBuilder.append("group by person.uuid ");
        stringBuilder.append("order by count(*) DESC limit 0,30;");

        return stringBuilder.toString();
    }

}
