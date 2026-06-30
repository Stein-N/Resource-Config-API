package net.morthen.resourceconfigapi.platform;

import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Path;

public class FabricPlatformHelper implements PlatformHelper {

    private String modName;

    @Override
    public Path getConfigDir() {
        return FabricLoader.getInstance().getConfigDir();
    }

    @Override
    public Path getServerConfigDir() {
        return Path.of("./world/serverconfig");
    }

    @Override
    public boolean isServer() {
        return FabricLoader.getInstance().getEnvironmentType().equals(EnvType.SERVER);
    }

    @Override
    public boolean isDevEnv() {
        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }

    @Override
    public String getModName(String modId) {
        FabricLoader.getInstance().getModContainer(modId).ifPresent(modContainer -> {
            modName = modContainer.getMetadata().getName();
        });
        return modName;
    }
}
