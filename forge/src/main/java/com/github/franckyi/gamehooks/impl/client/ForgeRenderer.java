package com.github.franckyi.gamehooks.impl.client;

import com.github.franckyi.gamehooks.api.client.Renderer;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FontRenderer;

public final class ForgeRenderer implements Renderer<MatrixStack> {
    public static final Renderer<?> INSTANCE = new ForgeRenderer();

    private ForgeRenderer() {
    }

    private Minecraft mc() {
        return Minecraft.getInstance();
    }

    @Override
    public Font<MatrixStack> font() {
        return font;
    }

    @Override
    public Shape<MatrixStack> shape() {
        return shape;
    }

    private final Font<MatrixStack> font = new Font<MatrixStack>() {
        private FontRenderer font() {
            return mc().fontRenderer;
        }

        @Override
        public int getFontHeight() {
            return font().FONT_HEIGHT;
        }

        @Override
        public int getFontWidth(String text) {
            return font().getStringWidth(text);
        }

        @Override
        public void drawString(MatrixStack matrixStack, String text, float x, float y, int color, boolean shadow) {
            if (shadow) {
                font().drawStringWithShadow(matrixStack, text, x, y, color);
            } else {
                font().drawString(matrixStack, text, x, y, color);
            }
        }

        @Override
        public String trimToWidth(String text, int maxWidth) {
            return font().func_238412_a_(text, maxWidth);
        }
    };

    private final Shape<MatrixStack> shape = new Shape<MatrixStack>() {
        @Override
        public void fillRectangle(MatrixStack matrices, int x0, int y0, int x1, int y1, int color) {
            AbstractGui.fill(matrices, x0, y0, x1, y1, color);
        }
    };
}