package mrunknown404.dice.entity;

import java.awt.Color;
import java.util.Random;

import mrunknown404.dice.registries.DiceRegistry;
import mrunknown404.dice.utils.DiceConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

public class DiceEntity extends Entity {
	private static final EntityDataAccessor<Integer> DATA_ROLLED = SynchedEntityData.defineId(DiceEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> DATA_DICE_TYPE = SynchedEntityData.defineId(DiceEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> DATA_RED = SynchedEntityData.defineId(DiceEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> DATA_GREEN = SynchedEntityData.defineId(DiceEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> DATA_BLUE = SynchedEntityData.defineId(DiceEntity.class, EntityDataSerializers.INT);
	private int life;
	private boolean lastTickOnGround;
	
	public DiceEntity(EntityType<? extends DiceEntity> type, Level world) {
		super(type, world);
		setRed(0);
		setGreen(0);
		setBlue(0);
		setDiceType(0);
	}
	
	public DiceEntity(Level world, Entity entity, Color color, int diceType) {
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
			remove(RemovalReason.KILLED);
			level.addParticle(ParticleTypes.POOF, getX(), getY(), getZ(), 0, 0.15625f, 0);
		}
		
		if (!isAlive()) {
			return;
		}
		
		xo = getX();
		yo = getY();
		zo = getZ();
		
		Vec3 vector3d = getDeltaMovement();
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
				f1 = state.getFriction(level, new BlockPos(getX(), getY() - 1, getZ()), this) * 0.98f;
			}
			
			setDeltaMovement(getDeltaMovement().multiply(f1, 0.98, f1));
			if (onGround) {
				Vec3 vector3d1 = getDeltaMovement();
				if (vector3d1.y < 0) {
					setDeltaMovement(vector3d1.multiply(1, -0.5, 1));
				}
			}
		}
		
		boolean flag = Math.floor(xo) != Math.floor(getX()) || Math.floor(yo) != Math.floor(getY()) || Math.floor(zo) != Math.floor(getZ());
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
			level.playSound(null, getX(), getY(), getZ(), state.getSoundType().getStepSound(), SoundSource.NEUTRAL, 0.5f, 0.4f / (random.nextFloat() * 0.4f + 0.8f));
		}
		
		lastTickOnGround = onGround;
	}
	
	private void setUnderwaterMovement() {
		Vec3 vector3d = getDeltaMovement();
		setDeltaMovement(vector3d.x * 0.99f, vector3d.y + (vector3d.y < 0.06f ? 5.0E-4f : 0f), vector3d.z * 0.99f);
	}
	
	private void setUnderLavaMovement() {
		Vec3 vector3d = getDeltaMovement();
		setDeltaMovement(vector3d.x * 0.95f, vector3d.y + (vector3d.y < 0.06f ? 5.0E-4f : 0f), vector3d.z * 0.95f);
	}
	
	public void shootFromRotation(Entity entity, float p_234612_2_, float p_234612_3_, float p_234612_4_, float p_234612_5_, float p_234612_6_) {
		double f = -Math.sin(p_234612_3_ * (Math.PI / 180f)) * Math.cos(p_234612_2_ * (Math.PI / 180f));
		double f1 = -Math.sin((p_234612_2_ + p_234612_4_) * ((float) Math.PI / 180F));
		double f2 = Math.cos(p_234612_3_ * (Math.PI / 180f)) * Math.cos(p_234612_2_ * (Math.PI / 180f));
		shoot(f, f1, f2, p_234612_5_, p_234612_6_);
		Vec3 vector3d = entity.getDeltaMovement();
		setDeltaMovement(getDeltaMovement().add(vector3d.x, entity.isOnGround() ? 0.0D : vector3d.y, vector3d.z));
	}
	
	private void shoot(double p_70186_1_, double p_70186_3_, double p_70186_5_, float p_70186_7_, float p_70186_8_) {
		Vec3 vector3d = (new Vec3(p_70186_1_, p_70186_3_, p_70186_5_)).normalize()
				.add(random.nextGaussian() * 0.0075f * p_70186_8_, random.nextGaussian() * 0.0075f * p_70186_8_, random.nextGaussian() * 0.0075f * p_70186_8_)
				.scale(p_70186_7_);
		setDeltaMovement(vector3d);
		double f = Math.sqrt(getHorizontalDistanceSqr(vector3d));
		yRotO = (float) (Math.atan2(vector3d.x, vector3d.z) * (180f / (float) Math.PI));
		xRotO = (float) (Math.atan2(vector3d.y, f) * (180f / (float) Math.PI));
	}
	
	private double getHorizontalDistanceSqr(Vec3 vec) {
		double d0 = this.getX() - vec.x;
		double d2 = this.getZ() - vec.z;
		return d0 * d0 + d2 * d2;
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
	public Packet<?> getAddEntityPacket() {
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
	public void addAdditionalSaveData(CompoundTag nbt) {
		nbt.putInt("Roll", getRoll());
		nbt.putInt("Life", life);
		nbt.putInt("Red", getRed());
		nbt.putInt("Green", getGreen());
		nbt.putInt("Blue", getBlue());
		nbt.putInt("DiceType", getDiceType());
	}
	
	@Override
	public void readAdditionalSaveData(CompoundTag nbt) {
		setRoll(nbt.getInt("Roll"));
		life = nbt.getInt("Life");
		setRed(nbt.getInt("Red"));
		setGreen(nbt.getInt("Green"));
		setBlue(nbt.getInt("Blue"));
		setDiceType(nbt.getInt("DiceType"));
	}
}
