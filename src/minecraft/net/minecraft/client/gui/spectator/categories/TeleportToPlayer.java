package net.minecraft.client.gui.spectator.categories;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiSpectator;
import net.minecraft.client.gui.spectator.ISpectatorMenuObject;
import net.minecraft.client.gui.spectator.ISpectatorMenuView;
import net.minecraft.client.gui.spectator.PlayerMenuObject;
import net.minecraft.client.gui.spectator.SpectatorMenu;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.WorldSettings;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;

public class TeleportToPlayer implements ISpectatorMenuView, ISpectatorMenuObject {

public static final int EaZy = 552;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final Ordering field_178674_a = Ordering.from(new Comparator() {
		// private static final String __OBFID =
		// "http://https://fuckuskid00001921";
		public int func_178746_a(final NetworkPlayerInfo p_178746_1_, final NetworkPlayerInfo p_178746_2_) {
			return ComparisonChain.start()
					.compare(p_178746_1_.func_178845_a().getId(), p_178746_2_.func_178845_a().getId()).result();
		}

		@Override
		public int compare(final Object p_compare_1_, final Object p_compare_2_) {
			return func_178746_a((NetworkPlayerInfo) p_compare_1_, (NetworkPlayerInfo) p_compare_2_);
		}
	});
	private final List field_178673_b;
	// private static final String __OBFID = "http://https://fuckuskid00001922";

	public TeleportToPlayer() {

		this(field_178674_a.sortedCopy(Minecraft.getNetHandler().getPlayerInfo()));
	}

	public TeleportToPlayer(final Collection p_i45493_1_) {
		field_178673_b = Lists.newArrayList();
		final Iterator var2 = field_178674_a.sortedCopy(p_i45493_1_).iterator();

		while (var2.hasNext()) {
			final NetworkPlayerInfo var3 = (NetworkPlayerInfo) var2.next();

			if (var3.getGameType() != WorldSettings.GameType.SPECTATOR) {
				field_178673_b.add(new PlayerMenuObject(var3.func_178845_a()));
			}
		}
	}

	@Override
	public List func_178669_a() {
		return field_178673_b;
	}

	@Override
	public IChatComponent func_178670_b() {
		return new ChatComponentText("Select a player to teleport to");
	}

	@Override
	public void func_178661_a(final SpectatorMenu p_178661_1_) {
		p_178661_1_.func_178647_a(this);
	}

	@Override
	public IChatComponent func_178664_z_() {
		return new ChatComponentText("Teleport to player");
	}

	@Override
	public void func_178663_a(final float p_178663_1_, final int p_178663_2_) {

		Minecraft.getTextureManager().bindTexture(GuiSpectator.field_175269_a);
		Gui.drawModalRectWithCustomSizedTexture(0, 0, 0.0F, 0.0F, 16, 16, 256.0F, 256.0F);
	}

	@Override
	public boolean func_178662_A_() {
		return !field_178673_b.isEmpty();
	}
}
