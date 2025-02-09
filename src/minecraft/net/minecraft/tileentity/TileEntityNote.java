package net.minecraft.tileentity;

import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class TileEntityNote extends TileEntity {

public static final int EaZy = 1586;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	/** Note to play */
	public byte note;

	/** stores the latest redstone state */
	public boolean previousRedstoneState;
	// private static final String __OBFID = "http://https://fuckuskid00000362";

	@Override
	public void writeToNBT(final NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setByte("note", note);
	}

	@Override
	public void readFromNBT(final NBTTagCompound compound) {
		super.readFromNBT(compound);
		note = compound.getByte("note");
		note = (byte) MathHelper.clamp_int(note, 0, 24);
	}

	/**
	 * change pitch by -> (currentPitch + 1) % 25
	 */
	public void changePitch() {
		note = (byte) ((note + 1) % 25);
		markDirty();
	}

	public void func_175108_a(final World worldIn, final BlockPos p_175108_2_) {
		if (worldIn.getBlockState(p_175108_2_.offsetUp()).getBlock().getMaterial() == Material.air) {
			final Material var3 = worldIn.getBlockState(p_175108_2_.offsetDown()).getBlock().getMaterial();
			byte var4 = 0;

			if (var3 == Material.rock) {
				var4 = 1;
			}

			if (var3 == Material.sand) {
				var4 = 2;
			}

			if (var3 == Material.glass) {
				var4 = 3;
			}

			if (var3 == Material.wood) {
				var4 = 4;
			}

			worldIn.addBlockEvent(p_175108_2_, Blocks.noteblock, var4, note);
		}
	}
}
