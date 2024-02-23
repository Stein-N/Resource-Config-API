package net.xstopho.testmod;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.xstopho.resource_config_api.api.ConfigRegistry;

import java.nio.file.Path;

public class TestmodConfig implements ModInitializer {
    @Override
    public void onInitialize() {
        Path configDir = Path.of(FabricLoader.getInstance().getConfigDir() + "/customPath");

        ConfigRegistry.register("config_testmod", TestConfig.BUILDER);
        ConfigRegistry.register("config_testmod", "custom_file_name", TestConfig.BUILDER);
        ConfigRegistry.register("config_testmod", TestConfig.BUILDER, configDir);
        ConfigRegistry.register("config_testmod", "custom_file_name", TestConfig.BUILDER, configDir);
    }
}
