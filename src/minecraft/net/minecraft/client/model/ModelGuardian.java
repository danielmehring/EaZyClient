package net.minecraft.client.model;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class ModelGuardian extends ModelBase {

public static final int EaZy = 586;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final ModelRenderer guardianBody;
	private final ModelRenderer guardianEye;
	private final ModelRenderer[] guardianSpines;
	private final ModelRenderer[] guardianTail;
	// private static final String __OBFID = "http://https://fuckuskid00002628";

	public ModelGuardian() {
		textureWidth = 64;
		textureHeight = 64;
		guardianSpines = new ModelRenderer[12];
		guardianBody = new ModelRenderer(this);
		guardianBody.setTextureOffset(0, 0).addBox(-6.0F, 10.0F, -8.0F, 12, 12, 16);
		guardianBody.setTextureOffset(0, 28).addBox(-8.0F, 10.0F, -6.0F, 2, 12, 12);
		guardianBody.setTextureOffset(0, 28).addBox(6.0F, 10.0F, -6.0F, 2, 12, 12, true);
		guardianBody.setTextureOffset(16, 40).addBox(-6.0F, 8.0F, -6.0F, 12, 2, 12);
		guardianBody.setTextureOffset(16, 40).addBox(-6.0F, 22.0F, -6.0F, 12, 2, 12);

		for (int var1 = 0; var1 < guardianSpines.length; ++var1) {
			guardianSpines[var1] = new ModelRenderer(this, 0, 0);
			guardianSpines[var1].addBox(-1.0F, -4.5F, -1.0F, 2, 9, 2);
			guardianBody.addChild(guardianSpines[var1]);
		}

		guardianEye = new ModelRenderer(this, 8, 0);
		guardianEye.addBox(-1.0F, 15.0F, 0.0F, 2, 2, 1);
		guardianBody.addChild(guardianEye);
		guardianTail = new ModelRenderer[3];
		guardianTail[0] = new ModelRenderer(this, 40, 0);
		guardianTail[0].addBox(-2.0F, 14.0F, 7.0F, 4, 4, 8);
		guardianTail[1] = new ModelRenderer(this, 0, 54);
		guardianTail[1].addBox(0.0F, 14.0F, 0.0F, 3, 3, 7);
		guardianTail[2] = new ModelRenderer(this);
		guardianTail[2].setTextureOffset(41, 32).addBox(0.0F, 14.0F, 0.0F, 2, 2, 6);
		guardianTail[2].setTextureOffset(25, 19).addBox(1.0F, 10.5F, 3.0F, 1, 9, 9);
		guardianBody.addChild(guardianTail[0]);
		guardianTail[0].addChild(guardianTail[1]);
		guardianTail[1].addChild(guardianTail[2]);
	}

	public int func_178706_a() {
		return 54;
	}

	/**
	 * Sets the models various rotation angles then renders the model.
	 */
	@Override
	public void render(final Entity p_78088_1_, final float p_78088_2_, final float p_78088_3_, final float p_78088_4_,
			final float p_78088_5_, final float p_78088_6_, final float p_78088_7_) {
		setRotationAngles(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_, p_78088_1_);
		guardianBody.render(p_78088_7_);
	}

	/**
	 * Sets the model's various rotation angles. For bipeds, par1 and par2 are
	 * used for animating the movement of arms and legs, where par1 represents
	 * the time(so that arms and legs swing back and forth) and par2 represents
	 * how "far" arms and legs can swing at most.
	 */
	@Override
	public void setRotationAngles(final float p_78087_1_, final float p_78087_2_, final float p_78087_3_,
			final float p_78087_4_, final float p_78087_5_, final float p_78087_6_, final Entity p_78087_7_) {
		final EntityGuardian var8 = (EntityGuardian) p_78087_7_;
		final float var9 = p_78087_3_ - var8.ticksExisted;
		guardianBody.rotateAngleY = p_78087_4_ / (180F / (float) Math.PI);
		guardianBody.rotateAngleX = p_78087_5_ / (180F / (float) Math.PI);
		final float[] var10 = new float[] { 1.75F, 0.25F, 0.0F, 0.0F, 0.5F, 0.5F, 0.5F, 0.5F, 1.25F, 0.75F, 0.0F,
				0.0F };
		final float[] var11 = new float[] { 0.0F, 0.0F, 0.0F, 0.0F, 0.25F, 1.75F, 1.25F, 0.75F, 0.0F, 0.0F, 0.0F,
				0.0F };
		final float[] var12 = new float[] { 0.0F, 0.0F, 0.25F, 1.75F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.75F,
				1.25F };
		final float[] var13 = new float[] { 0.0F, 0.0F, 8.0F, -8.0F, -8.0F, 8.0F, 8.0F, -8.0F, 0.0F, 0.0F, 8.0F,
				-8.0F };
		final float[] var14 = new float[] { -8.0F, -8.0F, -8.0F, -8.0F, 0.0F, 0.0F, 0.0F, 0.0F, 8.0F, 8.0F, 8.0F,
				8.0F };
		final float[] var15 = new float[] { 8.0F, -8.0F, 0.0F, 0.0F, -8.0F, -8.0F, 8.0F, 8.0F, 8.0F, -8.0F, 0.0F,
				0.0F };
		final float var16 = (1.0F - var8.func_175469_o(var9)) * 0.55F;

		for (int var17 = 0; var17 < 12; ++var17) {
			guardianSpines[var17].rotateAngleX = (float) Math.PI * var10[var17];
			guardianSpines[var17].rotateAngleY = (float) Math.PI * var11[var17];
			guardianSpines[var17].rotateAngleZ = (float) Math.PI * var12[var17];
			guardianSpines[var17].rotationPointX = var13[var17]
					* (1.0F + MathHelper.cos(p_78087_3_ * 1.5F + var17) * 0.01F - var16);
			guardianSpines[var17].rotationPointY = 16.0F
					+ var14[var17] * (1.0F + MathHelper.cos(p_78087_3_ * 1.5F + var17) * 0.01F - var16);
			guardianSpines[var17].rotationPointZ = var15[var17]
					* (1.0F + MathHelper.cos(p_78087_3_ * 1.5F + var17) * 0.01F - var16);
		}

		guardianEye.rotationPointZ = -8.25F;
		Object var26 = Minecraft.getMinecraft().func_175606_aa();

		if (var8.func_175474_cn()) {
			var26 = var8.func_175466_co();
		}

		if (var26 != null) {
			final Vec3 var18 = ((Entity) var26).func_174824_e(0.0F);
			final Vec3 var19 = p_78087_7_.func_174824_e(0.0F);
			final double var20 = var18.yCoord - var19.yCoord;

			if (var20 > 0.0D) {
				guardianEye.rotationPointY = 0.0F;
			} else {
				guardianEye.rotationPointY = 1.0F;
			}

			Vec3 var22 = p_78087_7_.getLook(0.0F);
			var22 = new Vec3(var22.xCoord, 0.0D, var22.zCoord);
			final Vec3 var23 = new Vec3(var19.xCoord - var18.xCoord, 0.0D, var19.zCoord - var18.zCoord).normalize()
					.rotateYaw((float) Math.PI / 2F);
			final double var24 = var22.dotProduct(var23);
			guardianEye.rotationPointX = MathHelper.sqrt_float((float) Math.abs(var24)) * 2.0F
					* (float) Math.signum(var24);
		}

		guardianEye.showModel = true;
		final float var27 = var8.func_175471_a(var9);
		guardianTail[0].rotateAngleY = MathHelper.sin(var27) * (float) Math.PI * 0.05F;
		guardianTail[1].rotateAngleY = MathHelper.sin(var27) * (float) Math.PI * 0.1F;
		guardianTail[1].rotationPointX = -1.5F;
		guardianTail[1].rotationPointY = 0.5F;
		guardianTail[1].rotationPointZ = 14.0F;
		guardianTail[2].rotateAngleY = MathHelper.sin(var27) * (float) Math.PI * 0.15F;
		guardianTail[2].rotationPointX = 0.5F;
		guardianTail[2].rotationPointY = 0.5F;
		guardianTail[2].rotationPointZ = 6.0F;
	}
}
