package net.morthen.resourceconfigapi.mixin;

import net.minecraft.client.gui.components.AbstractSelectionList;
import net.morthen.resourceconfigapi.client.gui.widget.ResourceWidgetUpdater;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(AbstractSelectionList.class)
public abstract class AbstractSelectionListMixin implements ResourceWidgetUpdater {

    @Shadow(remap = false)
    public abstract void repositionEntries();

    @Override
    public void resourceconfigapi$updateWidgets() {
        this.repositionEntries();
    }
}
