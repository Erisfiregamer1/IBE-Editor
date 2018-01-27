package com.github.franckyi.ibeeditor.gui.child;

import com.github.franckyi.ibeeditor.gui.GuiEditor;
import com.github.franckyi.ibeeditor.gui.property.BaseProperty;
import net.minecraft.client.Minecraft;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public abstract class GuiPropertyListEditable extends GuiPropertyList {

    protected int clickedX, clickedY, startSlot;
    protected boolean isClicking;

    public GuiPropertyListEditable(GuiEditor parent, Minecraft mcIn, int widthIn, int heightIn, int topIn, int bottomIn, List<BaseProperty<?>> properties) {
        super(parent, mcIn, widthIn, heightIn, topIn, bottomIn, properties);
    }

    private boolean canUp(int index) {
        return index <= getListEnd() && index > getListStart();
    }

    private boolean canDown(int index) {
        return index < getListEnd() && index >= getListStart();
    }

    private boolean canAddBefore(int index) {
        return index <= getListEnd() && index >= getListStart();
    }

    private boolean canAddAfter(int index) {
        return index <= getListEnd() && index >= getListStart();
    }

    private boolean canRemove(int index) {
        return index <= getListEnd() && index >= getListStart();
    }

    protected void up(int index) {
        if(canUp(index)) {
            Collections.swap(properties, index, index - 1);
            listUpdated();
        }
    }

    protected void down(int index) {
        if(canDown(index)) {
            Collections.swap(properties, index, index + 1);
            listUpdated();
        }
    }

    protected void addBefore(int index, BaseProperty<?> property) {
        if(canAddBefore(index)) {
            properties.add(index, property);
            listUpdated();
        }
    }

    private void addAfter(int index, BaseProperty<?> property) {
        if(canAddAfter(index)) {
            properties.add(index + 1, property);
            listUpdated();
        }
    }

    protected void remove(int index) {
        if(canRemove(index)) {
            properties.remove(index);
            listUpdated();
        }
    }

    @Override
    public boolean mouseClicked(int mouseX, int mouseY, int mouseEvent) {
        int slot = getSlotIndexFromScreenCoords(mouseX, mouseY);
        if(mouseEvent == 1) {
            clickedX = mouseX;
            clickedY = mouseY;
            startSlot = slot;
            isClicking = true;
        }
        return super.mouseClicked(mouseX, mouseY, mouseEvent);
    }

    @Override
    public boolean mouseReleased(int releasedX, int releasedY, int mouseEvent) {
        if(mouseEvent == 1 && isClicking) {
            isClicking = false;
            EnumSelectionType sel = processSelection(releasedX, releasedY);
            if(sel != null) {
                switch (sel) {
                    case TOP_LEFT:
                    case TOP_CENTER:
                        up(startSlot);
                        break;
                    case TOP_RIGHT:
                        addBefore(startSlot, newProperty(startSlot));
                        break;
                    case CENTER_LEFT:
                        remove(startSlot);
                        break;
                    case CENTER_RIGHT:
                        break;
                    case BOTTOM_LEFT:
                    case BOTTOM_CENTER:
                        down(startSlot);
                        break;
                    case BOTTOM_RIGHT:
                        addAfter(startSlot, newProperty(startSlot));
                        break;
                }
            }
        }
        return super.mouseReleased(releasedX, releasedY, mouseEvent);
    }

    @Override
    public void drawScreen(int mouseXIn, int mouseYIn, float partialTicks) {
        super.drawScreen(mouseXIn, mouseYIn, partialTicks);
        mc.fontRenderer.drawString("Right-click on a slot and release the mouse on the action you want to perform!", 5, 5, 0xffffff);
        drawMenu();
    }

    protected void drawMenu() {
        if(isClicking) {
            if(canUp(startSlot)) mc.fontRenderer.drawStringWithShadow("Up", clickedX - 4, clickedY - 20, 0xffffff);
            if(canDown(startSlot)) mc.fontRenderer.drawStringWithShadow("Down", clickedX - 10, clickedY + 12, 0xffffff);
            if(canAddBefore(startSlot) && startSlot >= 0) mc.fontRenderer.drawStringWithShadow("Add before", clickedX + 20, clickedY - 20, 0x80ff80);
            if(canAddAfter(startSlot) && startSlot >= 0) mc.fontRenderer.drawStringWithShadow("Add after", clickedX + 20, clickedY + 12, 0x00ff00);
            if(canRemove(startSlot)) mc.fontRenderer.drawStringWithShadow("Remove", clickedX - 50, clickedY - 4, 0xff0000);
        }
    }

    protected int getListStart() {
        return 0;
    }

    protected int getListEnd() {
        return properties.size() - 1;
    }

    protected EnumSelectionType processSelection(int releasedX, int releasedY) {
        boolean left = clickedX - releasedX > 10;
        boolean right = releasedX - clickedX > 10;
        boolean top = clickedY - releasedY > 10;
        boolean bottom = releasedY - clickedY > 10;
        return EnumSelectionType.of(left ? 0 : (right ? 2 : 1), top ? 0 : (bottom ? 2 : 1));
    }

    protected abstract void listUpdated();

    protected abstract BaseProperty<?> newProperty(int index);

    protected enum EnumSelectionType {
        TOP_LEFT(0, 0), TOP_CENTER(1, 0), TOP_RIGHT(2, 0), CENTER_LEFT(0, 1), CENTER_RIGHT(2, 1), BOTTOM_LEFT(0, 2), BOTTOM_CENTER(1, 2), BOTTOM_RIGHT(2, 2);

        private int relativeX;
        private int relativeY;

        EnumSelectionType(int relativeX, int relativeY) {
            this.relativeX = relativeX;
            this.relativeY = relativeY;
        }

        public boolean isLeft() {
            return relativeX == 0;
        }

        public boolean isHCenter() {
            return relativeX == 1;
        }

        public boolean isRight() {
            return relativeX == 0;
        }

        public boolean isTop() {
            return relativeY == 0;
        }

        public boolean isVCenter() {
            return relativeY == 1;
        }

        public boolean isBottom() {
            return relativeY == 2;
        }

        @Nullable
        public static EnumSelectionType of(int relativeX, int relativeY) {
            for(EnumSelectionType type : values()) {
                if (type.relativeX == relativeX && type.relativeY == relativeY) return type;
            }
            return null;
        }
    }
}