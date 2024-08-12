package net.minecraft.client.gui;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import me.EaZy.client.gui.mc.ResourcePackListEntryNew;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.ResourcePackListEntryDefault;
import net.minecraft.client.resources.ResourcePackListEntryFound;
import net.minecraft.client.resources.ResourcePackRepository;
import net.minecraft.util.Util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.Sys;

import com.google.common.collect.Lists;

public class GuiScreenResourcePacks extends GuiScreen {

public static final int EaZy = 511;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final Logger logger = LogManager.getLogger();
	private final GuiScreen field_146965_f;
	private List field_146966_g;
	private List field_146969_h;
	private GuiResourcePackAvailable field_146970_i;
	private GuiResourcePackSelected field_146967_r;
	private boolean field_175289_s = false;
	// private static final String __OBFID = "http://https://fuckuskid00000820";

	public GuiScreenResourcePacks(final GuiScreen p_i45050_1_) {
		field_146965_f = p_i45050_1_;
	}

	/**
	 * Adds the buttons (and other controls) to the screen in question.
	 */
	@Override
	public void initGui() {
		buttonList.add(new GuiOptionButton(2, width / 2 - 154, height - 48,
				I18n.format("resourcePack.openFolder", new Object[0])));
		buttonList.add(new GuiOptionButton(1, width / 2 + 4, height - 48, I18n.format("gui.done", new Object[0])));
		field_146966_g = Lists.newArrayList();
		field_146969_h = Lists.newArrayList();
		final ResourcePackRepository var1 = mc.getResourcePackRepository();
		var1.updateRepositoryEntriesAll();
		final ArrayList var2 = Lists.newArrayList(var1.getRepositoryEntriesAll());
		var2.removeAll(var1.getRepositoryEntries());
		Iterator var3 = var2.iterator();
		ResourcePackRepository.Entry var4;

		while (var3.hasNext()) {
			var4 = (ResourcePackRepository.Entry) var3.next();
			field_146966_g.add(new ResourcePackListEntryFound(this, var4));
		}

		var3 = Lists.reverse(var1.getRepositoryEntries()).iterator();

		while (var3.hasNext()) {
			var4 = (ResourcePackRepository.Entry) var3.next();
			field_146969_h.add(new ResourcePackListEntryFound(this, var4));
		}

		field_146969_h.add(new ResourcePackListEntryDefault(this));
		field_146970_i = new GuiResourcePackAvailable(mc, 200, height, field_146966_g);
		field_146970_i.setSlotXBoundsFromLeft(width / 2 - 4 - 200);
		field_146970_i.registerScrollButtons(7, 8);
		field_146967_r = new GuiResourcePackSelected(mc, 200, height, field_146969_h);
		field_146967_r.setSlotXBoundsFromLeft(width / 2 + 4);
		field_146967_r.registerScrollButtons(7, 8);
	}

	/**
	 * Handles mouse input.
	 */
	@Override
	public void handleMouseInput() throws IOException {
		super.handleMouseInput();
		field_146967_r.func_178039_p();
		field_146970_i.func_178039_p();
	}

	public boolean hasResourcePackEntry(final ResourcePackListEntryNew resourcePackListEntryNew) {
		return field_146969_h.contains(resourcePackListEntryNew);
	}

	public List func_146962_b(final ResourcePackListEntryNew resourcePackListEntryOld) {
		return hasResourcePackEntry(resourcePackListEntryOld) ? field_146969_h : field_146966_g;
	}

	public List func_146964_g() {
		return field_146966_g;
	}

	public List func_146963_h() {
		return field_146969_h;
	}

	@Override
	protected void actionPerformed(final GuiButton button) throws IOException {
		if (button.enabled) {
			if (button.id == 2) {
				final File var2 = mc.getResourcePackRepository().getDirResourcepacks();
				final String var3 = var2.getAbsolutePath();

				if (Util.getOSType() == Util.EnumOS.OSX) {
					try {
						logger.info(var3);
						Runtime.getRuntime().exec(new String[] { "/usr/bin/open", var3 });
						return;
					} catch (final IOException var9) {
						logger.error("Couldn\'t open file", var9);
					}
				} else if (Util.getOSType() == Util.EnumOS.WINDOWS) {
					final String var4 = String.format("cmd.exe /C start \"Open file\" \"%s\"", new Object[] { var3 });

					try {
						Runtime.getRuntime().exec(var4);
						return;
					} catch (final IOException var8) {
						logger.error("Couldn\'t open file", var8);
					}
				}

				boolean var12 = false;

				try {
					final Class var5 = Class.forName("java.awt.Desktop");
					final Object var6 = var5.getMethod("getDesktop", new Class[0]).invoke((Object) null, new Object[0]);
					var5.getMethod("browse", new Class[] { URI.class }).invoke(var6, new Object[] { var2.toURI() });
				} catch (final Throwable var7) {
					logger.error("Couldn\'t open link", var7);
					var12 = true;
				}

				if (var12) {
					logger.info("Opening via system class!");
					Sys.openURL("file://" + var3);
				}
			} else if (button.id == 1) {
				if (field_175289_s) {
					final ArrayList var10 = Lists.newArrayList();
					Iterator var11 = field_146969_h.iterator();

					while (var11.hasNext()) {
						final ResourcePackListEntryNew var13 = (ResourcePackListEntryNew) var11.next();

						if (var13 instanceof ResourcePackListEntryFound) {
							var10.add(((ResourcePackListEntryFound) var13).func_148318_i());
						}
					}

					Collections.reverse(var10);
					mc.getResourcePackRepository().func_148527_a(var10);
					Minecraft.gameSettings.resourcePacks.clear();
					var11 = var10.iterator();

					while (var11.hasNext()) {
						final ResourcePackRepository.Entry var14 = (ResourcePackRepository.Entry) var11.next();
						Minecraft.gameSettings.resourcePacks.add(var14.getResourcePackName());
					}

					Minecraft.gameSettings.saveOptions();
					mc.refreshResources();
				}

				mc.displayGuiScreen(field_146965_f);
			}
		}
	}

	/**
	 * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
	 */
	@Override
	protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		field_146970_i.func_148179_a(mouseX, mouseY, mouseButton);
		field_146967_r.func_148179_a(mouseX, mouseY, mouseButton);
	}

	/**
	 * Called when a mouse button is released. Args : mouseX, mouseY,
	 * releaseButton
	 */
	@Override
	protected void mouseReleased(final int mouseX, final int mouseY, final int state) {
		super.mouseReleased(mouseX, mouseY, state);
	}

	/**
	 * Draws the screen and all the components in it. Args : mouseX, mouseY,
	 * renderPartialTicks
	 */
	@Override
	public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
		drawBackground(0);
		field_146970_i.drawScreen(mouseX, mouseY, partialTicks);
		field_146967_r.drawScreen(mouseX, mouseY, partialTicks);
		drawCenteredString(fontRendererObj, I18n.format("resourcePack.title", new Object[0]), width / 2, 16, 16777215);
		drawCenteredString(fontRendererObj, I18n.format("resourcePack.folderInfo", new Object[0]), width / 2 - 77,
				height - 26, 8421504);
		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	public void func_175288_g() {
		field_175289_s = true;
	}
}
