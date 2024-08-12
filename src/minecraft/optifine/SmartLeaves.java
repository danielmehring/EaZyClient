package optifine;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SmartLeaves {

public static final int EaZy = 1964;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static IBakedModel modelLeavesCullAcacia = null;
	private static IBakedModel modelLeavesCullBirch = null;
	private static IBakedModel modelLeavesCullDarkOak = null;
	private static IBakedModel modelLeavesCullJungle = null;
	private static IBakedModel modelLeavesCullOak = null;
	private static IBakedModel modelLeavesCullSpruce = null;
	private static List generalQuadsCullAcacia = null;
	private static List generalQuadsCullBirch = null;
	private static List generalQuadsCullDarkOak = null;
	private static List generalQuadsCullJungle = null;
	private static List generalQuadsCullOak = null;
	private static List generalQuadsCullSpruce = null;
	private static IBakedModel modelLeavesDoubleAcacia = null;
	private static IBakedModel modelLeavesDoubleBirch = null;
	private static IBakedModel modelLeavesDoubleDarkOak = null;
	private static IBakedModel modelLeavesDoubleJungle = null;
	private static IBakedModel modelLeavesDoubleOak = null;
	private static IBakedModel modelLeavesDoubleSpruce = null;

	public static IBakedModel getLeavesModel(final IBakedModel model) {
		if (!Config.isTreesSmart()) {
			return model;
		} else {
			final List generalQuads = model.func_177550_a();
			return generalQuads == generalQuadsCullAcacia ? modelLeavesDoubleAcacia
					: generalQuads == generalQuadsCullBirch ? modelLeavesDoubleBirch
							: generalQuads == generalQuadsCullDarkOak ? modelLeavesDoubleDarkOak
									: generalQuads == generalQuadsCullJungle ? modelLeavesDoubleJungle
											: generalQuads == generalQuadsCullOak ? modelLeavesDoubleOak
													: generalQuads == generalQuadsCullSpruce ? modelLeavesDoubleSpruce
															: model;
		}
	}

	public static void updateLeavesModels() {
		final ArrayList updatedTypes = new ArrayList();
		modelLeavesCullAcacia = getModelCull("acacia", updatedTypes);
		modelLeavesCullBirch = getModelCull("birch", updatedTypes);
		modelLeavesCullDarkOak = getModelCull("dark_oak", updatedTypes);
		modelLeavesCullJungle = getModelCull("jungle", updatedTypes);
		modelLeavesCullOak = getModelCull("oak", updatedTypes);
		modelLeavesCullSpruce = getModelCull("spruce", updatedTypes);
		generalQuadsCullAcacia = getGeneralQuadsSafe(modelLeavesCullAcacia);
		generalQuadsCullBirch = getGeneralQuadsSafe(modelLeavesCullBirch);
		generalQuadsCullDarkOak = getGeneralQuadsSafe(modelLeavesCullDarkOak);
		generalQuadsCullJungle = getGeneralQuadsSafe(modelLeavesCullJungle);
		generalQuadsCullOak = getGeneralQuadsSafe(modelLeavesCullOak);
		generalQuadsCullSpruce = getGeneralQuadsSafe(modelLeavesCullSpruce);
		modelLeavesDoubleAcacia = getModelDoubleFace(modelLeavesCullAcacia);
		modelLeavesDoubleBirch = getModelDoubleFace(modelLeavesCullBirch);
		modelLeavesDoubleDarkOak = getModelDoubleFace(modelLeavesCullDarkOak);
		modelLeavesDoubleJungle = getModelDoubleFace(modelLeavesCullJungle);
		modelLeavesDoubleOak = getModelDoubleFace(modelLeavesCullOak);
		modelLeavesDoubleSpruce = getModelDoubleFace(modelLeavesCullSpruce);

		if (updatedTypes.size() > 0) {
			Config.dbg("Enable face culling: " + Config.arrayToString(updatedTypes.toArray()));
		}
	}

	private static List getGeneralQuadsSafe(final IBakedModel model) {
		return model == null ? null : model.func_177550_a();
	}

	static IBakedModel getModelCull(final String type, final List updatedTypes) {
		final ModelManager modelManager = Config.getModelManager();

		if (modelManager == null) {
			return null;
		} else {
			final ResourceLocation locState = new ResourceLocation("blockstates/" + type + "_leaves.json");

			if (Config.getDefiningResourcePack(locState) != Config.getDefaultResourcePack()) {
				return null;
			} else {
				final ResourceLocation locModel = new ResourceLocation("models/block/" + type + "_leaves.json");

				if (Config.getDefiningResourcePack(locModel) != Config.getDefaultResourcePack()) {
					return null;
				} else {
					final ModelResourceLocation mrl = new ModelResourceLocation(type + "_leaves", "normal");
					final IBakedModel model = modelManager.getModel(mrl);

					if (model != null && model != modelManager.getMissingModel()) {
						final List listGeneral = model.func_177550_a();

						if (listGeneral.isEmpty()) {
							return model;
						} else if (listGeneral.size() != 6) {
							return null;
						} else {
							final Iterator it = listGeneral.iterator();

							while (it.hasNext()) {
								final BakedQuad quad = (BakedQuad) it.next();
								final List listFace = model.func_177551_a(quad.getFace());

								if (listFace.size() > 0) {
									return null;
								}

								listFace.add(quad);
							}

							listGeneral.clear();
							updatedTypes.add(type + "_leaves");
							return model;
						}
					} else {
						return null;
					}
				}
			}
		}
	}

	private static IBakedModel getModelDoubleFace(final IBakedModel model) {
		if (model == null) {
			return null;
		} else if (model.func_177550_a().size() > 0) {
			Config.warn("SmartLeaves: Model is not cube, general quads: " + model.func_177550_a().size() + ", model: "
					+ model);
			return model;
		} else {
			final EnumFacing[] faces = EnumFacing.VALUES;

			for (final EnumFacing faceQuads : faces) {
				final List i = model.func_177551_a(faceQuads);

				if (i.size() != 1) {
					Config.warn("SmartLeaves: Model is not cube, side: " + faceQuads + ", quads: " + i.size()
							+ ", model: " + model);
					return model;
				}
			}

			final IBakedModel var12 = ModelUtils.duplicateModel(model);
			for (final EnumFacing face : faces) {
				final List quads = var12.func_177551_a(face);
				final BakedQuad quad = (BakedQuad) quads.get(0);
				final BakedQuad quad2 = new BakedQuad(quad.func_178209_a().clone(), quad.func_178211_c(),
						quad.getFace(), quad.getSprite());
				final int[] vd = quad2.func_178209_a();
				final int[] vd2 = vd.clone();
				final int step = vd.length / 4;
				System.arraycopy(vd, 0 * step, vd2, 3 * step, step);
				System.arraycopy(vd, 1 * step, vd2, 2 * step, step);
				System.arraycopy(vd, 2 * step, vd2, 1 * step, step);
				System.arraycopy(vd, 3 * step, vd2, 0 * step, step);
				System.arraycopy(vd2, 0, vd, 0, vd2.length);
				quads.add(quad2);
			}

			return var12;
		}
	}
}
