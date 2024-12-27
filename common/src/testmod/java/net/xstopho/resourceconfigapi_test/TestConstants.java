package net.xstopho.resourceconfigapi_test;

import net.xstopho.resourceconfigapi.api.ConfigRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestConstants {

    public static final String MOD_ID = "resourceconfigapi_test";
    public static final String MOD_NAME = "Resource Config API Test";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

    public static void commonInit() {
        ConfigRegistry.register(TestConfigs.CommonConfig.class, MOD_ID);
        ConfigRegistry.register(TestConfigs.ServerConfig.class, MOD_ID);
        ConfigRegistry.register(TestConfigs.ClientConfig.class, MOD_ID);
    }

    public static void clientInit() {

    }
}
