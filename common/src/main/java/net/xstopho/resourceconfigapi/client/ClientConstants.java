package net.xstopho.resourceconfigapi.client;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.xstopho.resourceconfigapi.client.util.ComponentUtils;

public class ClientConstants {

    public static final Component SAVE_AND_CLOSE = ComponentUtils.label("button.save_and_close");
    public static final Component CLOSE = ComponentUtils.label("button.close");
    public static final Component RESET = ComponentUtils.label("button.reset");
    public static final Component RESET_ALL = ComponentUtils.label("button.reset_all");

    public static final Component RESET_TOOLTIP = ComponentUtils.tooltip("button.reset");
    public static final Component UNDO_TOOLTIP = ComponentUtils.tooltip("button.undo");

    public static final Component BOOLEAN_ENABLED = ComponentUtils.label("button.enabled").copy().withStyle(ChatFormatting.GREEN);
    public static final Component BOOLEAN_DISABLED = ComponentUtils.label("button.disabled").copy().withStyle(ChatFormatting.RED);
}
