package net.xstopho.resourceconfigapi;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.xstopho.resourceconfigapi.api.ConfigRegistry;
import net.xstopho.resourceconfigapi.config.ModConfig;
import net.xstopho.resourceconfigapi.gui.screen.ResourceConfigScreen;

import java.util.Map;
import java.util.Optional;

@Mod(Constants.MOD_ID)
public class ResourceConfig {

    public ResourceConfig(IEventBus eventBus) {
        eventBus.addListener(this::registerModConfigScreens);
    }

    public void registerModConfigScreens(FMLClientSetupEvent event) {
        for (Map.Entry<ResourceLocation, ModConfig> entry : ConfigRegistry.CONFIGS.entrySet()) {
            Optional<? extends ModContainer> container = ModList.get().getModContainerById(entry.getKey().getNamespace());

            container.ifPresent(modContainer -> {
                ModLoadingContext.get().setActiveContainer(modContainer);
                ModLoadingContext.get().registerExtensionPoint(
                        IConfigScreenFactory.class,
                        () -> (modContainer1, screen) -> new ResourceConfigScreen(screen, modContainer1.getModId())
                );
            });
        }
    }
}
