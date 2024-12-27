package net.xstopho.resourceconfigapi.client;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.xstopho.resourceconfigapi.util.ConfigUtils;

public class ClientConstants {

    public static boolean isOperator = true;

    public static final Component SAVE_AND_CLOSE = ConfigUtils.createLabel("button.save_and_close");
    public static final Component CLOSE = ConfigUtils.createLabel("button.close");
    public static final Component RESET = ConfigUtils.createLabel("button.reset");
    public static final Component RESET_ALL = ConfigUtils.createLabel("button.reset_all");

    public static final Component RESET_TOOLTIP = ConfigUtils.createTooltip("button.reset");
    public static final Component UNDO_TOOLTIP = ConfigUtils.createTooltip("button.undo");

    public static final Component BOOLEAN_ENABLED = ConfigUtils.createLabel("button.enabled").copy().withStyle(ChatFormatting.GREEN);
    public static final Component BOOLEAN_DISABLED = ConfigUtils.createLabel("button.disabled").copy().withStyle(ChatFormatting.RED);
}
