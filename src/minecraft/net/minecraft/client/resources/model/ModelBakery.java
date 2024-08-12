package net.minecraft.client.resources.model;

import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BlockPart;
import net.minecraft.client.renderer.block.model.BlockPartFace;
import net.minecraft.client.renderer.block.model.FaceBakery;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemModelGenerator;
import net.minecraft.client.renderer.block.model.ModelBlock;
import net.minecraft.client.renderer.block.model.ModelBlockDefinition;
import net.minecraft.client.renderer.texture.IIconCreator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IRegistry;
import net.minecraft.util.RegistrySimple;
import net.minecraft.util.ResourceLocation;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Queues;
import com.google.common.collect.Sets;

public class ModelBakery {

public static final int EaZy = 888;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final Set field_177602_b = Sets.newHashSet(new ResourceLocation[] {
			new ResourceLocation("blocks/water_flow"), new ResourceLocation("blocks/water_still"),
			new ResourceLocation("blocks/lava_flow"), new ResourceLocation("blocks/lava_still"),
			new ResourceLocation("blocks/destroy_stage_0"), new ResourceLocation("blocks/destroy_stage_1"),
			new ResourceLocation("blocks/destroy_stage_2"), new ResourceLocation("blocks/destroy_stage_3"),
			new ResourceLocation("blocks/destroy_stage_4"), new ResourceLocation("blocks/destroy_stage_5"),
			new ResourceLocation("blocks/destroy_stage_6"), new ResourceLocation("blocks/destroy_stage_7"),
			new ResourceLocation("blocks/destroy_stage_8"), new ResourceLocation("blocks/destroy_stage_9"),
			new ResourceLocation("items/empty_armor_slot_helmet"),
			new ResourceLocation("items/empty_armor_slot_chestplate"),
			new ResourceLocation("items/empty_armor_slot_leggings"),
			new ResourceLocation("items/empty_armor_slot_boots") });
	private static final Logger LOGGER = LogManager.getLogger();
	protected static final ModelResourceLocation MODEL_MISSING = new ModelResourceLocation("builtin/missing",
			"missing");
	private static final Map BUILT_IN_MODELS = Maps.newHashMap();
	private static final Joiner field_177601_e;
	private final IResourceManager resourceManager;
	private final Map field_177599_g = Maps.newHashMap();
	private final Map models = Maps.newLinkedHashMap();
	private final Map variants = Maps.newLinkedHashMap();
	private final TextureMap textureMap;
	private final BlockModelShapes blockModelShapes;
	private final FaceBakery field_177607_l = new FaceBakery();
	private final ItemModelGenerator itemModelGenerator = new ItemModelGenerator();
	private final RegistrySimple bakedRegistry = new RegistrySimple();
	private static final ModelBlock MODEL_GENERATED;
	private static final ModelBlock MODEL_COMPASS;
	private static final ModelBlock MODEL_CLOCK;
	private static final ModelBlock MODEL_ENTITY;
	private final Map itemLocations = Maps.newLinkedHashMap();
	private final Map field_177614_t = Maps.newHashMap();
	private final Map variantNames = Maps.newIdentityHashMap();
	// private static final String __OBFID = "http://https://fuckuskid00002391";

	public ModelBakery(final IResourceManager p_i46085_1_, final TextureMap p_i46085_2_,
			final BlockModelShapes p_i46085_3_) {
		resourceManager = p_i46085_1_;
		textureMap = p_i46085_2_;
		blockModelShapes = p_i46085_3_;
	}

	public IRegistry setupModelRegistry() {
		func_177577_b();
		func_177597_h();
		func_177572_j();
		bakeItemModels();
		bakeBlockModels();
		return bakedRegistry;
	}

	private void func_177577_b() {
		loadVariants(blockModelShapes.getBlockStateMapper().func_178446_a().values());
		variants.put(MODEL_MISSING,
				new ModelBlockDefinition.Variants(MODEL_MISSING.func_177518_c(),
						Lists.newArrayList(new ModelBlockDefinition.Variant[] {
								new ModelBlockDefinition.Variant(new ResourceLocation(MODEL_MISSING.getResourcePath()),
										ModelRotation.X0_Y0, false, 1) })));
		final ResourceLocation var1 = new ResourceLocation("item_frame");
		final ModelBlockDefinition var2 = getModelBlockDefinition(var1);
		registerVariant(var2, new ModelResourceLocation(var1, "normal"));
		registerVariant(var2, new ModelResourceLocation(var1, "map"));
		func_177595_c();
		loadItemModels();
	}

