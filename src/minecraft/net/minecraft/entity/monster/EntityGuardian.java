package net.minecraft.entity.monster;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityLookHelper;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFishFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.S2BPacketChangeGameState;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateSwimmer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.WeightedRandomFishable;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.List;

import com.google.common.base.Predicate;

public class EntityGuardian extends EntityMob {

public static final int EaZy = 1159;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private float field_175482_b;
	private float field_175484_c;
	private float field_175483_bk;
	private float field_175485_bl;
	private float field_175486_bm;
	private EntityLivingBase field_175478_bn;
	private int field_175479_bo;
	private boolean field_175480_bp;
	private EntityAIWander field_175481_bq;
	// private static final String __OBFID = "http://https://fuckuskid00002213";

	public EntityGuardian(final World worldIn) {
		super(worldIn);
		experienceValue = 10;
		setSize(0.85F, 0.85F);
		tasks.addTask(4, new EntityGuardian.AIGuardianAttack());
		EntityAIMoveTowardsRestriction var2;
		tasks.addTask(5, var2 = new EntityAIMoveTowardsRestriction(this, 1.0D));
		tasks.addTask(7, field_175481_bq = new EntityAIWander(this, 1.0D, 80));
		tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		tasks.addTask(8, new EntityAIWatchClosest(this, EntityGuardian.class, 12.0F, 0.01F));
		tasks.addTask(9, new EntityAILookIdle(this));
		field_175481_bq.setMutexBits(3);
		var2.setMutexBits(3);
		targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityLivingBase.class, 10, true, false,
				new EntityGuardian.GuardianTargetSelector()));
		moveHelper = new EntityGuardian.GuardianMoveHelper();
		field_175484_c = field_175482_b = rand.nextFloat();
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(6.0D);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5D);
		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(16.0D);
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30.0D);
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readEntityFromNBT(final NBTTagCompound tagCompund) {
		super.readEntityFromNBT(tagCompund);
		func_175467_a(tagCompund.getBoolean("Elder"));
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	public void writeEntityToNBT(final NBTTagCompound tagCompound) {
		super.writeEntityToNBT(tagCompound);
		tagCompound.setBoolean("Elder", func_175461_cl());
	}

	@Override
	protected PathNavigate func_175447_b(final World worldIn) {
		return new PathNavigateSwimmer(this, worldIn);
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		dataWatcher.addObject(16, 0);
		dataWatcher.addObject(17, 0);
	}

	private boolean func_175468_a(final int p_175468_1_) {
		return (dataWatcher.getWatchableObjectInt(16) & p_175468_1_) != 0;
	}

	private void func_175473_a(final int p_175473_1_, final boolean p_175473_2_) {
		final int var3 = dataWatcher.getWatchableObjectInt(16);

		if (p_175473_2_) {
			dataWatcher.updateObject(16, var3 | p_175473_1_);
		} else {
			dataWatcher.updateObject(16, var3 & ~p_175473_1_);
		}
	}

	public boolean func_175472_n() {
		return func_175468_a(2);
	}

	private void func_175476_l(final boolean p_175476_1_) {
		func_175473_a(2, p_175476_1_);
	}

	public int func_175464_ck() {
		return func_175461_cl() ? 60 : 80;
	}

	public boolean func_175461_cl() {
		return func_175468_a(4);
	}

	public void func_175467_a(final boolean p_175467_1_) {
		func_175473_a(4, p_175467_1_);

		if (p_175467_1_) {
			setSize(1.9975F, 1.9975F);
			getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.30000001192092896D);
			getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(8.0D);
			getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(80.0D);
			enablePersistence();
			field_175481_bq.func_179479_b(400);
		}
	}

	public void func_175465_cm() {
		func_175467_a(true);
		field_175486_bm = field_175485_bl = 1.0F;
	}

	private void func_175463_b(final int p_175463_1_) {
		dataWatcher.updateObject(17, p_175463_1_);
	}

	public boolean func_175474_cn() {
		return dataWatcher.getWatchableObjectInt(17) != 0;
	}

	public EntityLivingBase func_175466_co() {
		if (!func_175474_cn()) {
			return null;
		} else if (worldObj.isRemote) {
			if (field_175478_bn != null) {
				return field_175478_bn;
			} else {
				final Entity var1 = worldObj.getEntityByID(dataWatcher.getWatchableObjectInt(17));

				if (var1 instanceof EntityLivingBase) {
					field_175478_bn = (EntityLivingBase) var1;
					return field_175478_bn;
				} else {
					return null;
				}
			}
		} else {
			return getAttackTarget();
		}
	}

	@Override
	public void func_145781_i(final int p_145781_1_) {
		super.func_145781_i(p_145781_1_);

		if (p_145781_1_ == 16) {
			if (func_175461_cl() && width < 1.0F) {
				setSize(1.9975F, 1.9975F);
			}
		} else if (p_145781_1_ == 17) {
			field_175479_bo = 0;
			field_175478_bn = null;
		}
	}

	/**
	 * Get number of ticks, at least during which the living entity will be
	 * silent.
	 */
	@Override
	public int getTalkInterval() {
		return 160;
	}

	/**
	 * Returns the sound this mob makes while it's alive.
	 */
	@Override
	protected String getLivingSound() {
		return !isInWater() ? "mob.guardian.land.idle"
				: func_175461_cl() ? "mob.guardian.elder.idle" : "mob.guardian.idle";
	}

	/**
	 * Returns the sound this mob makes when it is hurt.
	 */
	@Override
	protected String getHurtSound() {
		return !isInWater() ? "mob.guardian.land.hit"
				: func_175461_cl() ? "mob.guardian.elder.hit" : "mob.guardian.hit";
	}

	/**
	 * Returns the sound this mob makes on death.
	 */
	@Override
	protected String getDeathSound() {
		return !isInWater() ? "mob.guardian.land.death"
				: func_175461_cl() ? "mob.guardian.elder.death" : "mob.guardian.death";
	}

	/**
	 * returns if this entity triggers Block.onEntityWalking on the blocks they
	 * walk on. used for spiders and wolves to prevent them from trampling crops
	 */
	@Override
	protected boolean canTriggerWalking() {
		return false;
	}

	@Override
	public float getEyeHeight() {
		return height * 0.5F;
	}

	@Override
	public float func_180484_a(final BlockPos p_180484_1_) {
		return worldObj.getBlockState(p_180484_1_).getBlock().getMaterial() == Material.water
				? 10.0F + worldObj.getLightBrightness(p_180484_1_) - 0.5F : super.func_180484_a(p_180484_1_);
	}

	/**
	 * Called frequently so the entity can update its state every tick as
	 * required. For example, zombies and skeletons use this to react to
	 * sunlight and start to burn.
	 */
	@Override
	public void onLivingUpdate() {
		if (worldObj.isRemote) {
			field_175484_c = field_175482_b;

			if (!isInWater()) {
				field_175483_bk = 2.0F;

				if (motionY > 0.0D && field_175480_bp && !isSlient()) {
					worldObj.playSound(posX, posY, posZ, "mob.guardian.flop", 1.0F, 1.0F, false);
				}

				field_175480_bp = motionY < 0.0D && worldObj.func_175677_d(new BlockPos(this).offsetDown(), false);
			} else if (func_175472_n()) {
				if (field_175483_bk < 0.5F) {
					field_175483_bk = 4.0F;
				} else {
					field_175483_bk += (0.5F - field_175483_bk) * 0.1F;
				}
			} else {
				field_175483_bk += (0.125F - field_175483_bk) * 0.2F;
			}

			field_175482_b += field_175483_bk;
			field_175486_bm = field_175485_bl;

			if (!isInWater()) {
				field_175485_bl = rand.nextFloat();
			} else if (func_175472_n()) {
				field_175485_bl += (0.0F - field_175485_bl) * 0.25F;
			} else {
				field_175485_bl += (1.0F - field_175485_bl) * 0.06F;
			}

			if (func_175472_n() && isInWater()) {
				final Vec3 var1 = getLook(0.0F);

				for (int var2 = 0; var2 < 2; ++var2) {
					worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE,
							posX + (rand.nextDouble() - 0.5D) * width - var1.xCoord * 1.5D,
							posY + rand.nextDouble() * height - var1.yCoord * 1.5D,
							posZ + (rand.nextDouble() - 0.5D) * width - var1.zCoord * 1.5D, 0.0D, 0.0D, 0.0D,
							new int[0]);
				}
			}

			if (func_175474_cn()) {
				if (field_175479_bo < func_175464_ck()) {
					++field_175479_bo;
				}

				final EntityLivingBase var14 = func_175466_co();

				if (var14 != null) {
					getLookHelper().setLookPositionWithEntity(var14, 90.0F, 90.0F);
					getLookHelper().onUpdateLook();
					final double var15 = func_175477_p(0.0F);
					double var4 = var14.posX - posX;
					double var6 = var14.posY + var14.height * 0.5F - (posY + getEyeHeight());
					double var8 = var14.posZ - posZ;
					final double var10 = Math.sqrt(var4 * var4 + var6 * var6 + var8 * var8);
					var4 /= var10;
					var6 /= var10;
					var8 /= var10;
					double var12 = rand.nextDouble();

					while (var12 < var10) {
						var12 += 1.8D - var15 + rand.nextDouble() * (1.7D - var15);
						worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, posX + var4 * var12,
								posY + var6 * var12 + getEyeHeight(), posZ + var8 * var12, 0.0D, 0.0D, 0.0D,
								new int[0]);
					}
				}
			}
		}

		if (inWater) {
			setAir(300);
		} else if (onGround) {
			motionY += 0.5D;
			motionX += (rand.nextFloat() * 2.0F - 1.0F) * 0.4F;
			motionZ += (rand.nextFloat() * 2.0F - 1.0F) * 0.4F;
			rotationYaw = rand.nextFloat() * 360.0F;
			onGround = false;
			isAirBorne = true;
		}

		if (func_175474_cn()) {
			rotationYaw = rotationYawHead;
		}

		super.onLivingUpdate();
	}

	public float func_175471_a(final float p_175471_1_) {
		return field_175484_c + (field_175482_b - field_175484_c) * p_175471_1_;
	}

	public float func_175469_o(final float p_175469_1_) {
		return field_175486_bm + (field_175485_bl - field_175486_bm) * p_175469_1_;
	}

	public float func_175477_p(final float p_175477_1_) {
		return (field_175479_bo + p_175477_1_) / func_175464_ck();
	}

	@Override
	protected void updateAITasks() {
		super.updateAITasks();

		if (func_175461_cl()) {
			if ((ticksExisted + getEntityId()) % 1200 == 0) {
				final Potion var5 = Potion.digSlowdown;
				final List var6 = worldObj.func_175661_b(EntityPlayerMP.class, new Predicate() {
					// private static final String __OBFID =
					// "http://https://fuckuskid00002212";
					public boolean func_179913_a(final EntityPlayerMP p_179913_1_) {
						return EntityGuardian.this.getDistanceSqToEntity(p_179913_1_) < 2500.0D
								&& p_179913_1_.theItemInWorldManager.func_180239_c();
					}

					@Override
					public boolean apply(final Object p_apply_1_) {
						return func_179913_a((EntityPlayerMP) p_apply_1_);
					}
				});
				final Iterator var7 = var6.iterator();

				while (var7.hasNext()) {
					final EntityPlayerMP var8 = (EntityPlayerMP) var7.next();

					if (!var8.isPotionActive(var5) || var8.getActivePotionEffect(var5).getAmplifier() < 2
							|| var8.getActivePotionEffect(var5).getDuration() < 1200) {
						var8.playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(10, 0.0F));
						var8.addPotionEffect(new PotionEffect(var5.id, 6000, 2));
					}
				}
			}

			if (!hasHome()) {
				func_175449_a(new BlockPos(this), 16);
			}
		}
	}

	/**
	 * Drop 0-2 items of this living's type
	 */
	@Override
	protected void dropFewItems(final boolean p_70628_1_, final int p_70628_2_) {
		final int var3 = rand.nextInt(3) + rand.nextInt(p_70628_2_ + 1);

		if (var3 > 0) {
			entityDropItem(new ItemStack(Items.prismarine_shard, var3, 0), 1.0F);
		}

		if (rand.nextInt(3 + p_70628_2_) > 1) {
			entityDropItem(new ItemStack(Items.fish, 1, ItemFishFood.FishType.COD.getItemDamage()), 1.0F);
		} else if (rand.nextInt(3 + p_70628_2_) > 1) {
			entityDropItem(new ItemStack(Items.prismarine_crystals, 1, 0), 1.0F);
		}

		if (p_70628_1_ && func_175461_cl()) {
			entityDropItem(new ItemStack(Blocks.sponge, 1, 1), 1.0F);
		}
	}

	/**
	 * Makes entity wear random armor based on difficulty
	 */
	@Override
	protected void addRandomArmor() {
		final ItemStack var1 = ((WeightedRandomFishable) WeightedRandom.getRandomItem(rand,
				EntityFishHook.func_174855_j())).getItemStack(rand);
		entityDropItem(var1, 1.0F);
	}

	/**
	 * Checks to make sure the light is not too bright where the mob is spawning
	 */
	@Override
	protected boolean isValidLightLevel() {
		return true;
	}

	/**
	 * Whether or not the current entity is in lava
	 */
	@Override
	public boolean handleLavaMovement() {
		return worldObj.checkNoEntityCollision(getEntityBoundingBox(), this)
				&& worldObj.getCollidingBoundingBoxes(this, getEntityBoundingBox()).isEmpty();
	}

	/**
	 * Checks if the entity's current position is a valid location to spawn this
	 * entity.
	 */
	@Override
	public boolean getCanSpawnHere() {
		return (rand.nextInt(20) == 0 || !worldObj.canBlockSeeSky(new BlockPos(this))) && super.getCanSpawnHere();
	}

	/**
	 * Called when the entity is attacked.
	 */
	@Override
	public boolean attackEntityFrom(final DamageSource source, final float amount) {
		if (!func_175472_n() && !source.isMagicDamage() && source.getSourceOfDamage() instanceof EntityLivingBase) {
			final EntityLivingBase var3 = (EntityLivingBase) source.getSourceOfDamage();

			if (!source.isExplosion()) {
				var3.attackEntityFrom(DamageSource.causeThornsDamage(this), 2.0F);
				var3.playSound("damage.thorns", 0.5F, 1.0F);
			}
		}

		field_175481_bq.func_179480_f();
		return super.attackEntityFrom(source, amount);
	}

	/**
	 * The speed it takes to move the entityliving's rotationPitch through the
	 * faceEntity method. This is only currently use in wolves.
	 */
	@Override
	public int getVerticalFaceSpeed() {
		return 180;
	}

	/**
	 * Moves the entity based on the specified heading. Args: strafe, forward
	 */
	@Override
	public void moveEntityWithHeading(final float p_70612_1_, final float p_70612_2_) {
		if (isServerWorld()) {
			if (isInWater()) {
				moveFlying(p_70612_1_, p_70612_2_, 0.1F);
				moveEntity(motionX, motionY, motionZ);
				motionX *= 0.8999999761581421D;
				motionY *= 0.8999999761581421D;
				motionZ *= 0.8999999761581421D;

				if (!func_175472_n() && getAttackTarget() == null) {
					motionY -= 0.005D;
				}
			} else {
				super.moveEntityWithHeading(p_70612_1_, p_70612_2_);
			}
		} else {
			super.moveEntityWithHeading(p_70612_1_, p_70612_2_);
		}
	}

	class AIGuardianAttack extends EntityAIBase {
		private final EntityGuardian field_179456_a = EntityGuardian.this;
		private int field_179455_b;
		// private static final String __OBFID =
		// "http://https://fuckuskid00002211";

		public AIGuardianAttack() {
			setMutexBits(3);
		}

		@Override
		public boolean shouldExecute() {
			final EntityLivingBase var1 = field_179456_a.getAttackTarget();
			return var1 != null && var1.isEntityAlive();
		}

		@Override
		public boolean continueExecuting() {
			return super.continueExecuting() && (field_179456_a.func_175461_cl()
					|| field_179456_a.getDistanceSqToEntity(field_179456_a.getAttackTarget()) > 9.0D);
		}

		@Override
		public void startExecuting() {
			field_179455_b = -10;
			field_179456_a.getNavigator().clearPathEntity();
			field_179456_a.getLookHelper().setLookPositionWithEntity(field_179456_a.getAttackTarget(), 90.0F, 90.0F);
			field_179456_a.isAirBorne = true;
		}

		@Override
		public void resetTask() {
			field_179456_a.func_175463_b(0);
			field_179456_a.setAttackTarget((EntityLivingBase) null);
			field_179456_a.field_175481_bq.func_179480_f();
		}

		@Override
		public void updateTask() {
			final EntityLivingBase var1 = field_179456_a.getAttackTarget();
			field_179456_a.getNavigator().clearPathEntity();
			field_179456_a.getLookHelper().setLookPositionWithEntity(var1, 90.0F, 90.0F);

			if (!field_179456_a.canEntityBeSeen(var1)) {
				field_179456_a.setAttackTarget((EntityLivingBase) null);
			} else {
				++field_179455_b;

				if (field_179455_b == 0) {
					field_179456_a.func_175463_b(field_179456_a.getAttackTarget().getEntityId());
					field_179456_a.worldObj.setEntityState(field_179456_a, (byte) 21);
				} else if (field_179455_b >= field_179456_a.func_175464_ck()) {
					float var2 = 1.0F;

					if (field_179456_a.worldObj.getDifficulty() == EnumDifficulty.HARD) {
						var2 += 2.0F;
					}

					if (field_179456_a.func_175461_cl()) {
						var2 += 2.0F;
					}

					var1.attackEntityFrom(DamageSource.causeIndirectMagicDamage(field_179456_a, field_179456_a), var2);
					var1.attackEntityFrom(DamageSource.causeMobDamage(field_179456_a), (float) field_179456_a
							.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue());
					field_179456_a.setAttackTarget((EntityLivingBase) null);
				} else if (field_179455_b >= 60 && field_179455_b % 20 == 0) {
				}

				super.updateTask();
			}
		}
	}

	class GuardianMoveHelper extends EntityMoveHelper {
		private final EntityGuardian field_179930_g = EntityGuardian.this;
		// private static final String __OBFID =
		// "http://https://fuckuskid00002209";

		public GuardianMoveHelper() {
			super(EntityGuardian.this);
		}

		@Override
		public void onUpdateMoveHelper() {
			if (update && !field_179930_g.getNavigator().noPath()) {
				final double var1 = posX - field_179930_g.posX;
				double var3 = posY - field_179930_g.posY;
				final double var5 = posZ - field_179930_g.posZ;
				double var7 = var1 * var1 + var3 * var3 + var5 * var5;
				var7 = MathHelper.sqrt_double(var7);
				var3 /= var7;
				final float var9 = (float) (Math.atan2(var5, var1) * 180.0D / Math.PI) - 90.0F;
				field_179930_g.rotationYaw = limitAngle(field_179930_g.rotationYaw, var9, 30.0F);
				field_179930_g.renderYawOffset = field_179930_g.rotationYaw;
				final float var10 = (float) (speed
						* field_179930_g.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue());
				field_179930_g.setAIMoveSpeed(
						field_179930_g.getAIMoveSpeed() + (var10 - field_179930_g.getAIMoveSpeed()) * 0.125F);
				double var11 = Math.sin((field_179930_g.ticksExisted + field_179930_g.getEntityId()) * 0.5D) * 0.05D;
				final double var13 = Math.cos(field_179930_g.rotationYaw * (float) Math.PI / 180.0F);
				final double var15 = Math.sin(field_179930_g.rotationYaw * (float) Math.PI / 180.0F);
				field_179930_g.motionX += var11 * var13;
				field_179930_g.motionZ += var11 * var15;
				var11 = Math.sin((field_179930_g.ticksExisted + field_179930_g.getEntityId()) * 0.75D) * 0.05D;
				field_179930_g.motionY += var11 * (var15 + var13) * 0.25D;
				field_179930_g.motionY += field_179930_g.getAIMoveSpeed() * var3 * 0.1D;
				final EntityLookHelper var17 = field_179930_g.getLookHelper();
				final double var18 = field_179930_g.posX + var1 / var7 * 2.0D;
				final double var20 = field_179930_g.getEyeHeight() + field_179930_g.posY + var3 / var7 * 1.0D;
				final double var22 = field_179930_g.posZ + var5 / var7 * 2.0D;
				double var24 = var17.func_180423_e();
				double var26 = var17.func_180422_f();
				double var28 = var17.func_180421_g();

				if (!var17.func_180424_b()) {
					var24 = var18;
					var26 = var20;
					var28 = var22;
				}

				field_179930_g.getLookHelper().setLookPosition(var24 + (var18 - var24) * 0.125D,
						var26 + (var20 - var26) * 0.125D, var28 + (var22 - var28) * 0.125D, 10.0F, 40.0F);
				field_179930_g.func_175476_l(true);
			} else {
				field_179930_g.setAIMoveSpeed(0.0F);
				field_179930_g.func_175476_l(false);
			}
		}
	}

	class GuardianTargetSelector implements Predicate {
		private final EntityGuardian field_179916_a = EntityGuardian.this;
		// private static final String __OBFID =
		// "http://https://fuckuskid00002210";

		public boolean func_179915_a(final EntityLivingBase p_179915_1_) {
			return (p_179915_1_ instanceof EntityPlayer || p_179915_1_ instanceof EntitySquid)
					&& p_179915_1_.getDistanceSqToEntity(field_179916_a) > 9.0D;
		}

		@Override
		public boolean apply(final Object p_apply_1_) {
			return func_179915_a((EntityLivingBase) p_apply_1_);
		}
	}
}
