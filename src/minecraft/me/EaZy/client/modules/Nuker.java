package me.EaZy.client.modules;

import java.util.ArrayList;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;

import de.Hero.settings.Setting;
import me.EaZy.client.Category;
import me.EaZy.client.Configs;
import me.EaZy.client.Module;
import me.EaZy.client.events.MouseEvent;
import me.EaZy.client.main.Client;
import me.EaZy.client.utils.RenderUtils;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;

public class Nuker extends Module {

	public static Nuker mod;

	public static final int EaZy = 152;

	@Override
	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	private int xPos;
	private int yPos;
	private int zPos;
	private int radius = 4;
	private int id = 0;

	public Nuker() {
		super("Nuker", 38, "n", Category.WORLD, "Break all blocks...");
		Client.setmgr.rSetting(new Setting("Radius", this, 4, 1, 6, true));

		final ArrayList<String> modes = new ArrayList<>();
		modes.add("Normal");
		modes.add("Click");

		Client.setmgr.rSetting(new Setting("Mode", this, "Normal", modes));
		mod = this;
	}

	@Override
	public String getRenderName() {
		if (Configs.suffix) {
			return "Nuker [" + Client.setmgr.getSettingByName(this, "Mode").getValString() + "]";
		} else {
			return "Nuker";
		}
	}

	@Override
	public void onDisable() {
		EventManager.unregister(this);
		super.onDisable();
	}

	@Override
	public void onEnable() {
		EventManager.register(this);
		super.onEnable();
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

		if (mc.thePlayer.ticksExisted < 60) {
			return;
		}

		radius = (int) Client.setmgr.getSettingByName(this, "Radius").getValFloat();

		switch (Client.setmgr.getSettingByName(this, "Mode").getValString()) {
		case "Normal":
			destroyAll();
			break;
		case "Click":
			if (id != 0) {
				doClick();
			}
			break;
		}

		super.onUpdate();
	}

	public void destroyAll() {
		int x = -radius;
		while (x < radius) {
			int y = radius;
			while (y > -radius) {
				int z = -radius;
				while (z < radius) {
					xPos = (int) Minecraft.thePlayer.posX + x;
					yPos = (int) Minecraft.thePlayer.posY + y;
					zPos = (int) Minecraft.thePlayer.posZ + z;
					final BlockPos blockPos = new BlockPos(xPos, yPos, zPos);
					final Block block = Minecraft.theWorld.getBlockState(blockPos).getBlock();
					if (block.getMaterial() != Material.air) {
						Minecraft.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(
								C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, blockPos, EnumFacing.UP));
						Minecraft.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(
								C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, blockPos, EnumFacing.UP));
					}
					++z;
				}
				--y;
			}
			++x;
		}
	}

	public void doClick() {
		int x = -radius;
		while (x < radius) {
			int y = radius;
			while (y > -radius) {
				int z = -radius;
				while (z < radius) {
					xPos = (int) Minecraft.thePlayer.posX + x;
					yPos = (int) Minecraft.thePlayer.posY + y;
					zPos = (int) Minecraft.thePlayer.posZ + z;
					final BlockPos blockPos = new BlockPos(xPos, yPos, zPos);
					final Block block = Minecraft.theWorld.getBlockState(blockPos).getBlock();
					if (Block.getIdFromBlock(block) == id) {
						Minecraft.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(
								C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, blockPos, EnumFacing.UP));
						Minecraft.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(
								C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, blockPos, EnumFacing.UP));
					}
					++z;
				}
				--y;
			}
			++x;
		}
	}

	public EventTarget onClick(final MouseEvent e) {
		if (e.key == 0 && mc.objectMouseOver != null && mc.objectMouseOver.typeOfHit == MovingObjectType.BLOCK) {
			id = Block.getIdFromBlock(Minecraft.theWorld.getBlockState(mc.objectMouseOver.getBlockPos()).getBlock());
		}
		return null;
	}

	@Override
	public void onRender() {
		if (!isToggled()) {
			return;
		}
		int mode = 0;
		switch (Client.setmgr.getSettingByName(this, "Mode").getValString()) {
		case "Normal":
			mode = 1;
			break;
		case "Click":
			mode = 2;
			break;
		}
		int x = -radius;
		while (x < radius) {
			int y = radius;
			while (y > -radius) {
				int z = -radius;
				while (z < radius) {
					xPos = (int) Minecraft.thePlayer.posX + x;
					yPos = (int) Minecraft.thePlayer.posY + y;
					zPos = (int) Minecraft.thePlayer.posZ + z;
					final BlockPos blockPos = new BlockPos(xPos, yPos, zPos);
					final Block block = Minecraft.theWorld.getBlockState(blockPos).getBlock();
					if (mode == 1) {
						if (block.getMaterial() != Material.air) {
							final double renderX = xPos - RenderManager.renderPosX;
							final double renderY = yPos - RenderManager.renderPosY;
							final double renderZ = zPos - RenderManager.renderPosZ;
							RenderUtils.drawOutlinedBlockESP(renderX, renderY, renderZ, 0.0f, 0.0f, 1.0f, 0.3f, 1.5f);
						}
					} else if (mode == 2) {
						if (id != 0) {
							if (Block.getIdFromBlock(block) == id) {
								final double renderX = xPos - RenderManager.renderPosX;
								final double renderY = yPos - RenderManager.renderPosY;
								final double renderZ = zPos - RenderManager.renderPosZ;
								RenderUtils.drawOutlinedBlockESP(renderX, renderY, renderZ, 0.0f, 0.0f, 1.0f, 0.3f,
										1.5f);
							}
						} else {
							return;
						}
					}
					++z;
				}
				--y;
			}
			++x;
		}
		super.onRender();
	}
}
