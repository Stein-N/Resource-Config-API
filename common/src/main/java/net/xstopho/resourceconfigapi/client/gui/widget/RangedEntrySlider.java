package net.xstopho.resourceconfigapi.client.gui.widget;

import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

import java.util.function.Consumer;

public class RangedEntrySlider extends AbstractSliderButton {

    private final double minValue, maxValue, currentValue;

    private final boolean integer;

    private Consumer<Double> responder;

    public RangedEntrySlider(int x, int y, int width, int height, double currentValue, double minValue, double maxValue, boolean integer) {
        super(x, y, width, height, Component.empty(), currentValue / maxValue);
        this.currentValue = currentValue;
        this.minValue = minValue;
        this.maxValue = maxValue;

        this.integer = integer;

        updateMessage();
    }

    @Override
    protected void updateMessage() {
        if (integer) {
            setMessage(Component.literal(String.valueOf((int) getValue())));
            return;
        }
        setMessage(Component.literal(String.valueOf(getValue())));
    }

    @Override
    protected void applyValue() {
        if (responder != null) {
            this.responder.accept(getValue());
        }
    }

    public void setResponder(Consumer<Double> consumer) {
        this.responder = consumer;
    }

    public double getValue() {
        double clampedValue = Mth.clampedLerp(this.minValue, this.maxValue, this.value);
        if (maxValue > 1) {
            clampedValue = Math.round(clampedValue / 0.10) * 0.10;
        }
        return this.integer ? (int) Math.round(clampedValue) : (double) Math.round(clampedValue * 100) / 100;
    }

    public void undoValue() {
        this.value = currentValue / maxValue;
        updateMessage();
    }

    public void setValue(double doubleValue) {
        this.value = doubleValue / maxValue;
        updateMessage();
        applyValue();
    }
}
