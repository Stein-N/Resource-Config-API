package net.xstopho.resourceconfigapi;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.SimpleChannel;

@Mod(Constants.MOD_ID)
public class ResourceConfig {

    public static SimpleChannel NETWORK;

    public ResourceConfig(FMLJavaModLoadingContext context) {

    }

}
