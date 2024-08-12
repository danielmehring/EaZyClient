package me.EaZy.client.utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;

import me.EaZy.client.modules.Team;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.world.WorldSettings;

public class PlayerUtil {

	public static final int EaZy = 236;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	private static final double[] jumpOffsets = new double[] { 0.41999998688697815, 0.7531999805212015,
			1.001335979112147, 1.1661092609382138, 1.249187078744681, 1.249187078744681, 1.1707870772188018,
			1.0155550727021998, 0.7850277037892361, 0.48071087633169185, 0.10408037809303661, 0.0 };

	public static ArrayList<String> getTabList() {
		final NetHandlerPlayClient var4 = Minecraft.thePlayer.sendQueue;
		final Ordering ordered = Ordering.from(new PlayerComparator(null));
		final List orderedList = ordered.sortedCopy(var4.getPlayerInfo());
		final Iterator orderedIterator = orderedList.iterator();

		final ArrayList<String> names = new ArrayList<>();

		while (orderedIterator.hasNext()) {
			final NetworkPlayerInfo var9 = (NetworkPlayerInfo) orderedIterator.next();

			names.add(EnumChatFormatting.getTextWithoutFormattingCodes(var9.getPlayerNameForReal()));
		}

		return names;
	}
	
	public static ArrayList<String> getTabListReal() {
		final NetHandlerPlayClient var4 = Minecraft.thePlayer.sendQueue;
		final Ordering ordered = Ordering.from(new PlayerComparator(null));
		final List orderedList = ordered.sortedCopy(var4.getPlayerInfo());
		final Iterator orderedIterator = orderedList.iterator();

		final ArrayList<String> names = new ArrayList<>();

		while (orderedIterator.hasNext()) {
			final NetworkPlayerInfo var9 = (NetworkPlayerInfo) orderedIterator.next();

			names.add(EnumChatFormatting.getTextWithoutFormattingCodes(var9.getGameProfile().getName()));
		}

		return names;
	}

	private static class PlayerComparator implements Comparator {

		private PlayerComparator() {}

		private int func_178952_a(final NetworkPlayerInfo p_178952_1_, final NetworkPlayerInfo p_178952_2_) {
			final ScorePlayerTeam var3 = p_178952_1_.func_178850_i();
			final ScorePlayerTeam var4 = p_178952_2_.func_178850_i();
			return ComparisonChain.start()
					.compareTrueFirst(p_178952_1_.getGameType() != WorldSettings.GameType.SPECTATOR,
							p_178952_2_.getGameType() != WorldSettings.GameType.SPECTATOR)
					.compare(var3 != null ? var3.getRegisteredName() : "", var4 != null ? var4.getRegisteredName() : "")
					.compare(p_178952_1_.func_178845_a().getName(), p_178952_2_.func_178845_a().getName()).result();
		}

		@Override
		public int compare(final Object p_compare_1_, final Object p_compare_2_) {
			return func_178952_a((NetworkPlayerInfo) p_compare_1_, (NetworkPlayerInfo) p_compare_2_);
		}

		private PlayerComparator(final Object p_i45528_1_) {
			this();
		}
	}

