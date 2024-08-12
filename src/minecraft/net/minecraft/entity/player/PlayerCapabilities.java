package net.minecraft.entity.player;

import net.minecraft.nbt.NBTTagCompound;

public class PlayerCapabilities {

public static final int EaZy = 1195;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	/** Disables player damage. */
	public boolean disableDamage;

	/** Sets/indicates whether the player is flying. */
	public boolean isFlying;

	/** whether or not to allow the player to fly when they double jump. */
	public boolean allowFlying;

	/**
	 * Used to determine if creative mode is enabled, and therefore if items
	 * should be depleted on usage
	 */
	public boolean isCreativeMode;

	/** Indicates whether the player is allowed to modify the surroundings */
	public boolean allowEdit = true;
	private float flySpeed = 0.05F;
	private float walkSpeed = 0.1F;
	// private static final String __OBFID = "http://https://fuckuskid00001708";

	public void writeCapabilitiesToNBT(final NBTTagCompound p_75091_1_) {
		final NBTTagCompound var2 = new NBTTagCompound();
		var2.setBoolean("invulnerable", disableDamage);
		var2.setBoolean("flying", isFlying);
		var2.setBoolean("mayfly", allowFlying);
		var2.setBoolean("instabuild", isCreativeMode);
		var2.setBoolean("mayBuild", allowEdit);
		var2.setFloat("flySpeed", flySpeed);
		var2.setFloat("walkSpeed", walkSpeed);
		p_75091_1_.setTag("abilities", var2);
	}

	public void readCapabilitiesFromNBT(final NBTTagCompound p_75095_1_) {
		if (p_75095_1_.hasKey("abilities", 10)) {
			final NBTTagCompound var2 = p_75095_1_.getCompoundTag("abilities");
			disableDamage = var2.getBoolean("invulnerable");
			isFlying = var2.getBoolean("flying");
			allowFlying = var2.getBoolean("mayfly");
			isCreativeMode = var2.getBoolean("instabuild");

			if (var2.hasKey("flySpeed", 99)) {
				flySpeed = var2.getFloat("flySpeed");
				walkSpeed = var2.getFloat("walkSpeed");
			}

			if (var2.hasKey("mayBuild", 1)) {
				allowEdit = var2.getBoolean("mayBuild");
			}
		}
	}

	public float getFlySpeed() {
		return flySpeed;
	}

	public void setFlySpeed(final float p_75092_1_) {
		flySpeed = p_75092_1_;
	}

	public float getWalkSpeed() {
		return walkSpeed;
	}

	public void setPlayerWalkSpeed(final float p_82877_1_) {
		walkSpeed = p_82877_1_;
	}
}
