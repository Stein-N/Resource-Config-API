package net.xstopho.resourceconfigapi_test.handler;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.xstopho.resourceconfigapi_test.TestConfigs;
import net.xstopho.resourceconfigapi_test.TestConstants;

@EventBusSubscriber(modid = TestConstants.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class NeoforgeHandler {

    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();

        dispatcher.register(Commands.literal("validateConfig").executes(context -> {

            context.getSource().sendSystemMessage(Component.literal("Common Config: " + TestConfigs.CommonConfig.disableModMenuCompat));
            context.getSource().sendSystemMessage(Component.literal("Client Config: " + TestConfigs.ClientConfig.itemSizeMultiplier));
            context.getSource().sendSystemMessage(Component.literal("Server Config: " + TestConfigs.ServerConfig.updateSpeed));

            return 0;
        }));
    }
}
