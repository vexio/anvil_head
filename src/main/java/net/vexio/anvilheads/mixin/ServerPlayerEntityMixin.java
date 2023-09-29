package net.vexio.anvilheads.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// this class file isn't registered on death! interesting...

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin {

    private static final Logger LOGGER = LoggerFactory.getLogger("anvil-heads");
    private PlayerEntity ServerPlayerEntity;

    @Inject(method = "onDeath", at = @At("HEAD")) // injection is not registered
    public void onPlayerDeath(DamageSource source, CallbackInfo ci) {
        LOGGER.info(source.getType().toString());
        ItemStack apple = Items.APPLE.getDefaultStack();
        this.ServerPlayerEntity.dropItem(apple, true, true);
        return;
    }
}
