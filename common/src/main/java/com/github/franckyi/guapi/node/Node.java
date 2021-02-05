package com.github.franckyi.guapi.node;

import com.github.franckyi.databindings.api.*;
import com.github.franckyi.databindings.event.PropertyChangeEvent;
import com.github.franckyi.databindings.factory.PropertyFactory;
import com.github.franckyi.guapi.GUAPI;
import com.github.franckyi.guapi.event.*;
import com.github.franckyi.guapi.hooks.api.RenderContext;
import com.github.franckyi.guapi.util.Color;
import com.github.franckyi.guapi.util.Insets;

public abstract class Node implements ScreenEventHandler {
    public static final Color.Background DEFAULT_BACKGROUND_COLOR = new Color.Background(0, 0, 0, 0);
    public static final Integer INFINITE_SIZE = Integer.MAX_VALUE;
    public static final Integer COMPUTED_SIZE = -1;

    protected final IntegerProperty xProperty = PropertyFactory.ofInteger();
    private final ObservableIntegerValue xPropertyReadOnly = PropertyFactory.readOnly(xProperty);
    protected final IntegerProperty yProperty = PropertyFactory.ofInteger();
    private final ObservableIntegerValue yPropertyReadOnly = PropertyFactory.readOnly(yProperty);
    protected final IntegerProperty widthProperty = PropertyFactory.ofInteger();
    private final ObservableIntegerValue widthPropertyReadOnly = PropertyFactory.readOnly(widthProperty);
    protected final IntegerProperty heightProperty = PropertyFactory.ofInteger();
    private final ObservableIntegerValue heightPropertyReadOnly = PropertyFactory.readOnly(heightProperty);

    private final IntegerProperty minWidthProperty = PropertyFactory.ofInteger();
    private final IntegerProperty minHeightProperty = PropertyFactory.ofInteger();
    private final IntegerProperty prefWidthProperty = PropertyFactory.ofInteger(COMPUTED_SIZE);
    private final IntegerProperty prefHeightProperty = PropertyFactory.ofInteger(COMPUTED_SIZE);
    private final IntegerProperty maxWidthProperty = PropertyFactory.ofInteger(INFINITE_SIZE);
    private final IntegerProperty maxHeightProperty = PropertyFactory.ofInteger(INFINITE_SIZE);

    protected final IntegerProperty computedWidthProperty = PropertyFactory.ofInteger();
    private final ObservableIntegerValue computedWidthPropertyReadOnly = PropertyFactory.readOnly(computedWidthProperty);
    protected final IntegerProperty computedHeightProperty = PropertyFactory.ofInteger();
    private final ObservableIntegerValue computedHeightPropertyReadOnly = PropertyFactory.readOnly(computedHeightProperty);

    private final ObjectProperty<Color.Background> backgroundColorProperty = PropertyFactory.ofObject(DEFAULT_BACKGROUND_COLOR);
    private final ObjectProperty<Insets> paddingProperty = PropertyFactory.ofObject(Insets.NONE);

    protected final ObjectProperty<Parent> parentProperty = PropertyFactory.ofObject();
    private final ObservableObjectValue<Parent> parentPropertyReadOnly = PropertyFactory.readOnly(parentProperty);
    protected final ObjectProperty<Scene> sceneProperty = PropertyFactory.ofObject();
    private final ObservableObjectValue<Scene> scenePropertyReadOnly = PropertyFactory.readOnly(sceneProperty);

    private final BooleanProperty disableProperty = PropertyFactory.ofBoolean();
    private final ObservableBooleanValue disabledProperty = disableProperty()
            .or(parentProperty().bindMapToBoolean(Parent::disabledProperty, false));

    private final ObservableBooleanValue rootProperty = sceneProperty()
            .bindMap(Scene::rootProperty, null)
            .mapToBoolean(node -> node == Node.this);
    private final ObservableBooleanValue focusedProperty = sceneProperty()
            .bindMap(Scene::focusedProperty, null)
            .mapToBoolean(node -> node == Node.this);
    private final ObservableBooleanValue hoveredProperty = sceneProperty()
            .bindMap(Scene::hoveredProperty, null)
            .mapToBoolean(node -> node == Node.this);
    private final ObservableBooleanValue activeProperty = sceneProperty()
            .bindMap(Scene::activeProperty, null)
            .mapToBoolean(node -> node == Node.this);

    protected final ScreenEventHandler eventHandlerDelegate = new ScreenEventHandlerDelegate();

