package net.minecraft.client.gui;

import net.minecraft.block.BlockTallGrass;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.FlatGeneratorInfo;
import net.minecraft.world.gen.FlatLayerInfo;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.lwjgl.input.Keyboard;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class GuiFlatPresets extends GuiScreen {

public static final int EaZy = 476;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final List field_146431_f = Lists.newArrayList();
	private final GuiCreateFlatWorld field_146432_g;
	private String field_146438_h;
	private String field_146439_i;
	private String field_146436_r;
	private GuiFlatPresets.ListSlot field_146435_s;
	private GuiButton field_146434_t;
	private GuiTextField field_146433_u;
	// private static final String __OBFID = "http://https://fuckuskid00000704";

	public GuiFlatPresets(final GuiCreateFlatWorld p_i46318_1_) {
		field_146432_g = p_i46318_1_;
	}

	/**
	 * Adds the buttons (and other controls) to the screen in question.
	 */
	@Override
	public void initGui() {
		buttonList.clear();
		Keyboard.enableRepeatEvents(true);
		field_146438_h = I18n.format("createWorld.customize.presets.title", new Object[0]);
		field_146439_i = I18n.format("createWorld.customize.presets.share", new Object[0]);
		field_146436_r = I18n.format("createWorld.customize.presets.list", new Object[0]);
		field_146433_u = new GuiTextField(2, fontRendererObj, 50, 40, width - 100, 20);
		field_146435_s = new GuiFlatPresets.ListSlot();
		field_146433_u.setMaxStringLength(1230);
		field_146433_u.setText(field_146432_g.func_146384_e());
		buttonList.add(field_146434_t = new GuiButton(0, width / 2 - 155, height - 28, 150, 20,
				I18n.format("createWorld.customize.presets.select", new Object[0])));
		buttonList.add(new GuiButton(1, width / 2 + 5, height - 28, 150, 20, I18n.format("gui.cancel", new Object[0])));
		func_146426_g();
	}

	/**
	 * Handles mouse input.
	 */
	@Override
	public void handleMouseInput() throws IOException {
		super.handleMouseInput();
		field_146435_s.func_178039_p();
	}

	/**
	 * Called when the screen is unloaded. Used to disable keyboard repeat
	 * events
	 */
	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}

	/**
	 * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
	 */
	@Override
	protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
		field_146433_u.mouseClicked(mouseX, mouseY, mouseButton);
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}

	/**
	 * Fired when a key is typed (except F11 who toggle full screen). This is
	 * the equivalent of KeyListener.keyTyped(KeyEvent e). Args : character
	 * (character on the key), keyCode (lwjgl Keyboard key code)
	 */
	@Override
	protected void keyTyped(final char typedChar, final int keyCode) throws IOException {
		if (!field_146433_u.textboxKeyTyped(typedChar, keyCode)) {
			super.keyTyped(typedChar, keyCode);
		}
	}

	@Override
	protected void actionPerformed(final GuiButton button) throws IOException {
		if (button.id == 0 && func_146430_p()) {
			field_146432_g.func_146383_a(field_146433_u.getText());
			mc.displayGuiScreen(field_146432_g);
		} else if (button.id == 1) {
			mc.displayGuiScreen(field_146432_g);
		}
	}

	/**
	 * Draws the screen and all the components in it. Args : mouseX, mouseY,
	 * renderPartialTicks
	 */
	@Override
	public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
		drawDefaultBackground(mouseX, mouseY);
		field_146435_s.drawScreen(mouseX, mouseY, partialTicks);
		drawCenteredString(fontRendererObj, field_146438_h, width / 2, 8, 16777215);
		drawString(fontRendererObj, field_146439_i, 50, 30, 10526880);
		drawString(fontRendererObj, field_146436_r, 50, 70, 10526880);
		field_146433_u.drawTextBox();
		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	/**
	 * Called from the main game loop to update the screen.
	 */
	@Override
	public void updateScreen() {
		field_146433_u.updateCursorCounter();
		super.updateScreen();
	}

	public void func_146426_g() {
		final boolean var1 = func_146430_p();
		field_146434_t.enabled = var1;
	}

	private boolean func_146430_p() {
		return field_146435_s.field_148175_k > -1 && field_146435_s.field_148175_k < field_146431_f.size()
				|| field_146433_u.getText().length() > 1;
	}

	private static void func_146425_a(final String p_146425_0_, final Item p_146425_1_, final BiomeGenBase p_146425_2_,
			final FlatLayerInfo... p_146425_3_) {
		func_175354_a(p_146425_0_, p_146425_1_, 0, p_146425_2_, (List) null, p_146425_3_);
	}

	private static void func_146421_a(final String p_146421_0_, final Item p_146421_1_, final BiomeGenBase p_146421_2_,
			final List p_146421_3_, final FlatLayerInfo... p_146421_4_) {
		func_175354_a(p_146421_0_, p_146421_1_, 0, p_146421_2_, p_146421_3_, p_146421_4_);
	}

	private static void func_175354_a(final String p_175354_0_, final Item p_175354_1_, final int p_175354_2_,
			final BiomeGenBase p_175354_3_, final List p_175354_4_, final FlatLayerInfo... p_175354_5_) {
		final FlatGeneratorInfo var6 = new FlatGeneratorInfo();

		for (int var7 = p_175354_5_.length - 1; var7 >= 0; --var7) {
			var6.getFlatLayers().add(p_175354_5_[var7]);
		}

		var6.setBiome(p_175354_3_.biomeID);
		var6.func_82645_d();

		if (p_175354_4_ != null) {
			final Iterator var9 = p_175354_4_.iterator();

			while (var9.hasNext()) {
				final String var8 = (String) var9.next();
				var6.getWorldFeatures().put(var8, Maps.newHashMap());
			}
		}

		field_146431_f.add(new GuiFlatPresets.LayerItem(p_175354_1_, p_175354_2_, p_175354_0_, var6.toString()));
	}

	static {
		func_146421_a("Classic Flat", Item.getItemFromBlock(Blocks.grass), BiomeGenBase.plains,
				Arrays.asList(new String[] { "village" }), new FlatLayerInfo[] { new FlatLayerInfo(1, Blocks.grass),
						new FlatLayerInfo(2, Blocks.dirt), new FlatLayerInfo(1, Blocks.bedrock) });
		func_146421_a("Tunnelers\' Dream", Item.getItemFromBlock(Blocks.stone), BiomeGenBase.extremeHills,
				Arrays.asList(new String[] { "biome_1", "dungeon", "decoration", "stronghold", "mineshaft" }),
				new FlatLayerInfo[] { new FlatLayerInfo(1, Blocks.grass), new FlatLayerInfo(5, Blocks.dirt),
						new FlatLayerInfo(230, Blocks.stone), new FlatLayerInfo(1, Blocks.bedrock) });
		func_146421_a("Water World", Items.water_bucket, BiomeGenBase.deepOcean,
				Arrays.asList(new String[] { "biome_1", "oceanmonument" }),
				new FlatLayerInfo[] { new FlatLayerInfo(90, Blocks.water), new FlatLayerInfo(5, Blocks.sand),
						new FlatLayerInfo(5, Blocks.dirt), new FlatLayerInfo(5, Blocks.stone),
						new FlatLayerInfo(1, Blocks.bedrock) });
		func_175354_a("Overworld", Item.getItemFromBlock(Blocks.tallgrass),
				BlockTallGrass.EnumType.GRASS.func_177044_a(), BiomeGenBase.plains,
				Arrays.asList(new String[] { "village", "biome_1", "decoration", "stronghold", "mineshaft", "dungeon",
						"lake", "lava_lake" }),
				new FlatLayerInfo[] { new FlatLayerInfo(1, Blocks.grass), new FlatLayerInfo(3, Blocks.dirt),
						new FlatLayerInfo(59, Blocks.stone), new FlatLayerInfo(1, Blocks.bedrock) });
		func_146421_a("Snowy Kingdom", Item.getItemFromBlock(Blocks.snow_layer), BiomeGenBase.icePlains,
				Arrays.asList(new String[] { "village", "biome_1" }),
				new FlatLayerInfo[] { new FlatLayerInfo(1, Blocks.snow_layer), new FlatLayerInfo(1, Blocks.grass),
						new FlatLayerInfo(3, Blocks.dirt), new FlatLayerInfo(59, Blocks.stone),
						new FlatLayerInfo(1, Blocks.bedrock) });
		func_146421_a("Bottomless Pit", Items.feather, BiomeGenBase.plains,
				Arrays.asList(new String[] { "village", "biome_1" }),
				new FlatLayerInfo[] { new FlatLayerInfo(1, Blocks.grass), new FlatLayerInfo(3, Blocks.dirt),
						new FlatLayerInfo(2, Blocks.cobblestone) });
		func_146421_a("Desert", Item.getItemFromBlock(Blocks.sand), BiomeGenBase.desert,
				Arrays.asList(
						new String[] { "village", "biome_1", "decoration", "stronghold", "mineshaft", "dungeon" }),
				new FlatLayerInfo[] { new FlatLayerInfo(8, Blocks.sand), new FlatLayerInfo(52, Blocks.sandstone),
						new FlatLayerInfo(3, Blocks.stone), new FlatLayerInfo(1, Blocks.bedrock) });
		func_146425_a("Redstone Ready", Items.redstone, BiomeGenBase.desert,
				new FlatLayerInfo[] { new FlatLayerInfo(52, Blocks.sandstone), new FlatLayerInfo(3, Blocks.stone),
						new FlatLayerInfo(1, Blocks.bedrock) });
	}

	static class LayerItem {
		public Item field_148234_a;
		public int field_179037_b;
		public String field_148232_b;
		public String field_148233_c;
		// private static final String __OBFID =
		// "http://https://fuckuskid00000705";

		public LayerItem(final Item p_i45518_1_, final int p_i45518_2_, final String p_i45518_3_,
				final String p_i45518_4_) {
			field_148234_a = p_i45518_1_;
			field_179037_b = p_i45518_2_;
			field_148232_b = p_i45518_3_;
			field_148233_c = p_i45518_4_;
		}
	}

	class ListSlot extends GuiSlot {
		public int field_148175_k = -1;
		// private static final String __OBFID =
		// "http://https://fuckuskid00000706";

		public ListSlot() {
			super(GuiFlatPresets.this.mc, GuiFlatPresets.this.width, GuiFlatPresets.this.height, 80,
					GuiFlatPresets.this.height - 37, 24);
		}

		private void func_178054_a(final int p_178054_1_, final int p_178054_2_, final Item p_178054_3_,
				final int p_178054_4_) {
			func_148173_e(p_178054_1_ + 1, p_178054_2_ + 1);
			GlStateManager.enableRescaleNormal();
			RenderHelper.enableGUIStandardItemLighting();
			GuiFlatPresets.this.itemRender.func_175042_a(new ItemStack(p_178054_3_, 1, p_178054_4_), p_178054_1_ + 2,
					p_178054_2_ + 2);
			RenderHelper.disableStandardItemLighting();
			GlStateManager.disableRescaleNormal();
		}

		private void func_148173_e(final int p_148173_1_, final int p_148173_2_) {
			func_148171_c(p_148173_1_, p_148173_2_, 0, 0);
		}

		private void func_148171_c(final int p_148171_1_, final int p_148171_2_, final int p_148171_3_,
				final int p_148171_4_) {
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			Minecraft.getTextureManager().bindTexture(Gui.statIcons);
			final Tessellator var9 = Tessellator.getInstance();
			final WorldRenderer var10 = var9.getWorldRenderer();
			var10.startDrawingQuads();
			var10.addVertexWithUV(p_148171_1_ + 0, p_148171_2_ + 18, GuiFlatPresets.this.zLevel,
					(p_148171_3_ + 0) * 0.0078125F, (p_148171_4_ + 18) * 0.0078125F);
			var10.addVertexWithUV(p_148171_1_ + 18, p_148171_2_ + 18, GuiFlatPresets.this.zLevel,
					(p_148171_3_ + 18) * 0.0078125F, (p_148171_4_ + 18) * 0.0078125F);
			var10.addVertexWithUV(p_148171_1_ + 18, p_148171_2_ + 0, GuiFlatPresets.this.zLevel,
					(p_148171_3_ + 18) * 0.0078125F, (p_148171_4_ + 0) * 0.0078125F);
			var10.addVertexWithUV(p_148171_1_ + 0, p_148171_2_ + 0, GuiFlatPresets.this.zLevel,
					(p_148171_3_ + 0) * 0.0078125F, (p_148171_4_ + 0) * 0.0078125F);
			var9.draw();
		}

		@Override
		protected int getSize() {
			return GuiFlatPresets.field_146431_f.size();
		}

		@Override
		protected void elementClicked(final int slotIndex, final boolean isDoubleClick, final int mouseX,
				final int mouseY) {
			field_148175_k = slotIndex;
			func_146426_g();
			field_146433_u.setText(((GuiFlatPresets.LayerItem) GuiFlatPresets.field_146431_f
					.get(field_146435_s.field_148175_k)).field_148233_c);
		}

		@Override
		protected boolean isSelected(final int slotIndex) {
			return slotIndex == field_148175_k;
		}

		@Override
		protected void drawBackground() {}

		@Override
		protected void drawSlot(final int p_180791_1_, final int p_180791_2_, final int p_180791_3_,
				final int p_180791_4_, final int p_180791_5_, final int p_180791_6_) {
			final GuiFlatPresets.LayerItem var7 = (GuiFlatPresets.LayerItem) GuiFlatPresets.field_146431_f
					.get(p_180791_1_);
			func_178054_a(p_180791_2_, p_180791_3_, var7.field_148234_a, var7.field_179037_b);
			GuiFlatPresets.this.fontRendererObj.drawString(var7.field_148232_b, p_180791_2_ + 18 + 5, p_180791_3_ + 6,
					16777215);
		}
	}
}
