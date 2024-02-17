package net.xstopho.resource_config_api.platform.services;

import java.nio.file.Path;

public interface IPlatformHelper {

    Path getConfigDir();

    boolean isDevEnvironment();

}