    public Node() {
        minWidthProperty().addListener(this::_updateWidth);
        minHeightProperty().addListener(this::_updateHeight);
        prefWidthProperty().addListener(this::_updateWidth);
        prefHeightProperty().addListener(this::_updateHeight);
        maxWidthProperty().addListener(this::_updateWidth);
        maxHeightProperty().addListener(this::_updateHeight);
        computedWidthProperty().addListener(this::_updateWidth);
        computedHeightProperty().addListener(this::_updateHeight);
        widthProperty().addListener(this::_updateParentWidth);
        heightProperty().addListener(this::_updateParentHeight);
        paddingProperty().addListener(this::_updateSize);
        parentProperty().addListener(this::_updateScene);
    }

    public int getX() {
        return xProperty().getValue();
    }

    public ObservableIntegerValue xProperty() {
        return xPropertyReadOnly;
    }

    protected void setX(int value) {
        xProperty.setValue(value);
    }

    public int getY() {
        return yProperty().getValue();
    }

    public ObservableIntegerValue yProperty() {
        return yPropertyReadOnly;
    }

    protected void setY(int value) {
        yProperty.setValue(value);
    }

    public int getWidth() {
        return widthProperty().getValue();
    }

    public ObservableIntegerValue widthProperty() {
        return widthPropertyReadOnly;
    }

    protected void setWidth(int value) {
        widthProperty.setValue(value);
    }

    public int getHeight() {
        return heightProperty().getValue();
    }

    public ObservableIntegerValue heightProperty() {
        return heightPropertyReadOnly;
    }

    protected void setHeight(int value) {
        heightProperty.setValue(value);
    }

    public int getMinWidth() {
        return minWidthProperty().getValue();
    }

    public IntegerProperty minWidthProperty() {
        return minWidthProperty;
    }

    public void setMinWidth(int value) {
        minWidthProperty().setValue(value);
    }

    public int getMinHeight() {
        return minHeightProperty().getValue();
    }

    public IntegerProperty minHeightProperty() {
        return minHeightProperty;
    }

    public void setMinHeight(int value) {
        minHeightProperty().setValue(value);
    }

    public int getPrefWidth() {
        return prefWidthProperty().getValue();
    }

    public IntegerProperty prefWidthProperty() {
        return prefWidthProperty;
    }

    public void setPrefWidth(int value) {
        prefWidthProperty().setValue(value);
    }

    public int getPrefHeight() {
        return prefHeightProperty().getValue();
    }

    public IntegerProperty prefHeightProperty() {
        return prefHeightProperty;
    }

    public void setPrefHeight(int value) {
        prefHeightProperty().setValue(value);
    }

    public int getMaxWidth() {
        return maxWidthProperty().getValue();
    }

    public IntegerProperty maxWidthProperty() {
        return maxWidthProperty;
    }

    public void setMaxWidth(int value) {
        maxWidthProperty().setValue(value);
    }

    public int getMaxHeight() {
        return maxHeightProperty().getValue();
    }

    public IntegerProperty maxHeightProperty() {
        return maxHeightProperty;
    }

    public void setMaxHeight(int value) {
        maxHeightProperty().setValue(value);
    }

    public int getComputedWidth() {
        return computedWidthProperty().getValue();
    }

    public ObservableIntegerValue computedWidthProperty() {
        return computedWidthPropertyReadOnly;
    }

    protected void setComputedWidth(int value) {
        computedWidthProperty.setValue(value);
    }

    public int getComputedHeight() {
        return computedHeightProperty().getValue();
    }

    public ObservableIntegerValue computedHeightProperty() {
        return computedHeightPropertyReadOnly;
    }

    protected void setComputedHeight(int value) {
        computedHeightProperty.setValue(value);
    }

    public Color.Background getBackgroundColor() {
        return backgroundColorProperty().getValue();
    }

    public ObjectProperty<Color.Background> backgroundColorProperty() {
        return backgroundColorProperty;
    }

    public void setBackgroundColor(Color.Background value) {
        backgroundColorProperty().setValue(value);
    }

    public Insets getPadding() {
        return paddingProperty().getValue();
    }

    public ObjectProperty<Insets> paddingProperty() {
        return paddingProperty;
    }

    public void setPadding(Insets value) {
        paddingProperty().setValue(value);
    }

    public Parent getParent() {
        return parentProperty().getValue();
    }

    public ObservableObjectValue<Parent> parentProperty() {
        return parentPropertyReadOnly;
    }

    protected void setParent(Parent value) {
        parentProperty.setValue(value);
    }

    public Scene getScene() {
        return sceneProperty().getValue();
    }

    public ObservableObjectValue<Scene> sceneProperty() {
        return scenePropertyReadOnly;
    }

    public boolean isDisable() {
        return disableProperty().getValue();
    }

    public BooleanProperty disableProperty() {
        return disableProperty;
    }

    public void setDisable(boolean value) {
        disableProperty().setValue(value);
    }

    public boolean isDisabled() {
        return disabledProperty().getValue();
    }

    public ObservableBooleanValue disabledProperty() {
        return disabledProperty;
    }

    public boolean isRoot() {
        return rootProperty().getValue();
    }

