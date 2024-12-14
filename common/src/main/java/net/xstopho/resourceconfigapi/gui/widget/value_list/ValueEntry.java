package net.xstopho.resourceconfigapi.gui.widget.value_list;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.xstopho.resourceconfigapi.Constants;
import net.xstopho.resourceconfigapi.util.ConfigUtils;

import java.lang.reflect.Field;

public class ValueEntry extends BaseEntry {

    private final Field field;

    protected final Button reset;

    public ValueEntry(String fileName, String category, Field field) {
        super(fileName, category, ChatFormatting.WHITE);
        this.field = field;

        reset = Button.builder(Constants.RESET, button -> Constants.LOG.error("Test press {}", field.getName()))
                .bounds(0, 0, 50, 20).tooltip(Tooltip.create(Constants.RESET)).build();

        this.children.add(reset);
    }


    @Override
    public void render(GuiGraphics guiGraphics, int index, int yPos, int xPos, int rowWidth, int rowHeight,
                       int mouseX, int mouseY, boolean hovered, float delta) {

        ConfigUtils.drawStringWithTooltip(guiGraphics, label, tooltip,
                xPos, yPos + 6, mouseX, mouseY, hovered);

        reset.setX(xPos + rowWidth - reset.getWidth());
        reset.setY(yPos);

        reset.render(guiGraphics, mouseX, mouseY, delta);
    }

}
