package net.minecraft.entity.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.BlockRailPowered;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityMinecartCommandBlock;
import net.minecraft.entity.ai.EntityMinecartMobSpawner;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.IWorldNameable;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import java.util.Iterator;
import java.util.Map;

import com.google.common.collect.Maps;

public abstract class EntityMinecart extends Entity implements IWorldNameable {

public static final int EaZy = 1141;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private boolean isInReverse;
	private String entityName;

	/** Minecart rotational logic matrix */
	private static final int[][][] matrix = new int[][][] { { { 0, 0, -1 }, { 0, 0, 1 } },
			{ { -1, 0, 0 }, { 1, 0, 0 } }, { { -1, -1, 0 }, { 1, 0, 0 } }, { { -1, 0, 0 }, { 1, -1, 0 } },
			{ { 0, 0, -1 }, { 0, -1, 1 } }, { { 0, -1, -1 }, { 0, 0, 1 } }, { { 0, 0, 1 }, { 1, 0, 0 } },
			{ { 0, 0, 1 }, { -1, 0, 0 } }, { { 0, 0, -1 }, { -1, 0, 0 } }, { { 0, 0, -1 }, { 1, 0, 0 } } };

	/** appears to be the progress of the turn */
	private int turnProgress;
	private double minecartX;
	private double minecartY;
	private double minecartZ;
	private double minecartYaw;
	private double minecartPitch;
	private double velocityX;
	private double velocityY;
	private double velocityZ;
	// private static final String __OBFID = "http://https://fuckuskid00001670";

	public EntityMinecart(final World worldIn) {
		super(worldIn);
		preventEntitySpawning = true;
		setSize(0.98F, 0.7F);
	}

