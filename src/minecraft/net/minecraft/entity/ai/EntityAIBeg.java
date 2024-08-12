package net.minecraft.entity.ai;

import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityAIBeg extends EntityAIBase {

public static final int EaZy = 1046;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final EntityWolf theWolf;
	private EntityPlayer thePlayer;
	private final World worldObject;
	private final float minPlayerDistance;
	private int field_75384_e;
	// private static final String __OBFID = "http://https://fuckuskid00001576";

	public EntityAIBeg(final EntityWolf p_i1617_1_, final float p_i1617_2_) {
		theWolf = p_i1617_1_;
		worldObject = p_i1617_1_.worldObj;
		minPlayerDistance = p_i1617_2_;
		setMutexBits(2);
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	@Override
	public boolean shouldExecute() {
		thePlayer = worldObject.getClosestPlayerToEntity(theWolf, minPlayerDistance);
		return thePlayer == null ? false : hasPlayerGotBoneInHand(thePlayer);
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	@Override
	public boolean continueExecuting() {
		return !thePlayer.isEntityAlive() ? false
				: theWolf.getDistanceSqToEntity(thePlayer) > minPlayerDistance * minPlayerDistance ? false
						: field_75384_e > 0 && hasPlayerGotBoneInHand(thePlayer);
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	@Override
	public void startExecuting() {
		theWolf.func_70918_i(true);
		field_75384_e = 40 + theWolf.getRNG().nextInt(40);
	}

	/**
	 * Resets the task
	 */
	@Override
	public void resetTask() {
		theWolf.func_70918_i(false);
		thePlayer = null;
	}

	/**
	 * Updates the task
	 */
	@Override
	public void updateTask() {
		theWolf.getLookHelper().setLookPosition(thePlayer.posX, thePlayer.posY + thePlayer.getEyeHeight(),
				thePlayer.posZ, 10.0F, theWolf.getVerticalFaceSpeed());
		--field_75384_e;
	}

	/**
	 * Gets if the Player has the Bone in the hand.
	 */
	private boolean hasPlayerGotBoneInHand(final EntityPlayer p_75382_1_) {
		final ItemStack var2 = p_75382_1_.inventory.getCurrentItem();
		return var2 == null ? false
				: !theWolf.isTamed() && var2.getItem() == Items.bone ? true : theWolf.isBreedingItem(var2);
	}
}
