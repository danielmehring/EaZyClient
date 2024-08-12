package net.minecraft.client.model;

import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.google.common.collect.Lists;

import optifine.ModelSprite;

public class ModelRenderer {

public static final int EaZy = 599;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	/** The size of the texture file's width in pixels. */
	public float textureWidth;

	/** The size of the texture file's height in pixels. */
	public float textureHeight;

	/** The X offset into the texture used for displaying this model */
	private int textureOffsetX;

	/** The Y offset into the texture used for displaying this model */
	private int textureOffsetY;
	public float rotationPointX;
	public float rotationPointY;
	public float rotationPointZ;
	public float rotateAngleX;
	public float rotateAngleY;
	public float rotateAngleZ;
	private boolean compiled;

	/** The GL display list rendered by the Tessellator for this model */
	private int displayList;
	public boolean mirror;
	public boolean showModel;

	/** Hides the model. */
	public boolean isHidden;
	public List cubeList;
	public List childModels;
	public final String boxName;
	private final ModelBase baseModel;
	public float offsetX;
	public float offsetY;
	public float offsetZ;
	// private static final String __OBFID = "http://https://fuckuskid00000874";
	public List spriteList;
	public boolean mirrorV;
	float savedScale;

	public ModelRenderer(final ModelBase p_i1172_1_, final String p_i1172_2_) {
		spriteList = new ArrayList();
		mirrorV = false;
		textureWidth = 64.0F;
		textureHeight = 32.0F;
		showModel = true;
		cubeList = Lists.newArrayList();
		baseModel = p_i1172_1_;
		p_i1172_1_.boxList.add(this);
		boxName = p_i1172_2_;
		setTextureSize(p_i1172_1_.textureWidth, p_i1172_1_.textureHeight);
	}

	public ModelRenderer(final ModelBase p_i1173_1_) {
		this(p_i1173_1_, (String) null);
	}

	public ModelRenderer(final ModelBase p_i46358_1_, final int p_i46358_2_, final int p_i46358_3_) {
		this(p_i46358_1_);
		setTextureOffset(p_i46358_2_, p_i46358_3_);
	}

	/**
	 * Sets the current box's rotation points and rotation angles to another
	 * box.
	 */
	public void addChild(final ModelRenderer p_78792_1_) {
		if (childModels == null) {
			childModels = Lists.newArrayList();
		}

		childModels.add(p_78792_1_);
	}

	public ModelRenderer setTextureOffset(final int p_78784_1_, final int p_78784_2_) {
		textureOffsetX = p_78784_1_;
		textureOffsetY = p_78784_2_;
		return this;
	}

	public ModelRenderer addBox(String p_78786_1_, final float p_78786_2_, final float p_78786_3_,
			final float p_78786_4_, final int p_78786_5_, final int p_78786_6_, final int p_78786_7_) {
		p_78786_1_ = boxName + "." + p_78786_1_;
		final TextureOffset var8 = baseModel.getTextureOffset(p_78786_1_);
		setTextureOffset(var8.textureOffsetX, var8.textureOffsetY);
		cubeList.add(new ModelBox(this, textureOffsetX, textureOffsetY, p_78786_2_, p_78786_3_, p_78786_4_, p_78786_5_,
				p_78786_6_, p_78786_7_, 0.0F).func_78244_a(p_78786_1_));
		return this;
	}

	public ModelRenderer addBox(final float p_78789_1_, final float p_78789_2_, final float p_78789_3_,
			final int p_78789_4_, final int p_78789_5_, final int p_78789_6_) {
		cubeList.add(new ModelBox(this, textureOffsetX, textureOffsetY, p_78789_1_, p_78789_2_, p_78789_3_, p_78789_4_,
				p_78789_5_, p_78789_6_, 0.0F));
		return this;
	}

	public ModelRenderer addBox(final float p_178769_1_, final float p_178769_2_, final float p_178769_3_,
			final int p_178769_4_, final int p_178769_5_, final int p_178769_6_, final boolean p_178769_7_) {
		cubeList.add(new ModelBox(this, textureOffsetX, textureOffsetY, p_178769_1_, p_178769_2_, p_178769_3_,
				p_178769_4_, p_178769_5_, p_178769_6_, 0.0F, p_178769_7_));
		return this;
	}

	/**
	 * Creates a textured box. Args: originX, originY, originZ, width, height,
	 * depth, scaleFactor.
	 */
	public void addBox(final float p_78790_1_, final float p_78790_2_, final float p_78790_3_, final int p_78790_4_,
			final int p_78790_5_, final int p_78790_6_, final float p_78790_7_) {
		cubeList.add(new ModelBox(this, textureOffsetX, textureOffsetY, p_78790_1_, p_78790_2_, p_78790_3_, p_78790_4_,
				p_78790_5_, p_78790_6_, p_78790_7_));
	}

	public void setRotationPoint(final float p_78793_1_, final float p_78793_2_, final float p_78793_3_) {
		rotationPointX = p_78793_1_;
		rotationPointY = p_78793_2_;
		rotationPointZ = p_78793_3_;
	}

