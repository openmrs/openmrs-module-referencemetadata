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

package org.openmrs.module.referencemetadata.reporting.reports.admissions;

import org.openmrs.module.referencemetadata.reporting.reports.ReferenceApplicationReportManager;
import org.openmrs.module.reporting.dataset.definition.SqlDataSetDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.reporting.report.ReportDesign;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.openmrs.module.reporting.report.manager.ReportManagerUtil;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class NumberOfAllAdmissions extends ReferenceApplicationReportManager {

    private static final String DATA_SET_UUID = "1db15456-95a9-4be4-96fd-9409ad8f3950";

    public NumberOfAllAdmissions() {
    }

    @Override
    public String getUuid() {
        return "923b9664-84c3-4988-a888-458c42405605";
    }

    @Override
    public String getName() {
        return "Number of All Admissions";
    }

    @Override
    public String getDescription() {
        return "Number of All Admissions for a given location";
    }

    @Override
    public List<Parameter> getParameters() {
        List<Parameter> parameterArrayList = new ArrayList<Parameter>();
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

        reportDef.addDataSetDefinition("All Admission Count", Mapped.mapStraightThrough(sqlDataDef));


        return reportDef;
    }

    @Override
    public List<ReportDesign> constructReportDesigns(ReportDefinition reportDefinition) {
        List<ReportDesign> l = new ArrayList<ReportDesign>();
        l.add(ReportManagerUtil.createExcelDesign("4273e03b-b67a-4bc6-b952-556edd07b2eb", reportDefinition));
        return l;
    }

    @Override
    public String getVersion() {
        return "1.0";
    }

    private String getSQLQuery(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("select  max(loc.name) as Location, count(*) as Count from encounter e  ");
        stringBuilder.append("inner join location loc on loc.location_id=e.location_id ");
        stringBuilder.append("where e.encounter_type=(select encounter_type_id from encounter_type where uuid='e22e39fd-7db2-45e7-80f1-60fa0d5a4378') ");
		stringBuilder.append("and e.voided = false ");
        stringBuilder.append("group by e.location_id ");

        return stringBuilder.toString();
    }
}
