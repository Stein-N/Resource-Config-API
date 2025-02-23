package net.xstopho.resourceconfigapi.client.gui.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.tabs.TabManager;
import net.minecraft.client.gui.components.tabs.TabNavigationBar;
import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.xstopho.resourceconfigapi.ConfigConstants;
import net.xstopho.resourceconfigapi.api.ConfigRegistry;
import net.xstopho.resourceconfigapi.api.ConfigType;
import net.xstopho.resourceconfigapi.client.ClientConstants;
import net.xstopho.resourceconfigapi.client.gui.widget.ConfigTab;
import net.xstopho.resourceconfigapi.client.gui.widget.value_list.base.BaseEntry;
import net.xstopho.resourceconfigapi.client.util.ClientUtils;
import net.xstopho.resourceconfigapi.client.util.ComponentUtils;
import net.xstopho.resourceconfigapi.config.ConfigHolder;
import net.xstopho.resourceconfigapi.config.ModConfig;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class ResourceConfigScreen extends Screen {

    private final HeaderAndFooterLayout layout;
    private final Screen previous;
    private final String modId;

    private final TabManager manager;
    private TabNavigationBar navigationBar;

    private final Map<ResourceLocation, ConfigHolder> configs = new HashMap<>();

    private final ConfigTab commonTab, clientTab, serverTab;

    public ResourceConfigScreen(Screen previous, String modId) {
        super(Component.literal("Config Screen - " + modId));

        this.modId = modId;
        this.previous = previous;
        this.layout = new HeaderAndFooterLayout(this, 32, 32);

        manager = new TabManager(this::addRenderableWidget, this::removeWidget);

        ConfigRegistry.getConfigs().forEach(this::processConfigs);

        this.commonTab = new ConfigTab(ConfigType.COMMON, this.configs);
        this.clientTab = new ConfigTab(ConfigType.CLIENT, this.configs);
        this.serverTab = new ConfigTab(ConfigType.SERVER, this.configs);
    }

    @Override
    protected void init() {
        TabNavigationBar.Builder builder = TabNavigationBar.builder(this.manager, this.width);

        if (clientTab.containsConfigs()) builder.addTabs(clientTab);
        if (commonTab.containsConfigs() && ClientUtils.isOperator()) builder.addTabs(commonTab);
        if (serverTab.containsConfigs() && ClientUtils.isOperator() && ClientUtils.isMultiplayer()) builder.addTabs(serverTab);

        this.navigationBar = builder.build();


        LinearLayout footer = this.layout.addToFooter(LinearLayout.horizontal().spacing(8));
        footer.addChild(Button.builder(ClientConstants.SAVE_AND_CLOSE, button -> {
            consumeAction(BaseEntry::saveValues);
            configs.forEach(this::saveConfigChanges);
            this.onClose();
        }).width(100).build());

        footer.addChild(Button.builder(ClientConstants.RESET_ALL, button -> consumeAction(BaseEntry::resetValues)).width(100).build());
        footer.addChild(Button.builder(ClientConstants.CLOSE, button -> {
            consumeAction(BaseEntry::undoChanges);
            this.onClose();
        }).width(100).build());


        this.layout.visitWidgets(this::addRenderableWidget);

        if (!this.navigationBar.children().isEmpty()) {
            this.addRenderableWidget(navigationBar);
            this.navigationBar.selectTab(0, true);
        }

        this.repositionElements();

        ComponentUtils.loggMissingTranslations(modId);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float ticks) {
        this.renderBackground(guiGraphics, mouseX, mouseY, ticks);
        super.render(guiGraphics, mouseX, mouseY, ticks);

        guiGraphics.blit(RenderType::guiTextured, Screen.FOOTER_SEPARATOR, 0, this.height - 35,
                0F, 0F, this.width, 2, 32, 2);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 256 && this.shouldCloseOnEsc()) {
            Minecraft.getInstance().setScreen(previous);
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    protected void repositionElements() {
        if (this.navigationBar != null && !this.navigationBar.children().isEmpty()) {
            this.navigationBar.setWidth(this.width);
            this.navigationBar.arrangeElements();
            int i = this.navigationBar.getRectangle().bottom();
            ScreenRectangle screenRectangle = new ScreenRectangle(0, i, this.width, this.height - (i * 2) - 10);
            this.manager.setTabArea(screenRectangle);
        }
        this.layout.arrangeElements();
    }

    @Override
    public void onClose() {
        Minecraft.getInstance().setScreen(previous);
    }

    private void processConfigs(ResourceLocation location, ModConfig config) {
        if (location.getNamespace().equals(this.modId)) {
            this.configs.put(location, new ConfigHolder(config));
        }
    }

    private void saveConfigChanges(ResourceLocation location, ConfigHolder holder) {
        ModConfig config = holder.getConfig();

        if (ClientUtils.isMultiplayer()) {
            switch (config.configType) {
                case CLIENT -> saveConfig(holder);
                case COMMON, SERVER -> ClientUtils.sendConfigUpdateToServer(location.toString(), config.toJson().toString());

            }
        } else {
            switch (config.configType) {
                case COMMON, CLIENT -> saveConfig(holder);
            }
        }
    }

    private void saveConfig(ConfigHolder holder) {
        ModConfig config = holder.getConfig();
        ConfigConstants.LOG.info("Saving '{}' config from mod '{}' of type '{}'", holder.getFileName(), config.modId, config.configType);
        config.writeConfig(config.toJson());
    }

    private void consumeAction(Consumer<BaseEntry> consumer) {
        for (Map.Entry<ResourceLocation, ConfigHolder> entry : this.configs.entrySet()) {
            entry.getValue().getEntryList().forEach(consumer);
        }
    }
}
