package net.minecraft.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.world.World;

public class EntityFireworkStarterFX_Factory implements IParticleFactory {

public static final int EaZy = 649;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00002603";

	@Override
	public EntityFX func_178902_a(final int p_178902_1_, final World worldIn, final double p_178902_3_,
			final double p_178902_5_, final double p_178902_7_, final double p_178902_9_, final double p_178902_11_,
			final double p_178902_13_, final int... p_178902_15_) {

		final EntityFireworkSparkFX var16 = new EntityFireworkSparkFX(worldIn, p_178902_3_, p_178902_5_, p_178902_7_,
				p_178902_9_, p_178902_11_, p_178902_13_, Minecraft.effectRenderer);
		var16.setAlphaF(0.99F);
		return var16;
	}
}
