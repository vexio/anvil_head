package net.vexio.anvilheads.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import net.vexio.anvilheads.utils.TextureUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;
import java.util.concurrent.Executors;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

	private static final Logger LOGGER = LoggerFactory.getLogger("anvil-heads");

	@Inject(method = "dropLoot", at = @At("HEAD")) // more accurate way of adding loot to death loot table
	private void dropHead(DamageSource source, boolean causedByPlayer, CallbackInfo ci) {
		LOGGER.info(source.getType().toString());
		if (this.livingEntity instanceof ServerPlayerEntity player) {
			if (source.getType().msgId().equals("anvil")) {
				Executors.newSingleThreadExecutor().execute(() -> {

					ItemStack skull = Items.PLAYER_HEAD.getDefaultStack();

					NbtCompound compound = TextureUtils.getNbtFromProfile(player.getGameProfile());
					skull.setNbt(compound);

					LOGGER.info("Successfully dropped player head due to Anvil death!");
					this.dropStack(skull);
					return;
				});
			}
		}
	}

	private final LivingEntity livingEntity = (LivingEntity) (Object) this;

	// Needed for extends Entity
	public LivingEntityMixin(EntityType<?> type, World world) {
		super(type, world);
	}
}