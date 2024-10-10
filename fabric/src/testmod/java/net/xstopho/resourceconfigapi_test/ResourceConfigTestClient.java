package net.xstopho.resourceconfigapi_test;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.network.chat.Component;
import net.xstopho.resourceconfigapi.example_configs.ExampleCommonConfig;

public class ResourceConfigTestClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(ClientCommandManager.literal("checkConfig").executes(context -> {
                context.getSource().sendFeedback(Component.literal("Boolean: " + ExampleCommonConfig.BOOLEAN.get()));
                context.getSource().sendFeedback(Component.literal("Byte: " + ExampleCommonConfig.BYTE.get()));
                context.getSource().sendFeedback(Component.literal("Character: " + ExampleCommonConfig.CHARACTER.get()));
                context.getSource().sendFeedback(Component.literal("Double: " + ExampleCommonConfig.DOUBLE.get()));
                context.getSource().sendFeedback(Component.literal("Float: " + ExampleCommonConfig.FLOAT.get()));
                context.getSource().sendFeedback(Component.literal("Integer: " + ExampleCommonConfig.INTEGER.get()));
                context.getSource().sendFeedback(Component.literal("Long: " + ExampleCommonConfig.LONG.get()));
                context.getSource().sendFeedback(Component.literal("Short: " + ExampleCommonConfig.SHORT.get()));
                context.getSource().sendFeedback(Component.literal("String: " + ExampleCommonConfig.STRING.get()));
                context.getSource().sendFeedback(Component.literal("Integer List: " + ExampleCommonConfig.INTEGER_LIST.get()));
                context.getSource().sendFeedback(Component.literal("Enum: " + ExampleCommonConfig.ENUM.get()));

                return 0;
            }));

            dispatcher.register(ClientCommandManager.literal("checkTranslation").executes(context -> {
                String path = "General.enableModMenu";
                String pathTranslation = "config.path.general.enableModMenu";

                Component noTranslation = Component.translatable(pathTranslation);

                context.getSource().sendFeedback(noTranslation.getString().equals(pathTranslation) ? Component.literal(path) : Component.translatable(pathTranslation));
                context.getSource().sendFeedback(Component.translatable("config.test.tooltip2"));
                return 0;
            }));
        });
    }
}
