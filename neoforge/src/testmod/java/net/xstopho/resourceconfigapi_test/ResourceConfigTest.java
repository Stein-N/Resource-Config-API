package net.xstopho.resourceconfigapi_test;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.xstopho.resourceconfigapi.api.ConfigRegistry;
import net.xstopho.resourceconfigapi.config.ResourceModConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(ResourceConfigTest.MOD_ID)
public class ResourceConfigTest {
    public static final String MOD_ID = "resourceconfigapi_test";
    public static final String MOD_NAME = "Resource Config API Test";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

    public static final ResourceModConfig CONFIG = ConfigRegistry.register(MOD_ID, TestConfig.BUILDER, false);

    public ResourceConfigTest(IEventBus bus) {

    }
}
