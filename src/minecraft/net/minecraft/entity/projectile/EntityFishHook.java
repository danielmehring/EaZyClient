package net.minecraft.entity.projectile;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemFishFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.WeightedRandomFishable;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import java.util.Arrays;
import java.util.List;

public class EntityFishHook extends Entity {

public static final int EaZy = 1199;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final List JUNK = Arrays.asList(new WeightedRandomFishable[] {
			new WeightedRandomFishable(new ItemStack(Items.leather_boots), 10).setMaxDamagePercent(0.9F),
			new WeightedRandomFishable(new ItemStack(Items.leather), 10),
			new WeightedRandomFishable(new ItemStack(Items.bone), 10),
			new WeightedRandomFishable(new ItemStack(Items.potionitem), 10),
			new WeightedRandomFishable(new ItemStack(Items.string), 5),
			new WeightedRandomFishable(new ItemStack(Items.fishing_rod), 2).setMaxDamagePercent(0.9F),
			new WeightedRandomFishable(new ItemStack(Items.bowl), 10),
			new WeightedRandomFishable(new ItemStack(Items.stick), 5),
			new WeightedRandomFishable(new ItemStack(Items.dye, 10, EnumDyeColor.BLACK.getDyeColorDamage()), 1),
			new WeightedRandomFishable(new ItemStack(Blocks.tripwire_hook), 10),
			new WeightedRandomFishable(new ItemStack(Items.rotten_flesh), 10) });
	private static final List VALUABLES = Arrays
			.asList(new WeightedRandomFishable[] { new WeightedRandomFishable(new ItemStack(Blocks.waterlily), 1),
					new WeightedRandomFishable(new ItemStack(Items.name_tag), 1),
					new WeightedRandomFishable(new ItemStack(Items.saddle), 1),
					new WeightedRandomFishable(new ItemStack(Items.bow), 1).setMaxDamagePercent(0.25F).setEnchantable(),
					new WeightedRandomFishable(new ItemStack(Items.fishing_rod), 1).setMaxDamagePercent(0.25F)
							.setEnchantable(),
					new WeightedRandomFishable(new ItemStack(Items.book), 1).setEnchantable() });
	private static final List FISH = Arrays.asList(new WeightedRandomFishable[] {
			new WeightedRandomFishable(new ItemStack(Items.fish, 1, ItemFishFood.FishType.COD.getItemDamage()), 60),
			new WeightedRandomFishable(new ItemStack(Items.fish, 1, ItemFishFood.FishType.SALMON.getItemDamage()), 25),
			new WeightedRandomFishable(new ItemStack(Items.fish, 1, ItemFishFood.FishType.CLOWNFISH.getItemDamage()),
					2),
			new WeightedRandomFishable(new ItemStack(Items.fish, 1, ItemFishFood.FishType.PUFFERFISH.getItemDamage()),
					13) });
	private int xTile;
	private int yTile;
	private int zTile;
	private Block inTile;
	private boolean inGround;
	public int shake;
	public EntityPlayer angler;
	private int ticksInGround;
	private int ticksInAir;
	private int ticksCatchable;
	private int ticksCaughtDelay;
	private int ticksCatchableDelay;
	private float fishApproachAngle;
	public Entity caughtEntity;
	private int fishPosRotationIncrements;
	private double fishX;
	private double fishY;
	private double fishZ;
	private double fishYaw;
	private double fishPitch;
	private double clientMotionX;
	private double clientMotionY;
	private double clientMotionZ;
	// private static final String __OBFID = "http://https://fuckuskid00001663";

	public static List func_174855_j() {
		return FISH;
	}

	public EntityFishHook(final World worldIn) {
		super(worldIn);
		xTile = -1;
		yTile = -1;
		zTile = -1;
		setSize(0.25F, 0.25F);
		ignoreFrustumCheck = true;
	}

