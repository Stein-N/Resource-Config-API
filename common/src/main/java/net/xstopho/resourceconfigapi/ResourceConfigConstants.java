package net.xstopho.resourceconfigapi;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResourceConfigConstants {

	public static final String MOD_ID = "resourceconfigapi";
	public static final String MOD_NAME = "Resource Config API";
	public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

	public static final Component SAVE_AND_CLOSE = Component.literal("Save and Close");
	public static final Component CLOSE = Component.literal("Close");
	public static final Component RESET = Component.literal("Reset");
	public static final Component RESET_ALL = Component.literal("Reset All");
	public static final Component UNDO = Component.literal("");
	public static final Component EDIT = Component.literal("Edit");

	public static final Component RESET_TOOLTIP = Component.literal("Placeholder Tooltip for Reset Button");
	public static final Component UNDO_TOOLTIP = Component.literal("Placeholder Tooltip for Undo Button");

	public static final Component BOOLEAN_ENABLED = Component.literal("Enabled").withStyle(ChatFormatting.GREEN);
	public static final Component BOOLEAN_DISABLED = Component.literal("Disabled").withStyle(ChatFormatting.RED);
}