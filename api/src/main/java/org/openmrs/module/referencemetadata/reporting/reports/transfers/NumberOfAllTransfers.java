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

package org.openmrs.module.referencemetadata.reporting.reports.transfers;

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
public class NumberOfAllTransfers extends ReferenceApplicationReportManager {

    private static final String DATA_SET_UUID = "ce3b4d0b-2567-4362-97ad-5d1c7d856a23";

    public NumberOfAllTransfers() {
    }

    @Override
    public String getUuid() {
        return "ad418753-8a1c-4f5f-8c61-5599369bd5d2";
    }

    @Override
    public String getName() {
        return "Number of All Transfers";
    }

    @Override
    public String getDescription() {
        return "Number of Transfers for all locations";
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

        reportDef.addDataSetDefinition("All Transfers Count", Mapped.mapStraightThrough(sqlDataDef));


        return reportDef;
    }

    @Override
    public List<ReportDesign> constructReportDesigns(ReportDefinition reportDefinition) {
        List<ReportDesign> l = new ArrayList<ReportDesign>();
        l.add(ReportManagerUtil.createExcelDesign("e0c0ae72-aec7-45aa-b972-7aaa678df5a9", reportDefinition));
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
        stringBuilder.append("where e.encounter_type=(select encounter_type_id from encounter_type where uuid='7b68d557-85ef-4fc8-b767-4fa4f5eb5c23') ");
		stringBuilder.append("and e.voided = false ");
        stringBuilder.append("group by e.location_id ");

        return stringBuilder.toString();
    }
}
