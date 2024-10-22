package net.xstopho.resourceconfigapi.gui.widgets.entries.reference;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.xstopho.resourceconfigapi.ResourceConfigConstants;
import net.xstopho.resourceconfigapi.config.entry.ConfigEntry;
import net.xstopho.resourceconfigapi.gui.widgets.SelectionWidget;
import net.xstopho.resourceconfigapi.gui.widgets.entries.ValueConfigEntry;

import java.util.ArrayList;
import java.util.List;

public class EnumConfigEntry extends ValueConfigEntry<Enum<?>> {

    private final SelectionWidget<SelectionWidget.SelectionEntry<Enum<?>>> selectionWidget;
    private final Class<Enum<?>> enumClass;
    private final Enum<?>[] enumConstants;

    public EnumConfigEntry(ConfigEntry<Enum<?>> configEntry) {
        super(configEntry);
        enumClass = (Class<Enum<?>>) configEntry.get().getDeclaringClass();
        enumConstants = enumClass.getEnumConstants();


        selectionWidget = new SelectionWidget<>(0, 0 , WIDGET_WIDTH, Component.literal("TEST"),
                b -> ResourceConfigConstants.LOG.error("TEST"));
        selectionWidget.setEntries(createEntries());

        this.add(selectionWidget);
    }

    private List<SelectionWidget.SelectionEntry<Enum<?>>> createEntries() {
        List<SelectionWidget.SelectionEntry<Enum<?>>> entries = new ArrayList<>();

        for (Enum<?> entry : enumConstants) {
            entries.add(new SelectionWidget.SelectionEntry<>(Component.literal(entry.name()), entry));
        }

        return entries;
    }

    public boolean isExtended() {
        return selectionWidget.isExtended();
    }

    public int getExtendedHeight() {
        return selectionWidget.getHeight();
    }

    @Override
    public Enum<?> getValue() {
        return null;
    }

    @Override
    public void undo() {

    }

    @Override
    public void reset() {

    }
}
