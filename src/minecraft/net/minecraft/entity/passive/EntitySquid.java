package net.minecraft.entity.passive;

import net.minecraft.block.material.Material;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntitySquid extends EntityWaterMob {

public static final int EaZy = 1185;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public float squidPitch;
	public float prevSquidPitch;
	public float squidYaw;
	public float prevSquidYaw;

	/**
	 * appears to be rotation in radians; we already have pitch & yaw, so this
	 * completes the triumvirate.
	 */
	public float squidRotation;

	/** previous squidRotation in radians */
	public float prevSquidRotation;

	/** angle of the tentacles in radians */
	public float tentacleAngle;

	/** the last calculated angle of the tentacles in radians */
	public float lastTentacleAngle;
	private float randomMotionSpeed;

	/** change in squidRotation in radians. */
	private float rotationVelocity;
	private float field_70871_bB;
	private float randomMotionVecX;
	private float randomMotionVecY;
	private float randomMotionVecZ;
	// private static final String __OBFID = "http://https://fuckuskid00001651";

	public EntitySquid(final World worldIn) {
		super(worldIn);
		setSize(0.95F, 0.95F);
		rand.setSeed(1 + getEntityId());
		rotationVelocity = 1.0F / (rand.nextFloat() + 1.0F) * 0.2F;
		tasks.addTask(0, new EntitySquid.AIMoveRandom());
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0D);
	}

	@Override
	public float getEyeHeight() {
		return height * 0.5F;
	}

	/**
	 * Returns the sound this mob makes while it's alive.
	 */
	@Override
	protected String getLivingSound() {
		return null;
	}

	/**
	 * Returns the sound this mob makes when it is hurt.
	 */
	@Override
	protected String getHurtSound() {
		return null;
	}

	/**
	 * Returns the sound this mob makes on death.
	 */
	@Override
	protected String getDeathSound() {
		return null;
	}

	/**
	 * Returns the volume for the sounds this mob makes.
	 */
	@Override
	protected float getSoundVolume() {
		return 0.4F;
	}

	@Override
	protected Item getDropItem() {
		return null;
	}

	/**
	 * returns if this entity triggers Block.onEntityWalking on the blocks they
	 * walk on. used for spiders and wolves to prevent them from trampling crops
	 */
	@Override
	protected boolean canTriggerWalking() {
		return false;
	}

	/**
	 * Drop 0-2 items of this living's type
	 */
	@Override
	protected void dropFewItems(final boolean p_70628_1_, final int p_70628_2_) {
		final int var3 = rand.nextInt(3 + p_70628_2_) + 1;

		for (int var4 = 0; var4 < var3; ++var4) {
			entityDropItem(new ItemStack(Items.dye, 1, EnumDyeColor.BLACK.getDyeColorDamage()), 0.0F);
		}
	}

	/**
	 * Checks if this entity is inside water (if inWater field is true as a
	 * result of handleWaterMovement() returning true)
	 */
	@Override
	public boolean isInWater() {
		return worldObj.handleMaterialAcceleration(getEntityBoundingBox().expand(0.0D, -0.6000000238418579D, 0.0D),
				Material.water, this);
	}

	/**
	 * Called frequently so the entity can update its state every tick as
	 * required. For example, zombies and skeletons use this to react to
	 * sunlight and start to burn.
	 */
	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		prevSquidPitch = squidPitch;
		prevSquidYaw = squidYaw;
		prevSquidRotation = squidRotation;
		lastTentacleAngle = tentacleAngle;
		squidRotation += rotationVelocity;

		if (squidRotation > Math.PI * 2D) {
			if (worldObj.isRemote) {
				squidRotation = (float) Math.PI * 2F;
			} else {
				squidRotation = (float) (squidRotation - Math.PI * 2D);

				if (rand.nextInt(10) == 0) {
					rotationVelocity = 1.0F / (rand.nextFloat() + 1.0F) * 0.2F;
				}

				worldObj.setEntityState(this, (byte) 19);
			}
		}

		if (inWater) {
			float var1;

			if (squidRotation < (float) Math.PI) {
				var1 = squidRotation / (float) Math.PI;
				tentacleAngle = MathHelper.sin(var1 * var1 * (float) Math.PI) * (float) Math.PI * 0.25F;

				if (var1 > 0.75D) {
					randomMotionSpeed = 1.0F;
					field_70871_bB = 1.0F;
				} else {
					field_70871_bB *= 0.8F;
				}
			} else {
				tentacleAngle = 0.0F;
				randomMotionSpeed *= 0.9F;
				field_70871_bB *= 0.99F;
			}

			if (!worldObj.isRemote) {
				motionX = randomMotionVecX * randomMotionSpeed;
				motionY = randomMotionVecY * randomMotionSpeed;
				motionZ = randomMotionVecZ * randomMotionSpeed;
			}

			var1 = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
			renderYawOffset += (-((float) Math.atan2(motionX, motionZ)) * 180.0F / (float) Math.PI - renderYawOffset)
					* 0.1F;
			rotationYaw = renderYawOffset;
			squidYaw = (float) (squidYaw + Math.PI * field_70871_bB * 1.5D);
			squidPitch += (-((float) Math.atan2(var1, motionY)) * 180.0F / (float) Math.PI - squidPitch) * 0.1F;
		} else {
			tentacleAngle = MathHelper.abs(MathHelper.sin(squidRotation)) * (float) Math.PI * 0.25F;

			if (!worldObj.isRemote) {
				motionX = 0.0D;
				motionY -= 0.08D;
				motionY *= 0.9800000190734863D;
				motionZ = 0.0D;
			}

			squidPitch = (float) (squidPitch + (-90.0F - squidPitch) * 0.02D);
		}
	}

	/**
	 * Moves the entity based on the specified heading. Args: strafe, forward
	 */
	@Override
	public void moveEntityWithHeading(final float p_70612_1_, final float p_70612_2_) {
		moveEntity(motionX, motionY, motionZ);
	}

	/**
	 * Checks if the entity's current position is a valid location to spawn this
	 * entity.
	 */
	@Override
	public boolean getCanSpawnHere() {
		return posY > 45.0D && posY < 63.0D && super.getCanSpawnHere();
	}

	@Override
	public void handleHealthUpdate(final byte p_70103_1_) {
		if (p_70103_1_ == 19) {
			squidRotation = 0.0F;
		} else {
			super.handleHealthUpdate(p_70103_1_);
		}
	}

	public void func_175568_b(final float p_175568_1_, final float p_175568_2_, final float p_175568_3_) {
		randomMotionVecX = p_175568_1_;
		randomMotionVecY = p_175568_2_;
		randomMotionVecZ = p_175568_3_;
	}

	public boolean func_175567_n() {
		return randomMotionVecX != 0.0F || randomMotionVecY != 0.0F || randomMotionVecZ != 0.0F;
	}

	class AIMoveRandom extends EntityAIBase {
		private final EntitySquid field_179476_a = EntitySquid.this;
		// private static final String __OBFID =
		// "http://https://fuckuskid00002232";

		@Override
		public boolean shouldExecute() {
			return true;
		}

		@Override
		public void updateTask() {
			final int var1 = field_179476_a.getAge();

			if (var1 > 100) {
				field_179476_a.func_175568_b(0.0F, 0.0F, 0.0F);
			} else if (field_179476_a.getRNG().nextInt(50) == 0 || !field_179476_a.inWater
					|| !field_179476_a.func_175567_n()) {
				final float var2 = field_179476_a.getRNG().nextFloat() * (float) Math.PI * 2.0F;
				final float var3 = MathHelper.cos(var2) * 0.2F;
				final float var4 = -0.1F + field_179476_a.getRNG().nextFloat() * 0.2F;
				final float var5 = MathHelper.sin(var2) * 0.2F;
				field_179476_a.func_175568_b(var3, var4, var5);
			}
		}
	}
}
