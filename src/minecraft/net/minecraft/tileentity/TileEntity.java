package net.minecraft.tileentity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockJukebox;
import net.minecraft.block.state.IBlockState;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.Map;
import java.util.concurrent.Callable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.Maps;

public abstract class TileEntity {

public static final int EaZy = 1568;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final Logger logger = LogManager.getLogger();

	/**
	 * A HashMap storing string names of classes mapping to the actual
	 * java.lang.Class type.
	 */
	private static Map nameToClassMap = Maps.newHashMap();

	/**
	 * A HashMap storing the classes and mapping to the string names (reverse of
	 * nameToClassMap).
	 */
	private static Map classToNameMap = Maps.newHashMap();

	/** the instance of the world the tile entity is in. */
	protected World worldObj;
	protected BlockPos pos;
	protected boolean tileEntityInvalid;
	private int blockMetadata;

	/** the Block type that this TileEntity is contained within */
	protected Block blockType;
	// private static final String __OBFID = "http://https://fuckuskid00000340";

	public TileEntity() {
		pos = BlockPos.ORIGIN;
		blockMetadata = -1;
	}

	/**
	 * Adds a new two-way mapping between the class and its string name in both
	 * hashmaps.
	 */
	private static void addMapping(final Class cl, final String id) {
		if (nameToClassMap.containsKey(id)) {
			throw new IllegalArgumentException("Duplicate id: " + id);
		} else {
			nameToClassMap.put(id, cl);
			classToNameMap.put(cl, id);
		}
	}

	/**
	 * Returns the worldObj for this tileEntity.
	 */
	public World getWorld() {
		return worldObj;
	}

	/**
	 * Sets the worldObj for this tileEntity.
	 */
	public void setWorldObj(final World worldIn) {
		worldObj = worldIn;
	}

	/**
	 * Returns true if the worldObj isn't null.
	 */
	public boolean hasWorldObj() {
		return worldObj != null;
	}

	public void readFromNBT(final NBTTagCompound compound) {
		pos = new BlockPos(compound.getInteger("x"), compound.getInteger("y"), compound.getInteger("z"));
	}

	public void writeToNBT(final NBTTagCompound compound) {
		final String var2 = (String) classToNameMap.get(this.getClass());

		if (var2 == null) {
			throw new RuntimeException(this.getClass() + " is missing a mapping! This is a bug!");
		} else {
			compound.setString("id", var2);
			compound.setInteger("x", pos.getX());
			compound.setInteger("y", pos.getY());
			compound.setInteger("z", pos.getZ());
		}
	}

	/**
	 * Creates a new entity and loads its data from the specified NBT.
	 */
	public static TileEntity createAndLoadEntity(final NBTTagCompound nbt) {
		TileEntity var1 = null;

		try {
			final Class var2 = (Class) nameToClassMap.get(nbt.getString("id"));

			if (var2 != null) {
				var1 = (TileEntity) var2.newInstance();
			}
		} catch (final Exception var3) {
			var3.printStackTrace();
		}

		if (var1 != null) {
			var1.readFromNBT(nbt);
		} else {
			logger.warn("Skipping BlockEntity with id " + nbt.getString("id"));
		}

		return var1;
	}

	public int getBlockMetadata() {
		if (blockMetadata == -1) {
			final IBlockState var1 = worldObj.getBlockState(pos);
			blockMetadata = var1.getBlock().getMetaFromState(var1);
		}

		return blockMetadata;
	}

	/**
	 * For tile entities, ensures the chunk containing the tile entity is saved
	 * to disk later - the game won't think it hasn't changed and skip it.
	 */
	public void markDirty() {
		if (worldObj != null) {
			final IBlockState var1 = worldObj.getBlockState(pos);
			blockMetadata = var1.getBlock().getMetaFromState(var1);
			worldObj.func_175646_b(pos, this);

			if (getBlockType() != Blocks.air) {
				worldObj.updateComparatorOutputLevel(pos, getBlockType());
			}
		}
	}

	/**
	 * Returns the square of the distance between this entity and the passed in
	 * coordinates.
	 */
	public double getDistanceSq(final double p_145835_1_, final double p_145835_3_, final double p_145835_5_) {
		final double var7 = pos.getX() + 0.5D - p_145835_1_;
		final double var9 = pos.getY() + 0.5D - p_145835_3_;
		final double var11 = pos.getZ() + 0.5D - p_145835_5_;
		return var7 * var7 + var9 * var9 + var11 * var11;
	}

