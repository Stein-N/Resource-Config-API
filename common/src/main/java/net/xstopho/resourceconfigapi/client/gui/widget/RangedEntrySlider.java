package net.xstopho.resourceconfigapi.client.gui.widget;

import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

import java.util.function.Consumer;

public class RangedEntrySlider extends AbstractSliderButton {

    private final double minValue, maxValue, currentValue;

    private final boolean integer;

    private Consumer<Double> responder;

    public RangedEntrySlider(int width, double currentValue, double minValue, double maxValue, boolean integer) {
        super(0, 0, width, 20, Component.empty(), currentValue / maxValue);
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
        setMessage(Component.literal(String.format("%.2f", getValue())));
    }

    @Override
    protected void applyValue() {
        if (responder != null) {
            this.responder.accept(getValue());
        }
    }

    public RangedEntrySlider setResponder(Consumer<Double> consumer) {
        this.responder = consumer;
        return this;
    }

    public double getValue() {
        double clampedValue = Mth.clampedLerp(this.minValue, this.maxValue, this.value);
        if (maxValue > 1) {
            clampedValue = Math.round(clampedValue / 0.10) * 0.10;
        }
        return clampedValue;
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
