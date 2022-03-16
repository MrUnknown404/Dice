package mrunknown404.dice.entity;

import net.minecraft.client.model.EntityModel;

public abstract class DiceModel extends EntityModel<DiceEntity> {
	@Override
	public void setupAnim(DiceEntity dice, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {
		
	}
	
	public abstract void setupRotation(DiceEntity dice);
}
