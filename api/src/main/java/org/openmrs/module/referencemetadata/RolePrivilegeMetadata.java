package org.openmrs.module.referencemetadata;

import org.openmrs.module.metadatadeploy.bundle.AbstractMetadataBundle;
import org.openmrs.util.RoleConstants;
import org.springframework.stereotype.Component;

import static org.openmrs.module.metadatadeploy.bundle.CoreConstructors.idSet;
import static org.openmrs.module.metadatadeploy.bundle.CoreConstructors.privilege;
import static org.openmrs.module.metadatadeploy.bundle.CoreConstructors.role;

@Component("referenceApplicationRolesAndPrivileges")
public class RolePrivilegeMetadata extends AbstractMetadataBundle {

    public static class _Privilege {
        public static final String APP_REFERENCEAPPLICATION_LEGACY_ADMIN = "App: referenceapplication.legacyAdmin";
        public static final String APP_REFERENCEAPPLICATION_VITALS = "App: referenceapplication.vitals";
        public static final String APP_REGISTRATIONAPP_REGISTER_PATIENT = "App: registrationapp.registerPatient";
        public static final String APP_COREAPPS_FIND_PATIENT = "App: coreapps.findPatient";
        public static final String APP_COREAPPS_PATIENT_DASHBOARD = "App: coreapps.patientDashboard";
        public static final String APP_COREAPPS_PATIENT_VISITS = "App: coreapps.patientVisits";
        public static final String APP_COREAPPS_ACTIVE_VISITS = "App: coreapps.activeVisits";
        public static final String APP_COREAPPS_SYSTEM_ADMINISTRATION = "App: coreapps.systemAdministration";
        public static final String APP_COREAPPS_CONFIGURE_METADATA = "App: coreapps.configuremetadata";
        public static final String APP_FORMENTRYAPP_FORMS = "App: formentryapp.forms";
        public static final String APP_ATLAS_MANAGE = "App: atlas.manage";
        public static final String TASK_COREAPPS_CREATE_RETROSPECTIVE_VISIT = "Task: coreapps.createRetrospectiveVisit";
        public static final String TASK_COREAPPS_CREATE_VISIT = "Task: coreapps.createVisit";
        public static final String TASK_COREAPPS_END_VISIT = "Task: coreapps.endVisit";
        public static final String TASK_COREAPPS_MERGE_VISITS = "Task: coreapps.mergeVisits";
        public static final String TASK_REFERENCEAPPLICATION_SIMPLE_ADMISSION = "Task: referenceapplication.simpleAdmission";
        public static final String TASK_REFERENCEAPPLICATION_SIMPLE_DISCHARGE = "Task: referenceapplication.simpleDischarge";
        public static final String TASK_REFERENCEAPPLICATION_SIMPLE_TRANSFER = "Task: referenceapplication.simpleTransfer";
        public static final String TASK_REFERENCEAPPLICATION_SIMPLE_VISIT_NOTE = "Task: referenceapplication.simpleVisitNote";
        public static final String TASK_REFERENCEAPPLICATION_VITALS = "Task: referenceapplication.vitals";
    }

    public static class _Role {
        public static final String APPLICATION_ADMINISTERS_SYSTEM = "Application: Administers System";
        public static final String APPLICATION_CONFIGURES_METADATA = "Application: Configures Metadata";
        public static final String APPLICATION_CONFIGURES_FORMS = "Application: Configures Forms";
        public static final String APPLICATION_MANAGES_ATLAS = "Application: Manages Atlas";
        public static final String APPLICATION_HAS_SUPERUSER_PRIVILEGES = "Application: Has Super User Privileges";

        public static final String APPLICATION_REGISTERS_PATIENTS = "Application: Registers Patients";
        public static final String APPLICATION_ENTERS_VITALS = "Application: Enters Vitals";

        public static final String APPLICATION_USES_CAPTURE_VITALS_APP = "Application: Uses Capture Vitals App";
        public static final String APPLICATION_USES_PATIENT_SUMMARY = "Application: Uses Patient Summary";
        public static final String APPLICATION_WRITES_CLINICAL_NOTES = "Application: Writes Clinical Notes";
        public static final String APPLICATION_ENTERS_ADT_EVENTS = "Application: Enters ADT Events";

