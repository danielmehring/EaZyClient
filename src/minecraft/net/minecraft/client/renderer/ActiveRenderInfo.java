package net.minecraft.client.renderer;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

public class ActiveRenderInfo {

public static final int EaZy = 673;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final IntBuffer field_178814_a = GLAllocation.createDirectIntBuffer(16);
	private static final FloatBuffer field_178812_b = GLAllocation.createDirectFloatBuffer(16);
	private static final FloatBuffer field_178813_c = GLAllocation.createDirectFloatBuffer(16);
	private static final FloatBuffer field_178810_d = GLAllocation.createDirectFloatBuffer(3);
	private static Vec3 field_178811_e = new Vec3(0.0D, 0.0D, 0.0D);

	/** The X component of the entity's yaw rotation */
	private static float rotationX;

	/** The combined X and Z components of the entity's pitch rotation */
	private static float rotationXZ;

	/** The Z component of the entity's yaw rotation */
	private static float rotationZ;

	/**
	 * The Y component (scaled along the Z axis) of the entity's pitch rotation
	 */
	private static float rotationYZ;

	/**
	 * The Y component (scaled along the X axis) of the entity's pitch rotation
	 */
	private static float rotationXY;

	/**
	 * Updates the current render info and camera location based on entity look
	 * angles and 1st/3rd person view mode
	 */
	public static void updateRenderInfo(final EntityPlayer p_74583_0_, final boolean p_74583_1_) {
		GlStateManager.getFloat(2982, field_178812_b);
		GlStateManager.getFloat(2983, field_178813_c);
		GL11.glGetInteger(GL11.GL_VIEWPORT, field_178814_a);
		final float var2 = (field_178814_a.get(0) + field_178814_a.get(2)) / 2;
		final float var3 = (field_178814_a.get(1) + field_178814_a.get(3)) / 2;
		GLU.gluUnProject(var2, var3, 0.0F, field_178812_b, field_178813_c, field_178814_a, field_178810_d);
		field_178811_e = new Vec3(field_178810_d.get(0), field_178810_d.get(1), field_178810_d.get(2));
		final int var4 = p_74583_1_ ? 1 : 0;
		final float var5 = p_74583_0_.rotationPitch;
		final float var6 = p_74583_0_.rotationYaw;
		rotationX = MathHelper.cos(var6 * (float) Math.PI / 180.0F) * (1 - var4 * 2);
		rotationZ = MathHelper.sin(var6 * (float) Math.PI / 180.0F) * (1 - var4 * 2);
		rotationYZ = -rotationZ * MathHelper.sin(var5 * (float) Math.PI / 180.0F) * (1 - var4 * 2);
		rotationXY = rotationX * MathHelper.sin(var5 * (float) Math.PI / 180.0F) * (1 - var4 * 2);
		rotationXZ = MathHelper.cos(var5 * (float) Math.PI / 180.0F);
	}

	public static Vec3 func_178806_a(final Entity p_178806_0_, final double p_178806_1_) {
		final double var3 = p_178806_0_.prevPosX + (p_178806_0_.posX - p_178806_0_.prevPosX) * p_178806_1_;
		final double var5 = p_178806_0_.prevPosY + (p_178806_0_.posY - p_178806_0_.prevPosY) * p_178806_1_;
		final double var7 = p_178806_0_.prevPosZ + (p_178806_0_.posZ - p_178806_0_.prevPosZ) * p_178806_1_;
		final double var9 = var3 + field_178811_e.xCoord;
		final double var11 = var5 + field_178811_e.yCoord;
		final double var13 = var7 + field_178811_e.zCoord;
		return new Vec3(var9, var11, var13);
	}

	public static Block func_180786_a(final World worldIn, final Entity p_180786_1_, final float p_180786_2_) {
		final Vec3 var3 = func_178806_a(p_180786_1_, p_180786_2_);
		final BlockPos var4 = new BlockPos(var3);
		final IBlockState var5 = worldIn.getBlockState(var4);
		Block var6 = var5.getBlock();

		if (var6.getMaterial().isLiquid()) {
			float var7 = 0.0F;

			if (var5.getBlock() instanceof BlockLiquid) {
				var7 = BlockLiquid.getLiquidHeightPercent(((Integer) var5.getValue(BlockLiquid.LEVEL)))
						- 0.11111111F;
			}

			final float var8 = var4.getY() + 1 - var7;

			if (var3.yCoord >= var8) {
				var6 = worldIn.getBlockState(var4.offsetUp()).getBlock();
			}
		}

		return var6;
	}

	public static Vec3 func_178804_a() {
		return field_178811_e;
	}

	public static float func_178808_b() {
		return rotationX;
	}

	public static float func_178809_c() {
		return rotationXZ;
	}

	public static float func_178803_d() {
		return rotationZ;
	}

	public static float func_178805_e() {
		return rotationYZ;
	}

	public static float func_178807_f() {
		return rotationXY;
	}
}
