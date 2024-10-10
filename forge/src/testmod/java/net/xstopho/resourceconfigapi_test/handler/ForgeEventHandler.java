package net.xstopho.resourceconfigapi_test.handler;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.xstopho.resourceconfigapi.example_configs.ExampleCommonConfig;
import net.xstopho.resourceconfigapi_test.ResourceConfigTest;

@Mod.EventBusSubscriber(modid = ResourceConfigTest.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeEventHandler {

    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();

        dispatcher.register(Commands.literal("checkConfig").executes(context -> {
            context.getSource().sendSystemMessage(Component.literal("Boolean: " + ExampleCommonConfig.BOOLEAN.get()));
            context.getSource().sendSystemMessage(Component.literal("Byte: " + ExampleCommonConfig.BYTE.get()));
            context.getSource().sendSystemMessage(Component.literal("Character: " + ExampleCommonConfig.CHARACTER.get()));
            context.getSource().sendSystemMessage(Component.literal("Double: " + ExampleCommonConfig.DOUBLE.get()));
            context.getSource().sendSystemMessage(Component.literal("Float: " + ExampleCommonConfig.FLOAT.get()));
            context.getSource().sendSystemMessage(Component.literal("Integer: " + ExampleCommonConfig.INTEGER.get()));
            context.getSource().sendSystemMessage(Component.literal("Long: " + ExampleCommonConfig.LONG.get()));
            context.getSource().sendSystemMessage(Component.literal("Short: " + ExampleCommonConfig.SHORT.get()));
            context.getSource().sendSystemMessage(Component.literal("String: " + ExampleCommonConfig.STRING.get()));
            context.getSource().sendSystemMessage(Component.literal("Integer List: " + ExampleCommonConfig.INTEGER_LIST.get()));
            context.getSource().sendSystemMessage(Component.literal("Enum: " + ExampleCommonConfig.ENUM.get()));

            return 0;
        }));
    }
}
