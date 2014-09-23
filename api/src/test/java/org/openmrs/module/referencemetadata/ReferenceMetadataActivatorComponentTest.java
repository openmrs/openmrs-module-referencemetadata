package org.openmrs.module.referencemetadata;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.openmrs.Concept;
import org.openmrs.ConceptSource;
import org.openmrs.api.ConceptService;
import org.openmrs.api.UserService;
import org.openmrs.api.context.Context;
import org.openmrs.module.emrapi.EmrApiActivator;
import org.openmrs.module.emrapi.metadata.MetadataPackageConfig;
import org.openmrs.module.emrapi.metadata.MetadataPackagesConfig;
import org.openmrs.module.emrapi.utils.MetadataUtil;
import org.openmrs.module.metadatasharing.ImportedPackage;
import org.openmrs.module.metadatasharing.api.MetadataSharingService;
import org.openmrs.test.BaseModuleContextSensitiveTest;
import org.openmrs.test.SkipBaseSetup;
import org.openmrs.validator.ValidateUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/**
 *
 */
@SkipBaseSetup          // note that we skip the base setup because we don't want to include the standard test data
public class ReferenceMetadataActivatorComponentTest extends BaseModuleContextSensitiveTest {

    private ReferenceMetadataActivator activator;

    @Autowired
    private ConceptService conceptService;

    @Autowired
    private UserService userService;

    // loading MDS packages is expensive, so we do everything in a single test. This is typically not best practice, but it speeds the build significantly.
    @Test
    public void testEverything() throws Exception {
        initializeInMemoryDatabase();
        executeDataSet("requiredDataTestDataset.xml");
        authenticate();

        // we need to make sure that if emrapi has already created its concept source, we don't duplicate it
        ConceptSource emrapiSource = new EmrApiActivator().createConceptSource(conceptService);

        activator = new ReferenceMetadataActivator();
        activator.willRefreshContext();
        activator.contextRefreshed();
        activator.willStart();
        activator.started();
        
        verifyMetadataPackagesConfigured();

        verifySentinelData();

        // verify there's only one concept source representing the emrapi module (and we haven't duplicated it)
        int count = 0;
        for (ConceptSource candidate : conceptService.getAllConceptSources()) {
            if (candidate.getName().equals(emrapiSource.getName())) {
                ++count;
            }
        }
        assertThat(count, is(1));

    }

    private void verifyMetadataPackagesConfigured() throws Exception {
        MetadataPackagesConfig config;
        {
            InputStream inputStream = activator.getClass().getClassLoader().getResourceAsStream(MetadataUtil.PACKAGES_FILENAME);
            String xml = IOUtils.toString(inputStream);
            config = Context.getSerializationService().getDefaultSerializer()
                    .deserialize(xml, MetadataPackagesConfig.class);
        }

        MetadataSharingService metadataSharingService = Context.getService(MetadataSharingService.class);

        // To catch the (common) case where someone gets the groupUuid wrong, we look for any installed packages that
        // we are not expecting

        List<String> groupUuids = new ArrayList<String>();

        for (MetadataPackageConfig metadataPackage : config.getPackages()) {
            groupUuids.add(metadataPackage.getGroupUuid());
        }

        for (ImportedPackage importedPackage : metadataSharingService.getAllImportedPackages()) {
            if (!groupUuids.contains(importedPackage.getGroupUuid())) {
                fail("Found a package with an unexpected groupUuid. Name: " + importedPackage.getName()
                        + " , groupUuid: " + importedPackage.getGroupUuid());
            }
        }

        for (MetadataPackageConfig metadataPackage : config.getPackages()) {
            ImportedPackage installedPackage = metadataSharingService.getImportedPackageByGroup(metadataPackage
                    .getGroupUuid());
            Integer actualVersion = installedPackage == null ? null : installedPackage.getVersion();
            assertEquals("Failed to install " + metadataPackage.getFilenameBase() + ". Expected version: "
                    + metadataPackage.getVersion() + " Actual version: " + actualVersion, metadataPackage.getVersion(),
                    actualVersion);
        }

        // this doesn't strictly belong here, but we include it as an extra sanity check on the MDS module
        for (Concept concept : conceptService.getAllConcepts()) {
            ValidateUtil.validate(concept);
        }
    }

    private void verifySentinelData() {
        // Verify a few pieces of sentinel data that should have been in the packages
        assertNotNull(userService.getRole("Organizational: Doctor"));
        
        assertThat(conceptService.getConcept(5085).getUuid(), is("5085AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"));
        assertThat(conceptService.getConcept(159947).getUuid(), is("159947AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"));
        
        assertThat(conceptService.getAllConcepts().size(), is(414));
    }

}
