package net.minecraft.client.renderer.tileentity;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntityBanner;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.EnumFacing;

import java.util.UUID;

import com.mojang.authlib.GameProfile;

public class TileEntityRendererChestHelper {

public static final int EaZy = 840;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	public static TileEntityRendererChestHelper instance = new TileEntityRendererChestHelper();
	private final TileEntityChest field_147717_b = new TileEntityChest(0);
	private final TileEntityChest field_147718_c = new TileEntityChest(1);
	private final TileEntityEnderChest field_147716_d = new TileEntityEnderChest();
	private final TileEntityBanner banner = new TileEntityBanner();
	private final TileEntitySkull field_179023_f = new TileEntitySkull();
	// private static final String __OBFID = "http://https://fuckuskid00000946";

	public void renderByItem(final ItemStack p_179022_1_) {
		if (p_179022_1_.getItem() == Items.banner) {
			banner.setItemValues(p_179022_1_);
			TileEntityRendererDispatcher.instance.renderTileEntityAt(banner, 0.0D, 0.0D, 0.0D, 0.0F);
		} else if (p_179022_1_.getItem() == Items.skull) {
			GameProfile var2 = null;

			if (p_179022_1_.hasTagCompound()) {
				final NBTTagCompound var3 = p_179022_1_.getTagCompound();

				if (var3.hasKey("SkullOwner", 10)) {
					var2 = NBTUtil.readGameProfileFromNBT(var3.getCompoundTag("SkullOwner"));
				} else if (var3.hasKey("SkullOwner", 8) && var3.getString("SkullOwner").length() > 0) {
					var2 = new GameProfile((UUID) null, var3.getString("SkullOwner"));
					var2 = TileEntitySkull.updateGameprofile(var2);
					var3.removeTag("SkullOwner");
					var3.setTag("SkullOwner", NBTUtil.writeGameProfile(new NBTTagCompound(), var2));
				}
			}

			if (TileEntitySkullRenderer.instance != null) {
				GlStateManager.pushMatrix();
				GlStateManager.translate(-0.5F, 0.0F, -0.5F);
				GlStateManager.scale(2.0F, 2.0F, 2.0F);
				GlStateManager.disableCull();
				TileEntitySkullRenderer.instance.renderSkull(0.0F, 0.0F, 0.0F, EnumFacing.UP, 0.0F,
						p_179022_1_.getMetadata(), var2, -1);
				GlStateManager.enableCull();
				GlStateManager.popMatrix();
			}
		} else {
			final Block var4 = Block.getBlockFromItem(p_179022_1_.getItem());

			if (var4 == Blocks.ender_chest) {
				TileEntityRendererDispatcher.instance.renderTileEntityAt(field_147716_d, 0.0D, 0.0D, 0.0D, 0.0F);
			} else if (var4 == Blocks.trapped_chest) {
				TileEntityRendererDispatcher.instance.renderTileEntityAt(field_147718_c, 0.0D, 0.0D, 0.0D, 0.0F);
			} else {
				TileEntityRendererDispatcher.instance.renderTileEntityAt(field_147717_b, 0.0D, 0.0D, 0.0D, 0.0F);
			}
		}
	}
}
