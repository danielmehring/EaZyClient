package me.EaZy.client.gui;

import java.io.IOException;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import me.EaZy.client.alts.GuiEmailFieldLogin;
import me.EaZy.client.utils.MathUtils;
import me.EaZy.client.utils.MiscUtils;
import me.EaZy.client.utils.RenderUtils;
import me.EaZy.client.utils.ShitUtils;
import me.EaZy.client.utils.ShitUtils2;
import me.EaZy.client.utils.ShitUtils3;
import me.EaZy.client.utils.holoimage.Image;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.network.play.client.C10PacketCreativeInventoryAction;

public class GuiHolo extends GuiScreen {

	public static final int EaZy = 2058;

	private static byte selection = 0;

	public static GuiEmailFieldLogin normal_text;
	public static boolean normal_motion;
	public static GuiEmailFieldLogin normal_motionMin;
	public static GuiEmailFieldLogin normal_motionMax;
	public static boolean normal_block;
	public static boolean normal_lockPos;

	public static GuiEmailFieldLogin image_text;
	public static GuiEmailFieldLogin image_width;
	public static GuiEmailFieldLogin image_height;

	public static GuiEmailFieldLogin spammer_text;
	public static boolean spammer_motion;
	public static GuiEmailFieldLogin spammer_motionMin;
	public static GuiEmailFieldLogin spammer_motionMax;
	public static boolean spammer_block;
	public static boolean spammer_randomColors;
	public static boolean spammer_coloredArmorStands;

	public static GuiEmailFieldLogin location;

