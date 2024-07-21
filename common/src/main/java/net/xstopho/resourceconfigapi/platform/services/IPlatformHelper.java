package net.xstopho.resourceconfigapi.platform.services;

import java.nio.file.Path;

public interface IPlatformHelper {

    Path getConfigDir();

    String getModName(String modId);
}