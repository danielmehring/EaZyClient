package me.EaZy.client.modules.SpeedModes;

import me.EaZy.client.Category;
import me.EaZy.client.Module;
import me.EaZy.client.utils.MovementUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Timer;

public class OldJump extends Module {

	public static OldJump mod;

	public static final int EaZy = 2042;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
		});
	}
	
	public int i = 0;

	public OldJump() {
		super("OldJump", 0, "", Category.SPEED);
		mod = this;
	}

	@Override
	public void onUpdate() {
		if (!isToggled()) {
			if (togglecmd) {
				setToggled(true);
				onEnable();
			}
			return;
		}
		if (isToggled() && !togglecmd) {
			setToggled(false);
			onDisable();
			return;
		}

		if (mc.thePlayer.onGround) {
			Timer.timerSpeed = 1;
		}

		if (Minecraft.thePlayer.capabilities.isFlying || !MovementUtil.areWalkingKeysDown()) {
			return;
		}

		i++;

		if (Minecraft.thePlayer.onGround) {
			final float var1 = mc.thePlayer.rotationYaw * 0.017453292F;
			mc.thePlayer.motionX -= MathHelper.sin(var1) * 0.209F;
			mc.thePlayer.motionZ += MathHelper.cos(var1) * 0.209F;
			mc.thePlayer.motionY = 0.099;
		} else {
			if (mc.thePlayer.fallDistance > 0.1)
				return;
			mc.thePlayer.motionX = 0;
			mc.thePlayer.motionZ = 0;
			if (i > 200) {
				mc.thePlayer.jumpMovementFactor = mc.thePlayer.ticksExisted % 2 == 0 ? 0.316f : 0.299f;
				if (i > 205) {
					mc.thePlayer.motionX /= 1.25;
					mc.thePlayer.motionZ /= 1.25;
					i = 0;
				}
			} else {
				mc.thePlayer.jumpMovementFactor = mc.thePlayer.ticksExisted % 2 == 0 ? 0.346f : 0.306f;
			}	
		}
		super.onUpdate();
	}

}
