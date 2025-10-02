package net.xstopho.resourceconfigapi.handler;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.xstopho.resourceconfigapi.ConfigConstants;
import net.xstopho.resourceconfigapi.network.payloads.ConfigSyncPayload;
import net.xstopho.resourceconfigapi.network.payloads.ConfigUpdatePayload;

@EventBusSubscriber(modid = ConfigConstants.MOD_ID)
public class ModHandler {

    @SubscribeEvent
    public static void registerPayloads(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registry = event.registrar(ConfigConstants.MOD_ID).optional();

//        registry.playToServer(ConfigUpdatePayload.TYPE, ConfigUpdatePayload.CODEC,
//                (payload, context) -> ConfigUpdatePayload.handle(payload, context));

        registry.playToClient(ConfigSyncPayload.TYPE, ConfigSyncPayload.CODEC,
                (payload, context) -> ConfigSyncPayload.handle(payload));
    }
}
