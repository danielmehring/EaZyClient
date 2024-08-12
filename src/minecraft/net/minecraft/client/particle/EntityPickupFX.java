package net.minecraft.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class EntityPickupFX extends EntityFX {

public static final int EaZy = 660;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final Entity field_174840_a;
	private final Entity field_174843_ax;
	private int age;
	private final int maxAge;
	private final float field_174841_aA;
	private final RenderManager field_174842_aB = Minecraft.getRenderManager();
	// private static final String __OBFID = "http://https://fuckuskid00000930";

	public EntityPickupFX(final World worldIn, final Entity p_i1233_2_, final Entity p_i1233_3_,
			final float p_i1233_4_) {
		super(worldIn, p_i1233_2_.posX, p_i1233_2_.posY, p_i1233_2_.posZ, p_i1233_2_.motionX, p_i1233_2_.motionY,
				p_i1233_2_.motionZ);
		field_174840_a = p_i1233_2_;
		field_174843_ax = p_i1233_3_;
		maxAge = 3;
		field_174841_aA = p_i1233_4_;
	}

	@Override
	public void func_180434_a(final WorldRenderer p_180434_1_, final Entity p_180434_2_, final float p_180434_3_,
			final float p_180434_4_, final float p_180434_5_, final float p_180434_6_, final float p_180434_7_,
			final float p_180434_8_) {
		float var9 = (age + p_180434_3_) / maxAge;
		var9 *= var9;
		final double var10 = field_174840_a.posX;
		final double var12 = field_174840_a.posY;
		final double var14 = field_174840_a.posZ;
		final double var16 = field_174843_ax.lastTickPosX
				+ (field_174843_ax.posX - field_174843_ax.lastTickPosX) * p_180434_3_;
		final double var18 = field_174843_ax.lastTickPosY
				+ (field_174843_ax.posY - field_174843_ax.lastTickPosY) * p_180434_3_ + field_174841_aA;
		final double var20 = field_174843_ax.lastTickPosZ
				+ (field_174843_ax.posZ - field_174843_ax.lastTickPosZ) * p_180434_3_;
		double var22 = var10 + (var16 - var10) * var9;
		double var24 = var12 + (var18 - var12) * var9;
		double var26 = var14 + (var20 - var14) * var9;
		final int var28 = getBrightnessForRender(p_180434_3_);
		final int var29 = var28 % 65536;
		final int var30 = var28 / 65536;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var29 / 1.0F, var30 / 1.0F);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		var22 -= interpPosX;
		var24 -= interpPosY;
		var26 -= interpPosZ;
		field_174842_aB.renderEntityWithPosYaw(field_174840_a, (float) var22, (float) var24, (float) var26,
				field_174840_a.rotationYaw, p_180434_3_);
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate() {
		++age;

		if (age == maxAge) {
			setDead();
		}
	}

	@Override
	public int getFXLayer() {
		return 3;
	}
}
