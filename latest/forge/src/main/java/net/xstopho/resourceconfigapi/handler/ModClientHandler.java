package net.xstopho.resourceconfigapi.handler;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.xstopho.resourceconfigapi.ConfigConstants;
import net.xstopho.resourceconfigapi.api.ConfigRegistry;
import net.xstopho.resourceconfigapi.client.gui.screen.ResourceConfigScreen;
import net.xstopho.resourceconfigapi.config.ModConfig;

import java.util.Map;
import java.util.Optional;

@Mod.EventBusSubscriber(modid = ConfigConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModClientHandler {

    @SubscribeEvent
    public static void registerModConfigScreens(FMLClientSetupEvent event) {
        for (Map.Entry<ResourceLocation, ModConfig> entry : ConfigRegistry.getConfigEntries()) {
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
