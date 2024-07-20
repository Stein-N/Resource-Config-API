package net.xstopho.resourceconfigapi_test.handler;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.xstopho.resourceconfigapi_test.ResourceConfigTest;
import net.xstopho.resourceconfigapi_test.TestConfig;

@EventBusSubscriber(modid = ResourceConfigTest.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class NeoForgeEventHandler {

    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();

        dispatcher.register(Commands.literal("checkConfig").executes(context -> {
            context.getSource().sendSystemMessage(Component.literal("Synced Integer: " + TestConfig.SYNCED_INTEGER.get()));
            context.getSource().sendSystemMessage(Component.literal("Synced Double: " + TestConfig.SYNCED_DOUBLE.get()));
            context.getSource().sendSystemMessage(Component.literal("Synced String: " + TestConfig.SYNCED_STRING.get()));
            context.getSource().sendSystemMessage(Component.literal("Synced Boolean: " + TestConfig.SYNCED_BOOLEAN.get()));

            return 0;
        }));
    }
}