	public static void packetJump() {
		final double[] arrd = jumpOffsets;
		final int n = arrd.length;
		int n2 = 0;
		final double start = Minecraft.thePlayer.posY;
		while (n2 < n) {
			final double jumpOffset = arrd[n2];
			Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(
					Minecraft.thePlayer.posX, start + jumpOffset * 100, Minecraft.thePlayer.posZ, false));
			++n2;
		}
	}

	public static boolean isPlayerMoving() {
		return Minecraft.gameSettings.keyBindForward.getIsKeyPressed()
				|| Minecraft.gameSettings.keyBindLeft.getIsKeyPressed()
				|| Minecraft.gameSettings.keyBindRight.getIsKeyPressed()
				|| Minecraft.gameSettings.keyBindBack.getIsKeyPressed();
	}

	/**
	 * change = 1337, if no move occurs
	 */
	public static double getPressedDirectionChange() {
		double change = Double.NaN;
		boolean forwards = Minecraft.gameSettings.keyBindForward.pressed;
		boolean backwards = Minecraft.gameSettings.keyBindBack.pressed;
		boolean left = Minecraft.gameSettings.keyBindLeft.pressed;
		boolean right = Minecraft.gameSettings.keyBindRight.pressed;

		if (forwards && backwards || left && right || (!forwards && !backwards && !left && !right)) { // nix
			change = Double.NaN;
		} else if (forwards) { // vor
			if (left) { // vor-links
				change = -45;
			} else if (right) { // vor-rechts
				change = 45;
			} else { // vor
				change = 0;
			}
		} else if (backwards) { // vor
			if (left) { // hinter-links
				change = -135;
			} else if (right) { // hinter-rechts
				change = 135;
			} else { // hinter
				change = 180;
			}
		} else if (left) {
			change = -90;
		} else if (right) {
			change = 90;
		}

		return change;
	}

	public static double getMoveDirection() {
		double dir = Math.toDegrees(Math.atan(Minecraft.thePlayer.motionX / Minecraft.thePlayer.motionZ));

		if (Minecraft.thePlayer.motionX > 0) {
			if (Minecraft.thePlayer.motionZ > 0) { // south-east
				dir = 0 - dir;
			} else if (Minecraft.thePlayer.motionZ < 0) { // north-east
				dir = -180 - dir;
			} else { // east
				dir = -90;
			}
		} else if (Minecraft.thePlayer.motionX < 0) { // west
			if (Minecraft.thePlayer.motionZ > 0) { // south-west
				dir = 0 - dir;
			} else if (Minecraft.thePlayer.motionZ < 0) { // north-west
				dir = 180 - dir;
			} else { // west
				dir = 90;
			}
		} else { // motionX = 0
			if (Minecraft.thePlayer.motionZ > 0) { // south
				dir = 0.0;
			} else if (Minecraft.thePlayer.motionZ < 0) { // north
				dir = -180;
			} else {
				// Not moving
			}
		}
		return dir;
	}

	public static boolean isPlayerInTeam(final EntityPlayer ply) {
		return Team.mod.isToggled()
				&& ply.getDisplayName().getFormattedText().startsWith("§" + Team.color.formattingCode);
	}

	public static boolean isPlayerInWater() {
		final ArrayList<Block> collisionBlocks = new ArrayList<>();
		final EntityPlayerSP p = Minecraft.thePlayer;
		final BlockPos var1 = new BlockPos(p.getEntityBoundingBox().minX - 0.001, p.getEntityBoundingBox().minY - 0.001,
				p.getEntityBoundingBox().minZ - 0.001);
		final BlockPos var2 = new BlockPos(p.getEntityBoundingBox().maxX + 0.001, p.getEntityBoundingBox().maxY + 0.001,
				p.getEntityBoundingBox().maxZ + 0.001);
		if (p.worldObj.isAreaLoaded(var1, var2)) {
			int var3 = var1.getX();
			while (var3 <= var2.getX()) {
				int var4 = var1.getY();
				while (var4 <= var2.getY()) {
					int var5 = var1.getZ();
					while (var5 <= var2.getZ()) {
						final BlockPos blockPos = new BlockPos(var3, var4, var5);
						final IBlockState var7 = p.worldObj.getBlockState(blockPos);
						try {
							if (var4 > p.posY - 1.1 && var4 <= p.posY - 0.1) {
								collisionBlocks.add(var7.getBlock());
							}
						} catch (final Throwable var9_11) {
							// empty catch block
						}
						++var5;
					}
					++var4;
				}
				++var3;
			}
		}
		for (final Block b : collisionBlocks) {
			if (b instanceof BlockLiquid) {
				continue;
			}
			return false;
		}
		return !collisionBlocks.isEmpty();
	}

	public static boolean isInLiquid() {
		boolean inLiquid = false;
		final int y = (int) Minecraft.thePlayer.boundingBox.minY;
		int x = MathHelper.floor_double(Minecraft.thePlayer.boundingBox.minX);
		while (x < MathHelper.floor_double(Minecraft.thePlayer.boundingBox.maxX) + 1) {
			int z = MathHelper.floor_double(Minecraft.thePlayer.boundingBox.minZ);
			while (z < MathHelper.floor_double(Minecraft.thePlayer.boundingBox.maxZ) + 1) {
				final Block block = BlockUtils.getBlock(x, y, z);
				if (block != null && !(block instanceof BlockAir) && block instanceof BlockLiquid) {
					inLiquid = true;
				}
				++z;
			}
			++x;
		}
		return inLiquid;
	}

	public static boolean isOnLiquid() {
		boolean onLiquid = false;
		final int y = (int) Minecraft.thePlayer.boundingBox.offset(0.0, -0.01, 0.0).minY;
		int x = MathHelper.floor_double(Minecraft.thePlayer.boundingBox.minX);
		while (x < MathHelper.floor_double(Minecraft.thePlayer.boundingBox.maxX) + 1) {
			int z = MathHelper.floor_double(Minecraft.thePlayer.boundingBox.minZ);
			while (z < MathHelper.floor_double(Minecraft.thePlayer.boundingBox.maxZ) + 1) {
				final Block block = BlockUtils.getBlock(x, y, z);
				if (block != null && !(block instanceof BlockAir)) {
					if (!(block instanceof BlockLiquid)) {
						return false;
					}
					onLiquid = true;
				}
				++z;
			}
			++x;
		}
		return onLiquid;
	}

	public static boolean isPlayerInsideBlock() {
		return BlockUtils.isInsideBlock(Minecraft.thePlayer);
	}
}
