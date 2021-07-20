package com.github.franckyi.minecraft.api.common.tag;

import java.util.Map;

public interface CompoundTag extends Tag {
    @Override
    default byte getType() {
        return Tag.COMPOUND_ID;
    }

    Map<String, Tag> getEntries();

    int getInt(String key);

    String getString(String key);

    boolean getBoolean(String key);

    ListTag getList(String key, byte type);

    ListTag getOrCreateList(String key, byte type);

    CompoundTag getCompound(String key);

    CompoundTag getOrCreateCompound(String key);

    void putString(String key, String value);

    void putInt(String key, int value);

    void putBoolean(String key, boolean value);

    void putTag(String key, Tag tag);

    boolean contains(String key, byte type);

    void remove(String key);

    boolean isEmpty();

    CompoundTag copy();
}