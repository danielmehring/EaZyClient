package net.minecraft.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.ILockableContainer;
import net.minecraft.world.LockCode;

public abstract class TileEntityLockable extends TileEntity implements IInteractionObject, ILockableContainer {

public static final int EaZy = 1584;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private LockCode code;
	// private static final String __OBFID = "http://https://fuckuskid00002040";

	public TileEntityLockable() {
		code = LockCode.EMPTY_CODE;
	}

	@Override
	public void readFromNBT(final NBTTagCompound compound) {
		super.readFromNBT(compound);
		code = LockCode.fromNBT(compound);
	}

	@Override
	public void writeToNBT(final NBTTagCompound compound) {
		super.writeToNBT(compound);

		if (code != null) {
			code.toNBT(compound);
		}
	}

	@Override
	public boolean isLocked() {
		return code != null && !code.isEmpty();
	}

	@Override
	public LockCode getLockCode() {
		return code;
	}

	@Override
	public void setLockCode(final LockCode code) {
		this.code = code;
	}

	@Override
	public IChatComponent getDisplayName() {
		return hasCustomName() ? new ChatComponentText(getName())
				: new ChatComponentTranslation(getName(), new Object[0]);
	}
}
