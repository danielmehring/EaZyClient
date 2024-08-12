package net.minecraft.client.gui;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
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
import net.minecraft.world.gen.FlatGeneratorInfo;
import net.minecraft.world.gen.FlatLayerInfo;

import java.io.IOException;

public class GuiCreateFlatWorld extends GuiScreen {

public static final int EaZy = 468;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final GuiCreateWorld createWorldGui;
	private FlatGeneratorInfo theFlatGeneratorInfo = FlatGeneratorInfo.getDefaultFlatGenerator();
	private String field_146393_h;
	private String field_146394_i;
	private String field_146391_r;
	private GuiCreateFlatWorld.Details createFlatWorldListSlotGui;
	private GuiButton field_146389_t;
	private GuiButton field_146388_u;
	private GuiButton field_146386_v;
	// private static final String __OBFID = "http://https://fuckuskid00000687";

	public GuiCreateFlatWorld(final GuiCreateWorld p_i1029_1_, final String p_i1029_2_) {
		createWorldGui = p_i1029_1_;
		func_146383_a(p_i1029_2_);
	}

	public String func_146384_e() {
		return theFlatGeneratorInfo.toString();
	}

	public void func_146383_a(final String p_146383_1_) {
		theFlatGeneratorInfo = FlatGeneratorInfo.createFlatGeneratorFromString(p_146383_1_);
	}

	/**
	 * Adds the buttons (and other controls) to the screen in question.
	 */
	@Override
	public void initGui() {
		buttonList.clear();
		field_146393_h = I18n.format("createWorld.customize.flat.title", new Object[0]);
		field_146394_i = I18n.format("createWorld.customize.flat.tile", new Object[0]);
		field_146391_r = I18n.format("createWorld.customize.flat.height", new Object[0]);
		createFlatWorldListSlotGui = new GuiCreateFlatWorld.Details();
		buttonList.add(field_146389_t = new GuiButton(2, width / 2 - 154, height - 52, 100, 20,
				I18n.format("createWorld.customize.flat.addLayer", new Object[0]) + " (NYI)"));
		buttonList.add(field_146388_u = new GuiButton(3, width / 2 - 50, height - 52, 100, 20,
				I18n.format("createWorld.customize.flat.editLayer", new Object[0]) + " (NYI)"));
		buttonList.add(field_146386_v = new GuiButton(4, width / 2 - 155, height - 52, 150, 20,
				I18n.format("createWorld.customize.flat.removeLayer", new Object[0])));
		buttonList.add(new GuiButton(0, width / 2 - 155, height - 28, 150, 20, I18n.format("gui.done", new Object[0])));
		buttonList.add(new GuiButton(5, width / 2 + 5, height - 52, 150, 20,
				I18n.format("createWorld.customize.presets", new Object[0])));
		buttonList.add(new GuiButton(1, width / 2 + 5, height - 28, 150, 20, I18n.format("gui.cancel", new Object[0])));
		field_146389_t.visible = field_146388_u.visible = false;
		theFlatGeneratorInfo.func_82645_d();
		func_146375_g();
	}

	/**
	 * Handles mouse input.
	 */
	@Override
	public void handleMouseInput() throws IOException {
		super.handleMouseInput();
		createFlatWorldListSlotGui.func_178039_p();
	}

	@Override
	protected void actionPerformed(final GuiButton button) throws IOException {
		final int var2 = theFlatGeneratorInfo.getFlatLayers().size() - createFlatWorldListSlotGui.field_148228_k - 1;

		if (button.id == 1) {
			mc.displayGuiScreen(createWorldGui);
		} else if (button.id == 0) {
			createWorldGui.field_146334_a = func_146384_e();
			mc.displayGuiScreen(createWorldGui);
		} else if (button.id == 5) {
			mc.displayGuiScreen(new GuiFlatPresets(this));
		} else if (button.id == 4 && func_146382_i()) {
			theFlatGeneratorInfo.getFlatLayers().remove(var2);
			createFlatWorldListSlotGui.field_148228_k = Math.min(createFlatWorldListSlotGui.field_148228_k,
					theFlatGeneratorInfo.getFlatLayers().size() - 1);
		}

		theFlatGeneratorInfo.func_82645_d();
		func_146375_g();
	}

	public void func_146375_g() {
		final boolean var1 = func_146382_i();
		field_146386_v.enabled = var1;
		field_146388_u.enabled = var1;
		field_146388_u.enabled = false;
		field_146389_t.enabled = false;
	}

	private boolean func_146382_i() {
		return createFlatWorldListSlotGui.field_148228_k > -1
				&& createFlatWorldListSlotGui.field_148228_k < theFlatGeneratorInfo.getFlatLayers().size();
	}

