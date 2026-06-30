package net.morthen.resourceconfigapi.client.gui.tooltip;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.util.FormattedCharSequence;

import java.util.List;

public class ResourceConfigTextTooltip implements ClientTooltipComponent {
    private final List<FormattedCharSequence> sequence;

    public ResourceConfigTextTooltip(List<FormattedCharSequence> sequence) {
        this.sequence = sequence;
    }

    @Override
    public int getHeight(Font font) {
        return font.lineHeight * sequence.size();
    }

    @Override
    public int getWidth(Font font) {
        return 170;
    }

    @Override
    public void extractText(GuiGraphicsExtractor graphics, Font font, int mouseX, int mouseY) {
        for (int i = 0; i < this.sequence.size(); i++) {
            graphics.text(font, sequence.get(i), mouseX,mouseY + (font.lineHeight * i), -1, true);
        }
    }
}
