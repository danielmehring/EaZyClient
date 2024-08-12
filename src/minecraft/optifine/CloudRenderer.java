package optifine;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

import org.lwjgl.opengl.GL11;

public class CloudRenderer {

public static final int EaZy = 1882;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final Minecraft mc;
	private boolean updated = false;
	private boolean renderFancy = false;
	int cloudTickCounter;
	float partialTicks;
	private int glListClouds = -1;
	private int cloudTickCounterUpdate = 0;
	private double cloudPlayerX = 0.0D;
	private double cloudPlayerY = 0.0D;
	private double cloudPlayerZ = 0.0D;

	public CloudRenderer(final Minecraft mc) {
		this.mc = mc;
		glListClouds = GLAllocation.generateDisplayLists(1);
	}

	public void prepareToRender(final boolean renderFancy, final int cloudTickCounter, final float partialTicks) {
		if (this.renderFancy != renderFancy) {
			updated = false;
		}

		this.renderFancy = renderFancy;
		this.cloudTickCounter = cloudTickCounter;
		this.partialTicks = partialTicks;
	}

	public boolean shouldUpdateGlList() {
		if (!updated) {
			return true;
		} else if (cloudTickCounter >= cloudTickCounterUpdate + 20) {
			return true;
		} else {
			final Entity rve = mc.func_175606_aa();
			final boolean belowCloudsPrev = cloudPlayerY + rve.getEyeHeight() < 128.0D
					+ Minecraft.gameSettings.ofCloudsHeight * 128.0F;
			final boolean belowClouds = rve.prevPosY + rve.getEyeHeight() < 128.0D
					+ Minecraft.gameSettings.ofCloudsHeight * 128.0F;
			return belowClouds != belowCloudsPrev;
		}
	}

	public void startUpdateGlList() {
		GL11.glNewList(glListClouds, GL11.GL_COMPILE);
	}

	public void endUpdateGlList() {
		GL11.glEndList();
		cloudTickCounterUpdate = cloudTickCounter;
		cloudPlayerX = mc.func_175606_aa().prevPosX;
		cloudPlayerY = mc.func_175606_aa().prevPosY;
		cloudPlayerZ = mc.func_175606_aa().prevPosZ;
		updated = true;
		GlStateManager.func_179117_G();
	}

	public void renderGlList() {
		final Entity entityliving = mc.func_175606_aa();
		final double exactPlayerX = entityliving.prevPosX + (entityliving.posX - entityliving.prevPosX) * partialTicks;
		final double exactPlayerY = entityliving.prevPosY + (entityliving.posY - entityliving.prevPosY) * partialTicks;
		final double exactPlayerZ = entityliving.prevPosZ + (entityliving.posZ - entityliving.prevPosZ) * partialTicks;
		final double dc = cloudTickCounter - cloudTickCounterUpdate + partialTicks;
		final float cdx = (float) (exactPlayerX - cloudPlayerX + dc * 0.03D);
		final float cdy = (float) (exactPlayerY - cloudPlayerY);
		final float cdz = (float) (exactPlayerZ - cloudPlayerZ);
		GlStateManager.pushMatrix();

		if (renderFancy) {
			GlStateManager.translate(-cdx / 12.0F, -cdy, -cdz / 12.0F);
		} else {
			GlStateManager.translate(-cdx, -cdy, -cdz);
		}

		GlStateManager.callList(glListClouds);
		GlStateManager.popMatrix();
		GlStateManager.func_179117_G();
	}

	public void reset() {
		updated = false;
	}
}
