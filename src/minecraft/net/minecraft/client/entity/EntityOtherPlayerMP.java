package net.minecraft.client.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.mojang.authlib.GameProfile;

public class EntityOtherPlayerMP extends AbstractClientPlayer {

	public static final int EaZy = 452;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	private boolean isItemInUse;
	private int otherPlayerMPPosRotationIncrements;
	private double otherPlayerMPX;
	private double otherPlayerMPY;
	private double otherPlayerMPZ;
	private double otherPlayerMPYaw;
	private double otherPlayerMPPitch;

	public boolean hasYawHeadChanged = false;
	private final float rotationYawHeadFirst;

	public EntityOtherPlayerMP(final World worldIn, final GameProfile p_i45075_2_) {
		super(worldIn, p_i45075_2_);
		stepHeight = 0.0F;
		noClip = true;
		field_71082_cx = 0.25F;
		renderDistanceWeight = 10.0D;
		rotationYawHeadFirst = rotationYawHead;
	}

	/**
	 * Called when the entity is attacked.
	 */
	@Override
	public boolean attackEntityFrom(final DamageSource source, final float amount) {
		return true;
	}

	@Override
	public void onDeath(DamageSource cause) {
		super.onDeath(cause);
	}
	
	@Override
	public void func_180426_a(final double p_180426_1_, final double p_180426_3_, final double p_180426_5_,
			final float p_180426_7_, final float p_180426_8_, final int p_180426_9_, final boolean p_180426_10_) {
		otherPlayerMPX = p_180426_1_;
		otherPlayerMPY = p_180426_3_;
		otherPlayerMPZ = p_180426_5_;
		otherPlayerMPYaw = p_180426_7_;
		otherPlayerMPPitch = p_180426_8_;
		otherPlayerMPPosRotationIncrements = p_180426_9_;
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate() {
		if (rotationYawHeadFirst != rotationYawHead) {
			hasYawHeadChanged = true;
		}
		field_71082_cx = 0.0F;
		super.onUpdate();
		prevLimbSwingAmount = limbSwingAmount;
		final double var1 = posX - prevPosX;
		final double var3 = posZ - prevPosZ;
		float var5 = MathHelper.sqrt_double(var1 * var1 + var3 * var3) * 4.0F;

		if (var5 > 1.0F) {
			var5 = 1.0F;
		}

		limbSwingAmount += (var5 - limbSwingAmount) * 0.4F;
		limbSwing += limbSwingAmount;

		if (!isItemInUse && isEating() && inventory.mainInventory[inventory.currentItem] != null) {
			final ItemStack var6 = inventory.mainInventory[inventory.currentItem];
			setItemInUse(inventory.mainInventory[inventory.currentItem], var6.getItem().getMaxItemUseDuration(var6));
			isItemInUse = true;
		} else if (isItemInUse && !isEating()) {
			clearItemInUse();
			isItemInUse = false;
		}
	}

	/**
	 * Called frequently so the entity can update its state every tick as
	 * required. For example, zombies and skeletons use this to react to
	 * sunlight and start to burn.
	 */
	@Override
	public void onLivingUpdate() {
		if (otherPlayerMPPosRotationIncrements > 0) {
			final double var1 = posX + (otherPlayerMPX - posX) / otherPlayerMPPosRotationIncrements;
			final double var3 = posY + (otherPlayerMPY - posY) / otherPlayerMPPosRotationIncrements;
			final double var5 = posZ + (otherPlayerMPZ - posZ) / otherPlayerMPPosRotationIncrements;
			double var7;

			for (var7 = otherPlayerMPYaw - rotationYaw; var7 < -180.0D; var7 += 360.0D) {}

			while (var7 >= 180.0D) {
				var7 -= 360.0D;
			}

			rotationYaw = (float) (rotationYaw + var7 / otherPlayerMPPosRotationIncrements);
			rotationPitch = (float) (rotationPitch
					+ (otherPlayerMPPitch - rotationPitch) / otherPlayerMPPosRotationIncrements);
			--otherPlayerMPPosRotationIncrements;
			setPosition(var1, var3, var5);
			setRotation(rotationYaw, rotationPitch);
		}

		prevCameraYaw = cameraYaw;
		updateArmSwingProgress();
		float var9 = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
		float var2 = (float) Math.atan(-motionY * 0.20000000298023224D) * 15.0F;

		if (var9 > 0.1F) {
			var9 = 0.1F;
		}

		if (!onGround || getHealth() <= 0.0F) {
			var9 = 0.0F;
		}

		if (onGround || getHealth() <= 0.0F) {
			var2 = 0.0F;
		}

		cameraYaw += (var9 - cameraYaw) * 0.4F;
		cameraPitch += (var2 - cameraPitch) * 0.8F;
	}

	/**
	 * Sets the held item, or an armor slot. Slot 0 is held item. Slot 1-4 is
	 * armor. Params: Item, slot
	 */
	@Override
	public void setCurrentItemOrArmor(final int slotIn, final ItemStack itemStackIn) {
		if (slotIn == 0) {
			inventory.mainInventory[inventory.currentItem] = itemStackIn;
		} else {
			inventory.armorInventory[slotIn - 1] = itemStackIn;
		}
	}

	/**
	 * Notifies this sender of some sort of information. This is for messages
	 * intended to display to the user. Used for typical output (like "you asked
	 * for whether or not this game rule is set, so here's your answer" ),
	 * warnings (like "I fetched this block for you by ID, but I'd like you to
	 * know that every time you do this, I die a little inside "), and errors
	 * (like "it's not called iron_pixacke, silly").
	 */
	@Override
	public void addChatMessage(final IChatComponent message) {
		Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(message);
	}

	/**
	 * Returns true if the command sender is allowed to use the given command.
	 */
	@Override
	public boolean canCommandSenderUseCommand(final int permissionLevel, final String command) {
		return false;
	}

	@Override
	public BlockPos getPosition() {
		return new BlockPos(posX + 0.5D, posY + 0.5D, posZ + 0.5D);
	}
}