	public double getMaxRenderDistanceSquared() {
		return 4096.0D;
	}

	public BlockPos getPos() {
		return pos;
	}

	/**
	 * Gets the block type at the location of this entity (client-only).
	 */
	public Block getBlockType() {
		if (blockType == null) {
			blockType = worldObj.getBlockState(pos).getBlock();
		}

		return blockType;
	}

	/**
	 * Overriden in a sign to provide the text.
	 */
	public Packet getDescriptionPacket() {
		return null;
	}

	public boolean isInvalid() {
		return tileEntityInvalid;
	}

	/**
	 * invalidates a tile entity
	 */
	public void invalidate() {
		tileEntityInvalid = true;
	}

	/**
	 * validates a tile entity
	 */
	public void validate() {
		tileEntityInvalid = false;
	}

	public boolean receiveClientEvent(final int id, final int type) {
		return false;
	}

	public void updateContainingBlockInfo() {
		blockType = null;
		blockMetadata = -1;
	}

	public void addInfoToCrashReport(final CrashReportCategory reportCategory) {
		reportCategory.addCrashSectionCallable("Name", new Callable() {
			// private static final String __OBFID =
			// "http://https://fuckuskid00000341";
			@Override
			public String call() {
				return (String) TileEntity.classToNameMap.get(TileEntity.this.getClass()) + " // "
						+ TileEntity.this.getClass().getCanonicalName();
			}
		});

		if (worldObj != null) {
			CrashReportCategory.addBlockInfo(reportCategory, pos, getBlockType(), getBlockMetadata());
			reportCategory.addCrashSectionCallable("Actual block type", new Callable() {
				// private static final String __OBFID =
				// "http://https://fuckuskid00000343";
				@Override
				public String call() {
					final int var1 = Block.getIdFromBlock(worldObj.getBlockState(pos).getBlock());

					try {
						return String.format("ID #%d (%s // %s)",
								new Object[] { var1, Block.getBlockById(var1).getUnlocalizedName(),
										Block.getBlockById(var1).getClass().getCanonicalName() });
					} catch (final Throwable var3) {
						return "ID #" + var1;
					}
				}
			});
			reportCategory.addCrashSectionCallable("Actual block data value", new Callable() {
				// private static final String __OBFID =
				// "http://https://fuckuskid00000344";
				@Override
				public String call() {
					final IBlockState var1 = worldObj.getBlockState(pos);
					final int var2 = var1.getBlock().getMetaFromState(var1);

					if (var2 < 0) {
						return "Unknown? (Got " + var2 + ")";
					} else {
						final String var3 = String.format("%4s", new Object[] { Integer.toBinaryString(var2) })
								.replace(" ", "0");
						return String.format("%1$d / 0x%1$X / 0b%2$s", new Object[] { var2, var3 });
					}
				}
			});
		}
	}

	public void setPos(final BlockPos posIn) {
		pos = posIn;
	}

	static {
		addMapping(TileEntityFurnace.class, "Furnace");
		addMapping(TileEntityChest.class, "Chest");
		addMapping(TileEntityEnderChest.class, "EnderChest");
		addMapping(BlockJukebox.TileEntityJukebox.class, "RecordPlayer");
		addMapping(TileEntityDispenser.class, "Trap");
		addMapping(TileEntityDropper.class, "Dropper");
		addMapping(TileEntitySign.class, "Sign");
		addMapping(TileEntityMobSpawner.class, "MobSpawner");
		addMapping(TileEntityNote.class, "Music");
		addMapping(TileEntityPiston.class, "Piston");
		addMapping(TileEntityBrewingStand.class, "Cauldron");
		addMapping(TileEntityEnchantmentTable.class, "EnchantTable");
		addMapping(TileEntityEndPortal.class, "Airportal");
		addMapping(TileEntityCommandBlock.class, "Control");
		addMapping(TileEntityBeacon.class, "Beacon");
		addMapping(TileEntitySkull.class, "Skull");
		addMapping(TileEntityDaylightDetector.class, "DLDetector");
		addMapping(TileEntityHopper.class, "Hopper");
		addMapping(TileEntityComparator.class, "Comparator");
		addMapping(TileEntityFlowerPot.class, "FlowerPot");
		addMapping(TileEntityBanner.class, "Banner");
	}
}