	private void loadVariants(final Collection p_177591_1_) {
		final Iterator var2 = p_177591_1_.iterator();

		while (var2.hasNext()) {
			final ModelResourceLocation var3 = (ModelResourceLocation) var2.next();

			try {
				final ModelBlockDefinition var4 = getModelBlockDefinition(var3);

				try {
					registerVariant(var4, var3);
				} catch (final Exception var6) {
					LOGGER.warn("Unable to load variant: " + var3.func_177518_c() + " from " + var3);
				}
			} catch (final Exception var7) {
				LOGGER.warn("Unable to load definition " + var3, var7);
			}
		}
	}

	private void registerVariant(final ModelBlockDefinition p_177569_1_, final ModelResourceLocation p_177569_2_) {
		variants.put(p_177569_2_, p_177569_1_.func_178330_b(p_177569_2_.func_177518_c()));
	}

	private ModelBlockDefinition getModelBlockDefinition(final ResourceLocation p_177586_1_) {
		final ResourceLocation var2 = getBlockStateLocation(p_177586_1_);
		ModelBlockDefinition var3 = (ModelBlockDefinition) field_177614_t.get(var2);

		if (var3 == null) {
			final ArrayList var4 = Lists.newArrayList();

			try {
				final Iterator var5 = resourceManager.getAllResources(var2).iterator();

				while (var5.hasNext()) {
					final IResource var6 = (IResource) var5.next();
					InputStream var7 = null;

					try {
						var7 = var6.getInputStream();
						final ModelBlockDefinition var8 = ModelBlockDefinition
								.func_178331_a(new InputStreamReader(var7, Charsets.UTF_8));
						var4.add(var8);
					} catch (final Exception var13) {
						throw new RuntimeException("Encountered an exception when loading model definition of \'"
								+ p_177586_1_ + "\' from: \'" + var6.func_177241_a() + "\' in resourcepack: \'"
								+ var6.func_177240_d() + "\'", var13);
					}
					finally {
						IOUtils.closeQuietly(var7);
					}
				}
			} catch (final IOException var15) {
				throw new RuntimeException(
						"Encountered an exception when loading model definition of model " + var2.toString(), var15);
			}

			var3 = new ModelBlockDefinition(var4);
			field_177614_t.put(var2, var3);
		}

		return var3;
	}

	private ResourceLocation getBlockStateLocation(final ResourceLocation p_177584_1_) {
		return new ResourceLocation(p_177584_1_.getResourceDomain(),
				"blockstates/" + p_177584_1_.getResourcePath() + ".json");
	}

	private void func_177595_c() {
		final Iterator var1 = variants.keySet().iterator();

		while (var1.hasNext()) {
			final ModelResourceLocation var2 = (ModelResourceLocation) var1.next();
			final Iterator var3 = ((ModelBlockDefinition.Variants) variants.get(var2)).getVariants().iterator();

			while (var3.hasNext()) {
				final ModelBlockDefinition.Variant var4 = (ModelBlockDefinition.Variant) var3.next();
				final ResourceLocation var5 = var4.getModelLocation();

				if (models.get(var5) == null) {
					try {
						final ModelBlock var6 = loadModel(var5);
						models.put(var5, var6);
					} catch (final Exception var7) {
						LOGGER.warn("Unable to load block model: \'" + var5 + "\' for variant: \'" + var2 + "\'", var7);
					}
				}
			}
		}
	}

	private ModelBlock loadModel(final ResourceLocation p_177594_1_) throws IOException {
		final String var3 = p_177594_1_.getResourcePath();

		if ("builtin/generated".equals(var3)) {
			return MODEL_GENERATED;
		} else if ("builtin/compass".equals(var3)) {
			return MODEL_COMPASS;
		} else if ("builtin/clock".equals(var3)) {
			return MODEL_CLOCK;
		} else if ("builtin/entity".equals(var3)) {
			return MODEL_ENTITY;
		} else {
			Object var2;

			if (var3.startsWith("builtin/")) {
				final String var4 = var3.substring("builtin/".length());
				final String var5 = (String) BUILT_IN_MODELS.get(var4);

				if (var5 == null) {
					throw new FileNotFoundException(p_177594_1_.toString());
				}

				var2 = new StringReader(var5);
			} else {
				final IResource var9 = resourceManager.getResource(getModelLocation(p_177594_1_));
				var2 = new InputStreamReader(var9.getInputStream(), Charsets.UTF_8);
			}

			ModelBlock var11;

			try {
				final ModelBlock var10 = ModelBlock.deserialize((Reader) var2);
				var10.field_178317_b = p_177594_1_.toString();
				var11 = var10;
			}
			finally {
				((Reader) var2).close();
			}

			return var11;
		}
	}

