package net.xstopho.resourceconfigapi;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.SimpleChannel;
import net.xstopho.resourceconfigapi.network.ForgeConfigNetwork;
import net.xstopho.resourceconfigapi.network.PayloadBuilder;

@Mod(ConfigConstants.MOD_ID)
public class ResourceConfig {

    public static SimpleChannel NETWORK;

    public ResourceConfig(FMLJavaModLoadingContext context) {
        var modBus = context.getModBusGroup();
        FMLCommonSetupEvent.getBus(modBus).addListener(this::initNetwork);
    }

    private void initNetwork(FMLCommonSetupEvent event) {
        event.enqueueWork(PayloadBuilder::build);
    }
}
