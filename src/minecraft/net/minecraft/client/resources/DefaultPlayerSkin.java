package net.minecraft.client.resources;

import net.minecraft.util.ResourceLocation;

import java.util.UUID;

public class DefaultPlayerSkin {

public static final int EaZy = 870;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final ResourceLocation field_177337_a = new ResourceLocation("textures/entity/steve.png");
	private static final ResourceLocation field_177336_b = new ResourceLocation("textures/entity/alex.png");
	// private static final String __OBFID = "http://https://fuckuskid00002396";

	public static ResourceLocation func_177335_a() {
		return field_177337_a;
	}

	public static ResourceLocation func_177334_a(final UUID p_177334_0_) {
		return func_177333_c(p_177334_0_) ? field_177336_b : field_177337_a;
	}

	public static String getModelNameFromUUID(final UUID p_177332_0_) {
		return func_177333_c(p_177332_0_) ? "slim" : "default";
	}

	private static boolean func_177333_c(final UUID p_177333_0_) {
		return (p_177333_0_.hashCode() & 1) == 1;
	}
}
