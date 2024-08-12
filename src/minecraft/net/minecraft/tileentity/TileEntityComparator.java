package net.minecraft.tileentity;

import net.minecraft.nbt.NBTTagCompound;

public class TileEntityComparator extends TileEntity {

public static final int EaZy = 1574;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private int outputSignal;
	// private static final String __OBFID = "http://https://fuckuskid00000349";

	@Override
	public void writeToNBT(final NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setInteger("OutputSignal", outputSignal);
	}

	@Override
	public void readFromNBT(final NBTTagCompound compound) {
		super.readFromNBT(compound);
		outputSignal = compound.getInteger("OutputSignal");
	}

	public int getOutputSignal() {
		return outputSignal;
	}

	public void setOutputSignal(final int p_145995_1_) {
		outputSignal = p_145995_1_;
	}
}
