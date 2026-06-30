package net.morthen.resourceconfigapi_test;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

public class ResourceConfigTest implements ModInitializer {

    @Override
    public void onInitialize() {
        TestConstants.init();
        
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment)
                -> TestConstants.changeCommand(dispatcher));
    }
}
