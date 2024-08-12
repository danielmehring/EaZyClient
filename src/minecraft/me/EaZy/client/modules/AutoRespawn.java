package me.EaZy.client.modules;

import me.EaZy.client.Category;
import me.EaZy.client.Module;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.network.play.client.C16PacketClientStatus.EnumState;

public class AutoRespawn extends Module {

	public static AutoRespawn mod;

	public static final int EaZy = 2065;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	public AutoRespawn() {
		super("AutoRespawn", 0, "InstantRespawn", Category.PLAYER);
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

		if (mc.currentScreen instanceof GuiGameOver) {
			mc.thePlayer.sendQueue.netManager.sendPacket(new C16PacketClientStatus(EnumState.PERFORM_RESPAWN));
			mc.thePlayer.isDead = false;
			mc.thePlayer.setHealth(20.0f);
			mc.displayGuiScreen(null);
		}

		super.onUpdate();
	}
}
