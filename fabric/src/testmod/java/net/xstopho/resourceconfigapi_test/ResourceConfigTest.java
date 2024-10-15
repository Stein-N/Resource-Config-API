package net.xstopho.resourceconfigapi_test;

import net.fabricmc.api.ModInitializer;
import net.xstopho.resourceconfigapi.api.ConfigRegistry;
import net.xstopho.resourceconfigapi.api.ConfigType;
import net.xstopho.resourceconfigapi.config.ResourceModConfig;
import net.xstopho.resourceconfigapi.example_configs.ExampleCommonConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResourceConfigTest implements ModInitializer {

    public static final String MOD_ID = "resourceconfigapi_test";
    public static final String MOD_NAME = "Resource Config API Test";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

    public static ResourceModConfig CONFIG = ConfigRegistry.register(MOD_ID, ConfigType.COMMON, ExampleCommonConfig.BUILDER);
    public static ResourceModConfig CONFIG_CLIENT = ConfigRegistry.register(MOD_ID, ConfigType.CLIENT, ExampleCommonConfig.BUILDER);
    public static ResourceModConfig CONFIG_SERVER = ConfigRegistry.register(MOD_ID, ConfigType.SERVER, ExampleCommonConfig.BUILDER);

    @Override
    public void onInitialize() {

    }
}
