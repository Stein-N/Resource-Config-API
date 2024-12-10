package net.xstopho.resourceconfigapi_test.handler;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.xstopho.resourceconfigapi_test.ResourceConfigTest;

@EventBusSubscriber(modid = ResourceConfigTest.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class NeoForgeEventHandler {

    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {

    }
}
