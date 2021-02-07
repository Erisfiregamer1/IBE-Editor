package com.github.franckyi.guapi.theme.vanilla;

import com.github.franckyi.guapi.hooks.api.RenderContext;
import com.github.franckyi.guapi.node.Label;
import com.github.franckyi.guapi.theme.AbstractSkin;
import com.github.franckyi.guapi.theme.Skin;
import com.github.franckyi.guapi.util.Align;

public class VanillaLabelSkin extends AbstractSkin<Label> {
    public static final Skin<Label> INSTANCE = new VanillaLabelSkin();

    private VanillaLabelSkin() {
    }

    @Override
    public void render(Label node, RenderContext<?> ctx) {
        super.render(node, ctx);
        String text = font().trimToWidth(node.getText(), node.getWidth() - node.getPadding().getHorizontal());
        int x = Align.getAlignedX(node.getTextAlign().getHorizontalAlign(), node, font().getFontWidth(text));
        int y = Align.getAlignedY(node.getTextAlign().getVerticalAlign(), node, font().getFontHeight());
        font().drawString(ctx.getMatrices(), text().getLiteralText(text), x, y, node.getColor(), node.hasShadow());
    }

    @Override
    public int computeWidth(Label node) {
        return font().getFontWidth(node.getText());
    }

    @Override
    public int computeHeight(Label node) {
        return font().getFontHeight();
    }
}