	private ResourceLocation getModelLocation(final ResourceLocation p_177580_1_) {
		return new ResourceLocation(p_177580_1_.getResourceDomain(),
				"models/" + p_177580_1_.getResourcePath() + ".json");
	}

	private void loadItemModels() {
		registerVariantNames();
		final Iterator var1 = Item.itemRegistry.iterator();

		while (var1.hasNext()) {
			final Item var2 = (Item) var1.next();
			final List var3 = getVariantNames(var2);
			final Iterator var4 = var3.iterator();

			while (var4.hasNext()) {
				final String var5 = (String) var4.next();
				final ResourceLocation var6 = getItemLocation(var5);
				itemLocations.put(var5, var6);

				if (models.get(var6) == null) {
					try {
						final ModelBlock var7 = loadModel(var6);
						models.put(var6, var7);
					} catch (final Exception var8) {
						LOGGER.warn("Unable to load item model: \'" + var6 + "\' for item: \'"
								+ Item.itemRegistry.getNameForObject(var2) + "\'", var8);
					}
				}
			}
		}
	}

	private void registerVariantNames() {
		variantNames.put(Item.getItemFromBlock(Blocks.stone), Lists.newArrayList(new String[] { "stone", "granite",
				"granite_smooth", "diorite", "diorite_smooth", "andesite", "andesite_smooth" }));
		variantNames.put(Item.getItemFromBlock(Blocks.dirt),
				Lists.newArrayList(new String[] { "dirt", "coarse_dirt", "podzol" }));
		variantNames.put(Item.getItemFromBlock(Blocks.planks), Lists.newArrayList(new String[] { "oak_planks",
				"spruce_planks", "birch_planks", "jungle_planks", "acacia_planks", "dark_oak_planks" }));
		variantNames.put(Item.getItemFromBlock(Blocks.sapling), Lists.newArrayList(new String[] { "oak_sapling",
				"spruce_sapling", "birch_sapling", "jungle_sapling", "acacia_sapling", "dark_oak_sapling" }));
		variantNames.put(Item.getItemFromBlock(Blocks.sand), Lists.newArrayList(new String[] { "sand", "red_sand" }));
		variantNames.put(Item.getItemFromBlock(Blocks.log),
				Lists.newArrayList(new String[] { "oak_log", "spruce_log", "birch_log", "jungle_log" }));
		variantNames.put(Item.getItemFromBlock(Blocks.leaves),
				Lists.newArrayList(new String[] { "oak_leaves", "spruce_leaves", "birch_leaves", "jungle_leaves" }));
		variantNames.put(Item.getItemFromBlock(Blocks.sponge),
				Lists.newArrayList(new String[] { "sponge", "sponge_wet" }));
		variantNames.put(Item.getItemFromBlock(Blocks.sandstone),
				Lists.newArrayList(new String[] { "sandstone", "chiseled_sandstone", "smooth_sandstone" }));
		variantNames.put(Item.getItemFromBlock(Blocks.red_sandstone),
				Lists.newArrayList(new String[] { "red_sandstone", "chiseled_red_sandstone", "smooth_red_sandstone" }));
		variantNames.put(Item.getItemFromBlock(Blocks.tallgrass),
				Lists.newArrayList(new String[] { "dead_bush", "tall_grass", "fern" }));
		variantNames.put(Item.getItemFromBlock(Blocks.deadbush), Lists.newArrayList(new String[] { "dead_bush" }));
		variantNames.put(Item.getItemFromBlock(Blocks.wool),
				Lists.newArrayList(new String[] { "black_wool", "red_wool", "green_wool", "brown_wool", "blue_wool",
						"purple_wool", "cyan_wool", "silver_wool", "gray_wool", "pink_wool", "lime_wool", "yellow_wool",
						"light_blue_wool", "magenta_wool", "orange_wool", "white_wool" }));
		variantNames.put(Item.getItemFromBlock(Blocks.yellow_flower), Lists.newArrayList(new String[] { "dandelion" }));
		variantNames.put(Item.getItemFromBlock(Blocks.red_flower),
				Lists.newArrayList(new String[] { "poppy", "blue_orchid", "allium", "houstonia", "red_tulip",
						"orange_tulip", "white_tulip", "pink_tulip", "oxeye_daisy" }));
		variantNames.put(Item.getItemFromBlock(Blocks.stone_slab),
				Lists.newArrayList(new String[] { "stone_slab", "sandstone_slab", "cobblestone_slab", "brick_slab",
						"stone_brick_slab", "nether_brick_slab", "quartz_slab" }));
		variantNames.put(Item.getItemFromBlock(Blocks.stone_slab2),
				Lists.newArrayList(new String[] { "red_sandstone_slab" }));
		variantNames.put(Item.getItemFromBlock(Blocks.stained_glass),
				Lists.newArrayList(new String[] { "black_stained_glass", "red_stained_glass", "green_stained_glass",
						"brown_stained_glass", "blue_stained_glass", "purple_stained_glass", "cyan_stained_glass",
						"silver_stained_glass", "gray_stained_glass", "pink_stained_glass", "lime_stained_glass",
						"yellow_stained_glass", "light_blue_stained_glass", "magenta_stained_glass",
						"orange_stained_glass", "white_stained_glass" }));
		variantNames.put(Item.getItemFromBlock(Blocks.monster_egg),
				Lists.newArrayList(new String[] { "stone_monster_egg", "cobblestone_monster_egg",
						"stone_brick_monster_egg", "mossy_brick_monster_egg", "cracked_brick_monster_egg",
						"chiseled_brick_monster_egg" }));
		variantNames.put(Item.getItemFromBlock(Blocks.stonebrick), Lists.newArrayList(
				new String[] { "stonebrick", "mossy_stonebrick", "cracked_stonebrick", "chiseled_stonebrick" }));
		variantNames.put(Item.getItemFromBlock(Blocks.wooden_slab), Lists.newArrayList(new String[] { "oak_slab",
				"spruce_slab", "birch_slab", "jungle_slab", "acacia_slab", "dark_oak_slab" }));
		variantNames.put(Item.getItemFromBlock(Blocks.cobblestone_wall),
				Lists.newArrayList(new String[] { "cobblestone_wall", "mossy_cobblestone_wall" }));
		variantNames.put(Item.getItemFromBlock(Blocks.anvil),
				Lists.newArrayList(new String[] { "anvil_intact", "anvil_slightly_damaged", "anvil_very_damaged" }));
		variantNames.put(Item.getItemFromBlock(Blocks.quartz_block),
				Lists.newArrayList(new String[] { "quartz_block", "chiseled_quartz_block", "quartz_column" }));
		variantNames.put(Item.getItemFromBlock(Blocks.stained_hardened_clay),
				Lists.newArrayList(new String[] { "black_stained_hardened_clay", "red_stained_hardened_clay",
						"green_stained_hardened_clay", "brown_stained_hardened_clay", "blue_stained_hardened_clay",
						"purple_stained_hardened_clay", "cyan_stained_hardened_clay", "silver_stained_hardened_clay",
						"gray_stained_hardened_clay", "pink_stained_hardened_clay", "lime_stained_hardened_clay",
						"yellow_stained_hardened_clay", "light_blue_stained_hardened_clay",
						"magenta_stained_hardened_clay", "orange_stained_hardened_clay",
						"white_stained_hardened_clay" }));
		variantNames.put(Item.getItemFromBlock(Blocks.stained_glass_pane),
				Lists.newArrayList(new String[] { "black_stained_glass_pane", "red_stained_glass_pane",
						"green_stained_glass_pane", "brown_stained_glass_pane", "blue_stained_glass_pane",
						"purple_stained_glass_pane", "cyan_stained_glass_pane", "silver_stained_glass_pane",
						"gray_stained_glass_pane", "pink_stained_glass_pane", "lime_stained_glass_pane",
						"yellow_stained_glass_pane", "light_blue_stained_glass_pane", "magenta_stained_glass_pane",
						"orange_stained_glass_pane", "white_stained_glass_pane" }));
		variantNames.put(Item.getItemFromBlock(Blocks.leaves2),
				Lists.newArrayList(new String[] { "acacia_leaves", "dark_oak_leaves" }));
		variantNames.put(Item.getItemFromBlock(Blocks.log2),
				Lists.newArrayList(new String[] { "acacia_log", "dark_oak_log" }));
		variantNames.put(Item.getItemFromBlock(Blocks.prismarine),
				Lists.newArrayList(new String[] { "prismarine", "prismarine_bricks", "dark_prismarine" }));
		variantNames.put(Item.getItemFromBlock(Blocks.carpet),
				Lists.newArrayList(new String[] { "black_carpet", "red_carpet", "green_carpet", "brown_carpet",
						"blue_carpet", "purple_carpet", "cyan_carpet", "silver_carpet", "gray_carpet", "pink_carpet",
						"lime_carpet", "yellow_carpet", "light_blue_carpet", "magenta_carpet", "orange_carpet",
						"white_carpet" }));
		variantNames.put(Item.getItemFromBlock(Blocks.double_plant), Lists.newArrayList(
				new String[] { "sunflower", "syringa", "double_grass", "double_fern", "double_rose", "paeonia" }));
		variantNames.put(Items.bow,
				Lists.newArrayList(new String[] { "bow", "bow_pulling_0", "bow_pulling_1", "bow_pulling_2" }));
		variantNames.put(Items.coal, Lists.newArrayList(new String[] { "coal", "charcoal" }));
		variantNames.put(Items.fishing_rod, Lists.newArrayList(new String[] { "fishing_rod", "fishing_rod_cast" }));
		variantNames.put(Items.fish, Lists.newArrayList(new String[] { "cod", "salmon", "clownfish", "pufferfish" }));
		variantNames.put(Items.cooked_fish, Lists.newArrayList(new String[] { "cooked_cod", "cooked_salmon" }));
		variantNames.put(Items.dye,
				Lists.newArrayList(new String[] { "dye_black", "dye_red", "dye_green", "dye_brown", "dye_blue",
						"dye_purple", "dye_cyan", "dye_silver", "dye_gray", "dye_pink", "dye_lime", "dye_yellow",
						"dye_light_blue", "dye_magenta", "dye_orange", "dye_white" }));
		variantNames.put(Items.potionitem, Lists.newArrayList(new String[] { "bottle_drinkable", "bottle_splash" }));
		variantNames.put(Items.skull, Lists.newArrayList(
				new String[] { "skull_skeleton", "skull_wither", "skull_zombie", "skull_char", "skull_creeper" }));
		variantNames.put(Item.getItemFromBlock(Blocks.oak_fence_gate),
				Lists.newArrayList(new String[] { "oak_fence_gate" }));
		variantNames.put(Item.getItemFromBlock(Blocks.oak_fence), Lists.newArrayList(new String[] { "oak_fence" }));
		variantNames.put(Items.oak_door, Lists.newArrayList(new String[] { "oak_door" }));
	}

