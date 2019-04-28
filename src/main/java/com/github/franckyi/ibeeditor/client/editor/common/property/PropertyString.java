package com.github.franckyi.ibeeditor.client.editor.common.property;

import com.github.franckyi.guapi.node.TextField;

import java.util.function.Consumer;

public class PropertyString extends LabeledProperty<String> {

    protected TextField textField;

    public PropertyString(String name, String value, Consumer<String> action) {
        super(name, value, action);
    }

    public PropertyString(String name, String initialValue, Consumer<String> action, int labelSize) {
        super(name, initialValue, action, labelSize);
    }

    @Override
    public String getValue() {
        return textField.getValue();
    }

    @Override
    protected void setValue(String value) {
        textField.setValue(value);
    }

    @Override
    protected void build() {
        super.build();
        this.addAll(textField = new TextField());
        textField.getOnValueChangedListeners().add((oldVal, newVal) -> onValueChanged(oldVal, newVal));
    }

    @Override
    public void updateSize(int listWidth) {
        textField.setPrefWidth(listWidth - 67 - nameLabel.getWidth());
    }
}