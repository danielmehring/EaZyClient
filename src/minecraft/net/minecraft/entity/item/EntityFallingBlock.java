package net.minecraft.entity.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAnvil;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Iterator;

import com.google.common.collect.Lists;

public class EntityFallingBlock extends Entity {

public static final int EaZy = 1137;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private IBlockState field_175132_d;
	public int fallTime;
	public boolean shouldDropItem = true;
	private boolean field_145808_f;
	private boolean hurtEntities;
	private int fallHurtMax = 40;
	private float fallHurtAmount = 2.0F;
	public NBTTagCompound tileEntityData;
	// private static final String __OBFID = "http://https://fuckuskid00001668";

	public EntityFallingBlock(final World worldIn) {
		super(worldIn);
	}

	public EntityFallingBlock(final World worldIn, final double p_i45848_2_, final double p_i45848_4_,
			final double p_i45848_6_, final IBlockState p_i45848_8_) {
		super(worldIn);
		field_175132_d = p_i45848_8_;
		preventEntitySpawning = true;
		setSize(0.98F, 0.98F);
		setPosition(p_i45848_2_, p_i45848_4_, p_i45848_6_);
		motionX = 0.0D;
		motionY = 0.0D;
		motionZ = 0.0D;
		prevPosX = p_i45848_2_;
		prevPosY = p_i45848_4_;
		prevPosZ = p_i45848_6_;
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
	protected void entityInit() {}

	/**
	 * Returns true if other Entities should be prevented from moving through
	 * this Entity.
	 */
	@Override
	public boolean canBeCollidedWith() {
		return !isDead;
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate() {
		final Block var1 = field_175132_d.getBlock();

		if (var1.getMaterial() == Material.air) {
			setDead();
		} else {
			prevPosX = posX;
			prevPosY = posY;
			prevPosZ = posZ;
			BlockPos var2;

			if (fallTime++ == 0) {
				var2 = new BlockPos(this);

				if (worldObj.getBlockState(var2).getBlock() == var1) {
					worldObj.setBlockToAir(var2);
				} else if (!worldObj.isRemote) {
					setDead();
					return;
				}
			}

			motionY -= 0.03999999910593033D;
			moveEntity(motionX, motionY, motionZ);
			motionX *= 0.9800000190734863D;
			motionY *= 0.9800000190734863D;
			motionZ *= 0.9800000190734863D;

			if (!worldObj.isRemote) {
				var2 = new BlockPos(this);

				if (onGround) {
					motionX *= 0.699999988079071D;
					motionZ *= 0.699999988079071D;
					motionY *= -0.5D;

					if (worldObj.getBlockState(var2).getBlock() != Blocks.piston_extension) {
						setDead();

						if (!field_145808_f
								&& worldObj.canBlockBePlaced(var1, var2, true, EnumFacing.UP, (Entity) null,
										(ItemStack) null)
								&& !BlockFalling.canFallInto(worldObj, var2.offsetDown())
								&& worldObj.setBlockState(var2, field_175132_d, 3)) {
							if (var1 instanceof BlockFalling) {
								((BlockFalling) var1).onEndFalling(worldObj, var2);
							}

							if (tileEntityData != null && var1 instanceof ITileEntityProvider) {
								final TileEntity var3 = worldObj.getTileEntity(var2);

								if (var3 != null) {
									final NBTTagCompound var4 = new NBTTagCompound();
									var3.writeToNBT(var4);
									final Iterator var5 = tileEntityData.getKeySet().iterator();

									while (var5.hasNext()) {
										final String var6 = (String) var5.next();
										final NBTBase var7 = tileEntityData.getTag(var6);

										if (!var6.equals("x") && !var6.equals("y") && !var6.equals("z")) {
											var4.setTag(var6, var7.copy());
										}
									}

									var3.readFromNBT(var4);
									var3.markDirty();
								}
							}
						} else if (shouldDropItem && !field_145808_f
								&& worldObj.getGameRules().getGameRuleBooleanValue("doTileDrops")) {
							entityDropItem(new ItemStack(var1, 1, var1.damageDropped(field_175132_d)), 0.0F);
						}
					}
				} else if (fallTime > 100 && !worldObj.isRemote && (var2.getY() < 1 || var2.getY() > 256)
						|| fallTime > 600) {
					if (shouldDropItem && worldObj.getGameRules().getGameRuleBooleanValue("doTileDrops")) {
						entityDropItem(new ItemStack(var1, 1, var1.damageDropped(field_175132_d)), 0.0F);
					}

					setDead();
				}
			}
		}
	}

	@Override
	public void fall(final float distance, final float damageMultiplier) {
		final Block var3 = field_175132_d.getBlock();

		if (hurtEntities) {
			final int var4 = MathHelper.ceiling_float_int(distance - 1.0F);

			if (var4 > 0) {
				final ArrayList var5 = Lists
						.newArrayList(worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox()));
				final boolean var6 = var3 == Blocks.anvil;
				final DamageSource var7 = var6 ? DamageSource.anvil : DamageSource.fallingBlock;
				final Iterator var8 = var5.iterator();

				while (var8.hasNext()) {
					final Entity var9 = (Entity) var8.next();
					var9.attackEntityFrom(var7, Math.min(MathHelper.floor_float(var4 * fallHurtAmount), fallHurtMax));
				}

				if (var6 && rand.nextFloat() < 0.05000000074505806D + var4 * 0.05D) {
					int var10 = ((Integer) field_175132_d.getValue(BlockAnvil.DAMAGE));
					++var10;

					if (var10 > 2) {
						field_145808_f = true;
					} else {
						field_175132_d = field_175132_d.withProperty(BlockAnvil.DAMAGE, var10);
					}
				}
			}
		}
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	protected void writeEntityToNBT(final NBTTagCompound tagCompound) {
		final Block var2 = field_175132_d != null ? field_175132_d.getBlock() : Blocks.air;
		final ResourceLocation var3 = (ResourceLocation) Block.blockRegistry.getNameForObject(var2);
		tagCompound.setString("Block", var3 == null ? "" : var3.toString());
		tagCompound.setByte("Data", (byte) var2.getMetaFromState(field_175132_d));
		tagCompound.setByte("Time", (byte) fallTime);
		tagCompound.setBoolean("DropItem", shouldDropItem);
		tagCompound.setBoolean("HurtEntities", hurtEntities);
		tagCompound.setFloat("FallHurtAmount", fallHurtAmount);
		tagCompound.setInteger("FallHurtMax", fallHurtMax);

		if (tileEntityData != null) {
			tagCompound.setTag("TileEntityData", tileEntityData);
		}
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	protected void readEntityFromNBT(final NBTTagCompound tagCompund) {
		final int var2 = tagCompund.getByte("Data") & 255;

		if (tagCompund.hasKey("Block", 8)) {
			field_175132_d = Block.getBlockFromName(tagCompund.getString("Block")).getStateFromMeta(var2);
		} else if (tagCompund.hasKey("TileID", 99)) {
			field_175132_d = Block.getBlockById(tagCompund.getInteger("TileID")).getStateFromMeta(var2);
		} else {
			field_175132_d = Block.getBlockById(tagCompund.getByte("Tile") & 255).getStateFromMeta(var2);
		}

		fallTime = tagCompund.getByte("Time") & 255;
		final Block var3 = field_175132_d.getBlock();

		if (tagCompund.hasKey("HurtEntities", 99)) {
			hurtEntities = tagCompund.getBoolean("HurtEntities");
			fallHurtAmount = tagCompund.getFloat("FallHurtAmount");
			fallHurtMax = tagCompund.getInteger("FallHurtMax");
		} else if (var3 == Blocks.anvil) {
			hurtEntities = true;
		}

		if (tagCompund.hasKey("DropItem", 99)) {
			shouldDropItem = tagCompund.getBoolean("DropItem");
		}

		if (tagCompund.hasKey("TileEntityData", 10)) {
			tileEntityData = tagCompund.getCompoundTag("TileEntityData");
		}

		if (var3 == null || var3.getMaterial() == Material.air) {
			field_175132_d = Blocks.sand.getDefaultState();
		}
	}

	public World getWorldObj() {
		return worldObj;
	}

	public void setHurtEntities(final boolean p_145806_1_) {
		hurtEntities = p_145806_1_;
	}

	/**
	 * Return whether this entity should be rendered as on fire.
	 */
	@Override
	public boolean canRenderOnFire() {
		return false;
	}

	@Override
	public void addEntityCrashInfo(final CrashReportCategory category) {
		super.addEntityCrashInfo(category);

		if (field_175132_d != null) {
			final Block var2 = field_175132_d.getBlock();
			category.addCrashSection("Immitating block ID", Block.getIdFromBlock(var2));
			category.addCrashSection("Immitating block data", var2.getMetaFromState(field_175132_d));
		}
	}

	public IBlockState getBlock() {
		return field_175132_d;
	}
}
