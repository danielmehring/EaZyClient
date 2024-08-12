package me.EaZy.client.modules;

import java.util.Arrays;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;

import de.Hero.settings.Setting;
import me.EaZy.client.Category;
import me.EaZy.client.Module;
import me.EaZy.client.events.EventSafewalk;
import me.EaZy.client.events.EventSendPacket;
import me.EaZy.client.main.Client;
import me.EaZy.client.modules.YesCheat.Mode;
import me.EaZy.client.utils.BlockUtils;
import me.EaZy.client.utils.InventoryUtil;
import me.EaZy.client.utils.PlayerUtil;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBlock;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C03PacketPlayer.C05PacketPlayerLook;
import net.minecraft.network.play.client.C03PacketPlayer.C06PacketPlayerPosLook;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C03PacketPlayer.C04PacketPlayerPosition;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;

public class Scaffold extends Module {

	public static Scaffold mod;

	public static final int EaZy = 158;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	private static BlockData blockData = null;
	private boolean cooldown;
	private int expandCooldown;
	private final List<Block> blacklist = Arrays.asList(Blocks.air, Blocks.water, Blocks.flowing_water, Blocks.lava,
			Blocks.flowing_lava);

	public Scaffold() {
		super("Scaffold", 44, "sc", Category.WORLD, "Place blocks\nunderneath you.");
		Client.setmgr.rSetting(new Setting("Expanded", this, false));
		Client.setmgr.rSetting(new Setting("Expand", this, 2.0f, 1.0f, 9.0f, true));
		Client.setmgr.rSetting(new Setting("KeepFacing", this, false));
		Client.setmgr.rSetting(new Setting("SafeWalk", this, true));
		Client.setmgr.rSetting(new Setting("Silent", this, true));
		Client.setmgr.rSetting(new Setting("Spoof", this, false));
		mod = this;
	}

	private void Swing() {
		if (Client.setmgr.getSettingByName(this, "Silent").getValBoolean()) {
			Minecraft.thePlayer.sendQueue.netManager.sendPacket(new C0APacketAnimation());
		} else {
			Minecraft.thePlayer.swingItem();
		}
	}

	@Override
	public String getRenderName() {
		if (GuiMainMenu.ersterapril) {
			return Client.setmgr.getSettingByName(this, "Expanded").getValBoolean()
					? "Gerüstbauer [Erweiterung:" + Client.setmgr.getSettingByName(this, "Expand").getValFloat() + "]"
					: "Gerüstbauer";
		} else {
			return Client.setmgr.getSettingByName(this, "Expanded").getValBoolean()
					? "Scaffold [Expand:" + Client.setmgr.getSettingByName(this, "Expand").getValFloat() + "]"
					: "Scaffold";
		}
	}

	@Override
	public void onEnable() {
		EventManager.register(this);
		Scaffold.blockData = null;
		super.onEnable();
	}

	@Override
	public void onDisable() {
		EventManager.unregister(this);
		if (mc.inGameHasFocus) {
			if (Minecraft.gameSettings.keyBindJump.getKeyCode() < 0) {
				KeyBinding.setKeyBindState(Minecraft.gameSettings.keyBindJump.getKeyCode(),
						Mouse.isButtonDown(Minecraft.gameSettings.keyBindJump.getKeyCode() + 100));
			} else {
				KeyBinding.setKeyBindState(Minecraft.gameSettings.keyBindJump.getKeyCode(),
						Keyboard.isKeyDown(Minecraft.gameSettings.keyBindJump.getKeyCode()));
			}
		} else {
			Minecraft.gameSettings.keyBindJump.pressed = false;
		}
		super.onDisable();
	}

	public EventTarget onWalking(final EventSafewalk event) {
		if (Client.setmgr.getSettingByName(this, "SafeWalk").getValBoolean()) {
			event.setShouldWalkSafely(true);
		}
		return null;
	}