	public static EntityMinecart func_180458_a(final World worldIn, final double p_180458_1_, final double p_180458_3_,
			final double p_180458_5_, final EntityMinecart.EnumMinecartType p_180458_7_) {
		switch (EntityMinecart.SwitchEnumMinecartType.field_180037_a[p_180458_7_.ordinal()]) {
			case 1:
				return new EntityMinecartChest(worldIn, p_180458_1_, p_180458_3_, p_180458_5_);

			case 2:
				return new EntityMinecartFurnace(worldIn, p_180458_1_, p_180458_3_, p_180458_5_);

			case 3:
				return new EntityMinecartTNT(worldIn, p_180458_1_, p_180458_3_, p_180458_5_);

			case 4:
				return new EntityMinecartMobSpawner(worldIn, p_180458_1_, p_180458_3_, p_180458_5_);

			case 5:
				return new EntityMinecartHopper(worldIn, p_180458_1_, p_180458_3_, p_180458_5_);

			case 6:
				return new EntityMinecartCommandBlock(worldIn, p_180458_1_, p_180458_3_, p_180458_5_);

			default:
				return new EntityMinecartEmpty(worldIn, p_180458_1_, p_180458_3_, p_180458_5_);
		}
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
	protected void entityInit() {
		dataWatcher.addObject(17, 0);
		dataWatcher.addObject(18, 1);
		dataWatcher.addObject(19, 0.0F);
		dataWatcher.addObject(20, 0);
		dataWatcher.addObject(21, 6);
		dataWatcher.addObject(22, (byte) 0);
	}

	/**
	 * Returns a boundingBox used to collide the entity with other entities and
	 * blocks. This enables the entity to be pushable on contact, like boats or
	 * minecarts.
	 */
	@Override
	public AxisAlignedBB getCollisionBox(final Entity entityIn) {
		return entityIn.canBePushed() ? entityIn.getEntityBoundingBox() : null;
	}

	/**
	 * returns the bounding box for this entity
	 */
	@Override
	public AxisAlignedBB getBoundingBox() {
		return null;
	}

	/**
	 * Returns true if this entity should push and be pushed by other entities
	 * when colliding.
	 */
	@Override
	public boolean canBePushed() {
		return true;
	}

	public EntityMinecart(final World worldIn, final double p_i1713_2_, final double p_i1713_4_,
			final double p_i1713_6_) {
		this(worldIn);
		setPosition(p_i1713_2_, p_i1713_4_, p_i1713_6_);
		motionX = 0.0D;
		motionY = 0.0D;
		motionZ = 0.0D;
		prevPosX = p_i1713_2_;
		prevPosY = p_i1713_4_;
		prevPosZ = p_i1713_6_;
	}

	/**
	 * Returns the Y offset from the entity's position for any entity riding
	 * this one.
	 */
	@Override
	public double getMountedYOffset() {
		return height * 0.5D - 0.20000000298023224D;
	}

	/**
	 * Called when the entity is attacked.
	 */
	@Override
	public boolean attackEntityFrom(final DamageSource source, final float amount) {
		if (!worldObj.isRemote && !isDead) {
			if (func_180431_b(source)) {
				return false;
			} else {
				setRollingDirection(-getRollingDirection());
				setRollingAmplitude(10);
				setBeenAttacked();
				setDamage(getDamage() + amount * 10.0F);
				final boolean var3 = source.getEntity() instanceof EntityPlayer
						&& ((EntityPlayer) source.getEntity()).capabilities.isCreativeMode;

				if (var3 || getDamage() > 40.0F) {
					if (riddenByEntity != null) {
						riddenByEntity.mountEntity((Entity) null);
					}

					if (var3 && !hasCustomName()) {
						setDead();
					} else {
						killMinecart(source);
					}
				}

				return true;
			}
		} else {
			return true;
		}
	}

	public void killMinecart(final DamageSource p_94095_1_) {
		setDead();
		final ItemStack var2 = new ItemStack(Items.minecart, 1);

		if (entityName != null) {
			var2.setStackDisplayName(entityName);
		}

		entityDropItem(var2, 0.0F);
	}

	/**
	 * Setups the entity to do the hurt animation. Only used by packets in
	 * multiplayer.
	 */
	@Override
	public void performHurtAnimation() {
		setRollingDirection(-getRollingDirection());
		setRollingAmplitude(10);
		setDamage(getDamage() + getDamage() * 10.0F);
	}

	/**
	 * Returns true if other Entities should be prevented from moving through
	 * this Entity.
	 */
	@Override
	public boolean canBeCollidedWith() {
		return !isDead;
	}

	/**
	 * Will get destroyed next tick.
	 */
	@Override
	public void setDead() {
		super.setDead();
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate() {
		if (getRollingAmplitude() > 0) {
			setRollingAmplitude(getRollingAmplitude() - 1);
		}

		if (getDamage() > 0.0F) {
			setDamage(getDamage() - 1.0F);
		}

		if (posY < -64.0D) {
			kill();
		}

		int var2;

		if (!worldObj.isRemote && worldObj instanceof WorldServer) {
			worldObj.theProfiler.startSection("portal");
			final MinecraftServer var1 = ((WorldServer) worldObj).func_73046_m();
			var2 = getMaxInPortalTime();

			if (inPortal) {
				if (var1.getAllowNether()) {
					if (ridingEntity == null && portalCounter++ >= var2) {
						portalCounter = var2;
						timeUntilPortal = getPortalCooldown();
						byte var3;

						if (worldObj.provider.getDimensionId() == -1) {
							var3 = 0;
						} else {
							var3 = -1;
						}

						travelToDimension(var3);
					}

					inPortal = false;
				}
			} else {
				if (portalCounter > 0) {
					portalCounter -= 4;
				}

				if (portalCounter < 0) {
					portalCounter = 0;
				}
			}

			if (timeUntilPortal > 0) {
				--timeUntilPortal;
			}

			worldObj.theProfiler.endSection();
		}

		if (worldObj.isRemote) {
			if (turnProgress > 0) {
				final double var15 = posX + (minecartX - posX) / turnProgress;
				final double var17 = posY + (minecartY - posY) / turnProgress;
				final double var18 = posZ + (minecartZ - posZ) / turnProgress;
				final double var7 = MathHelper.wrapAngleTo180_double(minecartYaw - rotationYaw);
				rotationYaw = (float) (rotationYaw + var7 / turnProgress);
				rotationPitch = (float) (rotationPitch + (minecartPitch - rotationPitch) / turnProgress);
				--turnProgress;
				setPosition(var15, var17, var18);
				setRotation(rotationYaw, rotationPitch);
			} else {
				setPosition(posX, posY, posZ);
				setRotation(rotationYaw, rotationPitch);
			}
		} else {
			prevPosX = posX;
			prevPosY = posY;
			prevPosZ = posZ;
			motionY -= 0.03999999910593033D;
			final int var14 = MathHelper.floor_double(posX);
			var2 = MathHelper.floor_double(posY);
			final int var16 = MathHelper.floor_double(posZ);

			if (BlockRailBase.func_176562_d(worldObj, new BlockPos(var14, var2 - 1, var16))) {
				--var2;
			}

			final BlockPos var4 = new BlockPos(var14, var2, var16);
			final IBlockState var5 = worldObj.getBlockState(var4);

			if (BlockRailBase.func_176563_d(var5)) {
				func_180460_a(var4, var5);

				if (var5.getBlock() == Blocks.activator_rail) {
					onActivatorRailPass(var14, var2, var16, ((Boolean) var5.getValue(BlockRailPowered.field_176569_M)));
				}
			} else {
				func_180459_n();
			}

			doBlockCollisions();
			rotationPitch = 0.0F;
			final double var6 = prevPosX - posX;
			final double var8 = prevPosZ - posZ;

			if (var6 * var6 + var8 * var8 > 0.001D) {
				rotationYaw = (float) (Math.atan2(var8, var6) * 180.0D / Math.PI);

				if (isInReverse) {
					rotationYaw += 180.0F;
				}
			}

			final double var10 = MathHelper.wrapAngleTo180_float(rotationYaw - prevRotationYaw);

			if (var10 < -170.0D || var10 >= 170.0D) {
				rotationYaw += 180.0F;
				isInReverse = !isInReverse;
			}

			setRotation(rotationYaw, rotationPitch);
			final Iterator var12 = worldObj.getEntitiesWithinAABBExcludingEntity(this,
					getEntityBoundingBox().expand(0.20000000298023224D, 0.0D, 0.20000000298023224D)).iterator();

			while (var12.hasNext()) {
				final Entity var13 = (Entity) var12.next();

				if (var13 != riddenByEntity && var13.canBePushed() && var13 instanceof EntityMinecart) {
					var13.applyEntityCollision(this);
				}
			}

			if (riddenByEntity != null && riddenByEntity.isDead) {
				if (riddenByEntity.ridingEntity == this) {
					riddenByEntity.ridingEntity = null;
				}

				riddenByEntity = null;
			}

			handleWaterMovement();
		}
	}

	protected double func_174898_m() {
		return 0.4D;
	}

	/**
	 * Called every tick the minecart is on an activator rail. Args: x, y, z, is
	 * the rail receiving power
	 */
	public void onActivatorRailPass(final int p_96095_1_, final int p_96095_2_, final int p_96095_3_,
			final boolean p_96095_4_) {}

	protected void func_180459_n() {
		final double var1 = func_174898_m();
		motionX = MathHelper.clamp_double(motionX, -var1, var1);
		motionZ = MathHelper.clamp_double(motionZ, -var1, var1);

		if (onGround) {
			motionX *= 0.5D;
			motionY *= 0.5D;
			motionZ *= 0.5D;
		}

		moveEntity(motionX, motionY, motionZ);

		if (!onGround) {
			motionX *= 0.949999988079071D;
			motionY *= 0.949999988079071D;
			motionZ *= 0.949999988079071D;
		}
	}

	protected void func_180460_a(final BlockPos p_180460_1_, final IBlockState p_180460_2_) {
		fallDistance = 0.0F;
		final Vec3 var3 = func_70489_a(posX, posY, posZ);
		posY = p_180460_1_.getY();
		boolean var4 = false;
		boolean var5 = false;
		final BlockRailBase var6 = (BlockRailBase) p_180460_2_.getBlock();

		if (var6 == Blocks.golden_rail) {
			var4 = ((Boolean) p_180460_2_.getValue(BlockRailPowered.field_176569_M));
			var5 = !var4;
		}

		final BlockRailBase.EnumRailDirection var9 = (BlockRailBase.EnumRailDirection) p_180460_2_
				.getValue(var6.func_176560_l());

		switch (EntityMinecart.SwitchEnumMinecartType.field_180036_b[var9.ordinal()]) {
			case 1:
				motionX -= 0.0078125D;
				++posY;
				break;

			case 2:
				motionX += 0.0078125D;
				++posY;
				break;

			case 3:
				motionZ += 0.0078125D;
				++posY;
				break;

			case 4:
				motionZ -= 0.0078125D;
				++posY;
		}

		final int[][] var10 = matrix[var9.func_177015_a()];
		double var11 = var10[1][0] - var10[0][0];
		double var13 = var10[1][2] - var10[0][2];
		final double var15 = Math.sqrt(var11 * var11 + var13 * var13);
		final double var17 = motionX * var11 + motionZ * var13;

		if (var17 < 0.0D) {
			var11 = -var11;
			var13 = -var13;
		}

		double var19 = Math.sqrt(motionX * motionX + motionZ * motionZ);

		if (var19 > 2.0D) {
			var19 = 2.0D;
		}

		motionX = var19 * var11 / var15;
		motionZ = var19 * var13 / var15;
		double var21;
		double var23;
		double var25;
		double var27;

		if (riddenByEntity instanceof EntityLivingBase) {
			var21 = ((EntityLivingBase) riddenByEntity).moveForward;

			if (var21 > 0.0D) {
				var23 = -Math.sin(riddenByEntity.rotationYaw * (float) Math.PI / 180.0F);
				var25 = Math.cos(riddenByEntity.rotationYaw * (float) Math.PI / 180.0F);
				var27 = motionX * motionX + motionZ * motionZ;

				if (var27 < 0.01D) {
					motionX += var23 * 0.1D;
					motionZ += var25 * 0.1D;
					var5 = false;
				}
			}
		}

		if (var5) {
			var21 = Math.sqrt(motionX * motionX + motionZ * motionZ);

			if (var21 < 0.03D) {
				motionX *= 0.0D;
				motionY *= 0.0D;
				motionZ *= 0.0D;
			} else {
				motionX *= 0.5D;
				motionY *= 0.0D;
				motionZ *= 0.5D;
			}
		}

		var21 = 0.0D;
		var23 = p_180460_1_.getX() + 0.5D + var10[0][0] * 0.5D;
		var25 = p_180460_1_.getZ() + 0.5D + var10[0][2] * 0.5D;
		var27 = p_180460_1_.getX() + 0.5D + var10[1][0] * 0.5D;
		final double var29 = p_180460_1_.getZ() + 0.5D + var10[1][2] * 0.5D;
		var11 = var27 - var23;
		var13 = var29 - var25;
		double var31;
		double var33;

		if (var11 == 0.0D) {
			posX = p_180460_1_.getX() + 0.5D;
			var21 = posZ - p_180460_1_.getZ();
		} else if (var13 == 0.0D) {
			posZ = p_180460_1_.getZ() + 0.5D;
			var21 = posX - p_180460_1_.getX();
		} else {
			var31 = posX - var23;
			var33 = posZ - var25;
			var21 = (var31 * var11 + var33 * var13) * 2.0D;
		}

		posX = var23 + var11 * var21;
		posZ = var25 + var13 * var21;
		setPosition(posX, posY, posZ);
		var31 = motionX;
		var33 = motionZ;

		if (riddenByEntity != null) {
			var31 *= 0.75D;
			var33 *= 0.75D;
		}

		final double var35 = func_174898_m();
		var31 = MathHelper.clamp_double(var31, -var35, var35);
		var33 = MathHelper.clamp_double(var33, -var35, var35);
		moveEntity(var31, 0.0D, var33);

		if (var10[0][1] != 0 && MathHelper.floor_double(posX) - p_180460_1_.getX() == var10[0][0]
				&& MathHelper.floor_double(posZ) - p_180460_1_.getZ() == var10[0][2]) {
			setPosition(posX, posY + var10[0][1], posZ);
		} else if (var10[1][1] != 0 && MathHelper.floor_double(posX) - p_180460_1_.getX() == var10[1][0]
				&& MathHelper.floor_double(posZ) - p_180460_1_.getZ() == var10[1][2]) {
			setPosition(posX, posY + var10[1][1], posZ);
		}

		applyDrag();
		final Vec3 var37 = func_70489_a(posX, posY, posZ);

		if (var37 != null && var3 != null) {
			final double var38 = (var3.yCoord - var37.yCoord) * 0.05D;
			var19 = Math.sqrt(motionX * motionX + motionZ * motionZ);

			if (var19 > 0.0D) {
				motionX = motionX / var19 * (var19 + var38);
				motionZ = motionZ / var19 * (var19 + var38);
			}

			setPosition(posX, var37.yCoord, posZ);
		}

		final int var44 = MathHelper.floor_double(posX);
		final int var39 = MathHelper.floor_double(posZ);

		if (var44 != p_180460_1_.getX() || var39 != p_180460_1_.getZ()) {
			var19 = Math.sqrt(motionX * motionX + motionZ * motionZ);
			motionX = var19 * (var44 - p_180460_1_.getX());
			motionZ = var19 * (var39 - p_180460_1_.getZ());
		}

		if (var4) {
			final double var40 = Math.sqrt(motionX * motionX + motionZ * motionZ);

			if (var40 > 0.01D) {
				final double var42 = 0.06D;
				motionX += motionX / var40 * var42;
				motionZ += motionZ / var40 * var42;
			} else if (var9 == BlockRailBase.EnumRailDirection.EAST_WEST) {
				if (worldObj.getBlockState(p_180460_1_.offsetWest()).getBlock().isNormalCube()) {
					motionX = 0.02D;
				} else if (worldObj.getBlockState(p_180460_1_.offsetEast()).getBlock().isNormalCube()) {
					motionX = -0.02D;
				}
			} else if (var9 == BlockRailBase.EnumRailDirection.NORTH_SOUTH) {
				if (worldObj.getBlockState(p_180460_1_.offsetNorth()).getBlock().isNormalCube()) {
					motionZ = 0.02D;
				} else if (worldObj.getBlockState(p_180460_1_.offsetSouth()).getBlock().isNormalCube()) {
					motionZ = -0.02D;
				}
			}
		}
	}

	protected void applyDrag() {
		if (riddenByEntity != null) {
			motionX *= 0.996999979019165D;
			motionY *= 0.0D;
			motionZ *= 0.996999979019165D;
		} else {
			motionX *= 0.9599999785423279D;
			motionY *= 0.0D;
			motionZ *= 0.9599999785423279D;
		}
	}

	/**
	 * Sets the x,y,z of the entity from the given parameters. Also seems to set
	 * up a bounding box.
	 */
	@Override
	public void setPosition(final double x, final double y, final double z) {
		posX = x;
		posY = y;
		posZ = z;
		final float var7 = width / 2.0F;
		final float var8 = height;
		func_174826_a(new AxisAlignedBB(x - var7, y, z - var7, x + var7, y + var8, z + var7));
	}

	public Vec3 func_70495_a(double p_70495_1_, double p_70495_3_, double p_70495_5_, final double p_70495_7_) {
		final int var9 = MathHelper.floor_double(p_70495_1_);
		int var10 = MathHelper.floor_double(p_70495_3_);
		final int var11 = MathHelper.floor_double(p_70495_5_);

		if (BlockRailBase.func_176562_d(worldObj, new BlockPos(var9, var10 - 1, var11))) {
			--var10;
		}

		final IBlockState var12 = worldObj.getBlockState(new BlockPos(var9, var10, var11));

		if (BlockRailBase.func_176563_d(var12)) {
			final BlockRailBase.EnumRailDirection var13 = (BlockRailBase.EnumRailDirection) var12
					.getValue(((BlockRailBase) var12.getBlock()).func_176560_l());
			p_70495_3_ = var10;

			if (var13.func_177018_c()) {
				p_70495_3_ = var10 + 1;
			}

			final int[][] var14 = matrix[var13.func_177015_a()];
			double var15 = var14[1][0] - var14[0][0];
			double var17 = var14[1][2] - var14[0][2];
			final double var19 = Math.sqrt(var15 * var15 + var17 * var17);
			var15 /= var19;
			var17 /= var19;
			p_70495_1_ += var15 * p_70495_7_;
			p_70495_5_ += var17 * p_70495_7_;

			if (var14[0][1] != 0 && MathHelper.floor_double(p_70495_1_) - var9 == var14[0][0]
					&& MathHelper.floor_double(p_70495_5_) - var11 == var14[0][2]) {
				p_70495_3_ += var14[0][1];
			} else if (var14[1][1] != 0 && MathHelper.floor_double(p_70495_1_) - var9 == var14[1][0]
					&& MathHelper.floor_double(p_70495_5_) - var11 == var14[1][2]) {
				p_70495_3_ += var14[1][1];
			}

			return func_70489_a(p_70495_1_, p_70495_3_, p_70495_5_);
		} else {
			return null;
		}
	}

	public Vec3 func_70489_a(double p_70489_1_, double p_70489_3_, double p_70489_5_) {
		final int var7 = MathHelper.floor_double(p_70489_1_);
		int var8 = MathHelper.floor_double(p_70489_3_);
		final int var9 = MathHelper.floor_double(p_70489_5_);

		if (BlockRailBase.func_176562_d(worldObj, new BlockPos(var7, var8 - 1, var9))) {
			--var8;
		}

		final IBlockState var10 = worldObj.getBlockState(new BlockPos(var7, var8, var9));

		if (BlockRailBase.func_176563_d(var10)) {
			final BlockRailBase.EnumRailDirection var11 = (BlockRailBase.EnumRailDirection) var10
					.getValue(((BlockRailBase) var10.getBlock()).func_176560_l());
			final int[][] var12 = matrix[var11.func_177015_a()];
			double var13 = 0.0D;
			final double var15 = var7 + 0.5D + var12[0][0] * 0.5D;
			final double var17 = var8 + 0.0625D + var12[0][1] * 0.5D;
			final double var19 = var9 + 0.5D + var12[0][2] * 0.5D;
			final double var21 = var7 + 0.5D + var12[1][0] * 0.5D;
			final double var23 = var8 + 0.0625D + var12[1][1] * 0.5D;
			final double var25 = var9 + 0.5D + var12[1][2] * 0.5D;
			final double var27 = var21 - var15;
			final double var29 = (var23 - var17) * 2.0D;
			final double var31 = var25 - var19;

			if (var27 == 0.0D) {
				p_70489_1_ = var7 + 0.5D;
				var13 = p_70489_5_ - var9;
			} else if (var31 == 0.0D) {
				p_70489_5_ = var9 + 0.5D;
				var13 = p_70489_1_ - var7;
			} else {
				final double var33 = p_70489_1_ - var15;
				final double var35 = p_70489_5_ - var19;
				var13 = (var33 * var27 + var35 * var31) * 2.0D;
			}

			p_70489_1_ = var15 + var27 * var13;
			p_70489_3_ = var17 + var29 * var13;
			p_70489_5_ = var19 + var31 * var13;

			if (var29 < 0.0D) {
				++p_70489_3_;
			}

			if (var29 > 0.0D) {
				p_70489_3_ += 0.5D;
			}

			return new Vec3(p_70489_1_, p_70489_3_, p_70489_5_);
		} else {
			return null;
		}
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	protected void readEntityFromNBT(final NBTTagCompound tagCompund) {
		if (tagCompund.getBoolean("CustomDisplayTile")) {
			final int var2 = tagCompund.getInteger("DisplayData");
			Block var3;

			if (tagCompund.hasKey("DisplayTile", 8)) {
				var3 = Block.getBlockFromName(tagCompund.getString("DisplayTile"));

				if (var3 == null) {
					func_174899_a(Blocks.air.getDefaultState());
				} else {
					func_174899_a(var3.getStateFromMeta(var2));
				}
			} else {
				var3 = Block.getBlockById(tagCompund.getInteger("DisplayTile"));

				if (var3 == null) {
					func_174899_a(Blocks.air.getDefaultState());
				} else {
					func_174899_a(var3.getStateFromMeta(var2));
				}
			}

			setDisplayTileOffset(tagCompund.getInteger("DisplayOffset"));
		}

		if (tagCompund.hasKey("CustomName", 8) && tagCompund.getString("CustomName").length() > 0) {
			entityName = tagCompund.getString("CustomName");
		}
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	protected void writeEntityToNBT(final NBTTagCompound tagCompound) {
		if (hasDisplayTile()) {
			tagCompound.setBoolean("CustomDisplayTile", true);
			final IBlockState var2 = func_174897_t();
			final ResourceLocation var3 = (ResourceLocation) Block.blockRegistry.getNameForObject(var2.getBlock());
			tagCompound.setString("DisplayTile", var3 == null ? "" : var3.toString());
			tagCompound.setInteger("DisplayData", var2.getBlock().getMetaFromState(var2));
			tagCompound.setInteger("DisplayOffset", getDisplayTileOffset());
		}

		if (entityName != null && entityName.length() > 0) {
			tagCompound.setString("CustomName", entityName);
		}
	}

	/**
	 * Applies a velocity to each of the entities pushing them away from each
	 * other. Args: entity
	 */
	@Override
	public void applyEntityCollision(final Entity entityIn) {
		if (!worldObj.isRemote) {
			if (!entityIn.noClip && !noClip) {
				if (entityIn != riddenByEntity) {
					if (entityIn instanceof EntityLivingBase && !(entityIn instanceof EntityPlayer)
							&& !(entityIn instanceof EntityIronGolem)
							&& func_180456_s() == EntityMinecart.EnumMinecartType.RIDEABLE
							&& motionX * motionX + motionZ * motionZ > 0.01D && riddenByEntity == null
							&& entityIn.ridingEntity == null) {
						entityIn.mountEntity(this);
					}

					double var2 = entityIn.posX - posX;
					double var4 = entityIn.posZ - posZ;
					double var6 = var2 * var2 + var4 * var4;

					if (var6 >= 9.999999747378752E-5D) {
						var6 = MathHelper.sqrt_double(var6);
						var2 /= var6;
						var4 /= var6;
						double var8 = 1.0D / var6;

						if (var8 > 1.0D) {
							var8 = 1.0D;
						}

						var2 *= var8;
						var4 *= var8;
						var2 *= 0.10000000149011612D;
						var4 *= 0.10000000149011612D;
						var2 *= 1.0F - entityCollisionReduction;
						var4 *= 1.0F - entityCollisionReduction;
						var2 *= 0.5D;
						var4 *= 0.5D;

						if (entityIn instanceof EntityMinecart) {
							final double var10 = entityIn.posX - posX;
							final double var12 = entityIn.posZ - posZ;
							final Vec3 var14 = new Vec3(var10, 0.0D, var12).normalize();
							final Vec3 var15 = new Vec3(MathHelper.cos(rotationYaw * (float) Math.PI / 180.0F), 0.0D,
									MathHelper.sin(rotationYaw * (float) Math.PI / 180.0F)).normalize();
							final double var16 = Math.abs(var14.dotProduct(var15));

							if (var16 < 0.800000011920929D) {
								return;
							}

							double var18 = entityIn.motionX + motionX;
							double var20 = entityIn.motionZ + motionZ;

							if (((EntityMinecart) entityIn).func_180456_s() == EntityMinecart.EnumMinecartType.FURNACE
									&& func_180456_s() != EntityMinecart.EnumMinecartType.FURNACE) {
								motionX *= 0.20000000298023224D;
								motionZ *= 0.20000000298023224D;
								addVelocity(entityIn.motionX - var2, 0.0D, entityIn.motionZ - var4);
								entityIn.motionX *= 0.949999988079071D;
								entityIn.motionZ *= 0.949999988079071D;
							} else if (((EntityMinecart) entityIn)
									.func_180456_s() != EntityMinecart.EnumMinecartType.FURNACE
									&& func_180456_s() == EntityMinecart.EnumMinecartType.FURNACE) {
								entityIn.motionX *= 0.20000000298023224D;
								entityIn.motionZ *= 0.20000000298023224D;
								entityIn.addVelocity(motionX + var2, 0.0D, motionZ + var4);
								motionX *= 0.949999988079071D;
								motionZ *= 0.949999988079071D;
							} else {
								var18 /= 2.0D;
								var20 /= 2.0D;
								motionX *= 0.20000000298023224D;
								motionZ *= 0.20000000298023224D;
								addVelocity(var18 - var2, 0.0D, var20 - var4);
								entityIn.motionX *= 0.20000000298023224D;
								entityIn.motionZ *= 0.20000000298023224D;
								entityIn.addVelocity(var18 + var2, 0.0D, var20 + var4);
							}
						} else {
							addVelocity(-var2, 0.0D, -var4);
							entityIn.addVelocity(var2 / 4.0D, 0.0D, var4 / 4.0D);
						}
					}
				}
			}
		}
	}

	@Override
	public void func_180426_a(final double p_180426_1_, final double p_180426_3_, final double p_180426_5_,
			final float p_180426_7_, final float p_180426_8_, final int p_180426_9_, final boolean p_180426_10_) {
		minecartX = p_180426_1_;
		minecartY = p_180426_3_;
		minecartZ = p_180426_5_;
		minecartYaw = p_180426_7_;
		minecartPitch = p_180426_8_;
		turnProgress = p_180426_9_ + 2;
		motionX = velocityX;
		motionY = velocityY;
		motionZ = velocityZ;
	}

	/**
	 * Sets the velocity to the args. Args: x, y, z
	 */
	@Override
	public void setVelocity(final double x, final double y, final double z) {
		velocityX = motionX = x;
		velocityY = motionY = y;
		velocityZ = motionZ = z;
	}

	/**
	 * Sets the current amount of damage the minecart has taken. Decreases over
	 * time. The cart breaks when this is over 40.
	 */
	public void setDamage(final float p_70492_1_) {
		dataWatcher.updateObject(19, p_70492_1_);
	}

	/**
	 * Gets the current amount of damage the minecart has taken. Decreases over
	 * time. The cart breaks when this is over 40.
	 */
	public float getDamage() {
		return dataWatcher.getWatchableObjectFloat(19);
	}

	/**
	 * Sets the rolling amplitude the cart rolls while being attacked.
	 */
	public void setRollingAmplitude(final int p_70497_1_) {
		dataWatcher.updateObject(17, p_70497_1_);
	}

	/**
	 * Gets the rolling amplitude the cart rolls while being attacked.
	 */
	public int getRollingAmplitude() {
		return dataWatcher.getWatchableObjectInt(17);
	}

	/**
	 * Sets the rolling direction the cart rolls while being attacked. Can be 1
	 * or -1.
	 */
	public void setRollingDirection(final int p_70494_1_) {
		dataWatcher.updateObject(18, p_70494_1_);
	}

	/**
	 * Gets the rolling direction the cart rolls while being attacked. Can be 1
	 * or -1.
	 */
	public int getRollingDirection() {
		return dataWatcher.getWatchableObjectInt(18);
	}

	public abstract EntityMinecart.EnumMinecartType func_180456_s();

	public IBlockState func_174897_t() {
		return !hasDisplayTile() ? func_180457_u() : Block.getStateById(getDataWatcher().getWatchableObjectInt(20));
	}

	public IBlockState func_180457_u() {
		return Blocks.air.getDefaultState();
	}

	public int getDisplayTileOffset() {
		return !hasDisplayTile() ? getDefaultDisplayTileOffset() : getDataWatcher().getWatchableObjectInt(21);
	}

	public int getDefaultDisplayTileOffset() {
		return 6;
	}

	public void func_174899_a(final IBlockState p_174899_1_) {
		getDataWatcher().updateObject(20, Block.getStateId(p_174899_1_));
		setHasDisplayTile(true);
	}

	public void setDisplayTileOffset(final int p_94086_1_) {
		getDataWatcher().updateObject(21, p_94086_1_);
		setHasDisplayTile(true);
	}

	public boolean hasDisplayTile() {
		return getDataWatcher().getWatchableObjectByte(22) == 1;
	}

	public void setHasDisplayTile(final boolean p_94096_1_) {
		getDataWatcher().updateObject(22, (byte) (p_94096_1_ ? 1 : 0));
	}

	/**
	 * Sets the custom name tag for this entity
	 */
	@Override
	public void setCustomNameTag(final String p_96094_1_) {
		entityName = p_96094_1_;
	}

	/**
	 * Gets the name of this command sender (usually username, but possibly
	 * "Rcon")
	 */
	@Override
	public String getName() {
		return entityName != null ? entityName : super.getName();
	}

	/**
	 * Returns true if this thing is named
	 */
	@Override
	public boolean hasCustomName() {
		return entityName != null;
	}

	@Override
	public String getCustomNameTag() {
		return entityName;
	}

	@Override
	public IChatComponent getDisplayName() {
		if (hasCustomName()) {
			final ChatComponentText var2 = new ChatComponentText(entityName);
			var2.getChatStyle().setChatHoverEvent(func_174823_aP());
			var2.getChatStyle().setInsertion(getUniqueID().toString());
			return var2;
		} else {
			final ChatComponentTranslation var1 = new ChatComponentTranslation(getName(), new Object[0]);
			var1.getChatStyle().setChatHoverEvent(func_174823_aP());
			var1.getChatStyle().setInsertion(getUniqueID().toString());
			return var1;
		}
	}

	public static enum EnumMinecartType {
		RIDEABLE("RIDEABLE", 0, 0, "MinecartRideable"), CHEST("CHEST", 1, 1, "MinecartChest"), FURNACE("FURNACE", 2, 2,
				"MinecartFurnace"), TNT("TNT", 3, 3, "MinecartTNT"), SPAWNER("SPAWNER", 4, 4,
						"MinecartSpawner"), HOPPER("HOPPER", 5, 5,
								"MinecartHopper"), COMMAND_BLOCK("COMMAND_BLOCK", 6, 6, "MinecartCommandBlock");
		private static final Map field_180051_h = Maps.newHashMap();
		private final int field_180052_i;
		private final String field_180049_j;

		private EnumMinecartType(final String p_i45847_1_, final int p_i45847_2_, final int p_i45847_3_,
				final String p_i45847_4_) {
			field_180052_i = p_i45847_3_;
			field_180049_j = p_i45847_4_;
		}

		public int func_180039_a() {
			return field_180052_i;
		}

		public String func_180040_b() {
			return field_180049_j;
		}

		public static EntityMinecart.EnumMinecartType func_180038_a(final int p_180038_0_) {
			final EntityMinecart.EnumMinecartType var1 = (EntityMinecart.EnumMinecartType) field_180051_h
					.get(p_180038_0_);
			return var1 == null ? RIDEABLE : var1;
		}

		static {
			final EntityMinecart.EnumMinecartType[] var0 = values();
			final int var1 = var0.length;

			for (int var2 = 0; var2 < var1; ++var2) {
				final EntityMinecart.EnumMinecartType var3 = var0[var2];
				field_180051_h.put(Integer.valueOf(var3.func_180039_a()), var3);
			}
		}
	}

	static final class SwitchEnumMinecartType {
		static final int[] field_180037_a;

		static final int[] field_180036_b = new int[BlockRailBase.EnumRailDirection.values().length];
		// private static final String __OBFID =
		// "http://https://fuckuskid00002227";

		static {
			try {
				field_180036_b[BlockRailBase.EnumRailDirection.ASCENDING_EAST.ordinal()] = 1;
			} catch (final NoSuchFieldError var10) {
			}

			try {
				field_180036_b[BlockRailBase.EnumRailDirection.ASCENDING_WEST.ordinal()] = 2;
			} catch (final NoSuchFieldError var9) {
			}

			try {
				field_180036_b[BlockRailBase.EnumRailDirection.ASCENDING_NORTH.ordinal()] = 3;
			} catch (final NoSuchFieldError var8) {
			}

			try {
				field_180036_b[BlockRailBase.EnumRailDirection.ASCENDING_SOUTH.ordinal()] = 4;
			} catch (final NoSuchFieldError var7) {
			}

			field_180037_a = new int[EntityMinecart.EnumMinecartType.values().length];

			try {
				field_180037_a[EntityMinecart.EnumMinecartType.CHEST.ordinal()] = 1;
			} catch (final NoSuchFieldError var6) {
			}

			try {
				field_180037_a[EntityMinecart.EnumMinecartType.FURNACE.ordinal()] = 2;
			} catch (final NoSuchFieldError var5) {
			}

			try {
				field_180037_a[EntityMinecart.EnumMinecartType.TNT.ordinal()] = 3;
			} catch (final NoSuchFieldError var4) {
			}

			try {
				field_180037_a[EntityMinecart.EnumMinecartType.SPAWNER.ordinal()] = 4;
			} catch (final NoSuchFieldError var3) {
			}

			try {
				field_180037_a[EntityMinecart.EnumMinecartType.HOPPER.ordinal()] = 5;
			} catch (final NoSuchFieldError var2) {
			}

			try {
				field_180037_a[EntityMinecart.EnumMinecartType.COMMAND_BLOCK.ordinal()] = 6;
			} catch (final NoSuchFieldError var1) {
			}
		}
	}
}