	private List getVariantNames(final Item p_177596_1_) {
		List var2 = (List) variantNames.get(p_177596_1_);

		if (var2 == null) {
			var2 = Collections
					.singletonList(((ResourceLocation) Item.itemRegistry.getNameForObject(p_177596_1_)).toString());
		}

		return var2;
	}

	private ResourceLocation getItemLocation(final String p_177583_1_) {
		final ResourceLocation var2 = new ResourceLocation(p_177583_1_);
		return new ResourceLocation(var2.getResourceDomain(), "item/" + var2.getResourcePath());
	}

	private void bakeBlockModels() {
		Iterator var1 = variants.keySet().iterator();

		while (var1.hasNext()) {
			final ModelResourceLocation var2 = (ModelResourceLocation) var1.next();
			final WeightedBakedModel.Builder var3 = new WeightedBakedModel.Builder();
			int var4 = 0;
			final Iterator var5 = ((ModelBlockDefinition.Variants) variants.get(var2)).getVariants().iterator();

			while (var5.hasNext()) {
				final ModelBlockDefinition.Variant var6 = (ModelBlockDefinition.Variant) var5.next();
				final ModelBlock var7 = (ModelBlock) models.get(var6.getModelLocation());

				if (var7 != null && var7.isResolved()) {
					++var4;
					var3.add(bakeModel(var7, var6.getRotation(), var6.isUvLocked()), var6.getWeight());
				} else {
					LOGGER.warn("Missing model for: " + var2);
				}
			}

			if (var4 == 0) {
				LOGGER.warn("No weighted models for: " + var2);
			} else if (var4 == 1) {
				bakedRegistry.putObject(var2, var3.first());
			} else {
				bakedRegistry.putObject(var2, var3.build());
			}
		}

		var1 = itemLocations.entrySet().iterator();

		while (var1.hasNext()) {
			final Entry var8 = (Entry) var1.next();
			final ResourceLocation var9 = (ResourceLocation) var8.getValue();
			final ModelResourceLocation var10 = new ModelResourceLocation((String) var8.getKey(), "inventory");
			final ModelBlock var11 = (ModelBlock) models.get(var9);

			if (var11 != null && var11.isResolved()) {
				if (isCustomRenderer(var11)) {
					bakedRegistry.putObject(var10,
							new BuiltInModel(new ItemCameraTransforms(var11.getThirdPersonTransform(),
									var11.getFirstPersonTransform(), var11.getHeadTransform(),
									var11.getInGuiTransform())));
				} else {
					bakedRegistry.putObject(var10, bakeModel(var11, ModelRotation.X0_Y0, false));
				}
			} else {
				LOGGER.warn("Missing model for: " + var9);
			}
		}
	}

