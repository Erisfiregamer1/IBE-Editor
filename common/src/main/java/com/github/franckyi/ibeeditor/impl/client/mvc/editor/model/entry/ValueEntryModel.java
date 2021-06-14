package com.github.franckyi.ibeeditor.impl.client.mvc.editor.model.entry;

import com.github.franckyi.databindings.DataBindings;
import com.github.franckyi.databindings.api.ObjectProperty;
import com.github.franckyi.ibeeditor.api.client.mvc.editor.model.CategoryModel;
import com.github.franckyi.minecraft.api.common.text.Text;

import java.util.function.Consumer;

public abstract class ValueEntryModel<T> extends LabeledEntryModel {
    protected T defaultValue;
    private final ObjectProperty<T> valueProperty;
    protected final Consumer<T> action;

    public ValueEntryModel(CategoryModel category, Text label, T value, Consumer<T> action) {
        super(category, label);
        defaultValue = value;
        valueProperty = DataBindings.getPropertyFactory().createObjectProperty(value);
        this.action = action;
    }

    public void reset() {
        valueProperty.setValue(defaultValue);
    }

    public T getValue() {
        return valueProperty().getValue();
    }

    public ObjectProperty<T> valueProperty() {
        return valueProperty;
    }

    public void setValue(T value) {
        valueProperty().setValue(value);
    }

    @Override
    public void apply() {
        action.accept(valueProperty.getValue());
        defaultValue = valueProperty.getValue();
    }
}