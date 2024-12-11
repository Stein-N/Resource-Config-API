package net.xstopho.resourceconfigapi.handler;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.xstopho.resourceconfigapi.Constants;
import net.xstopho.resourceconfigapi.network.server.SyncConfigPayload;

@EventBusSubscriber(modid = Constants.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ModHandler {

    @SubscribeEvent
    public static void registerPayloads(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar payload = event.registrar(Constants.MOD_ID);

        payload.playToClient(SyncConfigPayload.TYPE, SyncConfigPayload.CODEC, SyncConfigPayload::handle);
    }
}