	public EventTarget onSendPacket(EventSendPacket e) {
		if ((YesCheat.enabled && YesCheat.mode == Mode.Gomme) || Client.setmgr.getSettingByName(this, "KeepFacing").getValBoolean()) {
			if (e.getPacket() instanceof C03PacketPlayer && PlayerUtil.isPlayerMoving()) {
				if (System.currentTimeMillis() - lastPlaced < 310) {
					if (!((C03PacketPlayer) e.getPacket()).isScaffoldOrTower) {
						if (e.getPacket() instanceof C04PacketPlayerPosition) {
							C04PacketPlayerPosition p = (C04PacketPlayerPosition) e.getPacket();
							e.setPacket(new C06PacketPlayerPosLook(p.getPositionX(), p.getPositionY(), p.getPositionZ(),
									yaw, pitch, p.onGround));
							return null;
						}
						if (e.getPacket() instanceof C06PacketPlayerPosLook) {
							C06PacketPlayerPosLook p = (C06PacketPlayerPosLook) e.getPacket();
							e.setPacket(new C06PacketPlayerPosLook(p.getPositionX(), p.getPositionY(), p.getPositionZ(),
									yaw, pitch, p.onGround));
							return null;
						}
						e.setCancelled(true);
					} else {
						if (e.getPacket() instanceof C05PacketPlayerLook) {
							C05PacketPlayerLook p = (C05PacketPlayerLook) e.getPacket();
							e.setPacket(new C06PacketPlayerPosLook(Client.mc.thePlayer.posX,
									Client.mc.thePlayer.boundingBox.minY, Client.mc.thePlayer.posZ, p.getYaw(),
									p.getPitch(), p.onGround));
							return null;
						}
					}
				}
			}
		}
		return null;
	}

	public long lastPlaced;
	public float yaw;
	public float pitch;

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
		final boolean gomme = (YesCheat.enabled && YesCheat.mode == Mode.Gomme) || Client.setmgr.getSettingByName(this, "KeepFacing").getValBoolean();
		boolean hasInHand = Minecraft.thePlayer.getHeldItem() != null
				&& Minecraft.thePlayer.getHeldItem().getItem() instanceof ItemBlock;
		boolean spoof = Client.setmgr.getSettingByName(this, "Spoof").getValBoolean();
		boolean expand = Client.setmgr.getSettingByName(this, "Expanded").getValBoolean();
		boolean needsSpoof = false;
		int blockSlot = -1;
		if (!hasInHand && spoof) {
			blockSlot = InventoryUtil.findBlockInHotbar();
			if (blockSlot == -1) {
				return;
			} else {
				hasInHand = true;
				needsSpoof = true;
			}
		}

		if (gomme && hasInHand) {
			Minecraft.thePlayer.motionX /= 1.2;
			Minecraft.thePlayer.motionZ /= 1.2;
		}
		if (hasInHand) {
			if (!expand) {
				BlockPos blockBelow;
				blockData = null;
				if (Minecraft.theWorld.getBlockState(blockBelow = new BlockPos(Minecraft.thePlayer.posX,
						Minecraft.thePlayer.posY - 1.0, Minecraft.thePlayer.posZ)).getBlock() == Blocks.air
						&& (blockData = getBlockData(blockBelow)) != null) {
				}
				if (blockData == null) {
					return;
				}
				final float[] values = BlockUtils.getFacingRotations(blockData.position.getX(),
						blockData.position.getY(), blockData.position.getZ(), blockData.face, gomme);
				Minecraft.thePlayer.sendQueue.addToSendQueue(
						new C05PacketPlayerLook(values[0], values[1], Minecraft.thePlayer.onGround, true));
				yaw = values[0];
				pitch = values[1];
				Minecraft.rightClickDelayTimer = 0;
				double x = 0.5;
				double y = 0.5;
				double z = 0.5;
				final double random = (Math.random() - 0.5) / 2;
				switch (blockData.face.getIndex()) {

				case 1: {
					x = 0.5 + random;
					y = 1;
					z = 0.5 - random;
					break;
				}

				case 2: {
					x = 0.5 + random;
					y = 0.5 - random;
					z = 0;
					break;
				}

				case 3: {
					x = 0.5 + random;
					y = 0.5 - random;
					z = 1;
					break;
				}

				case 4: {
					x = 0;
					y = 0.5 + random;
					z = 0.5 - random;
					break;
				}

				case 5: {
					x = 1;
					y = 0.5 + random;
					z = 0.5 - random;
					break;
				}

				}
				if (needsSpoof) {
					mc.thePlayer.sendQueue.netManager.sendPacket(new C09PacketHeldItemChange(blockSlot));
				}
				if (Minecraft.playerController.placeBlock(Minecraft.thePlayer, Minecraft.theWorld,
						Minecraft.thePlayer.getHeldItem(), blockData.position, blockData.face,
						new Vec3(blockData.position.getX() + x, blockData.position.getY() + y,
								blockData.position.getZ() + z))) {
					lastPlaced = System.currentTimeMillis();
					Swing();
				}
				if (needsSpoof) {
					mc.thePlayer.sendQueue.netManager
							.sendPacket(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
				}
			} else {
				final BlockPos blockBelow = new BlockPos(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY - 1.0,
						Minecraft.thePlayer.posZ);
				if (Minecraft.theWorld.getBlockState(blockBelow).getBlock() == Blocks.air
						&& (blockData = getBlockData(blockBelow)) != null) {
					final float[] values = BlockUtils.getFacingRotations(blockData.position.getX(),
							blockData.position.getY(), blockData.position.getZ(), blockData.face, gomme);
					Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(values[0],
							values[1], Minecraft.thePlayer.onGround));
				}
				if (expandCooldown-- > 0) {
					return;
				}
				expandCooldown = 0;
				final EnumFacing faceing = Minecraft.thePlayer.getFacingDirection();
				final BlockPos playerBlock = new BlockPos(mc.func_175606_aa().posX,
						mc.func_175606_aa().getEntityBoundingBox().minY, mc.func_175606_aa().posZ);
				int i = 0;
				if (needsSpoof) {
					mc.thePlayer.sendQueue.netManager.sendPacket(new C09PacketHeldItemChange(blockSlot));
				}
				while (i < Client.setmgr.getSettingByName(this, "Expand").getValFloat()) {
					final BlockPos offset = playerBlock.add(0, -1, 0).offset(faceing, i);
					if (Minecraft.theWorld.isAirBlock(offset)) {
						cooldown = false;
						PlaceBlock(offset, faceing);
						expandCooldown = 2;
						break;
					}
					++i;
				}
				if (needsSpoof) {
					mc.thePlayer.sendQueue.netManager
							.sendPacket(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
				}
			}
		}
	}