        public static final String ORGANIZATIONAL_DOCTOR = "Organizational: Doctor";
        public static final String ORGANIZATIONAL_NURSE = "Organizational: Nurse";
        public static final String ORGANIZATIONAL_REGISTRATION_CLERK = "Organizational: Registration Clerk";
        public static final String ORGANIZATIONAL_SYSTEM_ADMINISTRATOR = "Organizational: System Administrator";
    }

    @Override
    public void install() throws Exception {
        install(privilege(_Privilege.APP_COREAPPS_SYSTEM_ADMINISTRATION, "Able to access the System Administration page"));
        install(privilege(_Privilege.APP_REFERENCEAPPLICATION_LEGACY_ADMIN, "Able to access the advanced administration app"));
        install(privilege(_Privilege.APP_COREAPPS_ACTIVE_VISITS, "Able to access the active visits app"));
        install(privilege(_Privilege.APP_ATLAS_MANAGE, "Manage whether and how this implementation is displayed on the OpenMRS Atlas"));
        install(privilege(_Privilege.APP_COREAPPS_CONFIGURE_METADATA, "Able to access the Configure Metadata page"));
        install(privilege(_Privilege.APP_COREAPPS_FIND_PATIENT, "Able to access the find patient app"));
        install(privilege(_Privilege.APP_COREAPPS_PATIENT_DASHBOARD, "Able to access the patient dashboard"));
        install(privilege(_Privilege.APP_COREAPPS_PATIENT_VISITS, "Able to access the patient visits screen"));
        install(privilege(_Privilege.APP_COREAPPS_SYSTEM_ADMINISTRATION, "Able to access the System Administration page"));
        install(privilege(_Privilege.APP_FORMENTRYAPP_FORMS, "Manages implementation-defined forms and attaches them to the UI"));
        install(privilege(_Privilege.APP_REFERENCEAPPLICATION_VITALS, "Able to access the vitals app"));
        install(privilege(_Privilege.APP_REGISTRATIONAPP_REGISTER_PATIENT, "Able to access the register patient app"));

        install(privilege(_Privilege.TASK_COREAPPS_CREATE_RETROSPECTIVE_VISIT, "Able to create a retrospective visit"));
        install(privilege(_Privilege.TASK_COREAPPS_CREATE_VISIT, "Able to create a visit"));
        install(privilege(_Privilege.TASK_COREAPPS_END_VISIT, "Able to end visit"));
        install(privilege(_Privilege.TASK_COREAPPS_MERGE_VISITS, "Able to merge visits"));
        install(privilege(_Privilege.TASK_REFERENCEAPPLICATION_SIMPLE_ADMISSION, "Able to admit patients"));
        install(privilege(_Privilege.TASK_REFERENCEAPPLICATION_SIMPLE_DISCHARGE, "Able to discharge patients"));
        install(privilege(_Privilege.TASK_REFERENCEAPPLICATION_SIMPLE_TRANSFER, "Able to transfer patients"));
        install(privilege(_Privilege.TASK_REFERENCEAPPLICATION_SIMPLE_VISIT_NOTE, "Able to write clinical notes"));
        install(privilege(_Privilege.TASK_REFERENCEAPPLICATION_VITALS, "Able to enter vitals"));

        install(role(_Role.APPLICATION_ADMINISTERS_SYSTEM, "Administers system", idSet(), idSet(
                _Privilege.APP_COREAPPS_SYSTEM_ADMINISTRATION,
                _Privilege.APP_REFERENCEAPPLICATION_LEGACY_ADMIN)));
        install(role(_Role.APPLICATION_CONFIGURES_METADATA, "Access to Configure Metadata app", idSet(), idSet(
                _Privilege.APP_COREAPPS_CONFIGURE_METADATA)));
        install(role(_Role.APPLICATION_CONFIGURES_FORMS, "Manages forms and attaches them to the UI", idSet(), idSet(
                _Privilege.APP_COREAPPS_CONFIGURE_METADATA,
                _Privilege.APP_FORMENTRYAPP_FORMS)));
        install(role(_Role.APPLICATION_MANAGES_ATLAS, "Can configure whether/how this implementation is displayed on the OpenMRS Atlas", idSet(), idSet(
                _Privilege.APP_COREAPPS_SYSTEM_ADMINISTRATION,
                _Privilege.APP_ATLAS_MANAGE)));
        install(role(_Role.APPLICATION_REGISTERS_PATIENTS, "Registers patients", idSet(), idSet(
                _Privilege.APP_COREAPPS_ACTIVE_VISITS,
                _Privilege.APP_REGISTRATIONAPP_REGISTER_PATIENT)));
        install(role(_Role.APPLICATION_ENTERS_VITALS, "Enters vitals", idSet(), idSet(
                _Privilege.APP_COREAPPS_ACTIVE_VISITS,
                _Privilege.APP_COREAPPS_FIND_PATIENT,
                _Privilege.TASK_REFERENCEAPPLICATION_VITALS)));
        install(role(_Role.APPLICATION_USES_CAPTURE_VITALS_APP, "Uses capture vitals app", idSet(), idSet(
                _Privilege.APP_REFERENCEAPPLICATION_VITALS)));
        install(role(_Role.APPLICATION_USES_PATIENT_SUMMARY, "Uses patient summary", idSet(), idSet(
                _Privilege.APP_COREAPPS_FIND_PATIENT,
                _Privilege.APP_COREAPPS_PATIENT_DASHBOARD,
                _Privilege.APP_COREAPPS_PATIENT_VISITS)));
        install(role(_Role.APPLICATION_WRITES_CLINICAL_NOTES, "Writes clinical notes", idSet(), idSet(
                _Privilege.APP_COREAPPS_ACTIVE_VISITS,
                _Privilege.APP_COREAPPS_FIND_PATIENT,
                _Privilege.TASK_REFERENCEAPPLICATION_SIMPLE_VISIT_NOTE)));
        install(role(_Role.APPLICATION_ENTERS_ADT_EVENTS, "Enters ADT events", idSet(), idSet(
                _Privilege.APP_COREAPPS_ACTIVE_VISITS,
                _Privilege.APP_COREAPPS_FIND_PATIENT,
                _Privilege.TASK_COREAPPS_CREATE_VISIT,
                _Privilege.TASK_COREAPPS_CREATE_RETROSPECTIVE_VISIT,
                _Privilege.TASK_COREAPPS_END_VISIT,
                _Privilege.TASK_COREAPPS_MERGE_VISITS,
                _Privilege.TASK_REFERENCEAPPLICATION_SIMPLE_ADMISSION,
                _Privilege.TASK_REFERENCEAPPLICATION_SIMPLE_DISCHARGE,
                _Privilege.TASK_REFERENCEAPPLICATION_SIMPLE_TRANSFER)));

        install(role(_Role.APPLICATION_HAS_SUPERUSER_PRIVILEGES, "Extends the underlying System Developer API role",
                idSet(RoleConstants.SUPERUSER), idSet()));

        install(role(_Role.ORGANIZATIONAL_DOCTOR, "Doctor", idSet(
                _Role.APPLICATION_USES_PATIENT_SUMMARY,
                _Role.APPLICATION_WRITES_CLINICAL_NOTES,
                _Role.APPLICATION_ENTERS_ADT_EVENTS), idSet()));
        install(role(_Role.ORGANIZATIONAL_NURSE, "Nurse", idSet(
                _Role.APPLICATION_ENTERS_VITALS,
                _Role.APPLICATION_USES_CAPTURE_VITALS_APP,
                _Role.APPLICATION_USES_PATIENT_SUMMARY,
                _Role.APPLICATION_ENTERS_ADT_EVENTS
        ), idSet()));
        install(role(_Role.ORGANIZATIONAL_REGISTRATION_CLERK, "Registration Clerk", idSet(
                _Role.APPLICATION_REGISTERS_PATIENTS
        ), idSet()));
        install(role(_Role.ORGANIZATIONAL_SYSTEM_ADMINISTRATOR, "System Administrator", idSet(
                _Role.APPLICATION_ADMINISTERS_SYSTEM,
                _Role.APPLICATION_MANAGES_ATLAS,
                _Role.APPLICATION_CONFIGURES_METADATA,
                _Role.APPLICATION_CONFIGURES_FORMS
        ), idSet()));
    }

}
