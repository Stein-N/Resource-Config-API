package net.xstopho.resourceconfigapi_test.handler;

import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.xstopho.resourceconfigapi_test.ResourceConfigTest;

@Mod.EventBusSubscriber(modid = ResourceConfigTest.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeEventHandler {

    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {

    }
}
