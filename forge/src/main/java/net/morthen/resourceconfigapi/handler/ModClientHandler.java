package net.morthen.resourceconfigapi.handler;

import net.minecraft.resources.Identifier;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.morthen.resourceconfigapi.ConfigConstants;
import net.morthen.resourceconfigapi.api.ConfigRegistry;
import net.morthen.resourceconfigapi.client.gui.screen.ResourceConfigScreen;
import net.morthen.resourceconfigapi.config.ModConfig;

import java.util.Map;
import java.util.Optional;

@Mod.EventBusSubscriber(modid = ConfigConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModClientHandler {

    @SubscribeEvent
    public static void registerModConfigScreens(FMLClientSetupEvent event) {
        for (Map.Entry<Identifier, ModConfig> entry : ConfigRegistry.getConfigEntries()) {
            Optional<? extends ModContainer> container = ModList.getModContainerById(entry.getKey().getNamespace());

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
