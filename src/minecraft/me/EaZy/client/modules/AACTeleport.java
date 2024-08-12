package me.EaZy.client.modules;

import org.lwjgl.opengl.GL11;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;

import me.EaZy.client.Category;
import me.EaZy.client.Module;
import me.EaZy.client.events.EventClick;
import me.EaZy.client.events.EventPostMotionUpdates;
import me.EaZy.client.events.EventPreMotionUpdates;
import me.EaZy.client.utils.EntityUtil;
import me.EaZy.client.utils.MoveUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;

public class AACTeleport extends Module {

	public static AACTeleport mod;

	public static final int EaZy = 91;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	public AACTeleport() {
		super(new String(new byte[] { 0b1000001, 0b1000001, 0b1000011, 0b1010100, 0b1100101, 0b1101100, 0b1100101,
				0b1110000, 0b1101111, 0b1110010, 0b1110100 }), 0,
				new String(new byte[] { 0b1100001, 0b1100001, 0b1100011, 0b1110100, 0b1110000 }), Category.PLAYER,
				new String(new byte[] { 0b1010100, 0b1100101, 0b1101100, 0b1100101, 0b1110000, 0b1101111, 0b1110010,
						0b1110100, 0b100000, 0b1100110, 0b1101111, 0b1110010, 0b100000, 0b1000001, 0b1000001, 0b1000011,
						0b101110 }));
		mod = this;
	}

	private double x;
	private double y;
	private double z;
	private static BlockPos to;

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

		super.onUpdate();
	}

	@Override
	public String getRenderName() {
		if (GuiMainMenu.ersterapril) {
			return "AbgewandeltesKontraHackTeleportieren";
		} else {
			return super.getRenderName();
		}
	}

	private int canTeleport(final BlockPos asd) {
		final Block sel0 = Minecraft.theWorld.getBlockState(asd).getBlock();
		final Block sel1 = Minecraft.theWorld.getBlockState(new BlockPos(asd.getX(), asd.getY() + 1, asd.getZ()))
				.getBlock();
		final Block sel2 = Minecraft.theWorld.getBlockState(new BlockPos(asd.getX(), asd.getY() + 2, asd.getZ()))
				.getBlock();

		if (sel0.isFullBlock()) {
			if (!sel1.isFullBlock() && !sel2.isFullBlock()) {
				return 1;
			} else {
				return -1;
			}
		} else {
			if (sel0 instanceof BlockGlass || sel0 instanceof BlockStainedGlass) {
				return 1;
			} else if (!sel1.isFullBlock()) {
				return 0;
			} else {
				return -1;
			}
		}

	}

	public EventTarget onClick(final EventClick e) {
		MovingObjectPosition mov = EntityUtil.rayTraceBlocks(9001);
		if (mov != null && to == null && mov.typeOfHit == MovingObjectType.BLOCK
				&& canTeleport(mov.getBlockPos()) != -1) {

			final BlockPos tpPos = new BlockPos(mov.getBlockPos().getX(),
					mov.getBlockPos().getY() + canTeleport(mov.getBlockPos()), mov.getBlockPos().getZ());

			if (tpPos.getY() - Minecraft.thePlayer.posY <= 0) {
				to = tpPos;
				msg("§2Teleport-Position§7: §3X:" + to.getX() + " Y:" + to.getY() + " Z:" + to.getZ());
			} else {
				msg("§6Your TpPos has to be at the same YPosition or lower.");
			}
		}
		return null;
	}

	public EventTarget onPre(final EventPreMotionUpdates e) {
		if (to != null) {
			if (Minecraft.thePlayer.isSneaking()) {
				x = Minecraft.thePlayer.posX;
				y = Minecraft.thePlayer.posY;
				z = Minecraft.thePlayer.posZ;
				final float yaw = Minecraft.thePlayer.rotationYaw;
				final float pitch = Minecraft.thePlayer.rotationPitch;
				if (Minecraft.thePlayer.playerLocation == to) {
					Minecraft.gameSettings.keyBindSneak.pressed = false;
				}
				Minecraft.thePlayer.sendQueue
						.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x, y, z, true));
				Minecraft.thePlayer.sendQueue.addToSendQueue(
						new C03PacketPlayer.C04PacketPlayerPosition(to.getX(), to.getY(), to.getZ(), true));
				Minecraft.thePlayer.sendQueue
						.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x, y, z, true));
				Minecraft.thePlayer.sendQueue
						.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x, y + 5.0, z, true));
				Minecraft.thePlayer.sendQueue.addToSendQueue(
						new C03PacketPlayer.C04PacketPlayerPosition(to.getX(), to.getY(), to.getZ(), true));
				Minecraft.thePlayer.sendQueue
						.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x, y, z, true));
				MoveUtils.toFwd(0.04);
				Minecraft.thePlayer.rotationYaw = yaw;
				Minecraft.thePlayer.rotationPitch = pitch;
			}
		}
		return null;
	}

	public EventTarget onPost(final EventPostMotionUpdates e) {
		if (to != null) {
			if ((int) Minecraft.thePlayer.posX == to.getX() && (int) Minecraft.thePlayer.posY == to.getY()
					&& (int) Minecraft.thePlayer.posZ == to.getZ()) {
				to = null;
			}
		}
		return null;
	}

	@Override
	public void onRender() {
		if (!isToggled()) {
			return;
		}

		if (to != null) {
			return;
		}

		final BlockPos asd = EntityUtil.rayTraceBlocks(9001).getBlockPos();
		final Block selectBlock = Minecraft.theWorld.getBlockState(asd).getBlock();

		if (selectBlock instanceof BlockAir) {
			return;
		}

		int color = -1;

		if (canTeleport(asd) != -1) {
			color = -12386305;
		} else {
			color = -48574;
		}

		GlStateManager.enableBlend();
		GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
		GL11.glLineWidth(2.0F);
		GlStateManager.disableTexture2D();
		GlStateManager.depthMask(false);

		selectBlock.setBlockBoundsBasedOnState(Minecraft.theWorld, asd);
		final double var8 = Minecraft.thePlayer.lastTickPosX
				+ (Minecraft.thePlayer.posX - Minecraft.thePlayer.lastTickPosX) * Minecraft.timer.renderPartialTicks;
		final double var10 = Minecraft.thePlayer.lastTickPosY
				+ (Minecraft.thePlayer.posY - Minecraft.thePlayer.lastTickPosY) * Minecraft.timer.renderPartialTicks;
		final double var12 = Minecraft.thePlayer.lastTickPosZ
				+ (Minecraft.thePlayer.posZ - Minecraft.thePlayer.lastTickPosZ) * Minecraft.timer.renderPartialTicks;
		RenderGlobal.drawOutlinedBoundingBox(selectBlock.getSelectedBoundingBox(Minecraft.theWorld, asd)
				.expand(0.0020000000949949026D, 0.0020000000949949026D, 0.0020000000949949026D)
				.offset(-var8, -var10, -var12), color);

		GlStateManager.depthMask(true);
		GlStateManager.enableTexture2D();
		GlStateManager.disableBlend();

		super.onRender();
	}

	@Override
	public void onEnable() {
		EventManager.register(this);
		to = null;
		super.onEnable();
	}

	@Override
	public void onDisable() {
		EventManager.unregister(this);
		super.onDisable();
	}
}
