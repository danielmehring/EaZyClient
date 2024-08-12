package net.minecraft.entity.monster;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIDefendVillage;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookAtVillager;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveThroughVillage;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAIMoveTowardsTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.village.Village;
import net.minecraft.world.World;

import com.google.common.base.Predicate;

public class EntityIronGolem extends EntityGolem {

public static final int EaZy = 1160;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	/** deincrements, and a distance-to-home check is done at 0 */
	private int homeCheckTimer;
	Village villageObj;
	private int attackTimer;
	private int holdRoseTick;
	// private static final String __OBFID = "http://https://fuckuskid00001652";

	public EntityIronGolem(final World worldIn) {
		super(worldIn);
		setSize(1.4F, 2.9F);
		((PathNavigateGround) getNavigator()).func_179690_a(true);
		tasks.addTask(1, new EntityAIAttackOnCollide(this, 1.0D, true));
		tasks.addTask(2, new EntityAIMoveTowardsTarget(this, 0.9D, 32.0F));
		tasks.addTask(3, new EntityAIMoveThroughVillage(this, 0.6D, true));
		tasks.addTask(4, new EntityAIMoveTowardsRestriction(this, 1.0D));
		tasks.addTask(5, new EntityAILookAtVillager(this));
		tasks.addTask(6, new EntityAIWander(this, 0.6D));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
		tasks.addTask(8, new EntityAILookIdle(this));
		targetTasks.addTask(1, new EntityAIDefendVillage(this));
		targetTasks.addTask(2, new EntityAIHurtByTarget(this, false, new Class[0]));
		targetTasks.addTask(3, new EntityIronGolem.AINearestAttackableTargetNonCreeper(this, EntityLiving.class, 10,
				false, true, IMob.field_175450_e));
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		dataWatcher.addObject(16, (byte) 0);
	}

	@Override
	protected void updateAITasks() {
		if (--homeCheckTimer <= 0) {
			homeCheckTimer = 70 + rand.nextInt(50);
			villageObj = worldObj.getVillageCollection().func_176056_a(new BlockPos(this), 32);

			if (villageObj == null) {
				detachHome();
			} else {
				final BlockPos var1 = villageObj.func_180608_a();
				func_175449_a(var1, (int) (villageObj.getVillageRadius() * 0.6F));
			}
		}

		super.updateAITasks();
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(100.0D);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
	}

	/**
	 * Decrements the entity's air supply when underwater
	 */
	@Override
	protected int decreaseAirSupply(final int p_70682_1_) {
		return p_70682_1_;
	}

	@Override
	protected void collideWithEntity(final Entity p_82167_1_) {
		if (p_82167_1_ instanceof IMob && getRNG().nextInt(20) == 0) {
			setAttackTarget((EntityLivingBase) p_82167_1_);
		}

		super.collideWithEntity(p_82167_1_);
	}

	/**
	 * Called frequently so the entity can update its state every tick as
	 * required. For example, zombies and skeletons use this to react to
	 * sunlight and start to burn.
	 */
	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();

		if (attackTimer > 0) {
			--attackTimer;
		}

		if (holdRoseTick > 0) {
			--holdRoseTick;
		}

