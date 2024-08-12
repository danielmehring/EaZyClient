package me.EaZy.client.utils;

import net.minecraft.block.Block;

public class XRayUtils {

	public static final int EaZy = 252;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	public static boolean isXRayBlock(Block blockIn) {
		if (blockIn == Block.getBlockById(16)) {
			return true;
		} else if (blockIn == Block.getBlockById(56)) {
			return true;
		} else if (blockIn == Block.getBlockById(14)) {
			return true;
		} else if (blockIn == Block.getBlockById(15)) {
			return true;
		} else if (blockIn == Block.getBlockById(73)) {
			return true;
		} else if (blockIn == Block.getBlockById(21)) {
			return true;
		} else if (blockIn == Block.getBlockById(129)) {
			return true;
		} else if (blockIn == Block.getBlockById(7))
			return true;
		else
			return false;
	}

}
