package net.xstopho.resourceconfigapi.handler;

import net.minecraft.resources.Identifier;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.xstopho.resourceconfigapi.ConfigConstants;
import net.xstopho.resourceconfigapi.api.ConfigRegistry;
import net.xstopho.resourceconfigapi.client.gui.screen.ResourceConfigScreen;
import net.xstopho.resourceconfigapi.config.ModConfig;

import java.util.Map;
import java.util.Optional;

@EventBusSubscriber(modid = ConfigConstants.MOD_ID, value = Dist.CLIENT)
public class ModClientHandler {

    @SubscribeEvent
    public static void registerModConfigScreens(FMLClientSetupEvent event) {
        for (Map.Entry<Identifier, ModConfig> entry : ConfigRegistry.getConfigEntries()) {
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