	@Override
	public void initGui() {

		if (selection == 0) { // first
			location = new GuiEmailFieldLogin(1, fontRendererObj, width / 2 - 50, height - 10, 100, 10);
			location.setMaxStringLength(Integer.MAX_VALUE);
			location.setText(MathUtils.round(mc.thePlayer.posX, 2) + ", " + MathUtils.round(mc.thePlayer.posY, 2) + ", "
					+ MathUtils.round(mc.thePlayer.posZ, 2));

			normal_text = new GuiEmailFieldLogin(101, fontRendererObj, width / 6 + 2, 73, width / 6 * 4 - 1, 10);
			normal_text.setMaxStringLength(Integer.MAX_VALUE);
			normal_text.setFocused(true);
			normal_motionMin = new GuiEmailFieldLogin(102, fontRendererObj, width / 6 + 2, 123, 50, 10);
			normal_motionMin.setMaxStringLength(Integer.MAX_VALUE);
			normal_motionMin.setText("0.0");
			normal_motionMax = new GuiEmailFieldLogin(103, fontRendererObj, width / 6 + 2, 143, 50, 10);
			normal_motionMax.setMaxStringLength(Integer.MAX_VALUE);
			normal_motionMax.setText("5.0");

			image_text = new GuiEmailFieldLogin(201, fontRendererObj, width / 6 + 2, 73, width / 6 * 4 - 1, 10);
			image_text.setMaxStringLength(Integer.MAX_VALUE);
			image_width = new GuiEmailFieldLogin(202, fontRendererObj, width / 6 + 2, 103, 50, 10);
			image_width.setMaxStringLength(Integer.MAX_VALUE);
			image_height = new GuiEmailFieldLogin(203, fontRendererObj, width / 6 + 2, 123, 50, 10);
			image_height.setMaxStringLength(Integer.MAX_VALUE);

			spammer_text = new GuiEmailFieldLogin(301, fontRendererObj, width / 6 + 2, 73, width / 6 * 4 - 1, 10);
			spammer_text.setMaxStringLength(Integer.MAX_VALUE);
			spammer_motionMin = new GuiEmailFieldLogin(302, fontRendererObj, width / 6 + 2, 123, 50, 10);
			spammer_motionMin.setMaxStringLength(Integer.MAX_VALUE);
			spammer_motionMin.setText("0.0");
			spammer_motionMax = new GuiEmailFieldLogin(303, fontRendererObj, width / 6 + 2, 143, 50, 10);
			spammer_motionMax.setMaxStringLength(Integer.MAX_VALUE);
			spammer_motionMax.setText("5.0");
			selection = 1;
		}
		location.xPosition = width / 2 - 50;

		normal_text.xPosition = normal_motionMin.xPosition = normal_motionMax.xPosition = width / 6 + 2;
		normal_text.width = width / 6 * 4 - 1;

		image_text.xPosition = image_width.xPosition = image_height.xPosition = width / 6 + 2;
		image_text.width = width / 6 * 4 - 1;

		spammer_text.xPosition = spammer_motionMin.xPosition = spammer_motionMax.xPosition = width / 6 + 2;
		spammer_text.width = width / 6 * 4 - 1;

		Keyboard.enableRepeatEvents(true);
		buttonList.clear();
		buttonList.add(new GuiInvisButton(1, width - 80, height - 22, 80, 22, "Give"));
		buttonList.add(new GuiInvisButton(2, 0, height - 22, 80, 22, "Set Location"));
		buttonList.add(new GuiInvisButton(1001, width / 6 * 1, 40, width / 6, 20, "Normal"));
		buttonList.add(new GuiInvisButton(1002, width / 6 * 2 + 1, 40, width / 6, 20, "Image"));
		buttonList.add(new GuiInvisButton(1003, width / 6 * 3 + 2, 40, width / 6, 20, "Spammer"));
		buttonList.add(new GuiInvisButton(1004, width / 6 * 4 + 3, 40, width / 6, 20, "Crash"));

		buttonList
				.add(new GuiButton(101, width / 6 * 1 + mc.fontRendererObj.getStringWidth("Motion") + 5, 89, 9, 9, ""));
		buttonList
				.add(new GuiButton(102, width / 6 * 1 + mc.fontRendererObj.getStringWidth("Motion") + 5, 99, 9, 9, ""));
		buttonList.add(
				new GuiButton(103, width / 6 * 1 + mc.fontRendererObj.getStringWidth("Lock Pos") + 5, 159, 9, 9, ""));

		buttonList
				.add(new GuiButton(301, width / 6 * 1 + mc.fontRendererObj.getStringWidth("Motion") + 5, 89, 9, 9, ""));
		buttonList
				.add(new GuiButton(302, width / 6 * 1 + mc.fontRendererObj.getStringWidth("Motion") + 5, 99, 9, 9, ""));
		buttonList.add(new GuiButton(303, width / 6 * 1 + mc.fontRendererObj.getStringWidth("Random Colors") + 5, 159,
				9, 9, ""));
		buttonList.add(new GuiButton(304, width / 6 * 1 + mc.fontRendererObj.getStringWidth("Colored Armor") + 5, 169,
				9, 9, ""));

		updateStates();
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		if (button.id == 1001) {
			selection = 1;
		} else if (button.id == 1002) {
			selection = 2;
		} else if (button.id == 1003) {
			selection = 3;
		} else if (button.id == 1004) {
			selection = 4;
		}
		if (button.id > 1000) {
			updateStates();
		}

		if (button.id == 101) {
			normal_block = !normal_block;
		}
		if (button.id == 102) {
			normal_motion = !normal_motion;
		}
		if (button.id == 103) {
			normal_lockPos = !normal_lockPos;
		}

		if (button.id == 301) {
			spammer_block = !spammer_block;
		}
		if (button.id == 302) {
			spammer_motion = !spammer_motion;
		}
		if (button.id == 303) {
			spammer_randomColors = !spammer_randomColors;
		}
		if (button.id == 304) {
			spammer_coloredArmorStands = !spammer_coloredArmorStands;
		}

		if (button.id == 2) {
			location.setText(MathUtils.round(mc.thePlayer.posX, 2) + ", " + MathUtils.round(mc.thePlayer.posY, 2) + ", "
					+ MathUtils.round(mc.thePlayer.posZ, 2));
		}
		if (button.id == 1) { // give
			switch (selection) {
			case 1: { // Normal
				if (normal_motion) {
					final ItemStack item = new ItemStack(Items.armor_stand);
					ShitUtils2.holoSpamText = normal_block ? "block:" + normal_text.getText() : normal_text.getText();
					double x;
					double y;
					double z;

					if (location.getText().replace(" ", "").split(",") != null
							&& location.getText().replace(" ", "").split(",").length == 3
							&& MiscUtils.isDouble(location.getText().replace(" ", "").split(",")[0])
							&& MiscUtils.isDouble(location.getText().replace(" ", "").split(",")[1])
							&& MiscUtils.isDouble(location.getText().replace(" ", "").split(",")[2])) {
						x = Double.parseDouble(location.getText().replace(" ", "").split(",")[0]);
						y = Double.parseDouble(location.getText().replace(" ", "").split(",")[1]);
						z = Double.parseDouble(location.getText().replace(" ", "").split(",")[2]);
					} else {
						location.setText(MathUtils.round(mc.thePlayer.posX, 2) + ", "
								+ MathUtils.round(mc.thePlayer.posY, 2) + ", " + MathUtils.round(mc.thePlayer.posZ, 2));
						x = Double.parseDouble(location.getText().replace(" ", "").split(",")[0]);
						y = Double.parseDouble(location.getText().replace(" ", "").split(",")[1]);
						z = Double.parseDouble(location.getText().replace(" ", "").split(",")[2]);
					}
					if (normal_lockPos) {
						ShitUtils2.lockPos = true;
						ShitUtils2.oholoposX = x;
						ShitUtils2.oholoposY = y;
						ShitUtils2.oholoposZ = z;
					} else {
						ShitUtils2.lockPos = false;
					}
					if (!normal_motionMin.getText().isEmpty() && MiscUtils.isDouble(normal_motionMin.getText())
							&& !normal_motionMax.getText().isEmpty()
							&& MiscUtils.isDouble(normal_motionMax.getText())) {
						ShitUtils2.min = Double.parseDouble(normal_motionMin.getText());
						ShitUtils2.max = Double.parseDouble(normal_motionMax.getText());
					} else {
						normal_motionMin.setText("0.0");
						normal_motionMax.setText("5.0");
						ShitUtils2.min = Double.parseDouble(normal_motionMin.getText());
						ShitUtils2.max = Double.parseDouble(normal_motionMax.getText());
					}
					ShitUtils2.nextHoloSpammer();
				} else {
					final ItemStack item = new ItemStack(Items.armor_stand);

					double x = 0;
					double y = 0;
					double z = 0;
					if (normal_lockPos) {
						if (location.getText().replace(" ", "").split(",") != null
								&& location.getText().replace(" ", "").split(",").length == 3
								&& MiscUtils.isDouble(location.getText().replace(" ", "").split(",")[0])
								&& MiscUtils.isDouble(location.getText().replace(" ", "").split(",")[1])
								&& MiscUtils.isDouble(location.getText().replace(" ", "").split(",")[2])) {
							x = Double.parseDouble(location.getText().replace(" ", "").split(",")[0]);
							y = Double.parseDouble(location.getText().replace(" ", "").split(",")[1]);
							z = Double.parseDouble(location.getText().replace(" ", "").split(",")[2]);
						} else {
							location.setText(
									MathUtils.round(mc.thePlayer.posX, 2) + ", " + MathUtils.round(mc.thePlayer.posY, 2)
											+ ", " + MathUtils.round(mc.thePlayer.posZ, 2));
							x = Double.parseDouble(location.getText().replace(" ", "").split(",")[0]);
							y = Double.parseDouble(location.getText().replace(" ", "").split(",")[1]);
							z = Double.parseDouble(location.getText().replace(" ", "").split(",")[2]);
						}
					}
					try {
						if (normal_lockPos) {
							if (normal_block) {
								item.setTagCompound(JsonToNBT.parse("{EntityTag:{Invisible:1,NoGravity:1,Pos:[0:" + x
										+ "d,1:" + y + "d,2:" + z + "d],Equipment:[{id:},{id:},{id:},{id:},{id:\""
										+ normal_text.getText() + "\"}]}}"));
							} else {
								item.setTagCompound(JsonToNBT.parse("{EntityTag:{Pos:[" + x + "," + y + "," + z
										+ "],Invisible:1,NoGravity:1,CustomName:\"" + normal_text.getText()
										+ "\",CustomNameVisible:true}}"));
							}
						} else {
							if (normal_block) {
								item.setTagCompound(JsonToNBT
										.parse("{EntityTag:{Invisible:1,NoGravity:1,Equipment:[{id:},{id:},{id:},{id:},{id:\""
												+ normal_text.getText() + "\"}]}}"));
							} else {
								item.setTagCompound(JsonToNBT.parse("{EntityTag:{Invisible:1,NoGravity:1,CustomName:\""
										+ normal_text.getText() + "\",CustomNameVisible:true}}"));
							}
						}
					} catch (NBTException e) {
						e.printStackTrace();
					}
					item.setStackDisplayName(normal_text.getText());

					mc.thePlayer.sendQueue.addToSendQueue(new C10PacketCreativeInventoryAction(
							Minecraft.thePlayer.inventory.getFirstEmptyStackReal(), item));
				}
				break;
			}
			case 2: { // Image
				if (!image_width.getText().isEmpty() && MiscUtils.isInteger(image_width.getText())
						&& !image_height.getText().isEmpty() && MiscUtils.isInteger(image_height.getText())
						&& !image_text.getText().isEmpty()) {
					double x = 0;
					double y = 0;
					double z = 0;
					if (location.getText().replace(" ", "").split(",") != null
							&& location.getText().replace(" ", "").split(",").length == 3
							&& MiscUtils.isDouble(location.getText().replace(" ", "").split(",")[0])
							&& MiscUtils.isDouble(location.getText().replace(" ", "").split(",")[1])
							&& MiscUtils.isDouble(location.getText().replace(" ", "").split(",")[2])) {
						x = Double.parseDouble(location.getText().replace(" ", "").split(",")[0]);
						y = Double.parseDouble(location.getText().replace(" ", "").split(",")[1]);
						z = Double.parseDouble(location.getText().replace(" ", "").split(",")[2]);
					} else {
						location.setText(MathUtils.round(mc.thePlayer.posX, 2) + ", "
								+ MathUtils.round(mc.thePlayer.posY, 2) + ", " + MathUtils.round(mc.thePlayer.posZ, 2));
						x = Double.parseDouble(location.getText().replace(" ", "").split(",")[0]);
						y = Double.parseDouble(location.getText().replace(" ", "").split(",")[1]);
						z = Double.parseDouble(location.getText().replace(" ", "").split(",")[2]);
					}
					Image.setLoc(x, y, z);
					Image.setup(image_text.getText().replace("\\", "/"), Integer.parseInt(image_width.getText()),
							Integer.parseInt(image_height.getText()));
				}
				break;
			}
			case 3: { // Spammer
				if (spammer_coloredArmorStands) {
					double x = 0;
					double y = 0;
					double z = 0;
					if (location.getText().replace(" ", "").split(",") != null
							&& location.getText().replace(" ", "").split(",").length == 3
							&& MiscUtils.isDouble(location.getText().replace(" ", "").split(",")[0])
							&& MiscUtils.isDouble(location.getText().replace(" ", "").split(",")[1])
							&& MiscUtils.isDouble(location.getText().replace(" ", "").split(",")[2])) {
						x = Double.parseDouble(location.getText().replace(" ", "").split(",")[0]);
						y = Double.parseDouble(location.getText().replace(" ", "").split(",")[1]);
						z = Double.parseDouble(location.getText().replace(" ", "").split(",")[2]);
					} else {
						location.setText(MathUtils.round(mc.thePlayer.posX, 2) + ", "
								+ MathUtils.round(mc.thePlayer.posY, 2) + ", " + MathUtils.round(mc.thePlayer.posZ, 2));
						x = Double.parseDouble(location.getText().replace(" ", "").split(",")[0]);
						y = Double.parseDouble(location.getText().replace(" ", "").split(",")[1]);
						z = Double.parseDouble(location.getText().replace(" ", "").split(",")[2]);
					}
					if (!spammer_motionMin.getText().isEmpty() && MiscUtils.isDouble(spammer_motionMin.getText())
							&& !spammer_motionMax.getText().isEmpty()
							&& MiscUtils.isDouble(spammer_motionMax.getText())) {
						ShitUtils3.min = Double.parseDouble(spammer_motionMin.getText());
						ShitUtils3.max = Double.parseDouble(spammer_motionMax.getText());
					} else {
						spammer_motionMin.setText("0.0");
						spammer_motionMax.setText("5.0");
						ShitUtils3.min = Double.parseDouble(spammer_motionMin.getText());
						ShitUtils3.max = Double.parseDouble(spammer_motionMax.getText());
					}
					ShitUtils3.oholoposX = x;
					ShitUtils3.oholoposY = y;
					ShitUtils3.oholoposZ = z;
					ShitUtils3.nextHoloSpammer();
				} else {
					if (!spammer_text.getText().isEmpty()) {
						ShitUtils.holoSpamText = spammer_block ? "block:" + spammer_text.getText()
								: spammer_text.getText();
					}
					double x = 0;
					double y = 0;
					double z = 0;
					if (location.getText().replace(" ", "").split(",") != null
							&& location.getText().replace(" ", "").split(",").length == 3
							&& MiscUtils.isDouble(location.getText().replace(" ", "").split(",")[0])
							&& MiscUtils.isDouble(location.getText().replace(" ", "").split(",")[1])
							&& MiscUtils.isDouble(location.getText().replace(" ", "").split(",")[2])) {
						x = Double.parseDouble(location.getText().replace(" ", "").split(",")[0]);
						y = Double.parseDouble(location.getText().replace(" ", "").split(",")[1]);
						z = Double.parseDouble(location.getText().replace(" ", "").split(",")[2]);
					} else {
						location.setText(MathUtils.round(mc.thePlayer.posX, 2) + ", "
								+ MathUtils.round(mc.thePlayer.posY, 2) + ", " + MathUtils.round(mc.thePlayer.posZ, 2));
						x = Double.parseDouble(location.getText().replace(" ", "").split(",")[0]);
						y = Double.parseDouble(location.getText().replace(" ", "").split(",")[1]);
						z = Double.parseDouble(location.getText().replace(" ", "").split(",")[2]);
					}
					ShitUtils.oholoposX = x;
					ShitUtils.oholoposY = y;
					ShitUtils.oholoposZ = z;
					if (!spammer_motionMin.getText().isEmpty() && MiscUtils.isDouble(spammer_motionMin.getText())
							&& !spammer_motionMax.getText().isEmpty()
							&& MiscUtils.isDouble(spammer_motionMax.getText())) {
						ShitUtils.min = Double.parseDouble(spammer_motionMin.getText());
						ShitUtils.max = Double.parseDouble(spammer_motionMax.getText());
					} else {
						spammer_motionMin.setText("0.0");
						spammer_motionMax.setText("5.0");
						ShitUtils.min = Double.parseDouble(spammer_motionMin.getText());
						ShitUtils.max = Double.parseDouble(spammer_motionMax.getText());
					}
					ShitUtils.motion = spammer_motion;
					ShitUtils.randomColors = spammer_randomColors;
					ShitUtils.nextHoloSpammer();
				}
				break;
			}
			case 4: { // Crasher
				final ItemStack item = new ItemStack(Items.armor_stand);
				double x = 0;
				double y = 0;
				double z = 0;
				if (location.getText().replace(" ", "").split(",") != null
						&& location.getText().replace(" ", "").split(",").length == 3
						&& MiscUtils.isDouble(location.getText().replace(" ", "").split(",")[0])
						&& MiscUtils.isDouble(location.getText().replace(" ", "").split(",")[1])
						&& MiscUtils.isDouble(location.getText().replace(" ", "").split(",")[2])) {
					x = Double.parseDouble(location.getText().replace(" ", "").split(",")[0]);
					y = Double.parseDouble(location.getText().replace(" ", "").split(",")[1]);
					z = Double.parseDouble(location.getText().replace(" ", "").split(",")[2]);
				} else {
					x = 0;
					y = 0;
					z = 0;
				}
				try {
					item.setTagCompound(JsonToNBT.parse(
							"{EntityTag:{Pos:[" + x + "," + y + "," + z + "],Invisible:1,NoGravity:1,Equipment:[0:"
									+ "{},1:" + "{id:leather_boots,tag:{display:{color:2147483647}}},2:"
									+ "{id:leather_leggings,tag:{display:{color:2147483647}}},3:"
									+ "{id:leather_chestplate,tag:{display:{color:2147483647}}},4:"
									+ "{id:leather_helmet,tag:{display:{color:2147483647}}}]}}"));
				} catch (NBTException e) {
					e.printStackTrace();
				}
				item.setStackDisplayName("§4§lCrasher");

				mc.thePlayer.sendQueue.addToSendQueue(new C10PacketCreativeInventoryAction(
						Minecraft.thePlayer.inventory.getFirstEmptyStackReal(), item));
				break;
			}
			}
			mc.displayGuiScreen(null);
		}

		super.actionPerformed(button);
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		try {
			drawDefaultBackground(mouseX, mouseY);

			mc.fontRendererObj.drawCenteredString("§lHolograms", width / 2 - 2, 10, 0xffffffff);

			RenderUtils.drawHollowRect(width / 6, 40, width / 6 * 5 + 3, 60, 2.0f, 0xffffffff);
			RenderUtils.drawRect(width / 6 * 2, 40, width / 6 * 2 + 1, 60, 0xffffffff);
			RenderUtils.drawRect(width / 6 * 3 + 1, 40, width / 6 * 3 + 2, 60, 0xffffffff);
			RenderUtils.drawRect(width / 6 * 4 + 2, 40, width / 6 * 4 + 3, 60, 0xffffffff);
			RenderUtils.drawRect(width / 6 * 1, 40, width / 6 * 2, 60, selection == 1 ? 0x3000ffff : 0x30ffffff);
			RenderUtils.drawRect(width / 6 * 2 + 1, 40, width / 6 * 3 + 1, 60,
					selection == 2 ? 0x3000ffff : 0x30ffffff);
			RenderUtils.drawRect(width / 6 * 3 + 2, 40, width / 6 * 4 + 2, 60,
					selection == 3 ? 0x3000ffff : 0x30ffffff);
			RenderUtils.drawRect(width / 6 * 4 + 3, 40, width / 6 * 5 + 3, 60,
					selection == 4 ? 0x3000ffff : 0x30ffffff);
			RenderUtils.drawRect(width / 6, 63, width / 6 * 5 + 3, 263, 0x30ffffff);
			RenderUtils.drawRect(width - 80, height - 22, width, height, 0x70000000);
			RenderUtils.drawHollowRect(width - 80, height - 22, width + 1, height + 1, 2.0f, 0x40ffffff);
			RenderUtils.drawRect(0, height - 22, 80, height, 0x70000000);
			RenderUtils.drawHollowRect(-1, height - 22, 80, height + 1, 2.0f, 0x40ffffff);
			if (selection > 0) {
				location.drawTextBox();
			}

			super.drawScreen(mouseX, mouseY, partialTicks);

			if (selection == 1) {
				mc.fontRendererObj.drawString("§3" + (normal_block ? "Block Name/ID" : "Text"), width / 6 + 2, 65,
						0xffffffff);
				normal_text.drawTextBox();
				mc.fontRendererObj.drawString("§3Block", width / 6 + 2, 90, 0xffffffff);
				RenderUtils.drawHollowRect(width / 6 + mc.fontRendererObj.getStringWidth("Motion") + 5, 89,
						width / 6 + mc.fontRendererObj.getStringWidth("Motion") + 14, 98, 1.0f, 0xff00face);
				if (normal_block) {
					mc.fontRendererObj.drawString("\u2714", width / 6 + mc.fontRendererObj.getStringWidth("Motion") + 6,
							89, 0xff00ff00);

					Block block = getBlock(normal_text.getText().toLowerCase());

					if (block != null) {
						ItemStack stack = new ItemStack(block);
						if (stack != null && stack.getItem() != null) {
							GlStateManager.pushMatrix();
							GlStateManager.enableRescaleNormal();
							GlStateManager.disableBlend();
							RenderHelper.enableStandardItemLighting();
							GL11.glTranslated(width / 6 * 4 + 15, 90, 42);
							GL11.glScaled(5, 5, 1);
							mc.getRenderItem().renderItemAndEffectIntoGUI(stack, 0, 0);
							mc.getRenderItem().renderItemOverlayIntoGUI(mc.fontRendererObj, stack, 0, 0);
							RenderHelper.disableStandardItemLighting();
							GlStateManager.disableRescaleNormal();
							GlStateManager.popMatrix();
						} else {
							mc.fontRendererObj.drawString("No Picture available", width / 6 * 4 + 18, 125, 0xffff0000);
						}
					} else {
						mc.fontRendererObj.drawString("No Picture available", width / 6 * 4 + 18, 125, 0xffff0000);
					}

				} else {
					mc.fontRendererObj.drawString("\u2718", width / 6 + mc.fontRendererObj.getStringWidth("Motion") + 7,
							90, 0xffff0000);
				}
				mc.fontRendererObj.drawString("§3Motion", width / 6 + 2, 100, 0xffffffff);
				RenderUtils.drawHollowRect(width / 6 + mc.fontRendererObj.getStringWidth("Motion") + 5, 99,
						width / 6 + mc.fontRendererObj.getStringWidth("Motion") + 14, 108, 1.0f, 0xff00face);
				if (normal_motion) {
					mc.fontRendererObj.drawString("\u2714", width / 6 + mc.fontRendererObj.getStringWidth("Motion") + 6,
							99, 0xff00ff00);
				} else {
					mc.fontRendererObj.drawString("\u2718", width / 6 + mc.fontRendererObj.getStringWidth("Motion") + 7,
							100, 0xffff0000);
				}
				mc.fontRendererObj.drawString("§3Min Motion", width / 6 + 2, 115, 0xffffffff);
				normal_motionMin.drawTextBox();
				mc.fontRendererObj.drawString("§3Max Motion", width / 6 + 2, 135, 0xffffffff);
				normal_motionMax.drawTextBox();
				mc.fontRendererObj.drawString("§3Lock Pos", width / 6 + 2, 160, 0xffffffff);
				RenderUtils.drawHollowRect(width / 6 + mc.fontRendererObj.getStringWidth("Lock Pos") + 5, 159,
						width / 6 + mc.fontRendererObj.getStringWidth("Lock Pos") + 14, 168, 1.0f, 0xff00face);
				if (normal_lockPos) {
					mc.fontRendererObj.drawString("\u2714",
							width / 6 + mc.fontRendererObj.getStringWidth("Lock Pos") + 6, 159, 0xff00ff00);
				} else {
					mc.fontRendererObj.drawString("\u2718",
							width / 6 + mc.fontRendererObj.getStringWidth("Lock Pos") + 7, 160, 0xffff0000);
				}
			}
			if (selection == 2) {
				mc.fontRendererObj.drawString("§3URL/Path", width / 6 + 2, 65, 0xffffffff);
				image_text.drawTextBox();
				mc.fontRendererObj.drawString("§3Width", width / 6 + 2, 95, 0xffffffff);
				image_width.drawTextBox();
				mc.fontRendererObj.drawString("§3Height", width / 6 + 2, 115, 0xffffffff);
				image_height.drawTextBox();
			}
			if (selection == 3) {
				mc.fontRendererObj.drawString("§3" + (normal_block ? "Block Name/ID" : "Text"), width / 6 + 2, 65,
						0xffffffff);
				spammer_text.drawTextBox();
				mc.fontRendererObj.drawString("§3Block", width / 6 + 2, 90, 0xffffffff);
				RenderUtils.drawHollowRect(width / 6 + mc.fontRendererObj.getStringWidth("Motion") + 5, 89,
						width / 6 + mc.fontRendererObj.getStringWidth("Motion") + 14, 98, 1.0f, 0xff00face);
				if (spammer_block) {
					mc.fontRendererObj.drawString("\u2714", width / 6 + mc.fontRendererObj.getStringWidth("Motion") + 6,
							89, 0xff00ff00);

					Block block = getBlock(spammer_text.getText().toLowerCase());

					if (block != null) {
						ItemStack stack = new ItemStack(block);
						if (stack != null && stack.getItem() != null) {
							GlStateManager.pushMatrix();
							GlStateManager.enableRescaleNormal();
							GlStateManager.disableBlend();
							RenderHelper.enableStandardItemLighting();
							GL11.glTranslated(width / 6 * 4 + 15, 90, 42);
							GL11.glScaled(5, 5, 1);
							mc.getRenderItem().renderItemAndEffectIntoGUI(stack, 0, 0);
							mc.getRenderItem().renderItemOverlayIntoGUI(mc.fontRendererObj, stack, 0, 0);
							RenderHelper.disableStandardItemLighting();
							GlStateManager.disableRescaleNormal();
							GlStateManager.popMatrix();
						} else {
							mc.fontRendererObj.drawString("No Picture available", width / 6 * 4 + 18, 125, 0xffff0000);
						}
					} else {
						mc.fontRendererObj.drawString("No Picture available", width / 6 * 4 + 18, 125, 0xffff0000);
					}

				} else {
					mc.fontRendererObj.drawString("\u2718", width / 6 + mc.fontRendererObj.getStringWidth("Motion") + 7,
							90, 0xffff0000);
				}
				mc.fontRendererObj.drawString("§3Motion", width / 6 + 2, 100, 0xffffffff);
				RenderUtils.drawHollowRect(width / 6 + mc.fontRendererObj.getStringWidth("Motion") + 5, 99,
						width / 6 + mc.fontRendererObj.getStringWidth("Motion") + 14, 108, 1.0f, 0xff00face);
				if (spammer_motion) {
					mc.fontRendererObj.drawString("\u2714", width / 6 + mc.fontRendererObj.getStringWidth("Motion") + 6,
							99, 0xff00ff00);
				} else {
					mc.fontRendererObj.drawString("\u2718", width / 6 + mc.fontRendererObj.getStringWidth("Motion") + 7,
							100, 0xffff0000);
				}
				mc.fontRendererObj.drawString("§3Min Motion", width / 6 + 2, 115, 0xffffffff);
				spammer_motionMin.drawTextBox();
				mc.fontRendererObj.drawString("§3Max Motion", width / 6 + 2, 135, 0xffffffff);
				spammer_motionMax.drawTextBox();
				mc.fontRendererObj.drawString("§3Random Colors", width / 6 + 2, 160, 0xffffffff);
				RenderUtils.drawHollowRect(width / 6 + mc.fontRendererObj.getStringWidth("Random Colors") + 5, 159,
						width / 6 + mc.fontRendererObj.getStringWidth("Random Colors") + 14, 168, 1.0f, 0xff00face);
				if (spammer_randomColors) {
					mc.fontRendererObj.drawString("\u2714",
							width / 6 + mc.fontRendererObj.getStringWidth("Random Colors") + 6, 159, 0xff00ff00);
				} else {
					mc.fontRendererObj.drawString("\u2718",
							width / 6 + mc.fontRendererObj.getStringWidth("Random Colors") + 7, 160, 0xffff0000);
				}
				mc.fontRendererObj.drawString("§3Colored Armor", width / 6 + 2, 170, 0xffffffff);
				RenderUtils.drawHollowRect(width / 6 + mc.fontRendererObj.getStringWidth("Colored Armor") + 5, 169,
						width / 6 + mc.fontRendererObj.getStringWidth("Colored Armor") + 14, 178, 1.0f, 0xff00face);
				if (spammer_coloredArmorStands) {
					mc.fontRendererObj.drawString("\u2714",
							width / 6 + mc.fontRendererObj.getStringWidth("Colored Armor") + 6, 169, 0xff00ff00);
				} else {
					mc.fontRendererObj.drawString("\u2718",
							width / 6 + mc.fontRendererObj.getStringWidth("Colored Armor") + 7, 170, 0xffff0000);
				}
			}
			if (selection == 4) {
				GlStateManager.pushMatrix();
				GL11.glTranslated(width / 2, height / 3, 1);
				GL11.glScaled(4, 4, 42);
				mc.fontRendererObj.drawCenteredString("Don't spawn it", 0, 0, 0xffff0000);
				mc.fontRendererObj.drawCenteredString("near you!", 0, 10, 0xffff0000);
				GlStateManager.popMatrix();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateScreen() {
		if (selection > 0) {
			location.updateCursorCounter();
			normal_text.updateCursorCounter();
			normal_motionMin.updateCursorCounter();
			normal_motionMax.updateCursorCounter();
			image_text.updateCursorCounter();
			image_width.updateCursorCounter();
			image_height.updateCursorCounter();
			spammer_text.updateCursorCounter();
			spammer_motionMin.updateCursorCounter();
			spammer_motionMax.updateCursorCounter();
		}
		super.updateScreen();
	}

	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		if (selection > 0) {
			location.textboxKeyTyped(typedChar, keyCode);
			normal_text.textboxKeyTyped(typedChar, keyCode);
			normal_motionMin.textboxKeyTyped(typedChar, keyCode);
			normal_motionMax.textboxKeyTyped(typedChar, keyCode);
			image_text.textboxKeyTyped(typedChar, keyCode);
			image_width.textboxKeyTyped(typedChar, keyCode);
			image_height.textboxKeyTyped(typedChar, keyCode);
			spammer_text.textboxKeyTyped(typedChar, keyCode);
			spammer_motionMin.textboxKeyTyped(typedChar, keyCode);
			spammer_motionMax.textboxKeyTyped(typedChar, keyCode);
		}
		super.keyTyped(typedChar, keyCode);
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		if (selection > 0) {
			location.mouseClicked(mouseX, mouseY, mouseButton);
			normal_text.mouseClicked(mouseX, mouseY, mouseButton);
			normal_motionMin.mouseClicked(mouseX, mouseY, mouseButton);
			normal_motionMax.mouseClicked(mouseX, mouseY, mouseButton);
			image_text.mouseClicked(mouseX, mouseY, mouseButton);
			image_width.mouseClicked(mouseX, mouseY, mouseButton);
			image_height.mouseClicked(mouseX, mouseY, mouseButton);
			spammer_text.mouseClicked(mouseX, mouseY, mouseButton);
			spammer_motionMin.mouseClicked(mouseX, mouseY, mouseButton);
			spammer_motionMax.mouseClicked(mouseX, mouseY, mouseButton);
		}
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}

	public Block getBlock(String nameorid) {
		try {
			if (MiscUtils.isInteger(nameorid)) {
				return Block.getBlockById(Integer.parseInt(nameorid));
			} else {
				return Block.getBlockFromName(nameorid);
			}
		} catch (Exception e) {
			return null;
		}
	}

	public void setState(int btnID, int stateID) {
		((GuiButton) buttonList.get(btnID)).enabled = selection == stateID;
		((GuiButton) buttonList.get(btnID)).visible = selection == stateID;
	}

	public void setState(GuiEmailFieldLogin field, int stateID) {
		field.isEnabled = selection == stateID;
		field.visible = selection == stateID;
	}

	public void updateStates() {
		setState(6, 1);
		setState(7, 1);
		setState(8, 1);
		setState(normal_text, 1);
		setState(normal_motionMin, 1);
		setState(normal_motionMax, 1);
		setState(image_text, 2);
		setState(image_width, 2);
		setState(image_height, 2);
		setState(9, 3);
		setState(10, 3);
		setState(spammer_text, 3);
		setState(spammer_motionMin, 3);
		setState(spammer_motionMax, 3);
		setState(11, 3);
		setState(12, 3);
	}

}