	private Set func_177575_g() {
		final HashSet var1 = Sets.newHashSet();
		final ArrayList var2 = Lists.newArrayList(variants.keySet());
		Collections.sort(var2, new Comparator() {
			// private static final String __OBFID =
			// "http://https://fuckuskid00002390";
			public int func_177505_a(final ModelResourceLocation p_177505_1_, final ModelResourceLocation p_177505_2_) {
				return p_177505_1_.toString().compareTo(p_177505_2_.toString());
			}

			@Override
			public int compare(final Object p_compare_1_, final Object p_compare_2_) {
				return func_177505_a((ModelResourceLocation) p_compare_1_, (ModelResourceLocation) p_compare_2_);
			}
		});
		final Iterator var3 = var2.iterator();

		while (var3.hasNext()) {
			final ModelResourceLocation var4 = (ModelResourceLocation) var3.next();
			final ModelBlockDefinition.Variants var5 = (ModelBlockDefinition.Variants) variants.get(var4);
			final Iterator var6 = var5.getVariants().iterator();

			while (var6.hasNext()) {
				final ModelBlockDefinition.Variant var7 = (ModelBlockDefinition.Variant) var6.next();
				final ModelBlock var8 = (ModelBlock) models.get(var7.getModelLocation());

				if (var8 == null) {
					LOGGER.warn("Missing model for: " + var4);
				} else {
					var1.addAll(func_177585_a(var8));
				}
			}
		}

		var1.addAll(field_177602_b);
		return var1;
	}