	public EntityFishHook(final World worldIn, final double p_i1765_2_, final double p_i1765_4_,
			final double p_i1765_6_, final EntityPlayer p_i1765_8_) {
		this(worldIn);
		setPosition(p_i1765_2_, p_i1765_4_, p_i1765_6_);
		ignoreFrustumCheck = true;
		angler = p_i1765_8_;
		p_i1765_8_.fishEntity = this;
	}

	public EntityFishHook(final World worldIn, final EntityPlayer p_i1766_2_) {
		super(worldIn);
		xTile = -1;
		yTile = -1;
		zTile = -1;
		ignoreFrustumCheck = true;
		angler = p_i1766_2_;
		angler.fishEntity = this;
		setSize(0.25F, 0.25F);
		setLocationAndAngles(p_i1766_2_.posX, p_i1766_2_.posY + p_i1766_2_.getEyeHeight(), p_i1766_2_.posZ,
				p_i1766_2_.rotationYaw, p_i1766_2_.rotationPitch);
		posX -= MathHelper.cos(rotationYaw / 180.0F * (float) Math.PI) * 0.16F;
		posY -= 0.10000000149011612D;
		posZ -= MathHelper.sin(rotationYaw / 180.0F * (float) Math.PI) * 0.16F;
		setPosition(posX, posY, posZ);
		final float var3 = 0.4F;
		motionX = -MathHelper.sin(rotationYaw / 180.0F * (float) Math.PI)
				* MathHelper.cos(rotationPitch / 180.0F * (float) Math.PI) * var3;
		motionZ = MathHelper.cos(rotationYaw / 180.0F * (float) Math.PI)
				* MathHelper.cos(rotationPitch / 180.0F * (float) Math.PI) * var3;
		motionY = -MathHelper.sin(rotationPitch / 180.0F * (float) Math.PI) * var3;
		handleHookCasting(motionX, motionY, motionZ, 1.5F, 1.0F);
	}

	@Override
	protected void entityInit() {}

	/**
	 * Checks if the entity is in range to render by using the past in distance
	 * and comparing it to its average edge length * 64 * renderDistanceWeight
	 * Args: distance
	 */
	@Override
	public boolean isInRangeToRenderDist(final double distance) {
		double var3 = getEntityBoundingBox().getAverageEdgeLength() * 4.0D;
		var3 *= 64.0D;
		return distance < var3 * var3;
	}

	public void handleHookCasting(double p_146035_1_, double p_146035_3_, double p_146035_5_, final float p_146035_7_,
			final float p_146035_8_) {
		final float var9 = MathHelper
				.sqrt_double(p_146035_1_ * p_146035_1_ + p_146035_3_ * p_146035_3_ + p_146035_5_ * p_146035_5_);
		p_146035_1_ /= var9;
		p_146035_3_ /= var9;
		p_146035_5_ /= var9;
		p_146035_1_ += rand.nextGaussian() * 0.007499999832361937D * p_146035_8_;
		p_146035_3_ += rand.nextGaussian() * 0.007499999832361937D * p_146035_8_;
		p_146035_5_ += rand.nextGaussian() * 0.007499999832361937D * p_146035_8_;
		p_146035_1_ *= p_146035_7_;
		p_146035_3_ *= p_146035_7_;
		p_146035_5_ *= p_146035_7_;
		motionX = p_146035_1_;
		motionY = p_146035_3_;
		motionZ = p_146035_5_;
		final float var10 = MathHelper.sqrt_double(p_146035_1_ * p_146035_1_ + p_146035_5_ * p_146035_5_);
		prevRotationYaw = rotationYaw = (float) (Math.atan2(p_146035_1_, p_146035_5_) * 180.0D / Math.PI);
		prevRotationPitch = rotationPitch = (float) (Math.atan2(p_146035_3_, var10) * 180.0D / Math.PI);
		ticksInGround = 0;
	}

