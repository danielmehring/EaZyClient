package net.minecraft.world.storage;

import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S34PacketMaps;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec4b;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class MapData extends WorldSavedData {

public static final int EaZy = 1843;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public int xCenter;
	public int zCenter;
	public byte dimension;
	public byte scale;

	/** colours */
	public byte[] colors = new byte[16384];

	/**
	 * Holds a reference to the MapInfo of the players who own a copy of the map
	 */
	public List playersArrayList = Lists.newArrayList();

	/**
	 * Holds a reference to the players who own a copy of the map and a
	 * reference to their MapInfo
	 */
	private final Map playersHashMap = Maps.newHashMap();
	public Map playersVisibleOnMap = Maps.newLinkedHashMap();
	// private static final String __OBFID = "http://https://fuckuskid00000577";

	public MapData(final String p_i2140_1_) {
		super(p_i2140_1_);
	}

	public void func_176054_a(final double p_176054_1_, final double p_176054_3_, final int p_176054_5_) {
		final int var6 = 128 * (1 << p_176054_5_);
		final int var7 = MathHelper.floor_double((p_176054_1_ + 64.0D) / var6);
		final int var8 = MathHelper.floor_double((p_176054_3_ + 64.0D) / var6);
		xCenter = var7 * var6 + var6 / 2 - 64;
		zCenter = var8 * var6 + var6 / 2 - 64;
	}

	/**
	 * reads in data from the NBTTagCompound into this MapDataBase
	 */
	@Override
	public void readFromNBT(final NBTTagCompound nbt) {
		dimension = nbt.getByte("dimension");
		xCenter = nbt.getInteger("xCenter");
		zCenter = nbt.getInteger("zCenter");
		scale = nbt.getByte("scale");
		scale = (byte) MathHelper.clamp_int(scale, 0, 4);
		final short var2 = nbt.getShort("width");
		final short var3 = nbt.getShort("height");

		if (var2 == 128 && var3 == 128) {
			colors = nbt.getByteArray("colors");
		} else {
			final byte[] var4 = nbt.getByteArray("colors");
			colors = new byte[16384];
			final int var5 = (128 - var2) / 2;
			final int var6 = (128 - var3) / 2;

			for (int var7 = 0; var7 < var3; ++var7) {
				final int var8 = var7 + var6;

				if (var8 >= 0 || var8 < 128) {
					for (int var9 = 0; var9 < var2; ++var9) {
						final int var10 = var9 + var5;

						if (var10 >= 0 || var10 < 128) {
							colors[var10 + var8 * 128] = var4[var9 + var7 * var2];
						}
					}
				}
			}
		}
	}

	/**
	 * write data to NBTTagCompound from this MapDataBase, similar to Entities
	 * and TileEntities
	 */
	@Override
	public void writeToNBT(final NBTTagCompound nbt) {
		nbt.setByte("dimension", dimension);
		nbt.setInteger("xCenter", xCenter);
		nbt.setInteger("zCenter", zCenter);
		nbt.setByte("scale", scale);
		nbt.setShort("width", (short) 128);
		nbt.setShort("height", (short) 128);
		nbt.setByteArray("colors", colors);
	}

	/**
	 * Adds the player passed to the list of visible players and checks to see
	 * which players are visible
	 */
	public void updateVisiblePlayers(final EntityPlayer p_76191_1_, final ItemStack p_76191_2_) {
		if (!playersHashMap.containsKey(p_76191_1_)) {
			final MapData.MapInfo var3 = new MapData.MapInfo(p_76191_1_);
			playersHashMap.put(p_76191_1_, var3);
			playersArrayList.add(var3);
		}

		if (!p_76191_1_.inventory.hasItemStack(p_76191_2_)) {
			playersVisibleOnMap.remove(p_76191_1_.getName());
		}

		for (int var6 = 0; var6 < playersArrayList.size(); ++var6) {
			final MapData.MapInfo var4 = (MapData.MapInfo) playersArrayList.get(var6);

			if (!var4.entityplayerObj.isDead
					&& (var4.entityplayerObj.inventory.hasItemStack(p_76191_2_) || p_76191_2_.isOnItemFrame())) {
				if (!p_76191_2_.isOnItemFrame() && var4.entityplayerObj.dimension == dimension) {
					func_82567_a(0, var4.entityplayerObj.worldObj, var4.entityplayerObj.getName(),
							var4.entityplayerObj.posX, var4.entityplayerObj.posZ, var4.entityplayerObj.rotationYaw);
				}
			} else {
				playersHashMap.remove(var4.entityplayerObj);
				playersArrayList.remove(var4);
			}
		}

		if (p_76191_2_.isOnItemFrame()) {
			final EntityItemFrame var7 = p_76191_2_.getItemFrame();
			final BlockPos var9 = var7.func_174857_n();
			func_82567_a(1, p_76191_1_.worldObj, "frame-" + var7.getEntityId(), var9.getX(), var9.getZ(),
					var7.field_174860_b.getHorizontalIndex() * 90);
		}

		if (p_76191_2_.hasTagCompound() && p_76191_2_.getTagCompound().hasKey("Decorations", 9)) {
			final NBTTagList var8 = p_76191_2_.getTagCompound().getTagList("Decorations", 10);

			for (int var10 = 0; var10 < var8.tagCount(); ++var10) {
				final NBTTagCompound var5 = var8.getCompoundTagAt(var10);

				if (!playersVisibleOnMap.containsKey(var5.getString("id"))) {
					func_82567_a(var5.getByte("type"), p_76191_1_.worldObj, var5.getString("id"), var5.getDouble("x"),
							var5.getDouble("z"), var5.getDouble("rot"));
				}
			}
		}
	}

	private void func_82567_a(int p_82567_1_, final World worldIn, final String p_82567_3_, final double p_82567_4_,
			final double p_82567_6_, double p_82567_8_) {
		final int var10 = 1 << scale;
		final float var11 = (float) (p_82567_4_ - xCenter) / var10;
		final float var12 = (float) (p_82567_6_ - zCenter) / var10;
		byte var13 = (byte) (int) (var11 * 2.0F + 0.5D);
		byte var14 = (byte) (int) (var12 * 2.0F + 0.5D);
		final byte var16 = 63;
		byte var15;

		if (var11 >= -var16 && var12 >= -var16 && var11 <= var16 && var12 <= var16) {
			p_82567_8_ += p_82567_8_ < 0.0D ? -8.0D : 8.0D;
			var15 = (byte) (int) (p_82567_8_ * 16.0D / 360.0D);

			if (dimension < 0) {
				final int var17 = (int) (worldIn.getWorldInfo().getWorldTime() / 10L);
				var15 = (byte) (var17 * var17 * 34187121 + var17 * 121 >> 15 & 15);
			}
		} else {
			if (Math.abs(var11) >= 320.0F || Math.abs(var12) >= 320.0F) {
				playersVisibleOnMap.remove(p_82567_3_);
				return;
			}

			p_82567_1_ = 6;
			var15 = 0;

			if (var11 <= -var16) {
				var13 = (byte) (int) (var16 * 2 + 2.5D);
			}

			if (var12 <= -var16) {
				var14 = (byte) (int) (var16 * 2 + 2.5D);
			}

			if (var11 >= var16) {
				var13 = (byte) (var16 * 2 + 1);
			}

			if (var12 >= var16) {
				var14 = (byte) (var16 * 2 + 1);
			}
		}

		playersVisibleOnMap.put(p_82567_3_, new Vec4b((byte) p_82567_1_, var13, var14, var15));
	}

	public Packet func_176052_a(final ItemStack p_176052_1_, final World worldIn, final EntityPlayer p_176052_3_) {
		final MapData.MapInfo var4 = (MapData.MapInfo) playersHashMap.get(p_176052_3_);
		return var4 == null ? null : var4.func_176101_a(p_176052_1_);
	}

	public void func_176053_a(final int p_176053_1_, final int p_176053_2_) {
		super.markDirty();
		final Iterator var3 = playersArrayList.iterator();

		while (var3.hasNext()) {
			final MapData.MapInfo var4 = (MapData.MapInfo) var3.next();
			var4.func_176102_a(p_176053_1_, p_176053_2_);
		}
	}

	public MapData.MapInfo func_82568_a(final EntityPlayer p_82568_1_) {
		MapData.MapInfo var2 = (MapData.MapInfo) playersHashMap.get(p_82568_1_);

		if (var2 == null) {
			var2 = new MapData.MapInfo(p_82568_1_);
			playersHashMap.put(p_82568_1_, var2);
			playersArrayList.add(var2);
		}

		return var2;
	}

	public class MapInfo {
		public final EntityPlayer entityplayerObj;
		private boolean field_176105_d = true;
		private int field_176106_e = 0;
		private int field_176103_f = 0;
		private int field_176104_g = 127;
		private int field_176108_h = 127;
		private int field_176109_i;
		public int field_82569_d;
		// private static final String __OBFID =
		// "http://https://fuckuskid00000578";

		public MapInfo(final EntityPlayer p_i2138_2_) {
			entityplayerObj = p_i2138_2_;
		}

		public Packet func_176101_a(final ItemStack p_176101_1_) {
			if (field_176105_d) {
				field_176105_d = false;
				return new S34PacketMaps(p_176101_1_.getMetadata(), scale, playersVisibleOnMap.values(), colors,
						field_176106_e, field_176103_f, field_176104_g + 1 - field_176106_e,
						field_176108_h + 1 - field_176103_f);
			} else {
				return field_176109_i++ % 5 == 0 ? new S34PacketMaps(p_176101_1_.getMetadata(), scale,
						playersVisibleOnMap.values(), colors, 0, 0, 0, 0) : null;
			}
		}

		public void func_176102_a(final int p_176102_1_, final int p_176102_2_) {
			if (field_176105_d) {
				field_176106_e = Math.min(field_176106_e, p_176102_1_);
				field_176103_f = Math.min(field_176103_f, p_176102_2_);
				field_176104_g = Math.max(field_176104_g, p_176102_1_);
				field_176108_h = Math.max(field_176108_h, p_176102_2_);
			} else {
				field_176105_d = true;
				field_176106_e = p_176102_1_;
				field_176103_f = p_176102_2_;
				field_176104_g = p_176102_1_;
				field_176108_h = p_176102_2_;
			}
		}
	}
}
