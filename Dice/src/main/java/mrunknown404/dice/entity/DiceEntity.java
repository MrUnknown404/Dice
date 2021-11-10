package mrunknown404.dice.entity;

import java.awt.Color;
import java.util.Random;

import mrunknown404.dice.registries.DiceRegistry;
import mrunknown404.dice.utils.DiceConfig;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MoverType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class DiceEntity extends Entity {
	private static final DataParameter<Integer> DATA_ROLLED = EntityDataManager.defineId(DiceEntity.class, DataSerializers.INT);
	private static final DataParameter<Integer> DATA_DICE_TYPE = EntityDataManager.defineId(DiceEntity.class, DataSerializers.INT);
	private static final DataParameter<Integer> DATA_RED = EntityDataManager.defineId(DiceEntity.class, DataSerializers.INT);
	private static final DataParameter<Integer> DATA_GREEN = EntityDataManager.defineId(DiceEntity.class, DataSerializers.INT);
	private static final DataParameter<Integer> DATA_BLUE = EntityDataManager.defineId(DiceEntity.class, DataSerializers.INT);
	private int life;
	private boolean lastTickOnGround;
	
	public DiceEntity(EntityType<? extends DiceEntity> type, World world) {
		super(type, world);
		setRed(0);
		setGreen(0);
		setBlue(0);
		setDiceType(0);
	}
	
	public DiceEntity(World world, Entity entity, Color color, int diceType) {
		super(DiceRegistry.DICE_ENTITY.get(), world);
		setPos(entity.getX(), entity.getEyeY() - 0.1f, entity.getZ());
		setRed(color.getRed());
		setGreen(color.getGreen());
		setBlue(color.getBlue());
		setDiceType(diceType);
	}
	
	@Override
	public void tick() {
		super.tick();
		life++;
		if (life >= 20 * DiceConfig.COMMON.diceExpireTime.get()) {
			remove();
			level.addParticle(ParticleTypes.POOF, getX(), getY(), getZ(), 0, 0.15625f, 0);
		}
		
		if (!isAlive()) {
			return;
		}
		
		xo = getX();
		yo = getY();
		zo = getZ();
		
		Vector3d vector3d = getDeltaMovement();
		float f = getEyeHeight() - 0.11111111F;
		if (isInWater() && getFluidHeight(FluidTags.WATER) > f) {
			setUnderwaterMovement();
		} else if (isInLava() && getFluidHeight(FluidTags.LAVA) > f) {
			setUnderLavaMovement();
		} else if (!isNoGravity()) {
			setDeltaMovement(getDeltaMovement().add(0, -0.04, 0));
		}
		
		if (level.isClientSide) {
			noPhysics = false;
		} else {
			noPhysics = !level.noCollision(this);
			if (noPhysics) {
				moveTowardsClosestSpace(getX(), (getBoundingBox().minY + getBoundingBox().maxY) / 2, getZ());
			}
		}
		
		BlockState state = level.getBlockState(new BlockPos(getX(), getY() - 1, getZ()));
		if (!onGround || getHorizontalDistanceSqr(getDeltaMovement()) > 1.0E-5f || (tickCount + getId()) % 4 == 0) {
			move(MoverType.SELF, getDeltaMovement());
			float f1 = 0.98f;
			if (onGround) {
				f1 = state.getSlipperiness(level, new BlockPos(getX(), getY() - 1, getZ()), this) * 0.98f;
			}
			
			setDeltaMovement(getDeltaMovement().multiply(f1, 0.98, f1));
			if (onGround) {
				Vector3d vector3d1 = getDeltaMovement();
				if (vector3d1.y < 0) {
					setDeltaMovement(vector3d1.multiply(1, -0.5, 1));
				}
			}
		}
		
		boolean flag = MathHelper.floor(xo) != MathHelper.floor(getX()) || MathHelper.floor(yo) != MathHelper.floor(getY()) ||
				MathHelper.floor(zo) != MathHelper.floor(getZ());
		int i = flag ? 2 : 40;
		if (tickCount % i == 0) {
			if (level.getFluidState(blockPosition()).is(FluidTags.LAVA) && !fireImmune()) {
				playSound(SoundEvents.GENERIC_BURN, 0.4f, 2 + random.nextFloat() * 0.4f);
			}
		}
		
		hasImpulse |= updateInWaterStateAndDoFluidPushing();
		if (!level.isClientSide) {
			double d0 = getDeltaMovement().subtract(vector3d).lengthSqr();
			if (d0 > 0.01) {
				hasImpulse = true;
			}
		}
		
		if (onGround && !lastTickOnGround) {
			level.playSound(null, getX(), getY(), getZ(), state.getSoundType().getStepSound(), SoundCategory.NEUTRAL, 0.5f, 0.4f / (random.nextFloat() * 0.4f + 0.8f));
		}
		
		lastTickOnGround = onGround;
	}
	
	private void setUnderwaterMovement() {
		Vector3d vector3d = getDeltaMovement();
		setDeltaMovement(vector3d.x * 0.99f, vector3d.y + (vector3d.y < 0.06f ? 5.0E-4f : 0f), vector3d.z * 0.99f);
	}
	
	private void setUnderLavaMovement() {
		Vector3d vector3d = getDeltaMovement();
		setDeltaMovement(vector3d.x * 0.95f, vector3d.y + (vector3d.y < 0.06f ? 5.0E-4f : 0f), vector3d.z * 0.95f);
	}
	
	@Override
	protected boolean isMovementNoisy() {
		return false;
	}
	
	public void shootFromRotation(Entity entity, float p_234612_2_, float p_234612_3_, float p_234612_4_, float p_234612_5_, float p_234612_6_) {
		float f = -MathHelper.sin(p_234612_3_ * ((float) Math.PI / 180F)) * MathHelper.cos(p_234612_2_ * ((float) Math.PI / 180F));
		float f1 = -MathHelper.sin((p_234612_2_ + p_234612_4_) * ((float) Math.PI / 180F));
		float f2 = MathHelper.cos(p_234612_3_ * ((float) Math.PI / 180F)) * MathHelper.cos(p_234612_2_ * ((float) Math.PI / 180F));
		shoot(f, f1, f2, p_234612_5_, p_234612_6_);
		Vector3d vector3d = entity.getDeltaMovement();
		setDeltaMovement(getDeltaMovement().add(vector3d.x, entity.isOnGround() ? 0.0D : vector3d.y, vector3d.z));
	}
	
	private void shoot(double p_70186_1_, double p_70186_3_, double p_70186_5_, float p_70186_7_, float p_70186_8_) {
		Vector3d vector3d = (new Vector3d(p_70186_1_, p_70186_3_, p_70186_5_)).normalize()
				.add(random.nextGaussian() * 0.0075f * p_70186_8_, random.nextGaussian() * 0.0075f * p_70186_8_, random.nextGaussian() * 0.0075f * p_70186_8_)
				.scale(p_70186_7_);
		setDeltaMovement(vector3d);
		float f = MathHelper.sqrt(getHorizontalDistanceSqr(vector3d));
		yRotO = yRot = (float) (MathHelper.atan2(vector3d.x, vector3d.z) * (180f / (float) Math.PI));
		xRotO = xRot = (float) (MathHelper.atan2(vector3d.y, f) * (180f / (float) Math.PI));
	}
	
	@Override
	public boolean shouldRenderAtSqrDistance(double dist) {
		return dist > 1;
	}
	
	private void setRoll(int roll) {
		getEntityData().set(DATA_ROLLED, roll);
	}
	
	public void setRoll(Random r) {
		getEntityData().set(DATA_ROLLED, 1 + r.nextInt(getDiceType()));
	}
	
	protected int getRoll() {
		return getEntityData().get(DATA_ROLLED);
	}
	
	public void setRed(int red) {
		getEntityData().set(DATA_RED, red);
	}
	
	protected int getRed() {
		return getEntityData().get(DATA_RED);
	}
	
	public void setGreen(int green) {
		getEntityData().set(DATA_GREEN, green);
	}
	
	protected int getGreen() {
		return getEntityData().get(DATA_GREEN);
	}
	
	public void setBlue(int blue) {
		getEntityData().set(DATA_BLUE, blue);
	}
	
	protected int getBlue() {
		return getEntityData().get(DATA_BLUE);
	}
	
	public void setDiceType(int diceType) {
		getEntityData().set(DATA_DICE_TYPE, diceType);
	}
	
	protected int getDiceType() {
		return getEntityData().get(DATA_DICE_TYPE);
	}
	
	@Override
	public IPacket<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
	
	@Override
	protected void defineSynchedData() {
		getEntityData().define(DATA_ROLLED, 0);
		getEntityData().define(DATA_RED, 0);
		getEntityData().define(DATA_GREEN, 0);
		getEntityData().define(DATA_BLUE, 0);
		getEntityData().define(DATA_DICE_TYPE, 0);
	}
	
	@Override
	public void addAdditionalSaveData(CompoundNBT nbt) {
		nbt.putInt("Roll", getRoll());
		nbt.putInt("Life", life);
		nbt.putInt("Red", getRed());
		nbt.putInt("Green", getGreen());
		nbt.putInt("Blue", getBlue());
		nbt.putInt("DiceType", getDiceType());
	}
	
	@Override
	public void readAdditionalSaveData(CompoundNBT nbt) {
		setRoll(nbt.getInt("Roll"));
		life = nbt.getInt("Life");
		setRed(nbt.getInt("Red"));
		setGreen(nbt.getInt("Green"));
		setBlue(nbt.getInt("Blue"));
		setDiceType(nbt.getInt("DiceType"));
	}
}
