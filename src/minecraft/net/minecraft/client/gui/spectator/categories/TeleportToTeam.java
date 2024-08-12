package net.minecraft.client.gui.spectator.categories;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiSpectator;
import net.minecraft.client.gui.spectator.ISpectatorMenuObject;
import net.minecraft.client.gui.spectator.ISpectatorMenuView;
import net.minecraft.client.gui.spectator.SpectatorMenu;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

public class TeleportToTeam implements ISpectatorMenuView, ISpectatorMenuObject {

public static final int EaZy = 553;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final List field_178672_a = Lists.newArrayList();
	// private static final String __OBFID = "http://https://fuckuskid00001920";

	public TeleportToTeam() {

		final Iterator var2 = Minecraft.theWorld.getScoreboard().getTeams().iterator();

		while (var2.hasNext()) {
			final ScorePlayerTeam var3 = (ScorePlayerTeam) var2.next();
			field_178672_a.add(new TeleportToTeam.TeamSelectionObject(var3));
		}
	}

	@Override
	public List func_178669_a() {
		return field_178672_a;
	}

	@Override
	public IChatComponent func_178670_b() {
		return new ChatComponentText("Select a team to teleport to");
	}

	@Override
	public void func_178661_a(final SpectatorMenu p_178661_1_) {
		p_178661_1_.func_178647_a(this);
	}

	@Override
	public IChatComponent func_178664_z_() {
		return new ChatComponentText("Teleport to team member");
	}

	@Override
	public void func_178663_a(final float p_178663_1_, final int p_178663_2_) {

		Minecraft.getTextureManager().bindTexture(GuiSpectator.field_175269_a);
		Gui.drawModalRectWithCustomSizedTexture(0, 0, 16.0F, 0.0F, 16, 16, 256.0F, 256.0F);
	}

	@Override
	public boolean func_178662_A_() {
		final Iterator var1 = field_178672_a.iterator();
		ISpectatorMenuObject var2;

		do {
			if (!var1.hasNext()) {
				return false;
			}

			var2 = (ISpectatorMenuObject) var1.next();
		}
		while (!var2.func_178662_A_());

		return true;
	}

	class TeamSelectionObject implements ISpectatorMenuObject {
		private final ScorePlayerTeam field_178676_b;
		private final ResourceLocation field_178677_c;
		private final List field_178675_d;
		// private static final String __OBFID =
		// "http://https://fuckuskid00001919";

		public TeamSelectionObject(final ScorePlayerTeam p_i45492_2_) {
			field_178676_b = p_i45492_2_;
			field_178675_d = Lists.newArrayList();
			final Iterator var3 = p_i45492_2_.getMembershipCollection().iterator();

			while (var3.hasNext()) {
				final String var4 = (String) var3.next();

				final NetworkPlayerInfo var5 = Minecraft.getNetHandler().func_175104_a(var4);

				if (var5 != null) {
					field_178675_d.add(var5);
				}
			}

			if (!field_178675_d.isEmpty()) {
				final String var6 = ((NetworkPlayerInfo) field_178675_d
						.get(new Random().nextInt(field_178675_d.size()))).func_178845_a().getName();
				field_178677_c = AbstractClientPlayer.getLocationSkin(var6);
				AbstractClientPlayer.getDownloadImageSkin(field_178677_c, var6);
			} else {
				field_178677_c = DefaultPlayerSkin.func_177335_a();
			}
		}

		@Override
		public void func_178661_a(final SpectatorMenu p_178661_1_) {
			p_178661_1_.func_178647_a(new TeleportToPlayer(field_178675_d));
		}

		@Override
		public IChatComponent func_178664_z_() {
			return new ChatComponentText(field_178676_b.func_96669_c());
		}

		@Override
		public void func_178663_a(final float p_178663_1_, final int p_178663_2_) {
			int var3 = -1;
			final String var4 = FontRenderer.getFormatFromString(field_178676_b.getColorPrefix());

			if (var4.length() >= 2) {
				var3 = Minecraft.getMinecraft().fontRendererObj.func_175064_b(var4.charAt(1));
			}

			if (var3 >= 0) {
				final float var5 = (var3 >> 16 & 255) / 255.0F;
				final float var6 = (var3 >> 8 & 255) / 255.0F;
				final float var7 = (var3 & 255) / 255.0F;
				Gui.drawRect(1, 1, 15, 15,
						MathHelper.func_180183_b(var5 * p_178663_1_, var6 * p_178663_1_, var7 * p_178663_1_)
								| p_178663_2_ << 24);
			}

			Minecraft.getTextureManager().bindTexture(field_178677_c);
			GlStateManager.color(p_178663_1_, p_178663_1_, p_178663_1_, p_178663_2_ / 255.0F);
			Gui.drawScaledCustomSizeModalRect(2, 2, 8.0F, 8.0F, 8, 8, 12, 12, 64.0F, 64.0F);
			Gui.drawScaledCustomSizeModalRect(2, 2, 40.0F, 8.0F, 8, 8, 12, 12, 64.0F, 64.0F);
		}

		@Override
		public boolean func_178662_A_() {
			return !field_178675_d.isEmpty();
		}
	}
}
