package net.morthen.template.testmod;

import net.morthen.resourceconfigapi.ConfigConstants;
import net.morthen.resourceconfigapi.api.ConfigRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestmodConstants {
    public static final String MOD_ID = ConfigConstants.MOD_ID + "_testmod";
    public static final String MOD_NAME = ConfigConstants.MOD_NAME + " - Testmod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    public static void commonInit() {
        ConfigRegistry.register(TestConfigs.ClientConfig.class, MOD_ID);
        ConfigRegistry.register(TestConfigs.CommonConfig.class, MOD_ID);
        ConfigRegistry.register(TestConfigs.ServerConfig.class, MOD_ID);
        ConfigRegistry.register(TestConfigs.Generators.class, MOD_ID);
    }
}
