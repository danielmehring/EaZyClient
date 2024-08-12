package me.EaZy.client.modules;

import org.lwjgl.opengl.GL11;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;

import me.EaZy.client.Category;
import me.EaZy.client.Module;
import me.EaZy.client.events.EventClick;
import me.EaZy.client.events.EventSendPacket;
import me.EaZy.client.utils.EntityUtil;
import me.EaZy.client.utils.PlayerUtil;
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

public class VanillaTP extends Module {

	public static VanillaTP mod;

	public static final int EaZy = 193;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	public VanillaTP() {
		super("VanillaTP", 0, "tp", Category.MOVEMENT, "Teleport on Vanilla.");
		mod = this;
	}

	private static boolean isTpProgress;
	private BlockPos tpPos;
	private int delay = 0;

	@Override
	public void onUpdate() {
		// Unnötig
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
		// Bis hier

		delay++;

		if (isTpProgress && tpPos != null && delay >= 5) {
			isTpProgress = false;
			tpPos = null;
		}

		super.onUpdate();
	}

	@Override
	public String getRenderName() {
		if (GuiMainMenu.ersterapril) {
			return "Teleportierer mit Vanille Geschmack";
		} else {
			return super.getRenderName();
		}
	}

	public EventTarget onClick(final EventClick e) {
		MovingObjectPosition obj = EntityUtil.rayTraceBlocks(9001);
		if (obj != null && delay >= 20 && tpPos == null && Minecraft.thePlayer.movementInput.sneak
				&& obj.typeOfHit == MovingObjectType.BLOCK && canTeleport(obj.getBlockPos()) != -1) {
			tpPos = new BlockPos(obj.getBlockPos().getX(), obj.getBlockPos().getY() + canTeleport(obj.getBlockPos()),
					obj.getBlockPos().getZ());
			msg("§2Teleport-Position§7: §3X:" + tpPos.getX() + " Y:" + tpPos.getY() + " Z:" + tpPos.getZ());
			Minecraft.thePlayer.setPosition(tpPos.getX() + 0.5, tpPos.getY(), tpPos.getZ() + 0.5);
			mc.thePlayer.sendQueue.netManager.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(tpPos.getX() + 0.5,
					tpPos.getY(), tpPos.getZ() + 0.5, true));
			isTpProgress = true;
			delay = 0;
		}
		return null;
	}

	public EventTarget onSendPacket(final EventSendPacket e) {
		if (isTpProgress && e.getPacket() instanceof C03PacketPlayer) {
			e.setCancelled(true);
		}
		return null;
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

	@Override
	public void onRender() {
		if (!isToggled()) {
			return;
		}

		if (delay >= 20 && tpPos != null) {
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
		super.onEnable();
	}

	@Override
	public void onDisable() {
		EventManager.unregister(this);
		tpPos = null;
		isTpProgress = false;
		super.onDisable();
	}
}
