package net.xstopho.resourceconfigapi;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.SimpleChannel;
import net.xstopho.resourceconfigapi.api.ConfigRegistry;
import net.xstopho.resourceconfigapi.config.ModConfig;
import net.xstopho.resourceconfigapi.client.gui.screen.ResourceConfigScreen;
import net.xstopho.resourceconfigapi.network.ConfigNetwork;

import java.util.Map;
import java.util.Optional;

@Mod(Constants.MOD_ID)
public class ResourceConfig {

    public static SimpleChannel NETWORK;

    public ResourceConfig(FMLJavaModLoadingContext context) {
        context.getModEventBus().addListener(this::initNetwork);
        context.getModEventBus().addListener(this::registerModConfigScreens);
    }

    private void initNetwork(FMLCommonSetupEvent event) {
        event.enqueueWork(ConfigNetwork::initPayloads);
    }

    private void registerModConfigScreens(FMLClientSetupEvent event) {
        for (Map.Entry<ResourceLocation, ModConfig> entry : ConfigRegistry.CONFIGS.entrySet()) {
            Optional<? extends ModContainer> container = ModList.get().getModContainerById(entry.getKey().getNamespace());

            container.ifPresent(modContainer -> {
                container.get().registerExtensionPoint(
                        ConfigScreenHandler.ConfigScreenFactory.class,
                        () -> new ConfigScreenHandler.ConfigScreenFactory(
                                (minecraft, screen) -> new ResourceConfigScreen(screen, modContainer.getModId())
                        )
                );
            });
        }
    }
}
