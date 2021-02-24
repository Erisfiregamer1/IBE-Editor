package com.github.franckyi.gamehooks.impl.common;

import com.github.franckyi.gamehooks.api.common.Block;
import com.github.franckyi.gamehooks.util.common.tag.ObjectTag;

public class ForgeBlock implements Block {
    private final ObjectTag tag;

    public ForgeBlock(ObjectTag tag) {
        this.tag = tag;
    }

    @Override
    public ObjectTag getTag() {
        return tag;
    }
}
