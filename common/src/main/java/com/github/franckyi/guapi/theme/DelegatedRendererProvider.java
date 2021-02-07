package com.github.franckyi.guapi.theme;

import com.github.franckyi.guapi.hooks.api.theme.DelegatedRenderer;
import com.github.franckyi.guapi.node.Node;

@FunctionalInterface
public interface DelegatedRendererProvider<N extends Node> {
    DelegatedRenderer<?> provide(N node);
}
