package mrunknown404.dice.entity;

import net.minecraft.client.model.EntityModel;

public abstract class DiceModel extends EntityModel<DiceEntity> {
	public abstract void setupRotation(DiceEntity dice);
	protected abstract void setRotationAngle(float x, float y, float z);
}
