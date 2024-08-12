package net.minecraft.world.gen.structure;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.WorldSavedData;

public class MapGenStructureData extends WorldSavedData {

public static final int EaZy = 1813;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private NBTTagCompound field_143044_a = new NBTTagCompound();
	// private static final String __OBFID = "http://https://fuckuskid00000510";

	public MapGenStructureData(final String p_i43001_1_) {
		super(p_i43001_1_);
	}

	/**
	 * reads in data from the NBTTagCompound into this MapDataBase
	 */
	@Override
	public void readFromNBT(final NBTTagCompound nbt) {
		field_143044_a = nbt.getCompoundTag("Features");
	}

	/**
	 * write data to NBTTagCompound from this MapDataBase, similar to Entities
	 * and TileEntities
	 */
	@Override
	public void writeToNBT(final NBTTagCompound nbt) {
		nbt.setTag("Features", field_143044_a);
	}

	public void func_143043_a(final NBTTagCompound p_143043_1_, final int p_143043_2_, final int p_143043_3_) {
		field_143044_a.setTag(func_143042_b(p_143043_2_, p_143043_3_), p_143043_1_);
	}

	public static String func_143042_b(final int p_143042_0_, final int p_143042_1_) {
		return "[" + p_143042_0_ + "," + p_143042_1_ + "]";
	}

	public NBTTagCompound func_143041_a() {
		return field_143044_a;
	}
}