	/**
	 * Draws the screen and all the components in it. Args : mouseX, mouseY,
	 * renderPartialTicks
	 */
	@Override
	public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
		drawDefaultBackground(mouseX, mouseY);
		createFlatWorldListSlotGui.drawScreen(mouseX, mouseY, partialTicks);
		drawCenteredString(fontRendererObj, field_146393_h, width / 2, 8, 16777215);
		final int var4 = width / 2 - 92 - 16;
		drawString(fontRendererObj, field_146394_i, var4, 32, 16777215);
		drawString(fontRendererObj, field_146391_r, var4 + 2 + 213 - fontRendererObj.getStringWidth(field_146391_r), 32,
				16777215);
		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	class Details extends GuiSlot {
		public int field_148228_k = -1;
		// private static final String __OBFID =
		// "http://https://fuckuskid00000688";

		public Details() {
			super(GuiCreateFlatWorld.this.mc, GuiCreateFlatWorld.this.width, GuiCreateFlatWorld.this.height, 43,
					GuiCreateFlatWorld.this.height - 60, 24);
		}

		private void func_148225_a(final int p_148225_1_, final int p_148225_2_, final ItemStack p_148225_3_) {
			func_148226_e(p_148225_1_ + 1, p_148225_2_ + 1);
			GlStateManager.enableRescaleNormal();

			if (p_148225_3_ != null && p_148225_3_.getItem() != null) {
				RenderHelper.enableGUIStandardItemLighting();
				GuiCreateFlatWorld.this.itemRender.func_175042_a(p_148225_3_, p_148225_1_ + 2, p_148225_2_ + 2);
				RenderHelper.disableStandardItemLighting();
			}

			GlStateManager.disableRescaleNormal();
		}

		private void func_148226_e(final int p_148226_1_, final int p_148226_2_) {
			func_148224_c(p_148226_1_, p_148226_2_, 0, 0);
		}

		private void func_148224_c(final int p_148224_1_, final int p_148224_2_, final int p_148224_3_,
				final int p_148224_4_) {
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			Minecraft.getTextureManager().bindTexture(Gui.statIcons);
			final Tessellator var9 = Tessellator.getInstance();
			final WorldRenderer var10 = var9.getWorldRenderer();
			var10.startDrawingQuads();
			var10.addVertexWithUV(p_148224_1_ + 0, p_148224_2_ + 18, GuiCreateFlatWorld.this.zLevel,
					(p_148224_3_ + 0) * 0.0078125F, (p_148224_4_ + 18) * 0.0078125F);
			var10.addVertexWithUV(p_148224_1_ + 18, p_148224_2_ + 18, GuiCreateFlatWorld.this.zLevel,
					(p_148224_3_ + 18) * 0.0078125F, (p_148224_4_ + 18) * 0.0078125F);
			var10.addVertexWithUV(p_148224_1_ + 18, p_148224_2_ + 0, GuiCreateFlatWorld.this.zLevel,
					(p_148224_3_ + 18) * 0.0078125F, (p_148224_4_ + 0) * 0.0078125F);
			var10.addVertexWithUV(p_148224_1_ + 0, p_148224_2_ + 0, GuiCreateFlatWorld.this.zLevel,
					(p_148224_3_ + 0) * 0.0078125F, (p_148224_4_ + 0) * 0.0078125F);
			var9.draw();
		}

		@Override
		protected int getSize() {
			return theFlatGeneratorInfo.getFlatLayers().size();
		}

		@Override
		protected void elementClicked(final int slotIndex, final boolean isDoubleClick, final int mouseX,
				final int mouseY) {
			field_148228_k = slotIndex;
			func_146375_g();
		}

		@Override
		protected boolean isSelected(final int slotIndex) {
			return slotIndex == field_148228_k;
		}

		@Override
		protected void drawBackground() {}

		@Override
		protected void drawSlot(final int p_180791_1_, final int p_180791_2_, final int p_180791_3_,
				final int p_180791_4_, final int p_180791_5_, final int p_180791_6_) {
			final FlatLayerInfo var7 = (FlatLayerInfo) theFlatGeneratorInfo.getFlatLayers()
					.get(theFlatGeneratorInfo.getFlatLayers().size() - p_180791_1_ - 1);
			final IBlockState var8 = var7.func_175900_c();
			final Block var9 = var8.getBlock();
			Item var10 = Item.getItemFromBlock(var9);
			ItemStack var11 = var9 != Blocks.air && var10 != null ? new ItemStack(var10, 1, var9.getMetaFromState(var8))
					: null;
			String var12 = var11 == null ? "Air" : var10.getItemStackDisplayName(var11);

			if (var10 == null) {
				if (var9 != Blocks.water && var9 != Blocks.flowing_water) {
					if (var9 == Blocks.lava || var9 == Blocks.flowing_lava) {
						var10 = Items.lava_bucket;
					}
				} else {
					var10 = Items.water_bucket;
				}

				if (var10 != null) {
					var11 = new ItemStack(var10, 1, var9.getMetaFromState(var8));
					var12 = var9.getLocalizedName();
				}
			}

			func_148225_a(p_180791_2_, p_180791_3_, var11);
			GuiCreateFlatWorld.this.fontRendererObj.drawString(var12, p_180791_2_ + 18 + 5, p_180791_3_ + 3, 16777215);
			String var13;

			if (p_180791_1_ == 0) {
				var13 = I18n.format("createWorld.customize.flat.layer.top",
						new Object[] { var7.getLayerCount() });
			} else if (p_180791_1_ == theFlatGeneratorInfo.getFlatLayers().size() - 1) {
				var13 = I18n.format("createWorld.customize.flat.layer.bottom",
						new Object[] { var7.getLayerCount() });
			} else {
				var13 = I18n.format("createWorld.customize.flat.layer",
						new Object[] { var7.getLayerCount() });
			}

			GuiCreateFlatWorld.this.fontRendererObj.drawString(var13,
					p_180791_2_ + 2 + 213 - GuiCreateFlatWorld.this.fontRendererObj.getStringWidth(var13),
					p_180791_3_ + 3, 16777215);
		}

		@Override
		protected int getScrollBarX() {
			return width - 70;
		}
	}
}
