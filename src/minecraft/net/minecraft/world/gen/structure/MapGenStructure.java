package net.minecraft.world.gen.structure;

import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.LongHashMap;
import net.minecraft.util.ReportedException;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.MapGenBase;
import net.minecraft.world.storage.MapStorage;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Callable;

import com.google.common.collect.Maps;

import optifine.Reflector;

public abstract class MapGenStructure extends MapGenBase {

public static final int EaZy = 1812;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private MapGenStructureData field_143029_e;

	/**
	 * Used to store a list of all structures that have been recursively
	 * generated. Used so that during recursive generation, the structure
	 * generator can avoid generating structures that intersect ones that have
	 * already been placed.
	 */
	protected Map structureMap = Maps.newHashMap();
	// private static final String __OBFID = "http://https://fuckuskid00000505";
	private final LongHashMap structureLongMap = new LongHashMap();

	public abstract String getStructureName();

	@Override
	protected final void func_180701_a(final World worldIn, final int p_180701_2_, final int p_180701_3_,
			final int p_180701_4_, final int p_180701_5_, final ChunkPrimer p_180701_6_) {
		func_143027_a(worldIn);

		if (!structureLongMap.containsItem(ChunkCoordIntPair.chunkXZ2Int(p_180701_2_, p_180701_3_))) {
			rand.nextInt();

			try {
				if (canSpawnStructureAtCoords(p_180701_2_, p_180701_3_)) {
					final StructureStart var10 = getStructureStart(p_180701_2_, p_180701_3_);
					structureMap.put(ChunkCoordIntPair.chunkXZ2Int(p_180701_2_, p_180701_3_), var10);
					structureLongMap.add(ChunkCoordIntPair.chunkXZ2Int(p_180701_2_, p_180701_3_), var10);
					func_143026_a(p_180701_2_, p_180701_3_, var10);
				}
			} catch (final Throwable var101) {
				final CrashReport var8 = CrashReport.makeCrashReport(var101, "Exception preparing structure feature");
				final CrashReportCategory var9 = var8.makeCategory("Feature being prepared");
				var9.addCrashSectionCallable("Is feature chunk", new Callable() {
					// private static final String __OBFID =
					// "http://https://fuckuskid00000506";
					@Override
					public String call() {
						return MapGenStructure.this.canSpawnStructureAtCoords(p_180701_2_, p_180701_3_) ? "True"
								: "False";
					}
				});
				var9.addCrashSection("Chunk location", String.format("%d,%d",
						new Object[] { p_180701_2_, p_180701_3_}));
				var9.addCrashSectionCallable("Chunk pos hash", new Callable() {
					// private static final String __OBFID =
					// "http://https://fuckuskid00000507";
					@Override
					public String call() {
						return String.valueOf(ChunkCoordIntPair.chunkXZ2Int(p_180701_2_, p_180701_3_));
					}
				});
				var9.addCrashSectionCallable("Structure type", new Callable() {
					@Override
					public String call() {
						return MapGenStructure.this.getClass().getCanonicalName();
					}
				});
				throw new ReportedException(var8);
			}
		}
	}

	public boolean func_175794_a(final World worldIn, final Random p_175794_2_, final ChunkCoordIntPair p_175794_3_) {
		func_143027_a(worldIn);
		final int var4 = (p_175794_3_.chunkXPos << 4) + 8;
		final int var5 = (p_175794_3_.chunkZPos << 4) + 8;
		boolean var6 = false;
		final Iterator var7 = structureMap.values().iterator();

		while (var7.hasNext()) {
			final StructureStart var8 = (StructureStart) var7.next();

			if (var8.isSizeableStructure() && var8.func_175788_a(p_175794_3_)
					&& var8.getBoundingBox().intersectsWith(var4, var5, var4 + 15, var5 + 15)) {
				var8.generateStructure(worldIn, p_175794_2_,
						new StructureBoundingBox(var4, var5, var4 + 15, var5 + 15));
				var8.func_175787_b(p_175794_3_);
				var6 = true;
				func_143026_a(var8.func_143019_e(), var8.func_143018_f(), var8);
			}
		}

		return var6;
	}

	public boolean func_175795_b(final BlockPos p_175795_1_) {
		func_143027_a(worldObj);
		return func_175797_c(p_175795_1_) != null;
	}

	protected StructureStart func_175797_c(final BlockPos p_175797_1_) {
		final Iterator var2 = structureMap.values().iterator();

		while (var2.hasNext()) {
			final StructureStart var3 = (StructureStart) var2.next();

			if (var3.isSizeableStructure() && var3.getBoundingBox().func_175898_b(p_175797_1_)) {
				final Iterator var4 = var3.getComponents().iterator();

				while (var4.hasNext()) {
					final StructureComponent var5 = (StructureComponent) var4.next();

					if (var5.getBoundingBox().func_175898_b(p_175797_1_)) {
						return var3;
					}
				}
			}
		}

		return null;
	}

