package net.morthen.resourceconfigapi_test;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.morthen.resourceconfigapi.api.ConfigRegistry;
import net.morthen.resourceconfigapi.config.ModConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestConstants {

    public static final String MOD_ID = "resourceconfigapi_test";
    public static final String MOD_NAME = "Resource Config API Test";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

    public static final ModConfig COMMON_CONFIG = ConfigRegistry.register(TestConfigs.CommonConfig.class, MOD_ID);
    public static final ModConfig GENERATOR_CONFIG = ConfigRegistry.register(TestConfigs.Generators.class, MOD_ID);
    public static final ModConfig SERVER_CONFIG = ConfigRegistry.register(TestConfigs.ServerConfig.class, MOD_ID);
    public static final ModConfig CLIENT_CONFIG = ConfigRegistry.register(TestConfigs.ClientConfig.class, MOD_ID);

    public static void init() {}

    public static void changeCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("changeConfigValue")
                .then(Commands.argument("intValue", IntegerArgumentType.integer())
                .executes(context -> {
                    int value = IntegerArgumentType.getInteger(context, "intValue");
                    context.getSource().sendSystemMessage(Component.literal("Changing value 'generateByDay' from Generator Config to: " + value));

                    TestConfigs.Generators.basicGenerateByDay = value;
                    GENERATOR_CONFIG.save();

                    return 1;
                })));
    }
}
