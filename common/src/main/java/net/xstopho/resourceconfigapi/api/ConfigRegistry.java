package net.xstopho.resourceconfigapi.api;


import net.xstopho.resourceconfigapi.builder.IResourceConfigBuilder;
import net.xstopho.resourceconfigapi.config.ModConfigFile;
import net.xstopho.resourceconfigapi.platform.Services;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class ConfigRegistry {

    private static final HashMap<String, ModConfigFile> MOD_CONFIG_FILES = new LinkedHashMap<>();

    public static void register(String modId, String fileName, IResourceConfigBuilder builder, String folderName, boolean disableRangedComments) {
        validateBuilder(builder);
        MOD_CONFIG_FILES.put(fileName, new ModConfigFile(modId, fileName, builder, Path.of(Services.getConfigPath() + "/" + folderName), disableRangedComments));
    }

    public static void register(String modId, IResourceConfigBuilder builder, String folderName, boolean disableRangedComments) {
        validateBuilder(builder);
        MOD_CONFIG_FILES.put(modId, new ModConfigFile(modId, modId, builder, Path.of(Services.getConfigPath() + "/" + folderName), disableRangedComments));
    }

    public static void register(String modId, String fileName, IResourceConfigBuilder builder, boolean disableRangedComments) {
        validateBuilder(builder);
        MOD_CONFIG_FILES.put(fileName, new ModConfigFile(modId, fileName, builder, Services.getConfigPath(), disableRangedComments));
    }

    public static void register(String modId, IResourceConfigBuilder builder, boolean disableRangedComments) {
        validateBuilder(builder);
        MOD_CONFIG_FILES.put(modId, new ModConfigFile(modId, modId, builder, Services.getConfigPath(), disableRangedComments));
    }

    public static HashMap<String, ModConfigFile> getConfigFiles() {
        return MOD_CONFIG_FILES;
    }

    private static void validateBuilder(IResourceConfigBuilder builder) {
        for (ModConfigFile configFile : MOD_CONFIG_FILES.values()) {
            if (configFile.getBuilder().equals(builder)) {
                throw new IllegalStateException("You try to register the same ResourceConfigBuilder twice!");
            }
        }
    }
}
