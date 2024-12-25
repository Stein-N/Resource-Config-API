package net.xstopho.resourceconfigapi.gui.widget.value_list.base;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.xstopho.resourceconfigapi.Constants;
import net.xstopho.resourceconfigapi.annotations.RangedEntry;
import net.xstopho.resourceconfigapi.util.ConfigUtils;

import java.lang.reflect.Field;

public class ValueEntry extends BaseEntry {

    private final ResourceLocation undoSprite = Constants.of("textures/gui/sprites/icon/undo.png");

    private final Field field;
    private final Object defaultValue;

    protected final Button reset, undo;

    public ValueEntry(String fileName, String translationKey, Field field, Object defaultValue) {
        super(fileName, translationKey, ChatFormatting.WHITE);
        this.field = field;
        this.defaultValue = defaultValue;

        reset = Button.builder(Constants.RESET, button -> resetValues())
                .bounds(0, 0, 50, 20)
                .tooltip(ConfigUtils.hasTranslation(Constants.RESET_TOOLTIP) ? Tooltip.create(Constants.RESET_TOOLTIP) : null)
                .build();

        undo = Button.builder(Component.empty(), button -> undoChanges())
                .bounds(0, 0, 20, 20)
                .tooltip(ConfigUtils.hasTranslation(Constants.UNDO_TOOLTIP) ? Tooltip.create(Constants.UNDO_TOOLTIP) : null)
                .build();

        this.children.add(reset);
        this.children.add(undo);
    }


    @Override
    public void render(GuiGraphics guiGraphics, int index, int yPos, int xPos, int rowWidth, int rowHeight,
                       int mouseX, int mouseY, boolean hovered, float delta) {

        ConfigUtils.drawStringWithTooltip(guiGraphics, label, tooltip,
                xPos, yPos + 6, mouseX, mouseY, hovered);

        undo.setX(xPos + rowWidth - undo.getWidth() - reset.getWidth());
        undo.setY(yPos);

        reset.setX(xPos + rowWidth - reset.getWidth());
        reset.setY(yPos);

        reset.render(guiGraphics, mouseX, mouseY, delta);
        undo.render(guiGraphics, mouseX, mouseY, delta);

        guiGraphics.blit(RenderType::guiTexturedOverlay, undoSprite, undo.getX() + 2, undo.getY() + 2, 0f, 0f, 16, 16, 16, 16);
    }

    @Override
    public void resetValues() {
        try {
            this.field.set(this.field, this.defaultValue);
            Constants.LOG.error("Reset  value {}", field.getName());
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Failed to set default value for field: " + field.getName() + "\n" + e);
        }
    }

    private boolean isRanged() {
        return field.isAnnotationPresent(RangedEntry.class);
    }
}