	public void render(final float p_78785_1_) {
		if (!isHidden && showModel) {
			if (!compiled) {
				compileDisplayList(p_78785_1_);
			}

			GlStateManager.translate(offsetX, offsetY, offsetZ);
			int var2;

			if (rotateAngleX == 0.0F && rotateAngleY == 0.0F && rotateAngleZ == 0.0F) {
				if (rotationPointX == 0.0F && rotationPointY == 0.0F && rotationPointZ == 0.0F) {
					GlStateManager.callList(displayList);

					if (childModels != null) {
						for (var2 = 0; var2 < childModels.size(); ++var2) {
							((ModelRenderer) childModels.get(var2)).render(p_78785_1_);
						}
					}
				} else {
					GlStateManager.translate(rotationPointX * p_78785_1_, rotationPointY * p_78785_1_,
							rotationPointZ * p_78785_1_);
					GlStateManager.callList(displayList);

					if (childModels != null) {
						for (var2 = 0; var2 < childModels.size(); ++var2) {
							((ModelRenderer) childModels.get(var2)).render(p_78785_1_);
						}
					}

					GlStateManager.translate(-rotationPointX * p_78785_1_, -rotationPointY * p_78785_1_,
							-rotationPointZ * p_78785_1_);
				}
			} else {
				GlStateManager.pushMatrix();
				GlStateManager.translate(rotationPointX * p_78785_1_, rotationPointY * p_78785_1_,
						rotationPointZ * p_78785_1_);

				if (rotateAngleZ != 0.0F) {
					GlStateManager.rotate(rotateAngleZ * (180F / (float) Math.PI), 0.0F, 0.0F, 1.0F);
				}

				if (rotateAngleY != 0.0F) {
					GlStateManager.rotate(rotateAngleY * (180F / (float) Math.PI), 0.0F, 1.0F, 0.0F);
				}

				if (rotateAngleX != 0.0F) {
					GlStateManager.rotate(rotateAngleX * (180F / (float) Math.PI), 1.0F, 0.0F, 0.0F);
				}

				GlStateManager.callList(displayList);

				if (childModels != null) {
					for (var2 = 0; var2 < childModels.size(); ++var2) {
						((ModelRenderer) childModels.get(var2)).render(p_78785_1_);
					}
				}

				GlStateManager.popMatrix();
			}

			GlStateManager.translate(-offsetX, -offsetY, -offsetZ);
		}
	}

	public void renderWithRotation(final float p_78791_1_) {
		if (!isHidden && showModel) {
			if (!compiled) {
				compileDisplayList(p_78791_1_);
			}

			GlStateManager.pushMatrix();
			GlStateManager.translate(rotationPointX * p_78791_1_, rotationPointY * p_78791_1_,
					rotationPointZ * p_78791_1_);

			if (rotateAngleY != 0.0F) {
				GlStateManager.rotate(rotateAngleY * (180F / (float) Math.PI), 0.0F, 1.0F, 0.0F);
			}

			if (rotateAngleX != 0.0F) {
				GlStateManager.rotate(rotateAngleX * (180F / (float) Math.PI), 1.0F, 0.0F, 0.0F);
			}

			if (rotateAngleZ != 0.0F) {
				GlStateManager.rotate(rotateAngleZ * (180F / (float) Math.PI), 0.0F, 0.0F, 1.0F);
			}

			GlStateManager.callList(displayList);
			GlStateManager.popMatrix();
		}
	}

	/**
	 * Allows the changing of Angles after a box has been rendered
	 */
	public void postRender(final float p_78794_1_) {
		if (!isHidden && showModel) {
			if (!compiled) {
				compileDisplayList(p_78794_1_);
			}

			if (rotateAngleX == 0.0F && rotateAngleY == 0.0F && rotateAngleZ == 0.0F) {
				if (rotationPointX != 0.0F || rotationPointY != 0.0F || rotationPointZ != 0.0F) {
					GlStateManager.translate(rotationPointX * p_78794_1_, rotationPointY * p_78794_1_,
							rotationPointZ * p_78794_1_);
				}
			} else {
				GlStateManager.translate(rotationPointX * p_78794_1_, rotationPointY * p_78794_1_,
						rotationPointZ * p_78794_1_);

				if (rotateAngleZ != 0.0F) {
					GlStateManager.rotate(rotateAngleZ * (180F / (float) Math.PI), 0.0F, 0.0F, 1.0F);
				}

				if (rotateAngleY != 0.0F) {
					GlStateManager.rotate(rotateAngleY * (180F / (float) Math.PI), 0.0F, 1.0F, 0.0F);
				}

				if (rotateAngleX != 0.0F) {
					GlStateManager.rotate(rotateAngleX * (180F / (float) Math.PI), 1.0F, 0.0F, 0.0F);
				}
			}
		}
	}

	/**
	 * Compiles a GL display list for this model
	 */
	private void compileDisplayList(final float p_78788_1_) {
		if (displayList == 0) {
			savedScale = p_78788_1_;
			displayList = GLAllocation.generateDisplayLists(1);
		}

		GL11.glNewList(displayList, GL11.GL_COMPILE);
		final WorldRenderer var2 = Tessellator.getInstance().getWorldRenderer();
		int i;

		for (i = 0; i < cubeList.size(); ++i) {
			((ModelBox) cubeList.get(i)).render(var2, p_78788_1_);
		}

		for (i = 0; i < spriteList.size(); ++i) {
			final ModelSprite sprite = (ModelSprite) spriteList.get(i);
			sprite.render(Tessellator.getInstance(), p_78788_1_);
		}

		GL11.glEndList();
		compiled = true;
	}

	/**
	 * Returns the model renderer with the new texture parameters.
	 */
	public ModelRenderer setTextureSize(final int p_78787_1_, final int p_78787_2_) {
		textureWidth = p_78787_1_;
		textureHeight = p_78787_2_;
		return this;
	}

	public void addSprite(final float posX, final float posY, final float posZ, final int sizeX, final int sizeY,
			final int sizeZ, final float sizeAdd) {
		spriteList.add(
				new ModelSprite(this, textureOffsetX, textureOffsetY, posX, posY, posZ, sizeX, sizeY, sizeZ, sizeAdd));
	}

	public boolean getCompiled() {
		return compiled;
	}

	public int getDisplayList() {
		return displayList;
	}

	public void resetDisplayList() {
		if (compiled) {
			compiled = false;
			compileDisplayList(savedScale);
		}
	}
}
