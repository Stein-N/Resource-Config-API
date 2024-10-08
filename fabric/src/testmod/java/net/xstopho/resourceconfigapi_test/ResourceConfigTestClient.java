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
                context.getSource().sendFeedback(Component.literal("Boolean: " + TestConfig.BOOLEAN.get()));
                context.getSource().sendFeedback(Component.literal("Byte: " + TestConfig.BYTE.get()));
                context.getSource().sendFeedback(Component.literal("Character: " + TestConfig.CHARACTER.get()));
                context.getSource().sendFeedback(Component.literal("Double: " + TestConfig.DOUBLE.get()));
                context.getSource().sendFeedback(Component.literal("Float: " + TestConfig.FLOAT.get()));
                context.getSource().sendFeedback(Component.literal("Integer: " + TestConfig.INTEGER.get()));
                context.getSource().sendFeedback(Component.literal("Long: " + TestConfig.LONG.get()));
                context.getSource().sendFeedback(Component.literal("Short: " + TestConfig.SHORT.get()));
                context.getSource().sendFeedback(Component.literal("String: " + TestConfig.STRING.get()));
                context.getSource().sendFeedback(Component.literal("Integer List: " + TestConfig.INTEGER_LIST.get()));
                context.getSource().sendFeedback(Component.literal("Enum: " + TestConfig.ENUM.get()));

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
