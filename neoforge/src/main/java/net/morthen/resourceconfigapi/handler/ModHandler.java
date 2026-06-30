package net.morthen.resourceconfigapi.handler;

import net.morthen.resourceconfigapi.ConfigConstants;
import net.morthen.resourceconfigapi.network.payloads.ConfigSyncPayload;
import net.morthen.resourceconfigapi.network.payloads.ConfigUpdatePayload;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = ConfigConstants.MOD_ID)
public class ModHandler {

    @SubscribeEvent
    public static void registerPayloads(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registry = event.registrar(ConfigConstants.MOD_ID).optional();

        registry.playToServer(ConfigUpdatePayload.TYPE, ConfigUpdatePayload.CODEC,
                (ConfigUpdatePayload payload, IPayloadContext context) -> ConfigUpdatePayload.handle(payload, context.player().level().getServer()));

        registry.playToClient(ConfigSyncPayload.TYPE, ConfigSyncPayload.CODEC,
                (payload, context) -> ConfigSyncPayload.handle(payload));
    }
}
