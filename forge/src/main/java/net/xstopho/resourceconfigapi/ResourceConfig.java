package net.xstopho.resourceconfigapi;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.SimpleChannel;

@Mod(ResourceConfigConstants.MOD_ID)
public class ResourceConfig {

    public static SimpleChannel NETWORK;

    public ResourceConfig() {
        //FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doCommonStuff);
    }

    private void doCommonStuff(FMLCommonSetupEvent event) {
       // event.enqueueWork(ConfigNetwork::setupPackets);
    }
}