		if (motionX * motionX + motionZ * motionZ > 2.500000277905201E-7D && rand.nextInt(5) == 0) {
			final int var1 = MathHelper.floor_double(posX);
			final int var2 = MathHelper.floor_double(posY - 0.20000000298023224D);
			final int var3 = MathHelper.floor_double(posZ);
			final IBlockState var4 = worldObj.getBlockState(new BlockPos(var1, var2, var3));
			final Block var5 = var4.getBlock();

			if (var5.getMaterial() != Material.air) {
				worldObj.spawnParticle(EnumParticleTypes.BLOCK_CRACK, posX + (rand.nextFloat() - 0.5D) * width,
						getEntityBoundingBox().minY + 0.1D, posZ + (rand.nextFloat() - 0.5D) * width,
						4.0D * (rand.nextFloat() - 0.5D), 0.5D, (rand.nextFloat() - 0.5D) * 4.0D,
						new int[] { Block.getStateId(var4) });
			}
		}
	}

	/**
	 * Returns true if this entity can attack entities of the specified class.
	 */
	@Override
	public boolean canAttackClass(final Class p_70686_1_) {
		return isPlayerCreated() && EntityPlayer.class.isAssignableFrom(p_70686_1_) ? false
				: super.canAttackClass(p_70686_1_);
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	public void writeEntityToNBT(final NBTTagCompound tagCompound) {
		super.writeEntityToNBT(tagCompound);
		tagCompound.setBoolean("PlayerCreated", isPlayerCreated());
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readEntityFromNBT(final NBTTagCompound tagCompund) {
		super.readEntityFromNBT(tagCompund);
		setPlayerCreated(tagCompund.getBoolean("PlayerCreated"));
	}

	@Override
	public boolean attackEntityAsMob(final Entity p_70652_1_) {
		attackTimer = 10;
		worldObj.setEntityState(this, (byte) 4);
		final boolean var2 = p_70652_1_.attackEntityFrom(DamageSource.causeMobDamage(this), 7 + rand.nextInt(15));

		if (var2) {
			p_70652_1_.motionY += 0.4000000059604645D;
			func_174815_a(this, p_70652_1_);
		}

		playSound("mob.irongolem.throw", 1.0F, 1.0F);
		return var2;
	}

	@Override
	public void handleHealthUpdate(final byte p_70103_1_) {
		if (p_70103_1_ == 4) {
			attackTimer = 10;
			playSound("mob.irongolem.throw", 1.0F, 1.0F);
		} else if (p_70103_1_ == 11) {
			holdRoseTick = 400;
		} else {
			super.handleHealthUpdate(p_70103_1_);
		}
	}

	public Village getVillage() {
		return villageObj;
	}

	public int getAttackTimer() {
		return attackTimer;
	}

	public void setHoldingRose(final boolean p_70851_1_) {
		holdRoseTick = p_70851_1_ ? 400 : 0;
		worldObj.setEntityState(this, (byte) 11);
	}

	/**
	 * Returns the sound this mob makes when it is hurt.
	 */
	@Override
	protected String getHurtSound() {
		return "mob.irongolem.hit";
	}

	/**
	 * Returns the sound this mob makes on death.
	 */
	@Override
	protected String getDeathSound() {
		return "mob.irongolem.death";
	}

	@Override
	protected void func_180429_a(final BlockPos p_180429_1_, final Block p_180429_2_) {
		playSound("mob.irongolem.walk", 1.0F, 1.0F);
	}

	/**
	 * Drop 0-2 items of this living's type
	 */
	@Override
	protected void dropFewItems(final boolean p_70628_1_, final int p_70628_2_) {
		final int var3 = rand.nextInt(3);
		int var4;

		for (var4 = 0; var4 < var3; ++var4) {
			dropItemWithOffset(Item.getItemFromBlock(Blocks.red_flower), 1,
					BlockFlower.EnumFlowerType.POPPY.func_176968_b());
		}

		var4 = 3 + rand.nextInt(3);

		for (int var5 = 0; var5 < var4; ++var5) {
			dropItem(Items.iron_ingot, 1);
		}
	}

	public int getHoldRoseTick() {
		return holdRoseTick;
	}

	public boolean isPlayerCreated() {
		return (dataWatcher.getWatchableObjectByte(16) & 1) != 0;
	}

	public void setPlayerCreated(final boolean p_70849_1_) {
		final byte var2 = dataWatcher.getWatchableObjectByte(16);

		if (p_70849_1_) {
			dataWatcher.updateObject(16, (byte) (var2 | 1));
		} else {
			dataWatcher.updateObject(16, (byte) (var2 & -2));
		}
	}

	/**
	 * Called when the mob's health reaches 0.
	 */
	@Override
	public void onDeath(final DamageSource cause) {
		if (!isPlayerCreated() && attackingPlayer != null && villageObj != null) {
			villageObj.setReputationForPlayer(attackingPlayer.getName(), -5);
		}

		super.onDeath(cause);
	}

	static class AINearestAttackableTargetNonCreeper extends EntityAINearestAttackableTarget {
		// private static final String __OBFID =
		// "http://https://fuckuskid00002231";

		public AINearestAttackableTargetNonCreeper(final EntityCreature p_i45858_1_, final Class p_i45858_2_,
				final int p_i45858_3_, final boolean p_i45858_4_, final boolean p_i45858_5_,
				final Predicate p_i45858_6_) {
			super(p_i45858_1_, p_i45858_2_, p_i45858_3_, p_i45858_4_, p_i45858_5_, p_i45858_6_);
			targetEntitySelector = new Predicate() {
				// private static final String __OBFID =
				// "http://https://fuckuskid00002230";
				public boolean func_180096_a(final EntityLivingBase p_180096_1_) {
					if (p_i45858_6_ != null && !p_i45858_6_.apply(p_180096_1_)) {
						return false;
					} else if (p_180096_1_ instanceof EntityCreeper) {
						return false;
					} else {
						if (p_180096_1_ instanceof EntityPlayer) {
							double var2 = AINearestAttackableTargetNonCreeper.this.getTargetDistance();

							if (p_180096_1_.isSneaking()) {
								var2 *= 0.800000011920929D;
							}

							if (p_180096_1_.isInvisible()) {
								float var4 = ((EntityPlayer) p_180096_1_).getArmorVisibility();

								if (var4 < 0.1F) {
									var4 = 0.1F;
								}

								var2 *= 0.7F * var4;
							}

							if (p_180096_1_.getDistanceToEntity(p_i45858_1_) > var2) {
								return false;
							}
						}

						return AINearestAttackableTargetNonCreeper.this.isSuitableTarget(p_180096_1_, false);
					}
				}

				@Override
				public boolean apply(final Object p_apply_1_) {
					return func_180096_a((EntityLivingBase) p_apply_1_);
				}
			};
		}
	}
}
