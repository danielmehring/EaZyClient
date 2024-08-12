package net.minecraft.entity.monster;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateClimber;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

import java.util.Random;

public class EntitySpider extends EntityMob {

public static final int EaZy = 1168;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00001699";

	public EntitySpider(final World worldIn) {
		super(worldIn);
		setSize(1.4F, 0.9F);
		tasks.addTask(1, new EntityAISwimming(this));
		tasks.addTask(2, field_175455_a);
		tasks.addTask(3, new EntityAILeapAtTarget(this, 0.4F));
		tasks.addTask(4, new EntitySpider.AISpiderAttack(EntityPlayer.class));
		tasks.addTask(4, new EntitySpider.AISpiderAttack(EntityIronGolem.class));
		tasks.addTask(5, new EntityAIWander(this, 0.8D));
		tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		tasks.addTask(6, new EntityAILookIdle(this));
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
		targetTasks.addTask(2, new EntitySpider.AISpiderTarget(EntityPlayer.class));
		targetTasks.addTask(3, new EntitySpider.AISpiderTarget(EntityIronGolem.class));
	}

	@Override
	protected PathNavigate func_175447_b(final World worldIn) {
		return new PathNavigateClimber(this, worldIn);
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		dataWatcher.addObject(16, (byte) 0);
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate() {
		super.onUpdate();

		if (!worldObj.isRemote) {
			setBesideClimbableBlock(isCollidedHorizontally);
		}
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(16.0D);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.30000001192092896D);
	}

	/**
	 * Returns the sound this mob makes while it's alive.
	 */
	@Override
	protected String getLivingSound() {
		return "mob.spider.say";
	}

	/**
	 * Returns the sound this mob makes when it is hurt.
	 */
	@Override
	protected String getHurtSound() {
		return "mob.spider.say";
	}

	/**
	 * Returns the sound this mob makes on death.
	 */
	@Override
	protected String getDeathSound() {
		return "mob.spider.death";
	}

	@Override
	protected void func_180429_a(final BlockPos p_180429_1_, final Block p_180429_2_) {
		playSound("mob.spider.step", 0.15F, 1.0F);
	}

	@Override
	protected Item getDropItem() {
		return Items.string;
	}

	/**
	 * Drop 0-2 items of this living's type
	 */
	@Override
	protected void dropFewItems(final boolean p_70628_1_, final int p_70628_2_) {
		super.dropFewItems(p_70628_1_, p_70628_2_);

		if (p_70628_1_ && (rand.nextInt(3) == 0 || rand.nextInt(1 + p_70628_2_) > 0)) {
			dropItem(Items.spider_eye, 1);
		}
	}

	/**
	 * returns true if this entity is by a ladder, false otherwise
	 */
	@Override
	public boolean isOnLadder() {
		return isBesideClimbableBlock();
	}

	/**
	 * Sets the Entity inside a web block.
	 */
	@Override
	public void setInWeb() {}

	/**
	 * Get this Entity's EnumCreatureAttribute
	 */
	@Override
	public EnumCreatureAttribute getCreatureAttribute() {
		return EnumCreatureAttribute.ARTHROPOD;
	}

	@Override
	public boolean isPotionApplicable(final PotionEffect p_70687_1_) {
		return p_70687_1_.getPotionID() == Potion.poison.id ? false : super.isPotionApplicable(p_70687_1_);
	}

	/**
	 * Returns true if the WatchableObject (Byte) is 0x01 otherwise returns
	 * false. The WatchableObject is updated using setBesideClimableBlock.
	 */
	public boolean isBesideClimbableBlock() {
		return (dataWatcher.getWatchableObjectByte(16) & 1) != 0;
	}

	/**
	 * Updates the WatchableObject (Byte) created in entityInit(), setting it to
	 * 0x01 if par1 is true or 0x00 if it is false.
	 */
	public void setBesideClimbableBlock(final boolean p_70839_1_) {
		byte var2 = dataWatcher.getWatchableObjectByte(16);

		if (p_70839_1_) {
			var2 = (byte) (var2 | 1);
		} else {
			var2 &= -2;
		}

		dataWatcher.updateObject(16, var2);
	}

	@Override
	public IEntityLivingData func_180482_a(final DifficultyInstance p_180482_1_, final IEntityLivingData p_180482_2_) {
		Object p_180482_2_1 = super.func_180482_a(p_180482_1_, p_180482_2_);

		if (worldObj.rand.nextInt(100) == 0) {
			final EntitySkeleton var3 = new EntitySkeleton(worldObj);
			var3.setLocationAndAngles(posX, posY, posZ, rotationYaw, 0.0F);
			var3.func_180482_a(p_180482_1_, (IEntityLivingData) null);
			worldObj.spawnEntityInWorld(var3);
			var3.mountEntity(this);
		}

		if (p_180482_2_1 == null) {
			p_180482_2_1 = new EntitySpider.GroupData();

			if (worldObj.getDifficulty() == EnumDifficulty.HARD
					&& worldObj.rand.nextFloat() < 0.1F * p_180482_1_.func_180170_c()) {
				((EntitySpider.GroupData) p_180482_2_1).func_111104_a(worldObj.rand);
			}
		}

		if (p_180482_2_1 instanceof EntitySpider.GroupData) {
			final int var5 = ((EntitySpider.GroupData) p_180482_2_1).field_111105_a;

			if (var5 > 0 && Potion.potionTypes[var5] != null) {
				addPotionEffect(new PotionEffect(var5, Integer.MAX_VALUE));
			}
		}

		return (IEntityLivingData) p_180482_2_1;
	}

	@Override
	public float getEyeHeight() {
		return 0.65F;
	}

	class AISpiderAttack extends EntityAIAttackOnCollide {
		// private static final String __OBFID =
		// "http://https://fuckuskid00002197";

		public AISpiderAttack(final Class p_i45819_2_) {
			super(EntitySpider.this, p_i45819_2_, 1.0D, true);
		}

		@Override
		public boolean continueExecuting() {
			final float var1 = attacker.getBrightness(1.0F);

			if (var1 >= 0.5F && attacker.getRNG().nextInt(100) == 0) {
				attacker.setAttackTarget((EntityLivingBase) null);
				return false;
			} else {
				return super.continueExecuting();
			}
		}

		@Override
		protected double func_179512_a(final EntityLivingBase p_179512_1_) {
			return 4.0F + p_179512_1_.width;
		}
	}

	class AISpiderTarget extends EntityAINearestAttackableTarget {
		// private static final String __OBFID =
		// "http://https://fuckuskid00002196";

		public AISpiderTarget(final Class p_i45818_2_) {
			super(EntitySpider.this, p_i45818_2_, true);
		}

		@Override
		public boolean shouldExecute() {
			final float var1 = taskOwner.getBrightness(1.0F);
			return var1 >= 0.5F ? false : super.shouldExecute();
		}
	}

	public static class GroupData implements IEntityLivingData {
		public int field_111105_a;
		// private static final String __OBFID =
		// "http://https://fuckuskid00001700";

		public void func_111104_a(final Random p_111104_1_) {
			final int var2 = p_111104_1_.nextInt(5);

			if (var2 <= 1) {
				field_111105_a = Potion.moveSpeed.id;
			} else if (var2 <= 2) {
				field_111105_a = Potion.damageBoost.id;
			} else if (var2 <= 3) {
				field_111105_a = Potion.regeneration.id;
			} else if (var2 <= 4) {
				field_111105_a = Potion.invisibility.id;
			}
		}
	}
}
