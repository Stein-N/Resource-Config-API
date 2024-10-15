package net.xstopho.resourceconfigapi;

import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.SimpleChannel;
import net.xstopho.resourceconfigapi.api.ConfigRegistry;
import net.xstopho.resourceconfigapi.api.ConfigType;
import net.xstopho.resourceconfigapi.config.ResourceModConfig;
import net.xstopho.resourceconfigapi.gui.screen.ResourceConfigScreen;

import java.util.Map;
import java.util.Optional;

@Mod(ResourceConfigConstants.MOD_ID)
public class ResourceConfig {

    public static SimpleChannel NETWORK;

    public ResourceConfig(FMLJavaModLoadingContext context) {
        context.getModEventBus().addListener(this::registerConfigScreens);
    }

    private void registerConfigScreens(FMLCommonSetupEvent event) {
        for (Map.Entry<String, Map<ConfigType, ResourceModConfig>> entry : ConfigRegistry.getModConfigFiles().entrySet()) {
            Optional<? extends ModContainer> container = ModList.get().getModContainerById(entry.getKey());

            container.ifPresent(modContainer -> {
                container.get().registerExtensionPoint(
                        ConfigScreenHandler.ConfigScreenFactory.class,
                        () -> new ConfigScreenHandler.ConfigScreenFactory(
                                (minecraft, screen) -> new ResourceConfigScreen(screen, entry.getKey())
                        )
                );
            });
        }
    }
}
