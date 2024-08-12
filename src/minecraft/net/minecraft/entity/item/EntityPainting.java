package net.minecraft.entity.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

import java.util.ArrayList;

import com.google.common.collect.Lists;

public class EntityPainting extends EntityHanging {

public static final int EaZy = 1148;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public EntityPainting.EnumArt art;
	// private static final String __OBFID = "http://https://fuckuskid00001556";

	public EntityPainting(final World worldIn) {
		super(worldIn);
	}

	public EntityPainting(final World worldIn, final BlockPos p_i45849_2_, final EnumFacing p_i45849_3_) {
		super(worldIn, p_i45849_2_);
		final ArrayList var4 = Lists.newArrayList();
		final EntityPainting.EnumArt[] var5 = EntityPainting.EnumArt.values();
		final int var6 = var5.length;

		for (int var7 = 0; var7 < var6; ++var7) {
			final EntityPainting.EnumArt var8 = var5[var7];
			art = var8;
			func_174859_a(p_i45849_3_);

			if (onValidSurface()) {
				var4.add(var8);
			}
		}

		if (!var4.isEmpty()) {
			art = (EntityPainting.EnumArt) var4.get(rand.nextInt(var4.size()));
		}

		func_174859_a(p_i45849_3_);
	}

	public EntityPainting(final World worldIn, final BlockPos p_i45850_2_, final EnumFacing p_i45850_3_,
			final String p_i45850_4_) {
		this(worldIn, p_i45850_2_, p_i45850_3_);
		final EntityPainting.EnumArt[] var5 = EntityPainting.EnumArt.values();
		final int var6 = var5.length;

		for (int var7 = 0; var7 < var6; ++var7) {
			final EntityPainting.EnumArt var8 = var5[var7];

			if (var8.title.equals(p_i45850_4_)) {
				art = var8;
				break;
			}
		}

		func_174859_a(p_i45850_3_);
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	public void writeEntityToNBT(final NBTTagCompound tagCompound) {
		tagCompound.setString("Motive", art.title);
		super.writeEntityToNBT(tagCompound);
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readEntityFromNBT(final NBTTagCompound tagCompund) {
		final String var2 = tagCompund.getString("Motive");
		final EntityPainting.EnumArt[] var3 = EntityPainting.EnumArt.values();
		final int var4 = var3.length;

		for (int var5 = 0; var5 < var4; ++var5) {
			final EntityPainting.EnumArt var6 = var3[var5];

			if (var6.title.equals(var2)) {
				art = var6;
			}
		}

		if (art == null) {
			art = EntityPainting.EnumArt.KEBAB;
		}

		super.readEntityFromNBT(tagCompund);
	}

	@Override
	public int getWidthPixels() {
		return art.sizeX;
	}

	@Override
	public int getHeightPixels() {
		return art.sizeY;
	}

	/**
	 * Called when this entity is broken. Entity parameter may be null.
	 */
	@Override
	public void onBroken(final Entity p_110128_1_) {
		if (worldObj.getGameRules().getGameRuleBooleanValue("doTileDrops")) {
			if (p_110128_1_ instanceof EntityPlayer) {
				final EntityPlayer var2 = (EntityPlayer) p_110128_1_;

				if (var2.capabilities.isCreativeMode) {
					return;
				}
			}

			entityDropItem(new ItemStack(Items.painting), 0.0F);
		}
	}

	/**
	 * Sets the location and Yaw/Pitch of an entity in the world
	 */
	@Override
	public void setLocationAndAngles(final double x, final double y, final double z, final float yaw,
			final float pitch) {
		final BlockPos var9 = new BlockPos(x - posX, y - posY, z - posZ);
		final BlockPos var10 = field_174861_a.add(var9);
		setPosition(var10.getX(), var10.getY(), var10.getZ());
	}

	@Override
	public void func_180426_a(final double p_180426_1_, final double p_180426_3_, final double p_180426_5_,
			final float p_180426_7_, final float p_180426_8_, final int p_180426_9_, final boolean p_180426_10_) {
		final BlockPos var11 = new BlockPos(p_180426_1_ - posX, p_180426_3_ - posY, p_180426_5_ - posZ);
		final BlockPos var12 = field_174861_a.add(var11);
		setPosition(var12.getX(), var12.getY(), var12.getZ());
	}

	public static enum EnumArt {
		KEBAB("KEBAB", 0, "Kebab", 16, 16, 0, 0), AZTEC("AZTEC", 1, "Aztec", 16, 16, 16, 0), ALBAN("ALBAN", 2, "Alban",
				16, 16, 32, 0), AZTEC_2("AZTEC_2", 3, "Aztec2", 16, 16, 48, 0), BOMB("BOMB", 4, "Bomb", 16, 16, 64,
						0), PLANT("PLANT", 5, "Plant", 16, 16, 80, 0), WASTELAND("WASTELAND", 6, "Wasteland", 16, 16,
								96, 0), POOL("POOL", 7, "Pool", 32, 16, 0, 32), COURBET("COURBET", 8, "Courbet", 32, 16,
										32, 32), SEA("SEA", 9, "Sea", 32, 16, 64, 32), SUNSET("SUNSET", 10, "Sunset",
												32, 16, 96,
												32), CREEBET("CREEBET", 11, "Creebet", 32, 16, 128, 32), WANDERER(
														"WANDERER", 12, "Wanderer", 16, 32, 0,
														64), GRAHAM("GRAHAM", 13, "Graham", 16, 32, 16, 64), MATCH(
																"MATCH", 14, "Match", 32, 32, 0,
																128), BUST("BUST", 15, "Bust", 32, 32, 32, 128), STAGE(
																		"STAGE", 16, "Stage", 32, 32, 64,
																		128), VOID("VOID", 17, "Void", 32, 32, 96,
																				128), SKULL_AND_ROSES("SKULL_AND_ROSES",
																						18, "SkullAndRoses", 32, 32,
																						128, 128), WITHER("WITHER", 19,
																								"Wither", 32, 32, 160,
																								128), FIGHTERS(
																										"FIGHTERS", 20,
																										"Fighters", 64,
																										32, 0,
																										96), POINTER(
																												"POINTER",
																												21,
																												"Pointer",
																												64, 64,
																												0,
																												192), PIGSCENE(
																														"PIGSCENE",
																														22,
																														"Pigscene",
																														64,
																														64,
																														64,
																														192), BURNING_SKULL(
																																"BURNING_SKULL",
																																23,
																																"BurningSkull",
																																64,
																																64,
																																128,
																																192), SKELETON(
																																		"SKELETON",
																																		24,
																																		"Skeleton",
																																		64,
																																		48,
																																		192,
																																		64), DONKEY_KONG(
																																				"DONKEY_KONG",
																																				25,
																																				"DonkeyKong",
																																				64,
																																				48,
																																				192,
																																				112);
		public static final int field_180001_A = "SkullAndRoses".length();
		public final String title;
		public final int sizeX;
		public final int sizeY;
		public final int offsetX;
		public final int offsetY;

		private EnumArt(final String p_i1598_1_, final int p_i1598_2_, final String p_i1598_3_, final int p_i1598_4_,
				final int p_i1598_5_, final int p_i1598_6_, final int p_i1598_7_) {
			title = p_i1598_3_;
			sizeX = p_i1598_4_;
			sizeY = p_i1598_5_;
			offsetX = p_i1598_6_;
			offsetY = p_i1598_7_;
		}
	}
}
