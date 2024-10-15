package net.xstopho.resourceconfigapi;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.xstopho.resourceconfigapi.api.ConfigRegistry;
import net.xstopho.resourceconfigapi.api.ConfigType;
import net.xstopho.resourceconfigapi.config.ResourceModConfig;
import net.xstopho.resourceconfigapi.gui.screen.ResourceConfigScreen;

import java.util.Map;
import java.util.Optional;

@Mod(ResourceConfigConstants.MOD_ID)
public class ResourceConfig {

    public ResourceConfig(IEventBus bus) {
        bus.addListener(this::registerConfigScreens);
    }

    private void registerConfigScreens(FMLClientSetupEvent event) {
        for (Map.Entry<String, Map<ConfigType, ResourceModConfig>> entry : ConfigRegistry.getModConfigFiles().entrySet()) {
            Optional<? extends ModContainer> context = ModList.get().getModContainerById(entry.getKey());

            context.ifPresent(modContainer -> {
                ModLoadingContext.get().setActiveContainer(modContainer);
                ModLoadingContext.get().registerExtensionPoint(
                        IConfigScreenFactory.class,
                        () -> (modContainer1, screen) -> new ResourceConfigScreen(screen, entry.getKey())
                );
            });
        }
    }
}
