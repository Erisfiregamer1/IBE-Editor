package com.github.franckyi.minecraft.impl.common.text;

import com.github.franckyi.minecraft.api.common.text.Text;
import com.github.franckyi.minecraft.api.common.text.TextFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.util.text.ITextComponent;

public final class ForgeTextFactory implements TextFactory<ITextComponent> {
    public static final TextFactory<ITextComponent> INSTANCE = new ForgeTextFactory();

    private ForgeTextFactory() {
    }

    @Override
    public ITextComponent createComponentFromText(Text text) {
        return ITextComponent.Serializer.fromJson(Text.Serializer.GSON.toJson(text));
    }

    @Override
    public Text createTextFromComponent(ITextComponent component) {
        return Text.Serializer.GSON.fromJson(ITextComponent.Serializer.toJson(component), Text.class);
    }

    @Override
    public String getRawTextFromComponent(ITextComponent component) {
        return component.getString();
    }
}