	public boolean func_175796_a(final World worldIn, final BlockPos p_175796_2_) {
		func_143027_a(worldIn);
		final Iterator var3 = structureMap.values().iterator();
		StructureStart var4;

		do {
			if (!var3.hasNext()) {
				return false;
			}

			var4 = (StructureStart) var3.next();
		}
		while (!var4.isSizeableStructure() || !var4.getBoundingBox().func_175898_b(p_175796_2_));

		return true;
	}

	public BlockPos func_180706_b(final World worldIn, final BlockPos p_180706_2_) {
		worldObj = worldIn;
		func_143027_a(worldIn);
		rand.setSeed(worldIn.getSeed());
		final long var3 = rand.nextLong();
		final long var5 = rand.nextLong();
		final long var7 = (p_180706_2_.getX() >> 4) * var3;
		final long var9 = (p_180706_2_.getZ() >> 4) * var5;
		rand.setSeed(var7 ^ var9 ^ worldIn.getSeed());
		func_180701_a(worldIn, p_180706_2_.getX() >> 4, p_180706_2_.getZ() >> 4, 0, 0, (ChunkPrimer) null);
		double var11 = Double.MAX_VALUE;
		BlockPos var13 = null;
		final Iterator var14 = structureMap.values().iterator();
		BlockPos var17;
		double var18;

		while (var14.hasNext()) {
			final StructureStart var20 = (StructureStart) var14.next();

			if (var20.isSizeableStructure()) {
				final StructureComponent var21 = (StructureComponent) var20.getComponents().get(0);
				var17 = var21.func_180776_a();
				var18 = var17.distanceSq(p_180706_2_);

				if (var18 < var11) {
					var11 = var18;
					var13 = var17;
				}
			}
		}

		if (var13 != null) {
			return var13;
		} else {
			final List var201 = getCoordList();

			if (var201 != null) {
				BlockPos var211 = null;
				final Iterator var22 = var201.iterator();

				while (var22.hasNext()) {
					var17 = (BlockPos) var22.next();
					var18 = var17.distanceSq(p_180706_2_);

					if (var18 < var11) {
						var11 = var18;
						var211 = var17;
					}
				}

				return var211;
			} else {
				return null;
			}
		}
	}

	/**
	 * Returns a list of other locations at which the structure generation has
	 * been run, or null if not relevant to this structure generator.
	 */
	protected List getCoordList() {
		return null;
	}

	private void func_143027_a(final World worldIn) {
		if (field_143029_e == null) {
			MapStorage var2;

			if (Reflector.ForgeWorld_getPerWorldStorage.exists()) {
				var2 = (MapStorage) Reflector.call(worldIn, Reflector.ForgeWorld_getPerWorldStorage, new Object[0]);
				field_143029_e = (MapGenStructureData) var2.loadData(MapGenStructureData.class, getStructureName());
			} else {
				field_143029_e = (MapGenStructureData) worldIn.loadItemData(MapGenStructureData.class,
						getStructureName());
			}

			if (field_143029_e == null) {
				field_143029_e = new MapGenStructureData(getStructureName());

				if (Reflector.ForgeWorld_getPerWorldStorage.exists()) {
					var2 = (MapStorage) Reflector.call(worldIn, Reflector.ForgeWorld_getPerWorldStorage, new Object[0]);
					var2.setData(getStructureName(), field_143029_e);
				} else {
					worldIn.setItemData(getStructureName(), field_143029_e);
				}
			} else {
				final NBTTagCompound var21 = field_143029_e.func_143041_a();
				final Iterator var3 = var21.getKeySet().iterator();

				while (var3.hasNext()) {
					final String var4 = (String) var3.next();
					final NBTBase var5 = var21.getTag(var4);

					if (var5.getId() == 10) {
						final NBTTagCompound var6 = (NBTTagCompound) var5;

						if (var6.hasKey("ChunkX") && var6.hasKey("ChunkZ")) {
							final int var7 = var6.getInteger("ChunkX");
							final int var8 = var6.getInteger("ChunkZ");
							final StructureStart var9 = MapGenStructureIO.func_143035_a(var6, worldIn);

							if (var9 != null) {
								structureMap.put(ChunkCoordIntPair.chunkXZ2Int(var7, var8), var9);
								structureLongMap.add(ChunkCoordIntPair.chunkXZ2Int(var7, var8), var9);
							}
						}
					}
				}
			}
		}
	}

	private void func_143026_a(final int p_143026_1_, final int p_143026_2_, final StructureStart p_143026_3_) {
		field_143029_e.func_143043_a(p_143026_3_.func_143021_a(p_143026_1_, p_143026_2_), p_143026_1_, p_143026_2_);
		field_143029_e.markDirty();
	}

	protected abstract boolean canSpawnStructureAtCoords(int var1, int var2);

	protected abstract StructureStart getStructureStart(int var1, int var2);
}
