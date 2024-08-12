package me.EaZy.client.modules;

import me.EaZy.client.Category;
import me.EaZy.client.Configs;
import me.EaZy.client.Module;
import me.EaZy.client.utils.EntityUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.entity.EntityLivingBase;

public class LaserTag extends Module {

	public static LaserTag mod;

	public static final int EaZy = 134;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	public LaserTag() {
		super("LaserTag", 0, "lt", Category.COMBAT, "For LPMitKev.de");
		mod = this;
	}

	private EntityLivingBase target;

	private String suffix;

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

		target = EntityUtils.getClosestPlayerNotFriended();
		if (target == null) {
			EntityUtils.setLookChanged(false);
			return;
		}
		EntityUtils.faceEntityPacket(target);
		if (Minecraft.thePlayer.getCurrentEquippedItem() != null && !Minecraft.thePlayer.isInvisible()) {
			if (Minecraft.thePlayer.getCurrentEquippedItem().getDisplayName().contains("Laser"))
				doRightClick();
			else
				EntityUtils.setLookChanged(false);
		}

		super.onUpdate();
	}

	@Override
	public void onDisable() {
		EntityUtils.setLookChanged(false);
		super.onDisable();
	}

	private static void doRightClick() {
		Minecraft.playerController.sendUseItem(Minecraft.thePlayer, Minecraft.theWorld,
				Minecraft.thePlayer.getCurrentEquippedItem());
	}

	@Override
	public String getRenderName() {

		if (GuiMainMenu.ersterapril) {
			if (Configs.suffix) {
				suffix = "StrahlTag [" + (target != null ? target.getName() : "NONE") + "]";
			} else {
				suffix = "StrahlTag";
			}
		} else {

			if (Configs.suffix) {
				suffix = "LaserTag [" + (target != null ? target.getName() : "NONE") + "]";
			} else {
				suffix = "LaserTag";
			}
		}

		return suffix;
	}

}
