package net.morthen.resourceconfigapi.platform;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.fml.loading.FMLPaths;

import java.nio.file.Path;

public class NeoForgePlatformHelper implements PlatformHelper {

    private String modName;

    @Override
    public Path getConfigDir() {
        return FMLPaths.CONFIGDIR.get();
    }

    @Override
    public Path getServerConfigDir() {
        return Path.of("./world/serverconfig");
    }

    @Override
    public boolean isServer() {
        return FMLLoader.getCurrent().getDist().equals(Dist.DEDICATED_SERVER);
    }

    @Override
    public boolean isDevEnv() {
        return !FMLLoader.getCurrent().isProduction();
    }

    @Override
    public String getModName(String modId) {
        ModList.get().getMods().stream()
                .filter(iModInfo -> iModInfo.getModId().equals(modId))
                .findFirst()
                .ifPresent(iModInfo -> modName = iModInfo.getDisplayName());
        return modName;
    }
}