	private IBakedModel bakeModel(final ModelBlock p_177578_1_, final ModelRotation p_177578_2_,
			final boolean p_177578_3_) {
		final TextureAtlasSprite var4 = (TextureAtlasSprite) field_177599_g
				.get(new ResourceLocation(p_177578_1_.resolveTextureName("particle")));
		final SimpleBakedModel.Builder var5 = new SimpleBakedModel.Builder(p_177578_1_).func_177646_a(var4);
		final Iterator var6 = p_177578_1_.getElements().iterator();

		while (var6.hasNext()) {
			final BlockPart var7 = (BlockPart) var6.next();
			final Iterator var8 = var7.field_178240_c.keySet().iterator();

			while (var8.hasNext()) {
				final EnumFacing var9 = (EnumFacing) var8.next();
				final BlockPartFace var10 = (BlockPartFace) var7.field_178240_c.get(var9);
				final TextureAtlasSprite var11 = (TextureAtlasSprite) field_177599_g
						.get(new ResourceLocation(p_177578_1_.resolveTextureName(var10.field_178242_d)));

				if (var10.field_178244_b == null) {
					var5.func_177648_a(func_177589_a(var7, var10, var11, var9, p_177578_2_, p_177578_3_));
				} else {
					var5.func_177650_a(p_177578_2_.func_177523_a(var10.field_178244_b),
							func_177589_a(var7, var10, var11, var9, p_177578_2_, p_177578_3_));
				}
			}
		}

		return var5.func_177645_b();
	}

	private BakedQuad func_177589_a(final BlockPart p_177589_1_, final BlockPartFace p_177589_2_,
			final TextureAtlasSprite p_177589_3_, final EnumFacing p_177589_4_, final ModelRotation p_177589_5_,
			final boolean p_177589_6_) {
		return field_177607_l.func_178414_a(p_177589_1_.field_178241_a, p_177589_1_.field_178239_b, p_177589_2_,
				p_177589_3_, p_177589_4_, p_177589_5_, p_177589_1_.field_178237_d, p_177589_6_,
				p_177589_1_.field_178238_e);
	}

	private void func_177597_h() {
		func_177574_i();
		final Iterator var1 = models.values().iterator();

		while (var1.hasNext()) {
			final ModelBlock var2 = (ModelBlock) var1.next();
			var2.getParentFromMap(models);
		}

		ModelBlock.func_178312_b(models);
	}

