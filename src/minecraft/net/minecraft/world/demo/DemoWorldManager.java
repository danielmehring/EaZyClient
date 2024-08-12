package net.minecraft.world.demo;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.S2BPacketChangeGameState;
import net.minecraft.server.management.ItemInWorldManager;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class DemoWorldManager extends ItemInWorldManager {

public static final int EaZy = 1718;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private boolean field_73105_c;
	private boolean demoTimeExpired;
	private int field_73104_e;
	private int field_73102_f;
	// private static final String __OBFID = "http://https://fuckuskid00001429";

	public DemoWorldManager(final World worldIn) {
		super(worldIn);
	}

	@Override
	public void updateBlockRemoving() {
		super.updateBlockRemoving();
		++field_73102_f;
		final long var1 = theWorld.getTotalWorldTime();
		final long var3 = var1 / 24000L + 1L;

		if (!field_73105_c && field_73102_f > 20) {
			field_73105_c = true;
			thisPlayerMP.playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(5, 0.0F));
		}

		demoTimeExpired = var1 > 120500L;

		if (demoTimeExpired) {
			++field_73104_e;
		}

		if (var1 % 24000L == 500L) {
			if (var3 <= 6L) {
				thisPlayerMP.addChatMessage(new ChatComponentTranslation("demo.day." + var3, new Object[0]));
			}
		} else if (var3 == 1L) {
			if (var1 == 100L) {
				thisPlayerMP.playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(5, 101.0F));
			} else if (var1 == 175L) {
				thisPlayerMP.playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(5, 102.0F));
			} else if (var1 == 250L) {
				thisPlayerMP.playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(5, 103.0F));
			}
		} else if (var3 == 5L && var1 % 24000L == 22000L) {
			thisPlayerMP.addChatMessage(new ChatComponentTranslation("demo.day.warning", new Object[0]));
		}
	}

	/**
	 * Sends a message to the player reminding them that this is the demo
	 * version
	 */
	private void sendDemoReminder() {
		if (field_73104_e > 100) {
			thisPlayerMP.addChatMessage(new ChatComponentTranslation("demo.reminder", new Object[0]));
			field_73104_e = 0;
		}
	}

	@Override
	public void func_180784_a(final BlockPos p_180784_1_, final EnumFacing p_180784_2_) {
		if (demoTimeExpired) {
			sendDemoReminder();
		} else {
			super.func_180784_a(p_180784_1_, p_180784_2_);
		}
	}

	@Override
	public void func_180785_a(final BlockPos p_180785_1_) {
		if (!demoTimeExpired) {
			super.func_180785_a(p_180785_1_);
		}
	}

	@Override
	public boolean func_180237_b(final BlockPos p_180237_1_) {
		return demoTimeExpired ? false : super.func_180237_b(p_180237_1_);
	}

	/**
	 * Attempts to right-click use an item by the given EntityPlayer in the
	 * given World
	 */
	@Override
	public boolean tryUseItem(final EntityPlayer p_73085_1_, final World worldIn, final ItemStack p_73085_3_) {
		if (demoTimeExpired) {
			sendDemoReminder();
			return false;
		} else {
			return super.tryUseItem(p_73085_1_, worldIn, p_73085_3_);
		}
	}

	@Override
	public boolean func_180236_a(final EntityPlayer p_180236_1_, final World worldIn, final ItemStack p_180236_3_,
			final BlockPos p_180236_4_, final EnumFacing p_180236_5_, final float p_180236_6_, final float p_180236_7_,
			final float p_180236_8_) {
		if (demoTimeExpired) {
			sendDemoReminder();
			return false;
		} else {
			return super.func_180236_a(p_180236_1_, worldIn, p_180236_3_, p_180236_4_, p_180236_5_, p_180236_6_,
					p_180236_7_, p_180236_8_);
		}
	}
}
