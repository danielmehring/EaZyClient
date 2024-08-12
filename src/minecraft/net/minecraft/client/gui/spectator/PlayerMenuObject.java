package net.minecraft.client.gui.spectator;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.network.play.client.C18PacketSpectate;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ResourceLocation;

import com.mojang.authlib.GameProfile;

public class PlayerMenuObject implements ISpectatorMenuObject {

public static final int EaZy = 557;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final GameProfile field_178668_a;
	private final ResourceLocation field_178667_b;
	// private static final String __OBFID = "http://https://fuckuskid00001929";

	public PlayerMenuObject(final GameProfile p_i45498_1_) {
		field_178668_a = p_i45498_1_;
		field_178667_b = AbstractClientPlayer.getLocationSkin(p_i45498_1_.getName());
		AbstractClientPlayer.getDownloadImageSkin(field_178667_b, p_i45498_1_.getName());
	}

	@Override
	public void func_178661_a(final SpectatorMenu p_178661_1_) {

		Minecraft.getNetHandler().addToSendQueue(new C18PacketSpectate(field_178668_a.getId()));
	}

	@Override
	public IChatComponent func_178664_z_() {
		return new ChatComponentText(field_178668_a.getName());
	}

	@Override
	public void func_178663_a(final float p_178663_1_, final int p_178663_2_) {

		Minecraft.getTextureManager().bindTexture(field_178667_b);
		GlStateManager.color(1.0F, 1.0F, 1.0F, p_178663_2_ / 255.0F);
		Gui.drawScaledCustomSizeModalRect(2, 2, 8.0F, 8.0F, 8, 8, 12, 12, 64.0F, 64.0F);
		Gui.drawScaledCustomSizeModalRect(2, 2, 40.0F, 8.0F, 8, 8, 12, 12, 64.0F, 64.0F);
	}

	@Override
	public boolean func_178662_A_() {
		return true;
	}
}
