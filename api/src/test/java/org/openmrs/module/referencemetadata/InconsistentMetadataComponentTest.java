package org.openmrs.module.referencemetadata;

import org.junit.Test;
import org.openmrs.module.emrapi.utils.MetadataUtil;
import org.openmrs.test.BaseModuleContextSensitiveTest;

/**
 * If multiple MDS packages contain different versions of the same item, then loading them is order-dependent, which
 * is bad.
 * This test looks through all MDS packages, and throws an error in this situation.
 */
public class InconsistentMetadataComponentTest extends BaseModuleContextSensitiveTest {

    @Test
    public void testThatThereAreNoMdsPackagesWithInconsistentVersionsOfTheSameItem() throws Exception {
        MetadataUtil.verifyNoMdsPackagesWithInconsistentVersionsOfTheSameItem(getClass().getClassLoader());
    }

}