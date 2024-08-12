package net.minecraft.tileentity;

import net.minecraft.block.BlockDaylightDetector;
import net.minecraft.server.gui.IUpdatePlayerListBox;

public class TileEntityDaylightDetector extends TileEntity implements IUpdatePlayerListBox {

public static final int EaZy = 1575;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00000350";

	/**
	 * Updates the JList with a new model.
	 */
	@Override
	public void update() {
		if (worldObj != null && !worldObj.isRemote && worldObj.getTotalWorldTime() % 20L == 0L) {
			blockType = getBlockType();

			if (blockType instanceof BlockDaylightDetector) {
				((BlockDaylightDetector) blockType).func_180677_d(worldObj, pos);
			}
		}
	}
}
