package org.openmrs.module.referencemetadata;

/**
 *
 */
public class ReferenceMetadataConstants {

    public final static String OPENMRS_ID_NAME = "OpenMRS ID";
    public final static String OPENMRS_ID_DESCRIPION = "OpenMRS patient identifier, with check-digit";
    public final static String OPENMRS_ID_UUID = "05a29f94-c0ed-11e2-94be-8c13b969e334";

    public final static String OPENMRS_ID_GENERATOR_UUID = "691eed12-c0f1-11e2-94be-8c13b969e334";
    public final static String OPENMRS_ID_GENERATOR_NAME = "Generator for " + OPENMRS_ID_NAME;

    public final static String UNKNOWN_LOCATION_UUID = "8d6c993e-c2cc-11de-8d13-0010c6dffd0f";
    
    public static final String VISIT_LOCATION_TAG_UUID = "37dd4458-dc9e-4ae6-a1f1-789c1162d37b";
	
	public static final String ADMISSION_LOCATION_TAG_UUID = "1c783dca-fd54-4ea8-a0fc-2875374e9cb6";
	
	public static final String TRANSFER_LOCATION_TAG_UUID = "8d4626ca-7abd-42ad-be48-56767bbcf272";
	
	public static final String LOGIN_LOCATION_TAG_UUID = "b8bbf83e-645f-451f-8efe-a0db56f09676";
	
	public static final String INSTALLED_VERSION_GP = "referencemetadata.installedVersion";
	
	/**
	 * Should be set to a higher version in case of any changes in bundled metadata
	 */
	public static final Integer METADATA_VERSION = 17;

}
