package net.xstopho.resourceconfigapi.handler;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.xstopho.resourceconfigapi.ConfigConstants;
import net.xstopho.resourceconfigapi.network.payloads.ConfigSyncPayload;
import net.xstopho.resourceconfigapi.network.payloads.ConfigUpdatePayload;

@EventBusSubscriber(modid = ConfigConstants.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ModHandler {

    @SubscribeEvent
    public static void registerPayloads(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registry = event.registrar(ConfigConstants.MOD_ID);

        registry.playToServer(ConfigUpdatePayload.TYPE, ConfigUpdatePayload.CODEC,
                (payload, context) -> ConfigUpdatePayload.handle(payload, context.player().getServer()));

        registry.playToClient(ConfigSyncPayload.TYPE, ConfigSyncPayload.CODEC,
                (payload, context) -> ConfigSyncPayload.handle(payload));
    }
}
