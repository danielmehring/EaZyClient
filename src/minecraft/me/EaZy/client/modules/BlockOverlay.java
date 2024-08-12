package me.EaZy.client.modules;

import java.awt.Color;
import java.util.Arrays;

import org.lwjgl.opengl.GL11;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;

import de.Hero.settings.Setting;
import me.EaZy.client.Category;
import me.EaZy.client.Module;
import me.EaZy.client.events.EventClick;
import me.EaZy.client.events.EventPostMotionUpdates;
import me.EaZy.client.events.EventPreMotionUpdates;
import me.EaZy.client.main.Client;
import me.EaZy.client.utils.BlockUtils;
import me.EaZy.client.utils.ColorUtils;
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

public class BlockOverlay extends Module {

	public static BlockOverlay mod;

	public static final int EaZy = 2051;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	public BlockOverlay() {
		super("BlockOverlay", 0, "", Category.RENDER);
		Client.setmgr.rSetting(new Setting("Red", this, 0f, 0.0f, 255.0f, true));
		Client.setmgr.rSetting(new Setting("Green", this, 255f, 0.0f, 255.0f, true));
		Client.setmgr.rSetting(new Setting("Blue", this, 255f, 0.0f, 255.0f, true));
		mod = this;
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
		super.onUpdate();
	}
}
