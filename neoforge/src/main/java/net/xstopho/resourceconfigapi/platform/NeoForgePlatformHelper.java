package net.xstopho.resourceconfigapi.platform;

import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLPaths;
import net.xstopho.resourceconfigapi.platform.services.IPlatformHelper;

import java.nio.file.Path;

public class NeoForgePlatformHelper implements IPlatformHelper {

    private String modName;

    @Override
    public Path getConfigDir() {
        return FMLPaths.CONFIGDIR.get();
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
