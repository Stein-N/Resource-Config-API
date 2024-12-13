package net.xstopho.resourceconfigapi;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.xstopho.resourceconfigapi.util.ConfigUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Constants {

	public static final String MOD_ID = "resourceconfigapi";
	public static final String MOD_NAME = "Resource Config API";
	public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

	public static final Component SAVE_AND_CLOSE = ConfigUtils.createLabel("button.save_and_close");
	public static final Component CLOSE = ConfigUtils.createLabel("button.close");
	public static final Component RESET = ConfigUtils.createLabel("button.reset");
	public static final Component RESET_ALL = ConfigUtils.createLabel("button.reset_all");

	public static final Component RESET_TOOLTIP = ConfigUtils.createTooltip("button.reset");
	public static final Component UNDO_TOOLTIP = ConfigUtils.createTooltip("button.undo");

	public static final Component BOOLEAN_ENABLED = ConfigUtils.createLabel("button.enabled").copy().withStyle(ChatFormatting.GREEN);
	public static final Component BOOLEAN_DISABLED = ConfigUtils.createLabel("button.disabled").copy().withStyle(ChatFormatting.RED);

	public static ResourceLocation of(String id) {
		return ResourceLocation.fromNamespaceAndPath(MOD_ID, id);
	}
}