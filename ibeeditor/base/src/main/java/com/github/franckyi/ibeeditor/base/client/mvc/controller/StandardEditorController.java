package com.github.franckyi.ibeeditor.base.client.mvc.controller;

import com.github.franckyi.guapi.api.util.Color;
import com.github.franckyi.ibeeditor.base.client.ModScreenHandler;
import com.github.franckyi.ibeeditor.base.client.mvc.model.ColorSelectionScreenModel;
import com.github.franckyi.ibeeditor.base.client.mvc.model.StandardEditorModel;
import com.github.franckyi.ibeeditor.base.client.mvc.view.StandardEditorView;

import static com.github.franckyi.guapi.GuapiHelper.*;

public class StandardEditorController extends ListEditorController<StandardEditorModel<?, ?>, StandardEditorView> {
    public StandardEditorController(StandardEditorModel<?, ?> model, StandardEditorView view) {
        super(model, view);
    }

    @Override
    public void bind() {
        super.bind();
        view.getHeaderText().with(translated(model.getTitle()));
        model.activeTextEditorProperty().addListener(value -> view.setShowTextButtons(value != null));
        view.setTextEditorSupplier(model::getActiveTextEditor);
        if (model.isDisabled()) {
            view.getDoneButton().setDisable(true);
            view.getDoneButton().getTooltip().add(model.getDisabledTooltip());
        }
        view.getChooseCustomColorButton().onAction(e -> {
            e.consume();
            ModScreenHandler.openColorSelectionScreen(ColorSelectionScreenModel.Target.TEXT, this::updateCustomColor);
        });
        model.currentCustomColorProperty().addListener(value -> {
            view.getCustomColorButton().setBackgroundColor(Color.hex(value));
            view.getCustomColorButton().setVisible(value != null);
        });
        view.getCustomColorButton().onAction(e -> {
            e.consume();
            model.getActiveTextEditor().addColorFormatting(model.getCurrentCustomColor());
        });
    }

    private void updateCustomColor(String hex) {
        model.setCurrentCustomColor(hex);
        model.getActiveTextEditor().addColorFormatting(hex);
    }

    @Override
    protected void onValidationChange(boolean newVal) {
        if (!model.isDisabled()) {
            super.onValidationChange(newVal);
        }
    }
}