	private void func_177574_i() {
		final ArrayDeque var1 = Queues.newArrayDeque();
		final HashSet var2 = Sets.newHashSet();
		final Iterator var3 = models.keySet().iterator();
		ResourceLocation var5;

		while (var3.hasNext()) {
			final ResourceLocation var4 = (ResourceLocation) var3.next();
			var2.add(var4);
			var5 = ((ModelBlock) models.get(var4)).getParentLocation();

			if (var5 != null) {
				var1.add(var5);
			}
		}

		while (!var1.isEmpty()) {
			final ResourceLocation var7 = (ResourceLocation) var1.pop();

			try {
				if (models.get(var7) != null) {
					continue;
				}

				final ModelBlock var8 = loadModel(var7);
				models.put(var7, var8);
				var5 = var8.getParentLocation();

				if (var5 != null && !var2.contains(var5)) {
					var1.add(var5);
				}
			} catch (final Exception var6) {
				LOGGER.warn("In parent chain: " + field_177601_e.join(func_177573_e(var7))
						+ "; unable to load model: \'" + var7 + "\'", var6);
			}

			var2.add(var7);
		}
	}

	private List func_177573_e(final ResourceLocation p_177573_1_) {
		final ArrayList var2 = Lists.newArrayList(new ResourceLocation[] { p_177573_1_ });
		ResourceLocation var3 = p_177573_1_;

		while ((var3 = func_177576_f(var3)) != null) {
			var2.add(0, var3);
		}

		return var2;
	}

	private ResourceLocation func_177576_f(final ResourceLocation p_177576_1_) {
		final Iterator var2 = models.entrySet().iterator();
		Entry var3;
		ModelBlock var4;

		do {
			if (!var2.hasNext()) {
				return null;
			}

			var3 = (Entry) var2.next();
			var4 = (ModelBlock) var3.getValue();
		}
		while (var4 == null || !p_177576_1_.equals(var4.getParentLocation()));

		return (ResourceLocation) var3.getKey();
	}

	private Set func_177585_a(final ModelBlock p_177585_1_) {
		final HashSet var2 = Sets.newHashSet();
		final Iterator var3 = p_177585_1_.getElements().iterator();

		while (var3.hasNext()) {
			final BlockPart var4 = (BlockPart) var3.next();
			final Iterator var5 = var4.field_178240_c.values().iterator();

			while (var5.hasNext()) {
				final BlockPartFace var6 = (BlockPartFace) var5.next();
				final ResourceLocation var7 = new ResourceLocation(p_177585_1_.resolveTextureName(var6.field_178242_d));
				var2.add(var7);
			}
		}

		var2.add(new ResourceLocation(p_177585_1_.resolveTextureName("particle")));
		return var2;
	}

	private void func_177572_j() {
		final Set var1 = func_177575_g();
		var1.addAll(func_177571_k());
		var1.remove(TextureMap.field_174945_f);
		final IIconCreator var2 = new IIconCreator() {
			// private static final String __OBFID =
			// "http://https://fuckuskid00002389";
			@Override
			public void func_177059_a(final TextureMap p_177059_1_) {
				final Iterator var2 = var1.iterator();

				while (var2.hasNext()) {
					final ResourceLocation var3 = (ResourceLocation) var2.next();
					final TextureAtlasSprite var4 = p_177059_1_.func_174942_a(var3);
					field_177599_g.put(var3, var4);
				}
			}
		};
		textureMap.func_174943_a(resourceManager, var2);
		field_177599_g.put(new ResourceLocation("missingno"), textureMap.func_174944_f());
	}

	private Set func_177571_k() {
		final HashSet var1 = Sets.newHashSet();
		final Iterator var2 = itemLocations.values().iterator();

		while (var2.hasNext()) {
			final ResourceLocation var3 = (ResourceLocation) var2.next();
			final ModelBlock var4 = (ModelBlock) models.get(var3);

			if (var4 != null) {
				var1.add(new ResourceLocation(var4.resolveTextureName("particle")));
				Iterator var5;
				ResourceLocation var11;

				if (func_177581_b(var4)) {
					for (var5 = ItemModelGenerator.LAYERS.iterator(); var5.hasNext(); var1.add(var11)) {
						final String var10 = (String) var5.next();
						var11 = new ResourceLocation(var4.resolveTextureName(var10));

						if (var4.getRootModel() == MODEL_COMPASS && !TextureMap.field_174945_f.equals(var11)) {
							TextureAtlasSprite.func_176603_b(var11.toString());
						} else if (var4.getRootModel() == MODEL_CLOCK && !TextureMap.field_174945_f.equals(var11)) {
							TextureAtlasSprite.func_176602_a(var11.toString());
						}
					}
				} else if (!isCustomRenderer(var4)) {
					var5 = var4.getElements().iterator();

					while (var5.hasNext()) {
						final BlockPart var6 = (BlockPart) var5.next();
						final Iterator var7 = var6.field_178240_c.values().iterator();

						while (var7.hasNext()) {
							final BlockPartFace var8 = (BlockPartFace) var7.next();
							final ResourceLocation var9 = new ResourceLocation(
									var4.resolveTextureName(var8.field_178242_d));
							var1.add(var9);
						}
					}
				}
			}
		}

		return var1;
	}

