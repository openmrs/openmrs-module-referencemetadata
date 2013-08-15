package org.openmrs.module.referencemetadata;

import org.junit.Test;
import org.mockito.ArgumentMatcher;
import org.openmrs.GlobalProperty;
import org.openmrs.PatientIdentifierType;
import org.openmrs.api.AdministrationService;
import org.openmrs.api.PatientService;
import org.openmrs.module.emrapi.EmrApiConstants;
import org.openmrs.module.idgen.AutoGenerationOption;
import org.openmrs.module.idgen.IdentifierSource;
import org.openmrs.module.idgen.SequentialIdentifierGenerator;
import org.openmrs.module.idgen.service.IdentifierSourceService;
import org.openmrs.module.idgen.validator.LuhnMod30IdentifierValidator;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 *
 */
public class ReferenceMetadataActivatorTest {

    @Test
    public void testSetupOpenmrsIdWhenNotSetUp() throws Exception {
        /*AdministrationService administrationService = mock(AdministrationService.class);
        PatientService patientService = mock(PatientService.class);
        IdentifierSourceService identifierSourceService = mock(IdentifierSourceService.class);

        ReferenceMetadataActivator activator = new ReferenceMetadataActivator();
        activator.setupOpenmrsId(administrationService, patientService, identifierSourceService);

        verify(patientService).savePatientIdentifierType(argThat(new ArgumentMatcher<PatientIdentifierType>() {
            @Override
            public boolean matches(Object argument) {
                PatientIdentifierType actual = (PatientIdentifierType) argument;
                return actual.getName().equals(ReferenceMetadataConstants.OPENMRS_ID_NAME);
            }
        }));

        verify(identifierSourceService).saveIdentifierSource(argThat(new ArgumentMatcher<IdentifierSource>() {
            @Override
            public boolean matches(Object argument) {
                if (!(argument instanceof SequentialIdentifierGenerator)) {
                    return false;
                }
                SequentialIdentifierGenerator actual = (SequentialIdentifierGenerator) argument;
                return actual.getFirstIdentifierBase().equals("10000") &&
                        actual.getBaseCharacterSet().equals(new LuhnMod30IdentifierValidator().getBaseCharacters());
            }
        }));

        verify(identifierSourceService).saveAutoGenerationOption(any(AutoGenerationOption.class));

        verify(administrationService).saveGlobalProperty(argThat(new ArgumentMatcher<GlobalProperty>() {
            @Override
            public boolean matches(Object argument) {
                GlobalProperty actual = (GlobalProperty) argument;
                return actual.getProperty().equals(EmrApiConstants.PRIMARY_IDENTIFIER_TYPE) && actual.getPropertyValue().equals(ReferenceMetadataConstants.OPENMRS_ID_UUID);
            }
        }));*/
    }

}
