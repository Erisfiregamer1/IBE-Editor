package com.github.franckyi.gamehooks.impl.common;

import com.github.franckyi.gamehooks.api.common.Entity;
import com.github.franckyi.gamehooks.api.common.Item;
import com.github.franckyi.gamehooks.api.common.Player;
import com.github.franckyi.gamehooks.util.common.Text;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class FabricPlayer implements Player {
    private final PlayerEntity player;

    public FabricPlayer(PlayerEntity player) {
        this.player = player;
    }

    @Override
    public Item getItemMainHand() {
        ItemStack item = player.inventory.getMainHandStack();
        return item.isEmpty() ? null : new FabricItem(item);
    }

    @Override
    public void sendMessage(Text message, boolean actionBar) {
        player.sendMessage(FabricTextFactory.INSTANCE.create(message), actionBar);
    }

    @Override
    public Entity getPlayerEntity() {
        return null;
    }
}
