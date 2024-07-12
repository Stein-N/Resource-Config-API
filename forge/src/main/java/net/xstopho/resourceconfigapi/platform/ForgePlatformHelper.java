package net.xstopho.resourceconfigapi.platform;

import net.minecraftforge.fml.loading.FMLPaths;
import net.xstopho.resourceconfigapi.platform.services.IPlatformHelper;

import java.nio.file.Path;

public class ForgePlatformHelper implements IPlatformHelper {
    @Override
    public Path getConfigDir() {
        return FMLPaths.CONFIGDIR.get();
    }
}
