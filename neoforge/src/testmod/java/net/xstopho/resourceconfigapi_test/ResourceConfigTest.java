package net.xstopho.resourceconfigapi_test;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.xstopho.resourceconfigapi.api.ConfigRegistry;
import net.xstopho.resourceconfigapi.builder.ResourceConfigBuilder;
import net.xstopho.resourceconfigapi.gui.ResourceConfigScreenRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(ResourceConfigTest.MOD_ID)
public class ResourceConfigTest {
    public static final String MOD_ID = "resourceconfigapi_test";
    public static final String MOD_NAME = "Resource Config API Test";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

    public ResourceConfigTest(IEventBus bus) {
        bus.addListener(this::doClientStuff);
    }

    private void doClientStuff(FMLClientSetupEvent event) {
        ResourceConfigScreenRegistry.register(MOD_ID, ConfigRegistry.register(MOD_ID, TestConfig.BUILDER, true));
    }
}
