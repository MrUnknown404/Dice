package mrunknown404.dice.item;

import java.awt.Color;

import mrunknown404.dice.entity.DiceEntity;
import mrunknown404.dice.registries.DiceRegistry;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class DiceItem extends Item {
	private final Color color;
	private final int diceType;
	
	public DiceItem(Color color, int diceType) {
		super(new Item.Properties().tab(DiceRegistry.DICE).stacksTo(1));
		this.color = color;
		this.diceType = diceType;
	}
	
	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player entity, InteractionHand hand) {
		ItemStack itemstack = entity.getItemInHand(hand);
		
		if (!world.isClientSide) {
			DiceEntity dice = new DiceEntity(world, entity, color, diceType);
			dice.setRoll(world.getRandom());
			dice.shootFromRotation(entity, entity.getXRot(), entity.getYRot(), 0, 0.75f, 1);
			world.addFreshEntity(dice);
		}
		
		entity.awardStat(Stats.ITEM_USED.get(this));
		world.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.WITCH_THROW, SoundSource.NEUTRAL, 0.4f,
				0.4f / (world.getRandom().nextFloat() * 0.4f + 0.8f));
		entity.getCooldowns().addCooldown(this, 10);
		
		return InteractionResultHolder.sidedSuccess(itemstack, world.isClientSide());
	}
}
