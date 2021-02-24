package com.github.franckyi.gamehooks.impl.client;

import com.github.franckyi.gamehooks.api.client.ScreenScaling;
import net.minecraft.client.MinecraftClient;

public final class FabricScreenScaling extends AbstractScreenScaling {
    public static final ScreenScaling INSTANCE = new FabricScreenScaling();

    private FabricScreenScaling() {
    }

    private MinecraftClient mc() {
        return MinecraftClient.getInstance();
    }

    @Override
    protected void resetScale() {
        mc().onResolutionChanged();
    }

    @Override
    protected void setScreenScale(int value) {
        mc().getWindow().setScaleFactor(mc().getWindow().calculateScaleFactor(value, mc().forcesUnicodeFont()));
        mc().currentScreen.resize(mc(), mc().getWindow().getScaledWidth(), mc().getWindow().getScaledHeight());
    }

    @Override
    protected int getDefaultScale() {
        return mc().options.guiScale;
    }

    @Override
    public int getMaxScale() {
        return mc().getWindow().calculateScaleFactor(0, mc().forcesUnicodeFont());
    }
}