package net.xstopho.resourceconfigapi.handler;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.xstopho.resourceconfigapi.ResourceConfigConstants;
import net.xstopho.resourceconfigapi.network.ConfigNetwork;

@EventBusSubscriber(modid = ResourceConfigConstants.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ResourceConfigEventHandler {

    @SubscribeEvent
    public static void registerPacketHandler(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar payload = event.registrar(ResourceConfigConstants.MOD_ID);

        ConfigNetwork.initClient(payload);
    }
}
