package net.xstopho.resourceconfigapi_test;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(TestConstants.MOD_ID)
public class ResourceConfigTest {

    public ResourceConfigTest(IEventBus bus) {
        TestConstants.init();
    }
}
