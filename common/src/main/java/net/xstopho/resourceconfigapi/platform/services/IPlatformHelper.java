package net.xstopho.resourceconfigapi.platform.services;

import java.nio.file.Path;

public interface IPlatformHelper {

    Path getConfigDir();

    boolean isServer();

    boolean isDevelopmentEnvironment();

    String getModName(String modId);
}