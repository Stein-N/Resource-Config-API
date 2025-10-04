package net.xstopho.resourceconfigapi.mixin;

import net.minecraft.client.gui.components.AbstractSelectionList;
import net.xstopho.resourceconfigapi.client.gui.widget.ResourceWidgetUpdater;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(AbstractSelectionList.class)
public abstract class AbstractSelectionListMixin implements ResourceWidgetUpdater {

    @Shadow
    public abstract void repositionEntries();

    @Override
    public void resourceconfigapi$updateWidgets() {
        this.repositionEntries();
    }
}