	@Override
	public void func_180426_a(final double p_180426_1_, final double p_180426_3_, final double p_180426_5_,
			final float p_180426_7_, final float p_180426_8_, final int p_180426_9_, final boolean p_180426_10_) {
		fishX = p_180426_1_;
		fishY = p_180426_3_;
		fishZ = p_180426_5_;
		fishYaw = p_180426_7_;
		fishPitch = p_180426_8_;
		fishPosRotationIncrements = p_180426_9_;
		motionX = clientMotionX;
		motionY = clientMotionY;
		motionZ = clientMotionZ;
	}

	/**
	 * Sets the velocity to the args. Args: x, y, z
	 */
	@Override
	public void setVelocity(final double x, final double y, final double z) {
		clientMotionX = motionX = x;
		clientMotionY = motionY = y;
		clientMotionZ = motionZ = z;
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate() {
		super.onUpdate();

		if (fishPosRotationIncrements > 0) {
			final double var28 = posX + (fishX - posX) / fishPosRotationIncrements;
			final double var29 = posY + (fishY - posY) / fishPosRotationIncrements;
			final double var30 = posZ + (fishZ - posZ) / fishPosRotationIncrements;
			final double var7 = MathHelper.wrapAngleTo180_double(fishYaw - rotationYaw);
			rotationYaw = (float) (rotationYaw + var7 / fishPosRotationIncrements);
			rotationPitch = (float) (rotationPitch + (fishPitch - rotationPitch) / fishPosRotationIncrements);
			--fishPosRotationIncrements;
			setPosition(var28, var29, var30);
			setRotation(rotationYaw, rotationPitch);
		} else {
			if (!worldObj.isRemote) {
				final ItemStack var1 = angler.getCurrentEquippedItem();

				if (angler.isDead || !angler.isEntityAlive() || var1 == null || var1.getItem() != Items.fishing_rod
						|| getDistanceSqToEntity(angler) > 1024.0D) {
					setDead();
					angler.fishEntity = null;
					return;
				}

				if (caughtEntity != null) {
					if (!caughtEntity.isDead) {
						posX = caughtEntity.posX;
						final double var10002 = caughtEntity.height;
						posY = caughtEntity.getEntityBoundingBox().minY + var10002 * 0.8D;
						posZ = caughtEntity.posZ;
						return;
					}

					caughtEntity = null;
				}
			}

			if (shake > 0) {
				--shake;
			}

			if (inGround) {
				if (worldObj.getBlockState(new BlockPos(xTile, yTile, zTile)).getBlock() == inTile) {
					++ticksInGround;

					if (ticksInGround == 1200) {
						setDead();
					}

					return;
				}

				inGround = false;
				motionX *= rand.nextFloat() * 0.2F;
				motionY *= rand.nextFloat() * 0.2F;
				motionZ *= rand.nextFloat() * 0.2F;
				ticksInGround = 0;
				ticksInAir = 0;
			} else {
				++ticksInAir;
			}

			Vec3 var27 = new Vec3(posX, posY, posZ);
			Vec3 var2 = new Vec3(posX + motionX, posY + motionY, posZ + motionZ);
			MovingObjectPosition var3 = worldObj.rayTraceBlocks(var27, var2);
			var27 = new Vec3(posX, posY, posZ);
			var2 = new Vec3(posX + motionX, posY + motionY, posZ + motionZ);

			if (var3 != null) {
				var2 = new Vec3(var3.hitVec.xCoord, var3.hitVec.yCoord, var3.hitVec.zCoord);
			}

			Entity var4 = null;
			final List var5 = worldObj.getEntitiesWithinAABBExcludingEntity(this,
					getEntityBoundingBox().addCoord(motionX, motionY, motionZ).expand(1.0D, 1.0D, 1.0D));
			double var6 = 0.0D;
			double var13;

			for (int var8 = 0; var8 < var5.size(); ++var8) {
				final Entity var9 = (Entity) var5.get(var8);

				if (var9.canBeCollidedWith() && (var9 != angler || ticksInAir >= 5)) {
					final float var10 = 0.3F;
					final AxisAlignedBB var11 = var9.getEntityBoundingBox().expand(var10, var10, var10);
					final MovingObjectPosition var12 = var11.calculateIntercept(var27, var2);

					if (var12 != null) {
						var13 = var27.distanceTo(var12.hitVec);

						if (var13 < var6 || var6 == 0.0D) {
							var4 = var9;
							var6 = var13;
						}
					}
				}
			}

			if (var4 != null) {
				var3 = new MovingObjectPosition(var4);
			}

			if (var3 != null) {
				if (var3.entityHit != null) {
					if (var3.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, angler), 0.0F)) {
						caughtEntity = var3.entityHit;
					}
				} else {
					inGround = true;
				}
			}

			if (!inGround) {
				moveEntity(motionX, motionY, motionZ);
				final float var31 = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
				rotationYaw = (float) (Math.atan2(motionX, motionZ) * 180.0D / Math.PI);

				for (rotationPitch = (float) (Math.atan2(motionY, var31) * 180.0D / Math.PI); rotationPitch
						- prevRotationPitch < -180.0F; prevRotationPitch -= 360.0F) {
				}

				while (rotationPitch - prevRotationPitch >= 180.0F) {
					prevRotationPitch += 360.0F;
				}

				while (rotationYaw - prevRotationYaw < -180.0F) {
					prevRotationYaw -= 360.0F;
				}

				while (rotationYaw - prevRotationYaw >= 180.0F) {
					prevRotationYaw += 360.0F;
				}

				rotationPitch = prevRotationPitch + (rotationPitch - prevRotationPitch) * 0.2F;
				rotationYaw = prevRotationYaw + (rotationYaw - prevRotationYaw) * 0.2F;
				float var32 = 0.92F;

				if (onGround || isCollidedHorizontally) {
					var32 = 0.5F;
				}

				final byte var33 = 5;
				double var34 = 0.0D;
				double var19;

				for (int var35 = 0; var35 < var33; ++var35) {
					final AxisAlignedBB var14 = getEntityBoundingBox();
					final double var15 = var14.maxY - var14.minY;
					final double var17 = var14.minY + var15 * var35 / var33;
					var19 = var14.minY + var15 * (var35 + 1) / var33;
					final AxisAlignedBB var21 = new AxisAlignedBB(var14.minX, var17, var14.minZ, var14.maxX, var19,
							var14.maxZ);

					if (worldObj.isAABBInMaterial(var21, Material.water)) {
						var34 += 1.0D / var33;
					}
				}

				if (!worldObj.isRemote && var34 > 0.0D) {
					final WorldServer var36 = (WorldServer) worldObj;
					int var37 = 1;
					final BlockPos var38 = new BlockPos(this).offsetUp();

					if (rand.nextFloat() < 0.25F && worldObj.func_175727_C(var38)) {
						var37 = 2;
					}

					if (rand.nextFloat() < 0.5F && !worldObj.isAgainstSky(var38)) {
						--var37;
					}

					if (ticksCatchable > 0) {
						--ticksCatchable;

						if (ticksCatchable <= 0) {
							ticksCaughtDelay = 0;
							ticksCatchableDelay = 0;
						}
					} else {
						float var16;
						float var18;
						double var23;
						float var39;
						double var40;

						if (ticksCatchableDelay > 0) {
							ticksCatchableDelay -= var37;

							if (ticksCatchableDelay <= 0) {
								motionY -= 0.20000000298023224D;
								playSound("random.splash", 0.25F, 1.0F + (rand.nextFloat() - rand.nextFloat()) * 0.4F);
								var16 = MathHelper.floor_double(getEntityBoundingBox().minY);
								var36.func_175739_a(EnumParticleTypes.WATER_BUBBLE, posX, var16 + 1.0F, posZ,
										(int) (1.0F + width * 20.0F), width, 0.0D, width, 0.20000000298023224D,
										new int[0]);
								var36.func_175739_a(EnumParticleTypes.WATER_WAKE, posX, var16 + 1.0F, posZ,
										(int) (1.0F + width * 20.0F), width, 0.0D, width, 0.20000000298023224D,
										new int[0]);
								ticksCatchable = MathHelper.getRandomIntegerInRange(rand, 10, 30);
							} else {
								fishApproachAngle = (float) (fishApproachAngle + rand.nextGaussian() * 4.0D);
								var16 = fishApproachAngle * 0.017453292F;
								var39 = MathHelper.sin(var16);
								var18 = MathHelper.cos(var16);
								var19 = posX + var39 * ticksCatchableDelay * 0.1F;
								var40 = MathHelper.floor_double(getEntityBoundingBox().minY) + 1.0F;
								var23 = posZ + var18 * ticksCatchableDelay * 0.1F;

								if (rand.nextFloat() < 0.15F) {
									var36.func_175739_a(EnumParticleTypes.WATER_BUBBLE, var19,
											var40 - 0.10000000149011612D, var23, 1, var39, 0.1D, var18, 0.0D,
											new int[0]);
								}

								final float var25 = var39 * 0.04F;
								final float var26 = var18 * 0.04F;
								var36.func_175739_a(EnumParticleTypes.WATER_WAKE, var19, var40, var23, 0, var26, 0.01D,
										-var25, 1.0D, new int[0]);
								var36.func_175739_a(EnumParticleTypes.WATER_WAKE, var19, var40, var23, 0, -var26, 0.01D,
										var25, 1.0D, new int[0]);
							}
						} else if (ticksCaughtDelay > 0) {
							ticksCaughtDelay -= var37;
							var16 = 0.15F;

							if (ticksCaughtDelay < 20) {
								var16 = (float) (var16 + (20 - ticksCaughtDelay) * 0.05D);
							} else if (ticksCaughtDelay < 40) {
								var16 = (float) (var16 + (40 - ticksCaughtDelay) * 0.02D);
							} else if (ticksCaughtDelay < 60) {
								var16 = (float) (var16 + (60 - ticksCaughtDelay) * 0.01D);
							}

							if (rand.nextFloat() < var16) {
								var39 = MathHelper.randomFloatClamp(rand, 0.0F, 360.0F) * 0.017453292F;
								var18 = MathHelper.randomFloatClamp(rand, 25.0F, 60.0F);
								var19 = posX + MathHelper.sin(var39) * var18 * 0.1F;
								var40 = MathHelper.floor_double(getEntityBoundingBox().minY) + 1.0F;
								var23 = posZ + MathHelper.cos(var39) * var18 * 0.1F;
								var36.func_175739_a(EnumParticleTypes.WATER_SPLASH, var19, var40, var23,
										2 + rand.nextInt(2), 0.10000000149011612D, 0.0D, 0.10000000149011612D, 0.0D,
										new int[0]);
							}

							if (ticksCaughtDelay <= 0) {
								fishApproachAngle = MathHelper.randomFloatClamp(rand, 0.0F, 360.0F);
								ticksCatchableDelay = MathHelper.getRandomIntegerInRange(rand, 20, 80);
							}
						} else {
							ticksCaughtDelay = MathHelper.getRandomIntegerInRange(rand, 100, 900);
							ticksCaughtDelay -= EnchantmentHelper.func_151387_h(angler) * 20 * 5;
						}
					}

					if (ticksCatchable > 0) {
						motionY -= rand.nextFloat() * rand.nextFloat() * rand.nextFloat() * 0.2D;
					}
				}

				var13 = var34 * 2.0D - 1.0D;
				motionY += 0.03999999910593033D * var13;

				if (var34 > 0.0D) {
					var32 = (float) (var32 * 0.9D);
					motionY *= 0.8D;
				}

				motionX *= var32;
				motionY *= var32;
				motionZ *= var32;
				setPosition(posX, posY, posZ);
			}
		}
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	public void writeEntityToNBT(final NBTTagCompound tagCompound) {
		tagCompound.setShort("xTile", (short) xTile);
		tagCompound.setShort("yTile", (short) yTile);
		tagCompound.setShort("zTile", (short) zTile);
		final ResourceLocation var2 = (ResourceLocation) Block.blockRegistry.getNameForObject(inTile);
		tagCompound.setString("inTile", var2 == null ? "" : var2.toString());
		tagCompound.setByte("shake", (byte) shake);
		tagCompound.setByte("inGround", (byte) (inGround ? 1 : 0));
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readEntityFromNBT(final NBTTagCompound tagCompund) {
		xTile = tagCompund.getShort("xTile");
		yTile = tagCompund.getShort("yTile");
		zTile = tagCompund.getShort("zTile");

		if (tagCompund.hasKey("inTile", 8)) {
			inTile = Block.getBlockFromName(tagCompund.getString("inTile"));
		} else {
			inTile = Block.getBlockById(tagCompund.getByte("inTile") & 255);
		}

		shake = tagCompund.getByte("shake") & 255;
		inGround = tagCompund.getByte("inGround") == 1;
	}

	public int handleHookRetraction() {
		if (worldObj.isRemote) {
			return 0;
		} else {
			byte var1 = 0;

			if (caughtEntity != null) {
				final double var2 = angler.posX - posX;
				final double var4 = angler.posY - posY;
				final double var6 = angler.posZ - posZ;
				final double var8 = MathHelper.sqrt_double(var2 * var2 + var4 * var4 + var6 * var6);
				final double var10 = 0.1D;
				caughtEntity.motionX += var2 * var10;
				caughtEntity.motionY += var4 * var10 + MathHelper.sqrt_double(var8) * 0.08D;
				caughtEntity.motionZ += var6 * var10;
				var1 = 3;
			} else if (ticksCatchable > 0) {
				final EntityItem var13 = new EntityItem(worldObj, posX, posY, posZ, func_146033_f());
				final double var3 = angler.posX - posX;
				final double var5 = angler.posY - posY;
				final double var7 = angler.posZ - posZ;
				final double var9 = MathHelper.sqrt_double(var3 * var3 + var5 * var5 + var7 * var7);
				final double var11 = 0.1D;
				var13.motionX = var3 * var11;
				var13.motionY = var5 * var11 + MathHelper.sqrt_double(var9) * 0.08D;
				var13.motionZ = var7 * var11;
				worldObj.spawnEntityInWorld(var13);
				angler.worldObj.spawnEntityInWorld(new EntityXPOrb(angler.worldObj, angler.posX, angler.posY + 0.5D,
						angler.posZ + 0.5D, rand.nextInt(6) + 1));
				var1 = 1;
			}

			if (inGround) {
				var1 = 2;
			}

			setDead();
			angler.fishEntity = null;
			return var1;
		}
	}

	private ItemStack func_146033_f() {
		float var1 = worldObj.rand.nextFloat();
		final int var2 = EnchantmentHelper.func_151386_g(angler);
		final int var3 = EnchantmentHelper.func_151387_h(angler);
		float var4 = 0.1F - var2 * 0.025F - var3 * 0.01F;
		float var5 = 0.05F + var2 * 0.01F - var3 * 0.01F;
		var4 = MathHelper.clamp_float(var4, 0.0F, 1.0F);
		var5 = MathHelper.clamp_float(var5, 0.0F, 1.0F);

		if (var1 < var4) {
			angler.triggerAchievement(StatList.junkFishedStat);
			return ((WeightedRandomFishable) WeightedRandom.getRandomItem(rand, JUNK)).getItemStack(rand);
		} else {
			var1 -= var4;

			if (var1 < var5) {
				angler.triggerAchievement(StatList.treasureFishedStat);
				return ((WeightedRandomFishable) WeightedRandom.getRandomItem(rand, VALUABLES)).getItemStack(rand);
			} else {
				angler.triggerAchievement(StatList.fishCaughtStat);
				return ((WeightedRandomFishable) WeightedRandom.getRandomItem(rand, FISH)).getItemStack(rand);
			}
		}
	}

	/**
	 * Will get destroyed next tick.
	 */
	@Override
	public void setDead() {
		super.setDead();

		if (angler != null) {
			angler.fishEntity = null;
		}
	}
}
