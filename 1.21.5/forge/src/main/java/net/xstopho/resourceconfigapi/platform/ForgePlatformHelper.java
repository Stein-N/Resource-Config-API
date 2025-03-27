package net.xstopho.resourceconfigapi.platform;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.fml.loading.FMLPaths;

import java.nio.file.Path;

public class ForgePlatformHelper implements PlatformHelper {

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
        return FMLLoader.getDist().equals(Dist.DEDICATED_SERVER);
    }

    @Override
    public boolean isDevEnv() {
        return !FMLLoader.isProduction();
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
