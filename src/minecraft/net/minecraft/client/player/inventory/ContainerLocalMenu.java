package net.minecraft.client.player.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.ILockableContainer;
import net.minecraft.world.LockCode;

import java.util.Map;

import com.google.common.collect.Maps;

public class ContainerLocalMenu extends InventoryBasic implements ILockableContainer {

public static final int EaZy = 671;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final String field_174896_a;
	private final Map field_174895_b = Maps.newHashMap();
	// private static final String __OBFID = "http://https://fuckuskid00002570";

	public ContainerLocalMenu(final String p_i46276_1_, final IChatComponent p_i46276_2_, final int p_i46276_3_) {
		super(p_i46276_2_, p_i46276_3_);
		field_174896_a = p_i46276_1_;
	}

	@Override
	public int getField(final int id) {
		return field_174895_b.containsKey(id)
				? ((Integer) field_174895_b.get(id)) : 0;
	}

	@Override
	public void setField(final int id, final int value) {
		field_174895_b.put(id, value);
	}

	@Override
	public int getFieldCount() {
		return field_174895_b.size();
	}

	@Override
	public boolean isLocked() {
		return false;
	}

	@Override
	public void setLockCode(final LockCode code) {}

	@Override
	public LockCode getLockCode() {
		return LockCode.EMPTY_CODE;
	}

	@Override
	public String getGuiID() {
		return field_174896_a;
	}

	@Override
	public Container createContainer(final InventoryPlayer playerInventory, final EntityPlayer playerIn) {
		throw new UnsupportedOperationException();
	}
}
