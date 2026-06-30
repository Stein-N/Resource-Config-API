package net.morthen.resourceconfigapi.platform;

import net.morthen.resourceconfigapi.util.ConfigUtils;

import java.nio.file.Path;

public interface PlatformHelper {
    PlatformHelper INSTANCE = ConfigUtils.load(PlatformHelper.class);

    Path getConfigDir();
    Path getServerConfigDir();
    boolean isServer();
    boolean isDevEnv();
    String getModName(String modId);
}
