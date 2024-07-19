package net.xstopho.resourceconfigapi_test;

import net.neoforged.fml.common.Mod;
import net.xstopho.resourceconfigapi.api.ConfigRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(ResourceConfigTest.MOD_ID)
public class ResourceConfigTest {
    public static final String MOD_ID = "resourceconfigapi_test";
    public static final String MOD_NAME = "Resource Config API Test";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

    public ResourceConfigTest() {
        ConfigRegistry.register(MOD_ID, TestConfig.BUILDER, MOD_ID, false);
        ConfigRegistry.register(MOD_ID, MOD_ID + "_without", TestConfig.BUILDER, MOD_ID, true);

        ConfigRegistry.register(MOD_ID,TestConfig.BUILDER, false);
        ConfigRegistry.register(MOD_ID, MOD_ID + "_without",TestConfig.BUILDER, true);
    }
}
