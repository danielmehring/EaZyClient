package net.minecraft.world.pathfinder;

import net.minecraft.entity.Entity;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.IntHashMap;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;

public abstract class NodeProcessor {

public static final int EaZy = 1834;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	protected IBlockAccess field_176169_a;
	protected IntHashMap field_176167_b = new IntHashMap();
	protected int field_176168_c;
	protected int field_176165_d;
	protected int field_176166_e;
	// private static final String __OBFID = "http://https://fuckuskid00001967";

	public void func_176162_a(final IBlockAccess p_176162_1_, final Entity p_176162_2_) {
		field_176169_a = p_176162_1_;
		field_176167_b.clearMap();
		field_176168_c = MathHelper.floor_float(p_176162_2_.width + 1.0F);
		field_176165_d = MathHelper.floor_float(p_176162_2_.height + 1.0F);
		field_176166_e = MathHelper.floor_float(p_176162_2_.width + 1.0F);
	}

	public void func_176163_a() {}

	protected PathPoint func_176159_a(final int p_176159_1_, final int p_176159_2_, final int p_176159_3_) {
		final int var4 = PathPoint.makeHash(p_176159_1_, p_176159_2_, p_176159_3_);
		PathPoint var5 = (PathPoint) field_176167_b.lookup(var4);

		if (var5 == null) {
			var5 = new PathPoint(p_176159_1_, p_176159_2_, p_176159_3_);
			field_176167_b.addKey(var4, var5);
		}

		return var5;
	}

	public abstract PathPoint func_176161_a(Entity var1);

	public abstract PathPoint func_176160_a(Entity var1, double var2, double var4, double var6);

	public abstract int func_176164_a(PathPoint[] var1, Entity var2, PathPoint var3, PathPoint var4, float var5);
}
