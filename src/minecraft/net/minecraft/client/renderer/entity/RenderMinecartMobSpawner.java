package net.minecraft.client.renderer.entity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.tileentity.TileEntityMobSpawnerRenderer;
import net.minecraft.entity.ai.EntityMinecartMobSpawner;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.init.Blocks;

public class RenderMinecartMobSpawner extends RenderMinecart {

public static final int EaZy = 769;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00001014";

	public RenderMinecartMobSpawner(final RenderManager p_i46154_1_) {
		super(p_i46154_1_);
	}

	protected void func_177081_a(final EntityMinecartMobSpawner p_177081_1_, final float p_177081_2_,
			final IBlockState p_177081_3_) {
		super.func_180560_a(p_177081_1_, p_177081_2_, p_177081_3_);

		if (p_177081_3_.getBlock() == Blocks.mob_spawner) {
			TileEntityMobSpawnerRenderer.func_147517_a(p_177081_1_.func_98039_d(), p_177081_1_.posX, p_177081_1_.posY,
					p_177081_1_.posZ, p_177081_2_);
		}
	}

	@Override
	protected void func_180560_a(final EntityMinecart p_180560_1_, final float p_180560_2_,
			final IBlockState p_180560_3_) {
		func_177081_a((EntityMinecartMobSpawner) p_180560_1_, p_180560_2_, p_180560_3_);
	}
}
