package net.minecraft.client.player.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.IInteractionObject;

public class LocalBlockIntercommunication implements IInteractionObject {

public static final int EaZy = 672;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final String field_175126_a;
	private final IChatComponent field_175125_b;
	// private static final String __OBFID = "http://https://fuckuskid00002571";

	public LocalBlockIntercommunication(final String p_i46277_1_, final IChatComponent p_i46277_2_) {
		field_175126_a = p_i46277_1_;
		field_175125_b = p_i46277_2_;
	}

	@Override
	public Container createContainer(final InventoryPlayer playerInventory, final EntityPlayer playerIn) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Gets the name of this command sender (usually username, but possibly
	 * "Rcon")
	 */
	@Override
	public String getName() {
		return field_175125_b.getUnformattedText();
	}

	/**
	 * Returns true if this thing is named
	 */
	@Override
	public boolean hasCustomName() {
		return true;
	}

	@Override
	public String getGuiID() {
		return field_175126_a;
	}

	@Override
	public IChatComponent getDisplayName() {
		return field_175125_b;
	}
}
