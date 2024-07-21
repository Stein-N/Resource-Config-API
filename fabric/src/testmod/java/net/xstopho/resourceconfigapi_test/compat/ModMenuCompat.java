package net.xstopho.resourceconfigapi_test.compat;

import net.xstopho.resourceconfigapi.ResourceConfigConstants;
import net.xstopho.resourceconfigapi.gui.ResourceConfigScreenEntrypoint;
import net.xstopho.resourceconfigapi_test.ResourceConfigTest;

public class ModMenuCompat extends ResourceConfigScreenEntrypoint {
    public ModMenuCompat() {
        super(ResourceConfigConstants.MOD_ID, ResourceConfigTest.CONFIG);
    }
}
