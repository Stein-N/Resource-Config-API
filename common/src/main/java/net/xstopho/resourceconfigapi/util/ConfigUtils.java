package net.xstopho.resourceconfigapi.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.resources.language.ClientLanguage;
import net.minecraft.client.resources.language.LanguageManager;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import net.xstopho.resourceconfigapi.Constants;

import java.util.List;

public class ConfigUtils {

    private static final Font font = Minecraft.getInstance().font;
    private static final LanguageManager languageManager = Minecraft.getInstance().getLanguageManager();

    public static Component createTitle(String id) {
        return Component.translatable("config." + id + ".title");
    }

    public static Component createLabel(String key) {
        return Component.translatable("config." + key + ".label");
    }

    public static Component createTooltip(String key) {
        return Component.translatable("config." + key + ".tooltip");
    }

    public static void drawStringWithTooltip(GuiGraphics guiGraphics, Component title, Component tooltip, int xPos, int yPos, int mouseX, int mouseY, boolean hovered) {
        if (title != null) {
            guiGraphics.drawString(font, title, xPos, yPos, -1, false);

            if (tooltip != null && hovered) {
                if (inBounds(title, xPos, yPos, mouseX, mouseY)) {
                    guiGraphics.renderTooltip(font, splitTooltip(tooltip, 170), mouseX, mouseY);
                }
            }
        }
    }

    public static List<FormattedCharSequence> splitTooltip(Component component, int width) {
        return font.split(component, width);
    }

    public static boolean inBounds(Component title, int xPos, int yPos, int mouseX, int mouseY) {
        int xMax = font.width(title.getString());
        int yMax = font.lineHeight;
        return xPos <= mouseX && xMax >= mouseX
                && yPos <= mouseY && yMax >= mouseY;
    }

    public static boolean hasTranslation(Component component) {
        String key = getComponentKey(component);
        String translated = ClientLanguage.getInstance().getOrDefault(key);

        Constants.LOG.error("Key: {}\tTranslation: {}", key, translated);

        return !translated.equals(key);
    }

    private static String getComponentKey(Component component) {
        String fullKey = component.toString();
        return fullKey.substring(17, fullKey.length() - 11);
    }
}
