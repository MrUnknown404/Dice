package mrunknown404.dice.item;

import java.awt.Color;

import mrunknown404.dice.entity.DiceEntity;
import mrunknown404.dice.registries.DiceRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class DiceItem extends Item {
	private final Color color;
	
	public DiceItem(Color color) {
		super(new Item.Properties().tab(DiceRegistry.DICE).stacksTo(1));
		this.color = color;
	}
	
	@Override
	public ActionResult<ItemStack> use(World world, PlayerEntity entity, Hand hand) {
		ItemStack itemstack = entity.getItemInHand(hand);
		
		if (!world.isClientSide) {
			DiceEntity dice = new DiceEntity(world, entity, color);
			dice.setRoll(1 + random.nextInt(6));
			dice.shootFromRotation(entity, entity.xRot, entity.yRot, 0, 0.75f, 1);
			world.addFreshEntity(dice);
		}
		
		entity.awardStat(Stats.ITEM_USED.get(this));
		if (!entity.isCreative()) {
			itemstack.shrink(1);
		}
		
		world.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.WITCH_THROW, SoundCategory.NEUTRAL, 0.4f, 0.4f / (random.nextFloat() * 0.4f + 0.8f));
		entity.getCooldowns().addCooldown(this, 10);
		
		return ActionResult.sidedSuccess(itemstack, world.isClientSide());
	}
}
