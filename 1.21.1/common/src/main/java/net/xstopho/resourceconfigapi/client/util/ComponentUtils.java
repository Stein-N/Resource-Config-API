package net.xstopho.resourceconfigapi.client.util;

import net.minecraft.network.chat.Component;
import net.xstopho.resourceconfigapi.Constants;
import net.xstopho.resourceconfigapi.platform.CoreServices;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

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

        if (GuiUtils.hasTranslation(component)) return component;

        if (component.getString().contains("tooltip")) {
            addIfAbsent(OPTIONAL_TRANSLATION, component);
        } else {
            addIfAbsent(NEEDED_TRANSLATION, component);
        }

        return component;
    }

    private static String convert(String key) {
        return key.toLowerCase().replace(" ", "_");
    }

    public static void loggMissingTranslations(String modId) {
        if (!CoreServices.isDevelopmentEnvironment()) return;

        logMissingTranslations(modId, NEEDED_TRANSLATION, "The following keys are necessary for the User to properly edit your Configs!");
        logMissingTranslations(modId, OPTIONAL_TRANSLATION, "The following keys are optional, they add a Tooltip to the Label, this might help to explain some Options to the User.");
    }

    private static void logMissingTranslations(String modId, LinkedList<Component> translations, String message) {
        if (!translations.isEmpty()) {
            Constants.LOG.info("\nYour Config/s for '{}' contains untranslated keys.\n{}\n\n{}", modId, message, buildLog(modId, translations));
        }
    }

    private static String buildLog(String modId, LinkedList<Component> components) {
        List<Component> modComponents = collectModComponents(modId, components);
        StringBuilder builder = new StringBuilder();

        for (Iterator<Component> it = modComponents.iterator(); it.hasNext();) {
            Component comp = it.next();

            if (comp.getString().contains(modId)) {
                builder.append("\"").append(comp.getString()).append("\": \"\"");
            }

            if (it.hasNext()) builder.append(",\n");
            else builder.append("\n");
        }

        return builder.toString();
    }

    private static List<Component> collectModComponents(String modId, List<Component> list) {
        List<Component> modComponents = new LinkedList<>();

        for (Component comp : list) {
            if (comp.getString().contains(modId)) {
                modComponents.add(comp);
            }
        }

        return modComponents;
    }

    private static void addIfAbsent(List<Component> list, Component component) {
        if (list.contains(component)) return;
        list.add(component);
    }
}
