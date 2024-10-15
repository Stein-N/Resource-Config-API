package net.xstopho.resourceconfigapi.gui.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.tabs.TabManager;
import net.minecraft.client.gui.components.tabs.TabNavigationBar;
import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.xstopho.resourceconfigapi.ResourceConfigConstants;
import net.xstopho.resourceconfigapi.api.ConfigRegistry;
import net.xstopho.resourceconfigapi.api.ConfigType;
import net.xstopho.resourceconfigapi.config.ResourceModConfig;
import net.xstopho.resourceconfigapi.gui.widgets.ResourceCategoryTab;

import java.util.HashMap;
import java.util.Map;

public class ResourceConfigScreen extends Screen {

    private final String modId;
    private final Screen previous;
    private final HeaderAndFooterLayout layout;

    private final TabManager tabManager;
    private TabNavigationBar tabNavigationBar;

    private Map<ConfigType, ResourceModConfig> configMap = new HashMap<>();

    public ResourceConfigScreen(Screen previous, String modId) {
        super(Component.literal(""));

        this.modId = modId;

        this.previous = previous;
        this.layout = new HeaderAndFooterLayout(this, 32, 32);

        tabManager = new TabManager(this::addRenderableWidget, this::removeWidget);


    }

    @Override
    protected void init() {
        configMap = ConfigRegistry.getConfigs(modId);

        TabNavigationBar.Builder builder = TabNavigationBar.builder(this.tabManager, this.width);

        for (ResourceModConfig config : configMap.values()) {
            builder.addTabs(new ResourceCategoryTab(previous, config));
        }

        this.tabNavigationBar = builder.build();
        this.addRenderableWidget(this.tabNavigationBar);

        LinearLayout footer = this.layout.addToFooter(LinearLayout.horizontal().spacing(8));
        footer.addChild(Button.builder(ResourceConfigConstants.SAVE_AND_CLOSE, b -> ResourceConfigConstants.LOG.error("Values Saved")).width(100).build());
        footer.addChild(Button.builder(ResourceConfigConstants.RESET_ALL, b -> ResourceConfigConstants.LOG.error("Reset Values")).width(100).build());
        footer.addChild(Button.builder(ResourceConfigConstants.CLOSE, b -> this.onClose()).width(100).build());

        this.layout.visitWidgets(this::addRenderableWidget);

        this.tabNavigationBar.selectTab(0, true);
        this.repositionElements();
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float ticks) {
        this.renderBackground(guiGraphics, mouseX, mouseY, ticks);
        super.render(guiGraphics, mouseX, mouseY, ticks);

        guiGraphics.blit(Screen.FOOTER_SEPARATOR, 0, this.height - this.layout.getHeaderHeight() - 10, 0.0F, 0.0F, this.width, 2, 32, 2);
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
        if (this.tabNavigationBar != null) {
            this.tabNavigationBar.setWidth(this.width);
            this.tabNavigationBar.arrangeElements();
            int i = this.tabNavigationBar.getRectangle().bottom();
            ScreenRectangle screenRectangle = new ScreenRectangle(0, i, this.width, this.height - (i * 2) - 10);
            this.tabManager.setTabArea(screenRectangle);
            this.layout.setHeaderHeight(i);
            this.layout.arrangeElements();
        }
    }

    @Override
    public void onClose() {
        Minecraft.getInstance().setScreen(previous);
    }
}
