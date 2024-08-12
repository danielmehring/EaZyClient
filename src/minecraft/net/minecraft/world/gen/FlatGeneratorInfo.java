package net.minecraft.world.gen;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.biome.BiomeGenBase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class FlatGeneratorInfo {

public static final int EaZy = 1773;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	/** List of layers on this preset. */
	private final List flatLayers = Lists.newArrayList();

	/** List of world features enabled on this preset. */
	private final Map worldFeatures = Maps.newHashMap();
	private int biomeToUse;
	// private static final String __OBFID = "http://https://fuckuskid00000440";

	/**
	 * Return the biome used on this preset.
	 */
	public int getBiome() {
		return biomeToUse;
	}

	/**
	 * Set the biome used on this preset.
	 */
	public void setBiome(final int p_82647_1_) {
		biomeToUse = p_82647_1_;
	}

	/**
	 * Return the list of world features enabled on this preset.
	 */
	public Map getWorldFeatures() {
		return worldFeatures;
	}

	/**
	 * Return the list of layers on this preset.
	 */
	public List getFlatLayers() {
		return flatLayers;
	}

	public void func_82645_d() {
		int var1 = 0;
		FlatLayerInfo var3;

		for (final Iterator var2 = flatLayers.iterator(); var2.hasNext(); var1 += var3.getLayerCount()) {
			var3 = (FlatLayerInfo) var2.next();
			var3.setMinY(var1);
		}
	}

	@Override
	public String toString() {
		final StringBuilder var1 = new StringBuilder();
		var1.append(3);
		var1.append(";");
		int var2;

		for (var2 = 0; var2 < flatLayers.size(); ++var2) {
			if (var2 > 0) {
				var1.append(",");
			}

			var1.append(((FlatLayerInfo) flatLayers.get(var2)).toString());
		}

		var1.append(";");
		var1.append(biomeToUse);

		if (!worldFeatures.isEmpty()) {
			var1.append(";");
			var2 = 0;
			final Iterator var3 = worldFeatures.entrySet().iterator();

			while (var3.hasNext()) {
				final Entry var4 = (Entry) var3.next();

				if (var2++ > 0) {
					var1.append(",");
				}

				var1.append(((String) var4.getKey()).toLowerCase());
				final Map var5 = (Map) var4.getValue();

				if (!var5.isEmpty()) {
					var1.append("(");
					int var6 = 0;
					final Iterator var7 = var5.entrySet().iterator();

					while (var7.hasNext()) {
						final Entry var8 = (Entry) var7.next();

						if (var6++ > 0) {
							var1.append(" ");
						}

						var1.append((String) var8.getKey());
						var1.append("=");
						var1.append((String) var8.getValue());
					}

					var1.append(")");
				}
			}
		} else {
			var1.append(";");
		}

		return var1.toString();
	}

	private static FlatLayerInfo func_180715_a(final int p_180715_0_, final String p_180715_1_, final int p_180715_2_) {
		String[] var3 = p_180715_0_ >= 3 ? p_180715_1_.split("\\*", 2) : p_180715_1_.split("x", 2);
		int var4 = 1;
		int var5 = 0;

		if (var3.length == 2) {
			try {
				var4 = Integer.parseInt(var3[0]);

				if (p_180715_2_ + var4 >= 256) {
					var4 = 256 - p_180715_2_;
				}

				if (var4 < 0) {
					var4 = 0;
				}
			} catch (final Throwable var8) {
				return null;
			}
		}

		Block var6 = null;

		try {
			final String var7 = var3[var3.length - 1];

			if (p_180715_0_ < 3) {
				var3 = var7.split(":", 2);

				if (var3.length > 1) {
					var5 = Integer.parseInt(var3[1]);
				}

				var6 = Block.getBlockById(Integer.parseInt(var3[0]));
			} else {
				var3 = var7.split(":", 3);
				var6 = var3.length > 1 ? Block.getBlockFromName(var3[0] + ":" + var3[1]) : null;

				if (var6 != null) {
					var5 = var3.length > 2 ? Integer.parseInt(var3[2]) : 0;
				} else {
					var6 = Block.getBlockFromName(var3[0]);

					if (var6 != null) {
						var5 = var3.length > 1 ? Integer.parseInt(var3[1]) : 0;
					}
				}

				if (var6 == null) {
					return null;
				}
			}

			if (var6 == Blocks.air) {
				var5 = 0;
			}

			if (var5 < 0 || var5 > 15) {
				var5 = 0;
			}
		} catch (final Throwable var9) {
			return null;
		}

		final FlatLayerInfo var10 = new FlatLayerInfo(p_180715_0_, var4, var6, var5);
		var10.setMinY(p_180715_2_);
		return var10;
	}

	private static List func_180716_a(final int p_180716_0_, final String p_180716_1_) {
		if (p_180716_1_ != null && p_180716_1_.length() >= 1) {
			final ArrayList var2 = Lists.newArrayList();
			final String[] var3 = p_180716_1_.split(",");
			int var4 = 0;
			final String[] var5 = var3;
			final int var6 = var3.length;

			for (int var7 = 0; var7 < var6; ++var7) {
				final String var8 = var5[var7];
				final FlatLayerInfo var9 = func_180715_a(p_180716_0_, var8, var4);

				if (var9 == null) {
					return null;
				}

				var2.add(var9);
				var4 += var9.getLayerCount();
			}

			return var2;
		} else {
			return null;
		}
	}

	public static FlatGeneratorInfo createFlatGeneratorFromString(final String p_82651_0_) {
		if (p_82651_0_ == null) {
			return getDefaultFlatGenerator();
		} else {
			final String[] var1 = p_82651_0_.split(";", -1);
			final int var2 = var1.length == 1 ? 0 : MathHelper.parseIntWithDefault(var1[0], 0);

			if (var2 >= 0 && var2 <= 3) {
				final FlatGeneratorInfo var3 = new FlatGeneratorInfo();
				int var4 = var1.length == 1 ? 0 : 1;
				final List var5 = func_180716_a(var2, var1[var4++]);

				if (var5 != null && !var5.isEmpty()) {
					var3.getFlatLayers().addAll(var5);
					var3.func_82645_d();
					int var6 = BiomeGenBase.plains.biomeID;

					if (var2 > 0 && var1.length > var4) {
						var6 = MathHelper.parseIntWithDefault(var1[var4++], var6);
					}

					var3.setBiome(var6);

					if (var2 > 0 && var1.length > var4) {
						final String[] var7 = var1[var4++].toLowerCase().split(",");
						final String[] var8 = var7;
						final int var9 = var7.length;

						for (int var10 = 0; var10 < var9; ++var10) {
							final String var11 = var8[var10];
							final String[] var12 = var11.split("\\(", 2);
							final HashMap var13 = Maps.newHashMap();

							if (var12[0].length() > 0) {
								var3.getWorldFeatures().put(var12[0], var13);

								if (var12.length > 1 && var12[1].endsWith(")") && var12[1].length() > 1) {
									final String[] var14 = var12[1].substring(0, var12[1].length() - 1).split(" ");

									for (final String element : var14) {
										final String[] var16 = element.split("=", 2);

										if (var16.length == 2) {
											var13.put(var16[0], var16[1]);
										}
									}
								}
							}
						}
					} else {
						var3.getWorldFeatures().put("village", Maps.newHashMap());
					}

					return var3;
				} else {
					return getDefaultFlatGenerator();
				}
			} else {
				return getDefaultFlatGenerator();
			}
		}
	}

	public static FlatGeneratorInfo getDefaultFlatGenerator() {
		final FlatGeneratorInfo var0 = new FlatGeneratorInfo();
		var0.setBiome(BiomeGenBase.plains.biomeID);
		var0.getFlatLayers().add(new FlatLayerInfo(1, Blocks.bedrock));
		var0.getFlatLayers().add(new FlatLayerInfo(2, Blocks.dirt));
		var0.getFlatLayers().add(new FlatLayerInfo(1, Blocks.grass));
		var0.func_82645_d();
		var0.getWorldFeatures().put("village", Maps.newHashMap());
		return var0;
	}
}
