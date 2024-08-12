package net.minecraft.entity.monster;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSilverfish;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

import java.util.Random;

public class EntitySilverfish extends EntityMob {

public static final int EaZy = 1164;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private EntitySilverfish.AISummonSilverfish field_175460_b;
	// private static final String __OBFID = "http://https://fuckuskid00001696";

	public EntitySilverfish(final World worldIn) {
		super(worldIn);
		setSize(0.4F, 0.3F);
		tasks.addTask(1, new EntityAISwimming(this));
		tasks.addTask(3, field_175460_b = new EntitySilverfish.AISummonSilverfish());
		tasks.addTask(4, new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.0D, false));
		tasks.addTask(5, new EntitySilverfish.AIHideInStone());
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[0]));
		targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
	}

	@Override
	public float getEyeHeight() {
		return 0.1F;
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(8.0D);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
		getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(1.0D);
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
	 * Returns the sound this mob makes while it's alive.
	 */
	@Override
	protected String getLivingSound() {
		return "mob.silverfish.say";
	}

	/**
	 * Returns the sound this mob makes when it is hurt.
	 */
	@Override
	protected String getHurtSound() {
		return "mob.silverfish.hit";
	}

	/**
	 * Returns the sound this mob makes on death.
	 */
	@Override
	protected String getDeathSound() {
		return "mob.silverfish.kill";
	}

	/**
	 * Called when the entity is attacked.
	 */
	@Override
	public boolean attackEntityFrom(final DamageSource source, final float amount) {
		if (func_180431_b(source)) {
			return false;
		} else {
			if (source instanceof EntityDamageSource || source == DamageSource.magic) {
				field_175460_b.func_179462_f();
			}

			return super.attackEntityFrom(source, amount);
		}
	}

	@Override
	protected void func_180429_a(final BlockPos p_180429_1_, final Block p_180429_2_) {
		playSound("mob.silverfish.step", 0.15F, 1.0F);
	}

	@Override
	protected Item getDropItem() {
		return null;
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate() {
		renderYawOffset = rotationYaw;
		super.onUpdate();
	}

	@Override
	public float func_180484_a(final BlockPos p_180484_1_) {
		return worldObj.getBlockState(p_180484_1_.offsetDown()).getBlock() == Blocks.stone ? 10.0F
				: super.func_180484_a(p_180484_1_);
	}

	/**
	 * Checks to make sure the light is not too bright where the mob is spawning
	 */
	@Override
	protected boolean isValidLightLevel() {
		return true;
	}

	/**
	 * Checks if the entity's current position is a valid location to spawn this
	 * entity.
	 */
	@Override
	public boolean getCanSpawnHere() {
		if (super.getCanSpawnHere()) {
			final EntityPlayer var1 = worldObj.getClosestPlayerToEntity(this, 5.0D);
			return var1 == null;
		} else {
			return false;
		}
	}

	/**
	 * Get this Entity's EnumCreatureAttribute
	 */
	@Override
	public EnumCreatureAttribute getCreatureAttribute() {
		return EnumCreatureAttribute.ARTHROPOD;
	}

	class AIHideInStone extends EntityAIWander {
		private EnumFacing field_179483_b;
		private boolean field_179484_c;
		// private static final String __OBFID =
		// "http://https://fuckuskid00002205";

		public AIHideInStone() {
			super(EntitySilverfish.this, 1.0D, 10);
			setMutexBits(1);
		}

		@Override
		public boolean shouldExecute() {
			if (getAttackTarget() != null) {
				return false;
			} else if (!getNavigator().noPath()) {
				return false;
			} else {
				final Random var1 = getRNG();

				if (var1.nextInt(10) == 0) {
					field_179483_b = EnumFacing.random(var1);
					final BlockPos var2 = new BlockPos(EntitySilverfish.this.posX, EntitySilverfish.this.posY + 0.5D,
							EntitySilverfish.this.posZ).offset(field_179483_b);
					final IBlockState var3 = EntitySilverfish.this.worldObj.getBlockState(var2);

					if (BlockSilverfish.func_176377_d(var3)) {
						field_179484_c = true;
						return true;
					}
				}

				field_179484_c = false;
				return super.shouldExecute();
			}
		}

		@Override
		public boolean continueExecuting() {
			return field_179484_c ? false : super.continueExecuting();
		}

		@Override
		public void startExecuting() {
			if (!field_179484_c) {
				super.startExecuting();
			} else {
				final World var1 = EntitySilverfish.this.worldObj;
				final BlockPos var2 = new BlockPos(EntitySilverfish.this.posX, EntitySilverfish.this.posY + 0.5D,
						EntitySilverfish.this.posZ).offset(field_179483_b);
				final IBlockState var3 = var1.getBlockState(var2);

				if (BlockSilverfish.func_176377_d(var3)) {
					var1.setBlockState(var2, Blocks.monster_egg.getDefaultState().withProperty(
							BlockSilverfish.VARIANT_PROP, BlockSilverfish.EnumType.func_176878_a(var3)), 3);
					spawnExplosionParticle();
					setDead();
				}
			}
		}
	}

	class AISummonSilverfish extends EntityAIBase {
		private final EntitySilverfish field_179464_a = EntitySilverfish.this;
		private int field_179463_b;
		// private static final String __OBFID =
		// "http://https://fuckuskid00002204";

		public void func_179462_f() {
			if (field_179463_b == 0) {
				field_179463_b = 20;
			}
		}

		@Override
		public boolean shouldExecute() {
			return field_179463_b > 0;
		}

		@Override
		public void updateTask() {
			--field_179463_b;

			if (field_179463_b <= 0) {
				final World var1 = field_179464_a.worldObj;
				final Random var2 = field_179464_a.getRNG();
				final BlockPos var3 = new BlockPos(field_179464_a);

				for (int var4 = 0; var4 <= 5 && var4 >= -5; var4 = var4 <= 0 ? 1 - var4 : 0 - var4) {
					for (int var5 = 0; var5 <= 10 && var5 >= -10; var5 = var5 <= 0 ? 1 - var5 : 0 - var5) {
						for (int var6 = 0; var6 <= 10 && var6 >= -10; var6 = var6 <= 0 ? 1 - var6 : 0 - var6) {
							final BlockPos var7 = var3.add(var5, var4, var6);
							final IBlockState var8 = var1.getBlockState(var7);

							if (var8.getBlock() == Blocks.monster_egg) {
								if (var1.getGameRules().getGameRuleBooleanValue("mobGriefing")) {
									var1.destroyBlock(var7, true);
								} else {
									var1.setBlockState(var7,
											((BlockSilverfish.EnumType) var8.getValue(BlockSilverfish.VARIANT_PROP))
													.func_176883_d(),
											3);
								}

								if (var2.nextBoolean()) {
									return;
								}
							}
						}
					}
				}
			}
		}
	}
}
