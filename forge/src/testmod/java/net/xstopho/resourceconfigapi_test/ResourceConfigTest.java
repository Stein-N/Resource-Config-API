package net.xstopho.resourceconfigapi_test;

import net.minecraftforge.fml.common.Mod;
import net.xstopho.resourceconfigapi.api.ConfigRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(ResourceConfigTest.MOD_ID)
public class ResourceConfigTest {
    public static final String MOD_ID = "resourceconfigapi_test";
    public static final String MOD_NAME = "Resource Config API Test";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

    public ResourceConfigTest() {
        ConfigRegistry.register(MOD_ID,TestConfig.BUILDER, true);
    }
}
