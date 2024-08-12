package me.EaZy.client.modules;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;

import de.Hero.settings.Setting;
import me.EaZy.client.Category;
import me.EaZy.client.Configs;
import me.EaZy.client.Module;
import me.EaZy.client.events.EventJump;
import me.EaZy.client.events.EventStep;
import me.EaZy.client.main.Client;
import me.EaZy.client.utils.TimeHelper;
import me.EaZy.client.utils.TimeHelper2;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;

public class Step extends Module {

	public static Step mod;

	public static final int EaZy = 185;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	private final TimeHelper2 time1 = new TimeHelper2();
	private int steppedBlocks = 0;

	private final boolean reverse = true;
	private int groundTicks;
	private int recentStepTicks;
	private final TimeHelper timer = new TimeHelper();

	private String suffix;

	public Step() {
		super("Step", 0, "", Category.MOVEMENT, "Go up blocks\nwithout jumping.");
		Client.setmgr.rSetting(new Setting("Height", this, 1, 0.5f, 5, false));
		Client.setmgr.rSetting(new Setting("New", this, true));
		Client.setmgr.rSetting(new Setting("Reverse", this, false));
		mod = this;
	}

	@Override
	public void onDisable() {
		EventManager.unregister(this);
		super.onDisable();
	}

	@Override
	public String getRenderName() {

		if (GuiMainMenu.ersterapril) {
			if (Configs.suffix) {
				suffix = "Schritt [" + Client.setmgr.getSettingByName(this, "Height").getValFloat() + "]";
			} else {
				suffix = "Schritt";
			}
		} else {

			if (Configs.suffix) {
				suffix = "Step [" + Client.setmgr.getSettingByName(this, "Height").getValFloat() + "]";
			} else {
				suffix = "Step";
			}

		}

		return suffix;
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

		if (Minecraft.thePlayer.isCollidedHorizontally
				&& (Minecraft.gameSettings.keyBindForward.pressed || Minecraft.gameSettings.keyBindBack.pressed
						|| Minecraft.gameSettings.keyBindRight.pressed || Minecraft.gameSettings.keyBindLeft.pressed)
				&& Minecraft.thePlayer.onGround && !Minecraft.thePlayer.isOnLadder()
				&& !Minecraft.thePlayer.isInWater()) {
			Minecraft.thePlayer.stepHeight = Client.setmgr.getSettingByName(this, "Height").getValFloat();
			Minecraft.thePlayer.sendQueue
					.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.thePlayer.posX,
							Minecraft.thePlayer.posY + 0.42, Minecraft.thePlayer.posZ, Minecraft.thePlayer.onGround));
			if (Client.setmgr.getSettingByName(this, "New").getValBoolean()) {
				Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(
						Minecraft.thePlayer.posX, Minecraft.thePlayer.posY + 0.75, Minecraft.thePlayer.posZ,
						Minecraft.thePlayer.onGround));
			} else {
				Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(
						Minecraft.thePlayer.posX, Minecraft.thePlayer.posY + 0.753, Minecraft.thePlayer.posZ,
						Minecraft.thePlayer.onGround));
			}
			steppedBlocks++;
		} else {
			Minecraft.thePlayer.stepHeight = 0.5f;
		}

		if (Client.setmgr.getSettingByName(this, "Reverse").getValBoolean()) {
			if (!(!reverse || Minecraft.gameSettings.keyBindJump.pressed || Minecraft.thePlayer.isOnLadder()
					|| Minecraft.thePlayer.isInsideOfMaterial(Material.water)
					|| Minecraft.thePlayer.isInsideOfMaterial(Material.lava) || Minecraft.thePlayer.isInWater()
					|| (this.getBlock(-1.1) instanceof BlockAir || this.getBlock(-1.1) instanceof BlockAir)
							&& (this.getBlock(-0.1) instanceof BlockAir || Minecraft.thePlayer.motionX == 0.0
									|| Minecraft.thePlayer.motionZ == 0.0 || !reverse || Minecraft.thePlayer.onGround
									|| Minecraft.thePlayer.fallDistance >= 3.0f
									|| Minecraft.thePlayer.fallDistance <= 0.05))) {
				Minecraft.thePlayer.motionY = -5.0;
			}
			++recentStepTicks;
			groundTicks = Minecraft.thePlayer.onGround ? ++groundTicks : 0;
		}

		super.onUpdate();
	}

	@Override
	public void onEnable() {
		EventManager.register(this);
		super.onEnable();
	}

	public EventTarget onJump(final EventJump event) {
		if (recentStepTicks < 2 && Client.setmgr.getSettingByName(this, "Reverse").getValBoolean()) {
			event.setCancelled(true);
		}
		return null;
	}

	public EventTarget onStep(final EventStep event) {
		if (timer.delay(300.0f) && Minecraft.thePlayer.movementInput != null && recentStepTicks >= 2 && groundTicks >= 2
				&& !Minecraft.thePlayer.movementInput.jump
				&& Client.setmgr.getSettingByName(this, "Reverse").getValBoolean()) {
			event.stepHeight = 1.0;
			event.bypass = true;
			timer.reset();
		}
		return null;
	}

	private Block getBlock(final AxisAlignedBB bb) {
		final int y = (int) bb.minY;
		for (int x = MathHelper.floor_double(bb.minX); x < MathHelper.floor_double(bb.maxX) + 1; ++x) {
			for (int z = MathHelper.floor_double(bb.minZ); z < MathHelper.floor_double(bb.maxZ) + 1; ++z) {
				final Block block = Minecraft.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
				if (block == null) {
					continue;
				}
				return block;
			}
		}
		return null;
	}

	private Block getBlock(final double offset) {
		return this.getBlock(Minecraft.thePlayer.getEntityBoundingBox().offset(0.0, offset, 0.0));
	}
}
