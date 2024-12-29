package net.xstopho.resourceconfigapi.client.util;

import net.minecraft.network.chat.Component;
import net.xstopho.resourceconfigapi.Constants;
import net.xstopho.resourceconfigapi.platform.CoreServices;

import java.util.LinkedList;

public class ComponentUtils {

    public static final LinkedList<Component> NEEDED_TRANSLATION = new LinkedList<>();
    public static final LinkedList<Component> OPTIONAL_TRANSLATION = new LinkedList<>();

    public static Component title(String key) {
        return build(String.format("%s.%s", key, "title"));
    }

    public static Component label(String key) {
        return build(String.format("%s.%s", key, "label"));
    }

    public static Component tooltip(String key) {
        return build(String.format("%s.%s", key, "tooltip"));
    }

    public static Component modConfig(String modId, String key) {
        return build(String.format("%s.%s", modId, key));
    }

    public static Component modLabel(String modId, String fileName, String key) {
        return label(String.format("%s.%s.%s", modId, fileName, key));
    }

    public static Component modTooltip(String modId, String fileName, String key) {
        return tooltip(String.format("%s.%s.%s", modId, fileName, key));
    }

    private static Component build(String key) {
        Component component = Component.translatable(
                String.format("%s.%s", "config", convert(key)));

        if (!GuiUtils.hasTranslation(component)) {
            if (component.getString().contains("tooltip")) {
                OPTIONAL_TRANSLATION.add(component);
            } else {
                NEEDED_TRANSLATION.add(component);
            }
        }

        return component;
    }

    private static String convert(String key) {
        return key.toLowerCase().replace(" ", "_");
    }

    public static void printMissingTranslations(String modId) {
        if (!CoreServices.isDevelopmentEnvironment()) return;

        if (!NEEDED_TRANSLATION.isEmpty()) {
            Constants.LOG.info("\nYour Config/s for '{}' containing untranslated Keys, add proper translation for the user.\n{}",
                    modId, buildLog(modId, NEEDED_TRANSLATION));
        }

        if (!OPTIONAL_TRANSLATION.isEmpty()) {
            Constants.LOG.info("\nYour Config/s for '{}' containing optional untranslated Keys, these can help to explain options to the user but aren't necessary.\n{}",
                    modId, buildLog(modId, OPTIONAL_TRANSLATION));
        }
    }

    private static String buildLog(String modId, LinkedList<Component> components) {
        StringBuilder builder = new StringBuilder();

        for (Component comp : components) {
            if (comp.getString().contains(modId)) {
                builder.append(comp.getString()).append("\n");
            }
        }

        return builder.toString();
    }
}