	private void PlaceBlock(BlockPos pos, final EnumFacing face) {
		if (!cooldown) {
			cooldown = true;
			if (null != face) {
				switch (face) {
				case UP:
					pos = pos.add(0, -1, 0);
					break;
				case NORTH:
					pos = pos.add(0, 0, 1);
					break;
				case SOUTH:
					pos = pos.add(0, 0, -1);
					break;
				case EAST:
					pos = pos.add(-1, 0, 0);
					break;
				case WEST:
					pos = pos.add(1, 0, 0);
					break;
				default:
					break;
				}
			}
			if (Minecraft.playerController.placeBlock(Minecraft.thePlayer, Minecraft.theWorld,
					Minecraft.thePlayer.getHeldItem(), pos, face,
					new Vec3(pos.getX() + Math.random(), pos.getY() + Math.random(), pos.getZ() + Math.random()))) {
				Swing();
			}
		}
	}

	private BlockData getBlockData(final BlockPos pos) {
		if (!blacklist.contains(Minecraft.theWorld.getBlockState(pos.add(0, -1, 0)).getBlock())) {
			return new BlockData(pos.add(0, -1, 0), EnumFacing.UP);
		}
		if (!blacklist.contains(Minecraft.theWorld.getBlockState(pos.add(-1, 0, 0)).getBlock())) {
			return new BlockData(pos.add(-1, 0, 0), EnumFacing.EAST);
		}
		if (!blacklist.contains(Minecraft.theWorld.getBlockState(pos.add(1, 0, 0)).getBlock())) {
			return new BlockData(pos.add(1, 0, 0), EnumFacing.WEST);
		}
		if (!blacklist.contains(Minecraft.theWorld.getBlockState(pos.add(0, 0, -1)).getBlock())) {
			return new BlockData(pos.add(0, 0, -1), EnumFacing.SOUTH);
		}
		if (!blacklist.contains(Minecraft.theWorld.getBlockState(pos.add(0, 0, 1)).getBlock())) {
			return new BlockData(pos.add(0, 0, 1), EnumFacing.NORTH);
		}
		return null;
	}

	private class BlockData {

		public BlockPos position;
		public EnumFacing face;

		public BlockData(final BlockPos position, final EnumFacing face) {
			this.position = position;
			this.face = face;
		}
	}

}
