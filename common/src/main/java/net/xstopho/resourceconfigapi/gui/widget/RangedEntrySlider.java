package net.xstopho.resourceconfigapi.gui.widget;

import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.xstopho.resourceconfigapi.Constants;

public class RangedEntrySlider extends AbstractSliderButton {

    private final double minValue;
    private final double maxValue;

    private final boolean integer;

    public RangedEntrySlider(int x, int y, int width, int height, Component message, double currentValue, double minValue, double maxValue, boolean integer) {
        super(x, y, width, height, message, currentValue / maxValue);
        this.minValue = minValue;
        this.maxValue = maxValue;

        this.integer = integer;
    }

    @Override
    protected void updateMessage() {
        this.setMessage(Component.literal(String.valueOf(getValue())));
    }

    @Override
    protected void applyValue() {
        Constants.LOG.error("Current Value: {}", getValue());
    }

    public double getValue() {
        double current = Mth.clampedLerp(this.minValue, this.maxValue, this.value);
        return this.integer ? (int) Math.round( current) : (double) Math.round(current * 100) / 100;
    }
}
