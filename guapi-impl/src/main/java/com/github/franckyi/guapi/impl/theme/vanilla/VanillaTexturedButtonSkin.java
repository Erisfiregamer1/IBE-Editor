package com.github.franckyi.guapi.impl.theme.vanilla;

import com.github.franckyi.guapi.api.node.TexturedButton;
import com.github.franckyi.guapi.api.theme.DelegatedRenderer;
import com.github.franckyi.guapi.util.Color;
import com.github.franckyi.minecraft.Minecraft;
import com.github.franckyi.minecraft.api.client.render.Matrices;

public class VanillaTexturedButtonSkin<N extends TexturedButton> extends AbstractVanillaButtonSkin<N> {
    public VanillaTexturedButtonSkin(DelegatedRenderer delegatedRenderer) {
        super(delegatedRenderer);
    }

    @Override
    public void render(N node, Matrices matrices, int mouseX, int mouseY, float delta) {
        super.render(node, matrices, mouseX, mouseY, delta);
        Minecraft.getClient().getRenderer().drawTexture(matrices, node.getTextureId(), node.getX(), node.getY(), node.getWidth(), node.getHeight(),
                node.getImageX(), node.getImageY(), node.getImageWidth(), node.getImageHeight());
        if (!node.isDrawButton() && node.isDisabled()) {
            Minecraft.getClient().getRenderer().fillRectangle(matrices, node.getX(), node.getY(), node.getX() + node.getWidth(), node.getY() + node.getHeight(), Color.rgba(0, 0, 0, 191));
        }
    }

    @Override
    public int computeWidth(TexturedButton node) {
        return 20;
    }
}