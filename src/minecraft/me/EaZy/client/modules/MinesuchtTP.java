package me.EaZy.client.modules;

import java.util.Arrays;

import org.lwjgl.opengl.GL11;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;

import me.EaZy.client.Category;
import me.EaZy.client.Module;
import me.EaZy.client.events.EventClick;
import me.EaZy.client.events.EventPostMotionUpdates;
import me.EaZy.client.events.EventPreMotionUpdates;
import me.EaZy.client.utils.BlockUtils;
import me.EaZy.client.utils.EntityUtil;
import me.EaZy.client.utils.MoveUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;

public class MinesuchtTP extends Module {

	public static MinesuchtTP mod;

	public static final int EaZy = 91;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	public MinesuchtTP() {
		super("MinesuchtTP", 0, "RewiTP", Category.PLAYER);
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
			return "MinesuchtTeleportieren";
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
		MovingObjectPosition mop = EntityUtil.rayTraceBlocks(9001);
		if (to == null && mop != null && mop.typeOfHit == MovingObjectType.BLOCK
				&& canTeleport(mop.getBlockPos()) != -1) {

			final BlockPos tpPos = new BlockPos(mop.getBlockPos().getX(),
					mop.getBlockPos().getY() + canTeleport(mop.getBlockPos()), mop.getBlockPos().getZ());
			tpPos.add(0, BlockUtils.getOffset(BlockUtils.getBlock(tpPos), tpPos), 0);

			to = tpPos;

			double xch = to.getX() + 0.5 - mc.thePlayer.posX;
			double zch = to.getZ() + 0.5 - mc.thePlayer.posZ;

			double dir = Math.toDegrees(Math.atan(xch / zch));

			if (xch > 0) {
				if (zch > 0) { // south-east
					dir = 0 - dir;
				} else if (zch < 0) { // north-east
					dir = -180 - dir;
				} else { // east
					dir = -90;
				}
			} else if (xch < 0) { // west
				if (zch > 0) { // south-west
					dir = 0 - dir;
				} else if (zch < 0) { // north-west
					dir = 180 - dir;
				} else { // west
					dir = 90;
				}
			} else { // motionX = 0
				if (zch > 0) { // south
					dir = 0.0;
				} else if (zch < 0) { // north
					dir = -180;
				} else {
					// Not moving
				}
			}

			yawToPos = Math.toRadians(dir);

			msg("§2Teleport-Position§7: §3X:" + to.getX() + " Y:" + to.getY() + " Z:" + to.getZ());
		}
		return null;
	}

	public double yawToPos;

	public EventTarget onPre(final EventPreMotionUpdates e) {
		if (to != null) {
			if (Minecraft.thePlayer.isSneaking()) {
				double xch = to.getX() + 0.5 - mc.thePlayer.posX;
				double zch = to.getZ() + 0.5 - mc.thePlayer.posZ;

				double dir = Math.toDegrees(Math.atan(xch / zch));

				if (xch > 0) {
					if (zch > 0) { // south-east
						dir = 0 - dir;
					} else if (zch < 0) { // north-east
						dir = -180 - dir;
					} else { // east
						dir = -90;
					}
				} else if (xch < 0) { // west
					if (zch > 0) { // south-west
						dir = 0 - dir;
					} else if (zch < 0) { // north-west
						dir = 180 - dir;
					} else { // west
						dir = 90;
					}
				} else { // motionX = 0
					if (zch > 0) { // south
						dir = 0.0;
					} else if (zch < 0) { // north
						dir = -180;
					}
				}

				yawToPos = Math.toRadians(dir);

				if (!Double.isNaN(yawToPos)) {
					x = Minecraft.thePlayer.posX;
					y = Minecraft.thePlayer.posY;
					z = Minecraft.thePlayer.posZ;
					final float yaw = Minecraft.thePlayer.rotationYaw;
					final float pitch = Minecraft.thePlayer.rotationPitch;
					Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(
							to.getX() + 0.5, to.getY() + 0.05, to.getZ() + 0.5, true));
					Minecraft.thePlayer.sendQueue
							.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x, y, z, true));
					Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(
							to.getX() + 0.5, to.getY(), to.getZ() + 0.5, true));
					Minecraft.thePlayer.sendQueue
							.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x, y, z, true));

					final double dX = -Math.sin(yawToPos) * 0.05;
					final double dZ = Math.cos(yawToPos) * 0.05;
					Minecraft.thePlayer.setPosition(Minecraft.thePlayer.posX + dX, Minecraft.thePlayer.posY + 0.25,
							Minecraft.thePlayer.posZ + dZ);

					Minecraft.thePlayer.rotationYaw = yaw;
					Minecraft.thePlayer.rotationPitch = pitch;
				}
			}
		}
		return null;
	}

	public EventTarget onPost(final EventPostMotionUpdates e) {
		if (to != null) {
			if (new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ).toString()
					.equals(to.toString())) {
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
