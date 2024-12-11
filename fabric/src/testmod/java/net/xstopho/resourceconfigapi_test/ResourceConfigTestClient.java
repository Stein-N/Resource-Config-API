package net.xstopho.resourceconfigapi_test;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.network.chat.Component;

public class ResourceConfigTestClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientCommandRegistrationCallback.EVENT.register((commandDispatcher, commandBuildContext) -> {
            commandDispatcher.register(ClientCommandManager.literal("validateClientConfig").executes(commandContext -> {

                commandContext.getSource().sendFeedback(Component.literal("Common: " + TestConfigs.CommonConfig.disableModMenuCompat));
                commandContext.getSource().sendFeedback(Component.literal("Client: " + TestConfigs.ClientConfig.itemSizeMultiplier));
                commandContext.getSource().sendFeedback(Component.literal("Server: " + TestConfigs.ServerConfig.updateSpeed));

                return 0;
            }));
        });
    }
}
