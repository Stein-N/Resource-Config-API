package net.xstopho.resourceconfigapi.client.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.resources.language.ClientLanguage;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.xstopho.resourceconfigapi.Constants;
import net.xstopho.resourceconfigapi.annotations.ConfigEntry;
import net.xstopho.resourceconfigapi.client.ClientConstants;
import net.xstopho.resourceconfigapi.client.gui.tooltip.EntryLabelTooltipPosition;

import java.lang.reflect.Field;
import java.util.List;

public class GuiUtils {

    private static final ResourceLocation directImpact = Constants.of("textures/gui/sprites/icon/direct_impact.png");
    private static final ResourceLocation worldRestart = Constants.of("textures/gui/sprites/icon/world_restart.png");
    private static final ResourceLocation gameRestart = Constants.of("textures/gui/sprites/icon/game_restart.png");

    public static Font getFont() {
        return Minecraft.getInstance().font;
    }

    public static void renderIcon(GuiGraphics guiGraphics, Field field, int xPos, int yPos, int mouseX, int mouseY) {
        ConfigEntry config = field.getAnnotation(ConfigEntry.class);

        if (!config.needsGameRestart() && !config.needsWorldRestart()) {
            renderIcon(guiGraphics, directImpact, ClientConstants.DIRECT_IMPACT_TOOLTIP, xPos, yPos, mouseX, mouseY);
        }
        if (config.needsGameRestart()) {
            renderIcon(guiGraphics, gameRestart, ClientConstants.NEEDS_GAME_RESTART_TOOLTIP, xPos, yPos, mouseX, mouseY);
        }
        if (config.needsWorldRestart()) {
            renderIcon(guiGraphics, worldRestart, ClientConstants.NEEDS_WORLD_RESTART_TOOLTIP, xPos, yPos, mouseX, mouseY);
        }
    }

    public static void renderIcon(GuiGraphics guiGraphics, ResourceLocation texture, Component tooltip, int xPos, int yPos, int mouseX, int mouseY) {
        guiGraphics.blit(texture, xPos, yPos, 0, 0, 11, 11, 11, 11);

        if (inBounds(xPos, yPos, xPos + 11, yPos + 11, mouseX, mouseY)) {
            guiGraphics.renderTooltip(getFont(), splitTooltip(tooltip, 170),
                    EntryLabelTooltipPosition.INSTANCE, mouseX, mouseY);
        }
    }

    public static void drawStringWithTooltip(GuiGraphics guiGraphics, Component title, Component tooltip, int xPos, int yPos, int mouseX, int mouseY) {
        if (title != null) {
            guiGraphics.drawString(getFont(), title, xPos, yPos, -1, false);

            if (tooltip != null) {
                int xMax = xPos + getFont().width(title.getString());
                int yMax = yPos + getFont().lineHeight;
                if (hasTranslation(tooltip) && inBounds(xPos, yPos, xMax, yMax, mouseX, mouseY)) {
                    guiGraphics.renderTooltip(getFont(), splitTooltip(tooltip, 170),
                            EntryLabelTooltipPosition.INSTANCE, mouseX, mouseY);
                }
            }
        }
    }

    public static List<FormattedCharSequence> splitTooltip(Component component, int width) {
        return getFont().split(component, width);
    }

    public static boolean inBounds(int xPos, int yPos, int xMax , int yMax, int mouseX, int mouseY) {
        return xPos <= mouseX && xMax >= mouseX
                && yPos <= mouseY && yMax >= mouseY;
    }

    public static boolean hasTranslation(Component component) {
        String key = getComponentKey(component);
        String translated = ClientLanguage.getInstance().getOrDefault(key);

        return !translated.equals(key);
    }

    private static String getComponentKey(Component component) {
        String fullKey = component.toString();
        return fullKey.substring(17, fullKey.length() - 11);
    }
}
