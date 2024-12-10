package net.xstopho.resourceconfigapi_test;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(TestConstants.MOD_ID)
public class ResourceConfigTest {

    public ResourceConfigTest(IEventBus bus) {
        TestConstants.commonInit();
    }
}
