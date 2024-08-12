package net.minecraft.client.particle;

import net.minecraft.world.World;

public class EntityCritFX extends EntitySmokeFX {

public static final int EaZy = 641;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00000900";

	protected EntityCritFX(final World worldIn, final double p_i1201_2_, final double p_i1201_4_,
			final double p_i1201_6_, final double p_i1201_8_, final double p_i1201_10_, final double p_i1201_12_) {
		super(worldIn, p_i1201_2_, p_i1201_4_, p_i1201_6_, p_i1201_8_, p_i1201_10_, p_i1201_12_, 2.5F);
	}

	public static class Factory implements IParticleFactory {
		// private static final String __OBFID =
		// "http://https://fuckuskid00002596";

		@Override
		public EntityFX func_178902_a(final int p_178902_1_, final World worldIn, final double p_178902_3_,
				final double p_178902_5_, final double p_178902_7_, final double p_178902_9_, final double p_178902_11_,
				final double p_178902_13_, final int... p_178902_15_) {
			return new EntityCritFX(worldIn, p_178902_3_, p_178902_5_, p_178902_7_, p_178902_9_, p_178902_11_,
					p_178902_13_);
		}
	}
}
