package net.xstopho.resourceconfigapi_test;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.network.chat.Component;

public class ResourceConfigTestClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(ClientCommandManager.literal("checkConfig").executes(context -> {
                context.getSource().sendFeedback(Component.literal("Synced Integer: " + TestConfig.SYNCED_INTEGER.get()));

                context.getSource().sendFeedback(Component.literal("Client: " + TestConfig.SYNCED_DOUBLE.get()));

                context.getSource().sendFeedback(Component.literal("Client: " + TestConfig.SYNCED_STRING.get()));

                context.getSource().sendFeedback(Component.literal("Client: " + TestConfig.SYNCED_BOOLEAN.get()));

                return 0;
            }));
        });
    }
}
