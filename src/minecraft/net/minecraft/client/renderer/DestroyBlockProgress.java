package net.minecraft.client.renderer;

import net.minecraft.util.BlockPos;

public class DestroyBlockProgress {

public static final int EaZy = 712;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final BlockPos field_180247_b;

	/**
	 * damage ranges from 1 to 10. -1 causes the client to delete the partial
	 * block renderer.
	 */
	private int partialBlockProgress;

	/**
	 * keeps track of how many ticks this PartiallyDestroyedBlock already exists
	 */
	private int createdAtCloudUpdateTick;

	public DestroyBlockProgress(final int p_i45925_1_, final BlockPos p_i45925_2_) {
		field_180247_b = p_i45925_2_;
	}

	public BlockPos func_180246_b() {
		return field_180247_b;
	}

	/**
	 * inserts damage value into this partially destroyed Block. -1 causes
	 * client renderer to delete it, otherwise ranges from 1 to 10
	 */
	public void setPartialBlockDamage(int damage) {
		if (damage > 10) {
			damage = 10;
		}

		partialBlockProgress = damage;
	}

	public int getPartialBlockDamage() {
		return partialBlockProgress;
	}

	/**
	 * saves the current Cloud update tick into the PartiallyDestroyedBlock
	 */
	public void setCloudUpdateTick(final int p_82744_1_) {
		createdAtCloudUpdateTick = p_82744_1_;
	}

	/**
	 * retrieves the 'date' at which the PartiallyDestroyedBlock was created
	 */
	public int getCreationCloudUpdateTick() {
		return createdAtCloudUpdateTick;
	}
}