	private boolean func_177581_b(final ModelBlock p_177581_1_) {
		if (p_177581_1_ == null) {
			return false;
		} else {
			final ModelBlock var2 = p_177581_1_.getRootModel();
			return var2 == MODEL_GENERATED || var2 == MODEL_COMPASS || var2 == MODEL_CLOCK;
		}
	}

	private boolean isCustomRenderer(final ModelBlock p_177587_1_) {
		if (p_177587_1_ == null) {
			return false;
		} else {
			final ModelBlock var2 = p_177587_1_.getRootModel();
			return var2 == MODEL_ENTITY;
		}
	}

	private void bakeItemModels() {
		Iterator var1 = itemLocations.values().iterator();

		while (var1.hasNext()) {
			final ResourceLocation var2 = (ResourceLocation) var1.next();
			final ModelBlock var3 = (ModelBlock) models.get(var2);

			if (func_177581_b(var3)) {
				final ModelBlock var4 = func_177582_d(var3);

				if (var4 != null) {
					var4.field_178317_b = var2.toString();
				}

				models.put(var2, var4);
			} else if (isCustomRenderer(var3)) {
				models.put(var2, var3);
			}
		}

		var1 = field_177599_g.values().iterator();

		while (var1.hasNext()) {
			final TextureAtlasSprite var5 = (TextureAtlasSprite) var1.next();

			if (!var5.hasAnimationMetadata()) {
				var5.clearFramesTextureData();
			}
		}
	}

	private ModelBlock func_177582_d(final ModelBlock p_177582_1_) {
		return itemModelGenerator.func_178392_a(textureMap, p_177582_1_);
	}

	static {
		BUILT_IN_MODELS.put("missing",
				"{ \"textures\": {   \"particle\": \"missingno\",   \"missingno\": \"missingno\"}, \"elements\": [ {     \"from\": [ 0, 0, 0 ],     \"to\": [ 16, 16, 16 ],     \"faces\": {         \"down\":  { \"uv\": [ 0, 0, 16, 16 ], \"cullface\": \"down\", \"texture\": \"#missingno\" },         \"up\":    { \"uv\": [ 0, 0, 16, 16 ], \"cullface\": \"up\", \"texture\": \"#missingno\" },         \"north\": { \"uv\": [ 0, 0, 16, 16 ], \"cullface\": \"north\", \"texture\": \"#missingno\" },         \"south\": { \"uv\": [ 0, 0, 16, 16 ], \"cullface\": \"south\", \"texture\": \"#missingno\" },         \"west\":  { \"uv\": [ 0, 0, 16, 16 ], \"cullface\": \"west\", \"texture\": \"#missingno\" },         \"east\":  { \"uv\": [ 0, 0, 16, 16 ], \"cullface\": \"east\", \"texture\": \"#missingno\" }    }}]}");
		field_177601_e = Joiner.on(" -> ");
		MODEL_GENERATED = ModelBlock.deserialize(
				"{\"elements\":[{  \"from\": [0, 0, 0],   \"to\": [16, 16, 16],   \"faces\": {       \"down\": {\"uv\": [0, 0, 16, 16], \"texture\":\"\"}   }}]}");
		MODEL_COMPASS = ModelBlock.deserialize(
				"{\"elements\":[{  \"from\": [0, 0, 0],   \"to\": [16, 16, 16],   \"faces\": {       \"down\": {\"uv\": [0, 0, 16, 16], \"texture\":\"\"}   }}]}");
		MODEL_CLOCK = ModelBlock.deserialize(
				"{\"elements\":[{  \"from\": [0, 0, 0],   \"to\": [16, 16, 16],   \"faces\": {       \"down\": {\"uv\": [0, 0, 16, 16], \"texture\":\"\"}   }}]}");
		MODEL_ENTITY = ModelBlock.deserialize(
				"{\"elements\":[{  \"from\": [0, 0, 0],   \"to\": [16, 16, 16],   \"faces\": {       \"down\": {\"uv\": [0, 0, 16, 16], \"texture\":\"\"}   }}]}");
		MODEL_GENERATED.field_178317_b = "generation marker";
		MODEL_COMPASS.field_178317_b = "compass generation marker";
		MODEL_CLOCK.field_178317_b = "class generation marker";
		MODEL_ENTITY.field_178317_b = "block entity marker";
	}
}