    public ObservableBooleanValue rootProperty() {
        return rootProperty;
    }

    public boolean isFocused() {
        return focusedProperty().getValue();
    }

    public ObservableBooleanValue focusedProperty() {
        return focusedProperty;
    }

    public boolean isHovered() {
        return hoveredProperty().getValue();
    }

    public ObservableBooleanValue hoveredProperty() {
        return hoveredProperty;
    }

    public boolean isActive() {
        return activeProperty().getValue();
    }

    public ObservableBooleanValue activeProperty() {
        return activeProperty;
    }

    public int getLeft() {
        return getX();
    }

    public int getTop() {
        return getY();
    }

    public int getRight() {
        return getX() + getWidth();
    }

    public int getBottom() {
        return getY() + getHeight();
    }

    public boolean inBounds(double x, double y) {
        return x >= getLeft() && x <= getRight() && y >= getTop() && y <= getBottom();
    }

    public void mouseClicked(MouseButtonEvent event) {
    }

    public void mouseReleased(MouseButtonEvent event) {
    }

    public void mouseDragged(MouseDragEvent event) {
    }

    public void mouseScrolled(MouseScrollEvent event) {
    }

    public void keyPressed(KeyEvent event) {
    }

    public void keyReleased(KeyEvent event) {
    }

    public void charTyped(TypeEvent event) {
    }

    public void mouseMoved(MouseEvent event) {
    }

    public void action(ActionEvent event) {
    }

    @Override
    public <E extends ScreenEvent> void handleEvent(ScreenEventType<E> type, E event) {
        type.ifMouseEvent(event, this::handleMouseEvent, () -> notifyEvent(type, event));
    }

    protected abstract <E extends MouseEvent> void handleMouseEvent(ScreenEventType<E> type, E event);

    protected <E extends ScreenEvent> void notifyEvent(ScreenEventType<E> type, E event) {
        type.onEvent(this, event);
        eventHandlerDelegate.handleEvent(type, event);
    }

    @Override
    public <E extends ScreenEvent> void addListener(ScreenEventType<E> type, ScreenEvent.Listener<E> listener) {
        eventHandlerDelegate.addListener(type, listener);
    }

    @Override
    public <E extends ScreenEvent> void removeListener(ScreenEventType<E> type, ScreenEvent.Listener<E> listener) {
        eventHandlerDelegate.removeListener(type, listener);
    }

    public void render(RenderContext<?> ctx) {
        GUAPI.getTheme().getSkin(this).render(this, ctx);
    }

    public void computeWidth() {
        setComputedWidth(GUAPI.getTheme().getSkin(this).computeWidth(this) + getPadding().getHorizontal());
    }

    public void computeHeight() {
        setComputedHeight(GUAPI.getTheme().getSkin(this).computeHeight(this) + getPadding().getVertical());
    }

    protected void computeSize() {
        computeWidth();
        computeHeight();
    }

    protected void updateWidth() {
        int width = getPrefWidth();
        if (width == COMPUTED_SIZE) {
            width = getComputedWidth();
        } else {
            width = Math.max(width, getComputedWidth());
        }
        width = Math.max(Math.min(width, getMaxWidth()), getMinWidth());
        if (parentProperty().hasValue()) {
            width = Math.min(width, getParent().getMaxChildrenWidth());
        }
        setWidth(width);
    }

    protected void updateHeight() {
        int height = getPrefHeight();
        if (height == COMPUTED_SIZE) {
            height = getComputedHeight();
        } else {
            height = Math.max(height, getComputedHeight());
        }
        height = Math.max(Math.min(height, getMaxHeight()), getMinHeight());
        if (parentProperty().hasValue()) {
            height = Math.min(height, getParent().getMaxChildrenHeight());
        }
        setHeight(height);
    }

    protected void updateSize() {
        updateWidth();
        updateHeight();
    }

    private void _updateWidth(PropertyChangeEvent<?> event) {
        updateWidth();
    }

    private void _updateHeight(PropertyChangeEvent<?> event) {
        updateHeight();
    }

    private void _updateSize(PropertyChangeEvent<?> event) {
        updateSize();
    }

    private void _updateParentWidth(PropertyChangeEvent<?> event) {
        if (getParent() != null) {
            getParent().computeWidth();
            getParent().updateChildrenPos();
        }
    }

    private void _updateParentHeight(PropertyChangeEvent<?> event) {
        if (getParent() != null) {
            getParent().computeHeight();
            getParent().updateChildrenPos();
        }
    }

    private void _updateScene(PropertyChangeEvent<? extends Parent> event) {
        sceneProperty.unbind();
        if (event.getNewValue() != null) {
            sceneProperty.bind(event.getNewValue().sceneProperty());
        } else {
            sceneProperty.setValue(null);
        }
    }
}
