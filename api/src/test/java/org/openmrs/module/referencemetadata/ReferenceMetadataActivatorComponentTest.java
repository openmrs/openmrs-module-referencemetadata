package org.openmrs.module.referencemetadata;

import org.hibernate.cfg.Environment;
import org.junit.Test;
import org.openmrs.PatientIdentifierType;
import org.openmrs.api.PatientService;
import org.openmrs.api.context.Context;
import org.openmrs.module.idgen.service.IdentifierSourceService;
import org.openmrs.test.BaseModuleContextSensitiveTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.NotTransactional;

import java.util.Properties;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 *
 */
@DirtiesContext
public class ReferenceMetadataActivatorComponentTest extends BaseModuleContextSensitiveTest {

    @Override
    public Properties getRuntimeProperties() {
        Properties props = super.getRuntimeProperties();
        String url = props.getProperty(Environment.URL);
        if (url.contains("jdbc:h2:") && !url.contains(";MVCC=TRUE")) {
            props.setProperty(Environment.URL, url + ";MVCC=true");
        }
        return props;
    }

    @Test
    @NotTransactional
    public void testSetupOpenmrsId() throws Exception {
        ReferenceMetadataActivator activator = new ReferenceMetadataActivator();
        activator.started();

        Context.flushSession();

        PatientService patientService = Context.getPatientService();
        PatientIdentifierType openmrsIdType = patientService.getPatientIdentifierTypeByName(ReferenceMetadataConstants.OPENMRS_ID_NAME);

        IdentifierSourceService identifierSourceService = Context.getService(IdentifierSourceService.class);

        assertThat(identifierSourceService.generateIdentifier(openmrsIdType, "testing"), is("100010"));
        assertThat(identifierSourceService.generateIdentifier(openmrsIdType, "testing"), is("100020"));
    }

}
