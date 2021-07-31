package com.github.franckyi.guapi.forge.theme.mixin;

import net.minecraft.client.gui.widget.TextFieldWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(TextFieldWidget.class)
public interface ForgeTextFieldWidgetAccessorMixin {
    @Accessor("highlightPos")
    int getHighlightPos();

    @Accessor("displayPos")
    int getDisplayPos();

    @Accessor("shiftPressed")
    void setShiftPressed(boolean shiftPressed);
}