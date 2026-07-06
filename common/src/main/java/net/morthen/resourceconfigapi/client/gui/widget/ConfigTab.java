package net.morthen.resourceconfigapi.client.gui.widget;

import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.tabs.Tab;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.morthen.resourceconfigapi.api.ConfigType;
import net.morthen.resourceconfigapi.client.gui.widget.config_list.ConfigListEntry;
import net.morthen.resourceconfigapi.client.gui.widget.config_list.ConfigListWidget;
import net.morthen.resourceconfigapi.client.gui.widget.value_list.ValueListWidget;
import net.morthen.resourceconfigapi.client.util.ComponentUtils;
import net.morthen.resourceconfigapi.config.ConfigHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class ConfigTab  implements Tab {

    private final List<ConfigHolder> configHolderList = new ArrayList<>();
    private final ConfigListWidget configListWidget;
    private final ValueListWidget valueListWidget;
    private final ConfigType type;

    public ConfigTab(ConfigType type, Map<Identifier, ConfigHolder> configs) {
        this.type = type;
        configs.forEach(this::processConfigs);

        this.valueListWidget = new ValueListWidget(0, 0, 0, 24);

        this.configListWidget = new ConfigListWidget(0, 0, 0, 15, configHolderList, valueListWidget);
        this.configListWidget.setSelectedIndex(0);

        //Set the entries of the selected Config into the ValueListWidget
        ConfigListEntry entry = configListWidget.getSelected();
        if (entry != null && entry.getEntryList() != null) {
            this.valueListWidget.replaceEntries(entry.getEntryList());
        }
    }

    private void processConfigs(Identifier location, ConfigHolder configHolder) {
        String configType = location.getPath().split("/")[0];

        if (configType.equalsIgnoreCase(this.type.name())) {
            configHolderList.add(configHolder);
        }
    }

    @Override
    public Component getTabTitle() {
        return ComponentUtils.title(this.type.name().toLowerCase());
    }

    @Override
    public Component getTabExtraNarration() {
        //TODO: do i have to change this?
        return Component.literal(type.name());
    }

    @Override
    public void visitChildren(Consumer<AbstractWidget> consumer) {
        consumer.accept(this.configListWidget);
        consumer.accept(this.valueListWidget);
    }

    @Override
    public void doLayout(ScreenRectangle rec) {
        var clw = this.configListWidget;
        clw.setRectangle(100, rec.height() - 13, 5, 30);
        clw.updateSizeAndPosition(clw.getWidth(), clw.getHeight(), clw.getX(), clw.getY());

        var vlw = this.valueListWidget;
        vlw.setRectangle((rec.width() - clw.getRight()) - 15, rec.height() - 13, clw.getRight() + 10, 30);
        vlw.updateSizeAndPosition(vlw.getWidth(), vlw.getHeight(), vlw.getX(), vlw.getY());
    }

    public boolean containsConfigs() {
        return !configHolderList.isEmpty();
    }
}
