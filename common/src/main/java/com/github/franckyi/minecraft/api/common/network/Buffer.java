package com.github.franckyi.minecraft.api.common.network;

import com.github.franckyi.minecraft.api.common.BlockPos;
import com.github.franckyi.minecraft.api.common.tag.CompoundTag;

public interface Buffer {
    <B> B getBuffer();

    CompoundTag readTag();

    void writeTag(CompoundTag tag);

    int readInt();

    void writeInt(int i);

    BlockPos readPos();

    void writePos(BlockPos blockPos);

    boolean readBoolean();

    void writeBoolean(boolean bl);

    byte readByte();

    void writeByte(byte b);
}