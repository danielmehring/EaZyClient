package me.EaZy.client.hooks;

import java.awt.Color;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.lwjgl.opengl.GL11;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;

import de.Hero.clickgui.ClickGUI;
import de.Hero.clickgui.Panel;
import me.EaZy.client.Category;
import me.EaZy.client.Module;
import me.EaZy.client.events.EventReceivePacket;
import me.EaZy.client.main.Client;
import me.EaZy.client.modules.Speed;
import me.EaZy.client.modules.glides.HypixelFly;
import me.EaZy.client.modules.hud.Appearance;
import me.EaZy.client.modules.hud.Other;
import me.EaZy.client.tabgui.GuiTabHandler;
import me.EaZy.client.utils.ColorUtils;
import me.EaZy.client.utils.RenderUtils;
import me.EaZy.client.utils.TimeHelper;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;

public class InGameGUIHook extends GuiScreen {

	public static final int EaZy = 84;

	@Override
	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
		});
	}

	private final boolean watermark = true;
	// private final boolean arraylist = true;
	// public static boolean coords = false;
	// public static boolean fps = false;
	// public static boolean ign = false;
	// public static boolean time = false;
	// public static boolean potions = true;
	// public static boolean armor = true;
	// public static boolean Client.getValues().Other_TabGui.getState() = true;
	// public static boolean ping = true;
	private final TimeHelper timer = new TimeHelper();

	public InGameGUIHook() {
		EventManager.register(this);
		mc = Minecraft.getMinecraft();
	}

	private static final ArrayList<Module> sorted = new ArrayList<>();

	public EventTarget onEventCalled(final EventReceivePacket event) {
		if (event.getPacket() instanceof S02PacketChat) {
			return null;
		}
		timer.reset();
		return null;
	}

	private static void addBiggestMod() {
		Module modname = null;
		for (final Module mod : Client.getModules()) {
			if (!sorted.contains(mod)) {
				try {
					if (modname == null) {
						modname = mod;
					} else if (Client.font.getStringWidth(mod.getRenderName()) > Client.font
							.getStringWidth(modname.getRenderName())) {
						modname = mod;
					}
				} catch (final Exception e) {
					e.printStackTrace();
				}
			}
		}
		sorted.add(modname);
	}

	public static void sort() {
		sorted.clear();
		Client.getModules().forEach((_item) -> {
			addBiggestMod();
		});
	}

	private void drawArraylist(final ScaledResolution scaledResolution) {

		final int x = scaledResolution.getScaledWidth() - 1;
		int y = 3;
		try {

			int lastX = 0;
			for (final Module mod3 : sorted) {
				if (mod3.isCategory(Category.HIDDEN) || mod3.isCategory(Category.HUD)) {
					continue;
				}
				if (mod3.isCategory(Category.SPEED) && Speed.mod.isToggled()) {
					continue;
				}
				if (!mod3.isToggled() && mod3.xxx == -2) {
					continue;
				}

				final int anispeed = 1;
				if (mod3.isToggled()) {
					if (mod3.xxx < Client.font.getStringWidth(mod3.getRenderName())) {
						mod3.xxx += anispeed;
					} else {
						mod3.xxx = Client.font.getStringWidth(mod3.getRenderName());
					}
				} else if (!mod3.isToggled()) {
					if (mod3.xxx > -2) {
						mod3.xxx -= anispeed;
					} else {
						mod3.xxx = -2;
					}
				}

				final String name = mod3.getRenderName();

				RenderUtils.drawRect(x - mod3.xxx - 1, y - 2, x + 1, y + 8, new Color(0.1f, 0.1f, 0.1f, 0.6f).getRGB());
				Client.font.drawString(name, x - mod3.xxx, y,
						Client.getColor((long) (600000000L * ((y - 2) * 0.05))).getRGB());

				y += 10;
			}

		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	private void drawPing(final ScaledResolution scaledResolution) {
		if (mc.isSingleplayer()) {
			return;
		}
		int ping = 0;
		for (final Object obj : Minecraft.getNetHandler().playerInfoMap.values()) {
			if (!(obj instanceof NetworkPlayerInfo)) {
				return;
			}
			final NetworkPlayerInfo player = (NetworkPlayerInfo) obj;
			if (!StringUtils.stripControlCodes(player.getGameProfile().getName())
					.equals(StringUtils.stripControlCodes(Minecraft.thePlayer.getName()))) {
				continue;
			}
			ping = player.responseTime;
			break;
		}
		int y = Client.setmgr.getSettingByName(Other.mod, "TabGui").getValBoolean() ? 107 : 8;
		if (Client.setmgr.getSettingByName(Other.mod, "Coords").getValBoolean()) {
			y += 10;
		}
		if (Client.setmgr.getSettingByName(Other.mod, "IGN").getValBoolean()) {
			y += 10;
		}
		if (Client.setmgr.getSettingByName(Other.mod, "FPS").getValBoolean()) {
			y += 10;
		}
		if (Client.setmgr.getSettingByName(Other.mod, "ServerIP").getValBoolean()) {
			y += 10;
		}
		Client.font.drawStringWithShadow("§6Ping: §a" + ping + "ms", 2, y += 10, -1);
	}

	private void drawLag(final ScaledResolution scaledResolution) {
		if (mc.isSingleplayer()) {
			return;
		}
		String lag = "";
		String color;
		if (timer.hasReached(20000)) {
			color = "§4";
		} else if (timer.hasReached(10000)) {
			color = "§c";
		} else if (timer.hasReached(5000)) {
			color = "§6";
		} else if (timer.hasReached(2500)) {
			color = "§e";
		} else if (timer.hasReached(1000)) {
			color = "§a";
		} else {
			color = "§a";
		}
		if (timer.hasReached(1000)) {
			lag = color + timer.getDifference() + "ms";
		} else {
			return;
		}
		int y = Client.setmgr.getSettingByName(Other.mod, "TabGui").getValBoolean() ? 107 : 8;
		if (Client.setmgr.getSettingByName(Other.mod, "Coords").getValBoolean()) {
			y += 10;
		}
		if (Client.setmgr.getSettingByName(Other.mod, "IGN").getValBoolean()) {
			y += 10;
		}
		if (Client.setmgr.getSettingByName(Other.mod, "FPS").getValBoolean()) {
			y += 10;
		}
		if (Client.setmgr.getSettingByName(Other.mod, "Ping").getValBoolean()) {
			y += 10;
		}
		if (Client.setmgr.getSettingByName(Other.mod, "ServerIP").getValBoolean()) {
			y += 10;
		}
		Client.font.drawStringWithShadow("§6Lag: " + color + lag, 2, y += 10, -1);
	}

	private void drawArmor(final ScaledResolution scaledResolution) {
		if (Minecraft.currentScreen instanceof ClickGUI) {
			return;
		}
		if (Minecraft.playerController.isNotCreative()) {
			int x = 14;
			final int drawWidth = scaledResolution.getScaledWidth() / 2;
			final int drawHeight = scaledResolution.getScaledHeight()
					- (Minecraft.thePlayer.isInsideOfMaterial(Material.water)
							? Minecraft.thePlayer.getActivePotionEffect(Potion.absorption) != null ? 55 : 65 : 55);
			GlStateManager.pushMatrix();
			GlStateManager.enableRescaleNormal();
			GlStateManager.disableBlend();
			for (int index = 3; index >= 0; --index) {
				ItemStack stack = Minecraft.thePlayer.inventory.armorInventory[index];
				if (stack == null) {
					continue;
				}
				stack = stack.copy();
				if (stack.hasEffect()) {
					stack.stackTagCompound = null;
				}
				RenderHelper.enableStandardItemLighting();
				mc.getRenderItem().renderItemAndEffectIntoGUI(stack, drawWidth + x, drawHeight);
				mc.getRenderItem().renderItemOverlayIntoGUI(mc.fontRendererObj, stack, drawWidth + x, drawHeight);
				RenderHelper.disableStandardItemLighting();
				GlStateManager.disableDepth();
				GlStateManager.depthMask(false);
				GlStateManager.scale(0.5f, 0.5f, 0.5f);
				if (stack.getItem() == Items.golden_apple && stack.hasEffect()) {
					Client.font.drawStringWithShadow("god", (drawWidth + x) * 2, drawHeight * 2, -65536);
				} else {
					final NBTTagList enchants = stack.getEnchantmentTagList();
					if (enchants != null) {
						int encY = 0;
						final Enchantment[] important = new Enchantment[] { Enchantment.protection,
								Enchantment.unbreaking, Enchantment.sharpness, Enchantment.fireAspect,
								Enchantment.efficiency, Enchantment.feather_falling, Enchantment.power,
								Enchantment.flame, Enchantment.punch, Enchantment.fortune, Enchantment.infinity,
								Enchantment.thorns };
						if (enchants.tagCount() >= 6) {
							Client.font.drawStringWithShadow("god", (drawWidth + x) * 2, drawHeight * 2, -65536);
						} else {
							block1: for (int index1 = 0; index1 < enchants.tagCount(); ++index1) {
								final short id = enchants.getCompoundTagAt(index1).getShort("id");
								final short level = enchants.getCompoundTagAt(index1).getShort("lvl");
								final Enchantment enc = Enchantment.byID(id);
								if (enc == null) {
									continue;
								}
								for (final Enchantment importantEnchantment : important) {
									if (enc != importantEnchantment) {
										continue;
									}
									String encName = enc.getTranslatedName(level).substring(0, 1).toLowerCase();
									encName = level > 99 ? encName + "99+" : encName + level;
									Client.font.drawStringWithShadow(encName, (drawWidth + x) * 2,
											(drawHeight + encY) * 2, -5592406);
									encY += 5;
									continue block1;
								}
							}
						}
					}
				}
				GlStateManager.depthMask(true);
				GlStateManager.enableDepth();
				GlStateManager.scale(2.0f, 2.0f, 2.0f);
				x += 18;
			}
			GlStateManager.disableRescaleNormal();
			GlStateManager.popMatrix();
		}
	}

	private void drawPotions(final ScaledResolution scaledResolution) {
		final int x = scaledResolution.getScaledWidth() - 2;
		int y = scaledResolution.getScaledHeight() - 10;
		if (mc.ingameGUI.getChatGUI().getChatOpen()) {
			y -= 13;
		}
		for (final Object o : Minecraft.thePlayer.getActivePotionEffects()) {
			final PotionEffect effect = (PotionEffect) o;
			String name = I18n.format(effect.getEffectName(), new Object[0]);
			if (effect.getAmplifier() == 1) {
				name = name + " " + I18n.format("enchantment.level.2", new Object[0]);
			} else if (effect.getAmplifier() == 2) {
				name = name + " " + I18n.format("enchantment.level.3", new Object[0]);
			} else if (effect.getAmplifier() == 3) {
				name = name + " " + I18n.format("enchantment.level.4", new Object[0]);
			} else if (effect.getAmplifier() > 0) {
				name = name + " " + (effect.getAmplifier() + 1);
			}
			int var1 = effect.getDuration() / 20;
			final int var2 = var1 / 60;
			var1 %= 60;
			int color = 55;
			if (var2 == 0) {
				if (var1 <= 5) {
					color = 52;
				} else if (var1 <= 10) {
					color = 99;
				} else if (var1 <= 15) {
					color = 54;
				} else if (var1 <= 20) {
					color = 101;
				}
			}
			name = String.format("%s §%s%s", name, Character.valueOf((char) color), Potion.getDurationString(effect));
			Client.font.drawStringWithShadow(name, x - Client.font.getStringWidth(name), y,
					Potion.potionTypes[effect.getPotionID()].getLiquidColor());
			y -= 10;
		}
	}

	private void drawInfo(final ScaledResolution scaledResolution) {
		final ArrayList<String> info = new ArrayList<>();

		int y = Client.setmgr.getSettingByName(Other.mod, "TabGui").getValBoolean() ? 107 : 8;

		if (Client.setmgr.getSettingByName(Other.mod, "Coords").getValBoolean()) {
			info.add(String.format("§6XYZ:§a %s %s %s", MathHelper.floor_double(Minecraft.thePlayer.posX),
					MathHelper.floor_double(Minecraft.thePlayer.posY),
					MathHelper.floor_double(Minecraft.thePlayer.posZ)));
		}
		if (Client.setmgr.getSettingByName(Other.mod, "IGN").getValBoolean()) {
			info.add(String.format("§6IGN:§a %s", mc.getSession().getUsername()));
		}
		if (Client.setmgr.getSettingByName(Other.mod, "FPS").getValBoolean()) {
			info.add(String.format("§6FPS:§a %s", mc.debug.split(" fps")[0]));
		}
		if (Client.setmgr.getSettingByName(Other.mod, "ServerIP").getValBoolean()) {
			info.add(String.format("§6ServerIP:§a %s",
					mc.getCurrentServerData() == null ? "SinglePlayer" : mc.getCurrentServerData().serverIP));
		}
		for (final String infoString : info) {
			Client.font.drawStringWithShadow(infoString, 2, y + 10, -1);
			y += 10;
		}
	}

	public void renderScreen() {
		int x;
		int y;
		if (Minecraft.gameSettings.showDebugInfo) {
			return;
		}
		final ScaledResolution sr = RenderUtils.newScaledResolution();
		GlStateManager.pushMatrix();
		GlStateManager.enableBlend();
		if (watermark) {

			// TODO: draw Watermark

			drawRect(1, 1, 69, 15, 0x79000000);

			drawRect(2, 2, 68, 3, Client.getColor(0l).getRGB());

			drawRect(2, 13, 68, 14, Client.getColor(0l).getRGB());
			GL11.glPushMatrix();

			Client.font.drawString("EaZy", 35 - Client.font.getStringWidth("EaZy") / 2, 4,
					Client.getColor(0l).getRGB());

			GL11.glPopMatrix();

		}
		if (Client.setmgr.getSettingByName(Other.mod, "Time").getValBoolean()) {
			x = 70;
			final SimpleDateFormat format = new SimpleDateFormat("hh:mm a");
			Client.font.drawStringWithShadow("§6" + format.format(new Date()), x, 2.0f, 0);
		}
		if (Client.setmgr.getSettingByName(Other.mod, "TabGui").getValBoolean()) {
			y = 20;
			int _x = 2;
			// TODO
			
			Client.getTabHandler().drawGui(_x, y);
		}
		drawInfo(sr);
		if (Client.setmgr.getSettingByName(Other.mod, "ArrayList").getValBoolean()) {
			drawArraylist(sr);
		}

		if (Client.setmgr.getSettingByName(Other.mod, "Potions").getValBoolean()) {
			drawPotions(sr);
		}
		if (Client.setmgr.getSettingByName(Other.mod, "Armor").getValBoolean()) {
			drawArmor(sr);
		}
		if (Client.setmgr.getSettingByName(Other.mod, "Ping").getValBoolean()) {
			drawPing(sr);
		}

		drawLag(sr);
		if (Minecraft.currentScreen == null) {
			Client.font.drawString(Client.username, 2, sr.getScaledHeight() - mc.fontRendererObj.FONT_HEIGHT,
					0xffffffff);
		}
		if (Minecraft.currentScreen == null || Minecraft.currentScreen instanceof GuiContainer) {
			for (Panel p : ClickGUI.panels) {
				if (p.pinned) {
					p.drawScreen(0, 0, 42);
				}
			}
		}
		if (mc.antiLag) {
			Client.font.drawCenteredString("Reducing Lag...", sr.getScaledWidth() / 2, 2, 0xffffffff);
		}

		GlStateManager.disableBlend();
		GlStateManager.popMatrix();

	}
}
