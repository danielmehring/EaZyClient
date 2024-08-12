package net.minecraft.world.gen.structure;

import net.minecraft.util.MathHelper;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class MapGenMineshaft extends MapGenStructure {

public static final int EaZy = 1808;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private double field_82673_e = 0.004D;
	// private static final String __OBFID = "http://https://fuckuskid00000443";

	public MapGenMineshaft() {}

	@Override
	public String getStructureName() {
		return "Mineshaft";
	}

	public MapGenMineshaft(final Map p_i2034_1_) {
		final Iterator var2 = p_i2034_1_.entrySet().iterator();

		while (var2.hasNext()) {
			final Entry var3 = (Entry) var2.next();

			if (((String) var3.getKey()).equals("chance")) {
				field_82673_e = MathHelper.parseDoubleWithDefault((String) var3.getValue(), field_82673_e);
			}
		}
	}

	@Override
	protected boolean canSpawnStructureAtCoords(final int p_75047_1_, final int p_75047_2_) {
		return rand.nextDouble() < field_82673_e
				&& rand.nextInt(80) < Math.max(Math.abs(p_75047_1_), Math.abs(p_75047_2_));
	}

	@Override
	protected StructureStart getStructureStart(final int p_75049_1_, final int p_75049_2_) {
		return new StructureMineshaftStart(worldObj, rand, p_75049_1_, p_75049_2_);
	}
}
