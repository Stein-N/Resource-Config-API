package net.xstopho.resourceconfigapi.util;

import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipPositioner;
import org.joml.Vector2i;
import org.joml.Vector2ic;

public class EntryLabelTooltipPosition implements ClientTooltipPositioner {

    public static final EntryLabelTooltipPosition INSTANCE = new EntryLabelTooltipPosition();

    @Override
    public Vector2ic positionTooltip(int screenWidth, int screenHeight, int mouseX, int mouseY, int tooltipWidth, int tooltipHeight) {
        Vector2i vector2i = (new Vector2i(mouseX, mouseY)).add(12, 0);
        this.positionTooltip(screenWidth, screenHeight, vector2i, tooltipWidth, tooltipHeight);
        return vector2i;
    }

    private void positionTooltip(int screenWidth, int screenHeight, Vector2i tooltipPos, int tooltipWidth, int tooltipHeight) {
        if (tooltipPos.x + tooltipWidth > screenWidth) {
            tooltipPos.x = Math.max(tooltipPos.x - 24 - tooltipWidth, 4);
        }

        int i = tooltipHeight + 3;
        if (tooltipPos.y + i > screenHeight) {
            tooltipPos.y = screenHeight - i;
        }

    }
}
