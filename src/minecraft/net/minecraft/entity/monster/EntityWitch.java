package net.minecraft.entity.monster;

import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIArrowAttack;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class EntityWitch extends EntityMob implements IRangedAttackMob {

public static final int EaZy = 1169;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final UUID field_110184_bp = UUID.fromString("5CD17E52-A79A-43D3-A529-90FDE04B181E");
	private static final AttributeModifier field_110185_bq = new AttributeModifier(field_110184_bp,
			"Drinking speed penalty", -0.25D, 0).setSaved(false);

	/** List of items a witch should drop on death. */
	private static final Item[] witchDrops = new Item[] { Items.glowstone_dust, Items.sugar, Items.redstone,
			Items.spider_eye, Items.glass_bottle, Items.gunpowder, Items.stick, Items.stick };

	/**
	 * Timer used as interval for a witch's attack, decremented every tick if
	 * aggressive and when reaches zero the witch will throw a potion at the
	 * target entity.
	 */
	private int witchAttackTimer;
	// private static final String __OBFID = "http://https://fuckuskid00001701";

	public EntityWitch(final World worldIn) {
		super(worldIn);
		setSize(0.6F, 1.95F);
		tasks.addTask(1, new EntityAISwimming(this));
		tasks.addTask(2, new EntityAIArrowAttack(this, 1.0D, 60, 10.0F));
		tasks.addTask(2, new EntityAIWander(this, 1.0D));
		tasks.addTask(2, field_175455_a);
		tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		tasks.addTask(3, new EntityAILookIdle(this));
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
		targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		getDataWatcher().addObject(21, (byte) 0);
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
	 * Set whether this witch is aggressive at an entity.
	 */
	public void setAggressive(final boolean p_82197_1_) {
		getDataWatcher().updateObject(21, (byte) (p_82197_1_ ? 1 : 0));
	}

	/**
	 * Return whether this witch is aggressive at an entity.
	 */
	public boolean getAggressive() {
		return getDataWatcher().getWatchableObjectByte(21) == 1;
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(26.0D);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
	}

	/**
	 * Called frequently so the entity can update its state every tick as
	 * required. For example, zombies and skeletons use this to react to
	 * sunlight and start to burn.
	 */
	@Override
	public void onLivingUpdate() {
		if (!worldObj.isRemote) {
			if (getAggressive()) {
				if (witchAttackTimer-- <= 0) {
					setAggressive(false);
					final ItemStack var1 = getHeldItem();
					setCurrentItemOrArmor(0, (ItemStack) null);

					if (var1 != null && var1.getItem() == Items.potionitem) {
						final List var2 = Items.potionitem.getEffects(var1);

						if (var2 != null) {
							final Iterator var3 = var2.iterator();

							while (var3.hasNext()) {
								final PotionEffect var4 = (PotionEffect) var3.next();
								addPotionEffect(new PotionEffect(var4));
							}
						}
					}

					getEntityAttribute(SharedMonsterAttributes.movementSpeed).removeModifier(field_110185_bq);
				}
			} else {
				short var5 = -1;

				if (rand.nextFloat() < 0.15F && isInsideOfMaterial(Material.water)
						&& !this.isPotionActive(Potion.waterBreathing)) {
					var5 = 8237;
				} else if (rand.nextFloat() < 0.15F && isBurning() && !this.isPotionActive(Potion.fireResistance)) {
					var5 = 16307;
				} else if (rand.nextFloat() < 0.05F && getHealth() < getMaxHealth()) {
					var5 = 16341;
				} else if (rand.nextFloat() < 0.25F && getAttackTarget() != null
						&& !this.isPotionActive(Potion.moveSpeed)
						&& getAttackTarget().getDistanceSqToEntity(this) > 121.0D) {
					var5 = 16274;
				} else if (rand.nextFloat() < 0.25F && getAttackTarget() != null
						&& !this.isPotionActive(Potion.moveSpeed)
						&& getAttackTarget().getDistanceSqToEntity(this) > 121.0D) {
					var5 = 16274;
				}

				if (var5 > -1) {
					setCurrentItemOrArmor(0, new ItemStack(Items.potionitem, 1, var5));
					witchAttackTimer = getHeldItem().getMaxItemUseDuration();
					setAggressive(true);
					final IAttributeInstance var6 = getEntityAttribute(SharedMonsterAttributes.movementSpeed);
					var6.removeModifier(field_110185_bq);
					var6.applyModifier(field_110185_bq);
				}
			}

			if (rand.nextFloat() < 7.5E-4F) {
				worldObj.setEntityState(this, (byte) 15);
			}
		}

		super.onLivingUpdate();
	}

	@Override
	public void handleHealthUpdate(final byte p_70103_1_) {
		if (p_70103_1_ == 15) {
			for (int var2 = 0; var2 < rand.nextInt(35) + 10; ++var2) {
				worldObj.spawnParticle(EnumParticleTypes.SPELL_WITCH, posX + rand.nextGaussian() * 0.12999999523162842D,
						getEntityBoundingBox().maxY + 0.5D + rand.nextGaussian() * 0.12999999523162842D,
						posZ + rand.nextGaussian() * 0.12999999523162842D, 0.0D, 0.0D, 0.0D, new int[0]);
			}
		} else {
			super.handleHealthUpdate(p_70103_1_);
		}
	}

	/**
	 * Reduces damage, depending on potions
	 */
	@Override
	protected float applyPotionDamageCalculations(final DamageSource p_70672_1_, float p_70672_2_) {
		p_70672_2_ = super.applyPotionDamageCalculations(p_70672_1_, p_70672_2_);

		if (p_70672_1_.getEntity() == this) {
			p_70672_2_ = 0.0F;
		}

		if (p_70672_1_.isMagicDamage()) {
			p_70672_2_ = (float) (p_70672_2_ * 0.15D);
		}

		return p_70672_2_;
	}

	/**
	 * Drop 0-2 items of this living's type
	 */
	@Override
	protected void dropFewItems(final boolean p_70628_1_, final int p_70628_2_) {
		final int var3 = rand.nextInt(3) + 1;

		for (int var4 = 0; var4 < var3; ++var4) {
			int var5 = rand.nextInt(3);
			final Item var6 = witchDrops[rand.nextInt(witchDrops.length)];

			if (p_70628_2_ > 0) {
				var5 += rand.nextInt(p_70628_2_ + 1);
			}

			for (int var7 = 0; var7 < var5; ++var7) {
				dropItem(var6, 1);
			}
		}
	}

	/**
	 * Attack the specified entity using a ranged attack.
	 */
	@Override
	public void attackEntityWithRangedAttack(final EntityLivingBase p_82196_1_, final float p_82196_2_) {
		if (!getAggressive()) {
			final EntityPotion var3 = new EntityPotion(worldObj, this, 32732);
			final double var4 = p_82196_1_.posY + p_82196_1_.getEyeHeight() - 1.100000023841858D;
			var3.rotationPitch -= -20.0F;
			final double var6 = p_82196_1_.posX + p_82196_1_.motionX - posX;
			final double var8 = var4 - posY;
			final double var10 = p_82196_1_.posZ + p_82196_1_.motionZ - posZ;
			final float var12 = MathHelper.sqrt_double(var6 * var6 + var10 * var10);

			if (var12 >= 8.0F && !p_82196_1_.isPotionActive(Potion.moveSlowdown)) {
				var3.setPotionDamage(32698);
			} else if (p_82196_1_.getHealth() >= 8.0F && !p_82196_1_.isPotionActive(Potion.poison)) {
				var3.setPotionDamage(32660);
			} else if (var12 <= 3.0F && !p_82196_1_.isPotionActive(Potion.weakness) && rand.nextFloat() < 0.25F) {
				var3.setPotionDamage(32696);
			}

			var3.setThrowableHeading(var6, var8 + var12 * 0.2F, var10, 0.75F, 8.0F);
			worldObj.spawnEntityInWorld(var3);
		}
	}

	@Override
	public float getEyeHeight() {
		return 1.62F;
	}
}
