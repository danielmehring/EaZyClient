package net.minecraft.client.renderer;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.BlockCactus;
import net.minecraft.block.BlockColored;
import net.minecraft.block.BlockCommandBlock;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockFire;
import net.minecraft.block.BlockFlowerPot;
import net.minecraft.block.BlockHopper;
import net.minecraft.block.BlockJukebox;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockNewLeaf;
import net.minecraft.block.BlockNewLog;
import net.minecraft.block.BlockOldLeaf;
import net.minecraft.block.BlockOldLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.BlockPrismarine;
import net.minecraft.block.BlockQuartz;
import net.minecraft.block.BlockRedSandstone;
import net.minecraft.block.BlockRedstoneWire;
import net.minecraft.block.BlockReed;
import net.minecraft.block.BlockSand;
import net.minecraft.block.BlockSandStone;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.BlockSilverfish;
import net.minecraft.block.BlockStem;
import net.minecraft.block.BlockStone;
import net.minecraft.block.BlockStoneBrick;
import net.minecraft.block.BlockStoneSlab;
import net.minecraft.block.BlockStoneSlabNew;
import net.minecraft.block.BlockTNT;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.BlockTripWire;
import net.minecraft.block.BlockWall;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.statemap.BlockStateMapper;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Maps;

public class BlockModelShapes {

public static final int EaZy = 693;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final Map field_178129_a = Maps.newIdentityHashMap();
	private final BlockStateMapper blockStateMapper = new BlockStateMapper();
	private final ModelManager modelManager;

	public BlockModelShapes(final ModelManager p_i46245_1_) {
		modelManager = p_i46245_1_;
		func_178119_d();
	}

	public BlockStateMapper getBlockStateMapper() {
		return blockStateMapper;
	}

	public TextureAtlasSprite func_178122_a(final IBlockState p_178122_1_) {
		final Block var2 = p_178122_1_.getBlock();
		IBakedModel var3 = func_178125_b(p_178122_1_);

		if (var3 == null || var3 == modelManager.getMissingModel()) {
			if (var2 == Blocks.wall_sign || var2 == Blocks.standing_sign || var2 == Blocks.chest
					|| var2 == Blocks.trapped_chest || var2 == Blocks.standing_banner || var2 == Blocks.wall_banner) {
				return modelManager.func_174952_b().getAtlasSprite("minecraft:blocks/planks_oak");
			}

			if (var2 == Blocks.ender_chest) {
				return modelManager.func_174952_b().getAtlasSprite("minecraft:blocks/obsidian");
			}

			if (var2 == Blocks.flowing_lava || var2 == Blocks.lava) {
				return modelManager.func_174952_b().getAtlasSprite("minecraft:blocks/lava_still");
			}

			if (var2 == Blocks.flowing_water || var2 == Blocks.water) {
				return modelManager.func_174952_b().getAtlasSprite("minecraft:blocks/water_still");
			}

			if (var2 == Blocks.skull) {
				return modelManager.func_174952_b().getAtlasSprite("minecraft:blocks/soul_sand");
			}

			if (var2 == Blocks.barrier) {
				return modelManager.func_174952_b().getAtlasSprite("minecraft:items/barrier");
			}
		}

		if (var3 == null) {
			var3 = modelManager.getMissingModel();
		}

		return var3.getTexture();
	}

	public IBakedModel func_178125_b(final IBlockState p_178125_1_) {
		IBakedModel var2 = (IBakedModel) field_178129_a.get(p_178125_1_);

		if (var2 == null) {
			var2 = modelManager.getMissingModel();
		}

		return var2;
	}

	public ModelManager func_178126_b() {
		return modelManager;
	}

	public void func_178124_c() {
		field_178129_a.clear();
		final Iterator var1 = blockStateMapper.func_178446_a().entrySet().iterator();

		while (var1.hasNext()) {
			final Entry var2 = (Entry) var1.next();
			field_178129_a.put(var2.getKey(), modelManager.getModel((ModelResourceLocation) var2.getValue()));
		}
	}

	public void func_178121_a(final Block p_178121_1_, final IStateMapper p_178121_2_) {
		blockStateMapper.func_178447_a(p_178121_1_, p_178121_2_);
	}

	public void func_178123_a(final Block... p_178123_1_) {
		blockStateMapper.registerBuiltInBlocks(p_178123_1_);
	}

	private void func_178119_d() {
		func_178123_a(new Block[] { Blocks.air, Blocks.flowing_water, Blocks.water, Blocks.flowing_lava, Blocks.lava,
				Blocks.piston_extension, Blocks.chest, Blocks.ender_chest, Blocks.trapped_chest, Blocks.standing_sign,
				Blocks.skull, Blocks.end_portal, Blocks.barrier, Blocks.wall_sign, Blocks.wall_banner,
				Blocks.standing_banner });
		func_178121_a(Blocks.stone, new StateMap.Builder().func_178440_a(BlockStone.VARIANT_PROP).build());
		func_178121_a(Blocks.prismarine, new StateMap.Builder().func_178440_a(BlockPrismarine.VARIANTS).build());
		func_178121_a(Blocks.leaves,
				new StateMap.Builder().func_178440_a(BlockOldLeaf.VARIANT_PROP).func_178439_a("_leaves")
						.func_178442_a(new IProperty[] { BlockLeaves.field_176236_b, BlockLeaves.field_176237_a })
						.build());
		func_178121_a(Blocks.leaves2,
				new StateMap.Builder().func_178440_a(BlockNewLeaf.field_176240_P).func_178439_a("_leaves")
						.func_178442_a(new IProperty[] { BlockLeaves.field_176236_b, BlockLeaves.field_176237_a })
						.build());
		func_178121_a(Blocks.cactus,
				new StateMap.Builder().func_178442_a(new IProperty[] { BlockCactus.AGE_PROP }).build());
		func_178121_a(Blocks.reeds,
				new StateMap.Builder().func_178442_a(new IProperty[] { BlockReed.field_176355_a }).build());
		func_178121_a(Blocks.jukebox,
				new StateMap.Builder().func_178442_a(new IProperty[] { BlockJukebox.HAS_RECORD }).build());
		func_178121_a(Blocks.command_block,
				new StateMap.Builder().func_178442_a(new IProperty[] { BlockCommandBlock.TRIGGERED_PROP }).build());
		func_178121_a(Blocks.cobblestone_wall,
				new StateMap.Builder().func_178440_a(BlockWall.field_176255_P).func_178439_a("_wall").build());
		func_178121_a(Blocks.double_plant, new StateMap.Builder().func_178440_a(BlockDoublePlant.VARIANT_PROP).build());
		func_178121_a(Blocks.oak_fence_gate,
				new StateMap.Builder().func_178442_a(new IProperty[] { BlockFenceGate.field_176465_b }).build());
		func_178121_a(Blocks.spruce_fence_gate,
				new StateMap.Builder().func_178442_a(new IProperty[] { BlockFenceGate.field_176465_b }).build());
		func_178121_a(Blocks.birch_fence_gate,
				new StateMap.Builder().func_178442_a(new IProperty[] { BlockFenceGate.field_176465_b }).build());
		func_178121_a(Blocks.jungle_fence_gate,
				new StateMap.Builder().func_178442_a(new IProperty[] { BlockFenceGate.field_176465_b }).build());
		func_178121_a(Blocks.dark_oak_fence_gate,
				new StateMap.Builder().func_178442_a(new IProperty[] { BlockFenceGate.field_176465_b }).build());
		func_178121_a(Blocks.acacia_fence_gate,
				new StateMap.Builder().func_178442_a(new IProperty[] { BlockFenceGate.field_176465_b }).build());
		func_178121_a(Blocks.tripwire, new StateMap.Builder()
				.func_178442_a(new IProperty[] { BlockTripWire.field_176295_N, BlockTripWire.field_176293_a }).build());
		func_178121_a(Blocks.double_wooden_slab,
				new StateMap.Builder().func_178440_a(BlockPlanks.VARIANT_PROP).func_178439_a("_double_slab").build());
		func_178121_a(Blocks.wooden_slab,
				new StateMap.Builder().func_178440_a(BlockPlanks.VARIANT_PROP).func_178439_a("_slab").build());
		func_178121_a(Blocks.tnt,
				new StateMap.Builder().func_178442_a(new IProperty[] { BlockTNT.field_176246_a }).build());
		func_178121_a(Blocks.fire,
				new StateMap.Builder().func_178442_a(new IProperty[] { BlockFire.field_176543_a }).build());
		func_178121_a(Blocks.redstone_wire,
				new StateMap.Builder().func_178442_a(new IProperty[] { BlockRedstoneWire.POWER }).build());
		func_178121_a(Blocks.oak_door,
				new StateMap.Builder().func_178442_a(new IProperty[] { BlockDoor.POWERED_PROP }).build());
		func_178121_a(Blocks.spruce_door,
				new StateMap.Builder().func_178442_a(new IProperty[] { BlockDoor.POWERED_PROP }).build());
		func_178121_a(Blocks.birch_door,
				new StateMap.Builder().func_178442_a(new IProperty[] { BlockDoor.POWERED_PROP }).build());
		func_178121_a(Blocks.jungle_door,
				new StateMap.Builder().func_178442_a(new IProperty[] { BlockDoor.POWERED_PROP }).build());
		func_178121_a(Blocks.acacia_door,
				new StateMap.Builder().func_178442_a(new IProperty[] { BlockDoor.POWERED_PROP }).build());
		func_178121_a(Blocks.dark_oak_door,
				new StateMap.Builder().func_178442_a(new IProperty[] { BlockDoor.POWERED_PROP }).build());
		func_178121_a(Blocks.iron_door,
				new StateMap.Builder().func_178442_a(new IProperty[] { BlockDoor.POWERED_PROP }).build());
		func_178121_a(Blocks.wool,
				new StateMap.Builder().func_178440_a(BlockColored.COLOR).func_178439_a("_wool").build());
		func_178121_a(Blocks.carpet,
				new StateMap.Builder().func_178440_a(BlockColored.COLOR).func_178439_a("_carpet").build());
		func_178121_a(Blocks.stained_hardened_clay, new StateMap.Builder().func_178440_a(BlockColored.COLOR)
				.func_178439_a("_stained_hardened_clay").build());
		func_178121_a(Blocks.stained_glass_pane,
				new StateMap.Builder().func_178440_a(BlockColored.COLOR).func_178439_a("_stained_glass_pane").build());
		func_178121_a(Blocks.stained_glass,
				new StateMap.Builder().func_178440_a(BlockColored.COLOR).func_178439_a("_stained_glass").build());
		func_178121_a(Blocks.sandstone, new StateMap.Builder().func_178440_a(BlockSandStone.field_176297_a).build());
		func_178121_a(Blocks.red_sandstone, new StateMap.Builder().func_178440_a(BlockRedSandstone.TYPE).build());
		func_178121_a(Blocks.tallgrass, new StateMap.Builder().func_178440_a(BlockTallGrass.field_176497_a).build());
		func_178121_a(Blocks.bed,
				new StateMap.Builder().func_178442_a(new IProperty[] { BlockBed.OCCUPIED_PROP }).build());
		func_178121_a(Blocks.yellow_flower,
				new StateMap.Builder().func_178440_a(Blocks.yellow_flower.func_176494_l()).build());
		func_178121_a(Blocks.red_flower,
				new StateMap.Builder().func_178440_a(Blocks.red_flower.func_176494_l()).build());
		func_178121_a(Blocks.stone_slab,
				new StateMap.Builder().func_178440_a(BlockStoneSlab.field_176556_M).func_178439_a("_slab").build());
		func_178121_a(Blocks.stone_slab2,
				new StateMap.Builder().func_178440_a(BlockStoneSlabNew.field_176559_M).func_178439_a("_slab").build());
		func_178121_a(Blocks.monster_egg, new StateMap.Builder().func_178440_a(BlockSilverfish.VARIANT_PROP)
				.func_178439_a("_monster_egg").build());
		func_178121_a(Blocks.stonebrick, new StateMap.Builder().func_178440_a(BlockStoneBrick.VARIANT_PROP).build());
		func_178121_a(Blocks.dispenser,
				new StateMap.Builder().func_178442_a(new IProperty[] { BlockDispenser.TRIGGERED }).build());
		func_178121_a(Blocks.dropper,
				new StateMap.Builder().func_178442_a(new IProperty[] { BlockDispenser.TRIGGERED }).build());
		func_178121_a(Blocks.log,
				new StateMap.Builder().func_178440_a(BlockOldLog.VARIANT_PROP).func_178439_a("_log").build());
		func_178121_a(Blocks.log2,
				new StateMap.Builder().func_178440_a(BlockNewLog.field_176300_b).func_178439_a("_log").build());
		func_178121_a(Blocks.planks,
				new StateMap.Builder().func_178440_a(BlockPlanks.VARIANT_PROP).func_178439_a("_planks").build());
		func_178121_a(Blocks.sapling,
				new StateMap.Builder().func_178440_a(BlockSapling.TYPE_PROP).func_178439_a("_sapling").build());
		func_178121_a(Blocks.sand, new StateMap.Builder().func_178440_a(BlockSand.VARIANT_PROP).build());
		func_178121_a(Blocks.hopper,
				new StateMap.Builder().func_178442_a(new IProperty[] { BlockHopper.field_176429_b }).build());
		func_178121_a(Blocks.flower_pot,
				new StateMap.Builder().func_178442_a(new IProperty[] { BlockFlowerPot.field_176444_a }).build());
		func_178121_a(Blocks.quartz_block, new StateMapperBase() {
			@Override
			protected ModelResourceLocation func_178132_a(final IBlockState p_178132_1_) {
				final BlockQuartz.EnumType var2 = (BlockQuartz.EnumType) p_178132_1_.getValue(BlockQuartz.VARIANT_PROP);

				switch (BlockModelShapes.SwitchEnumType.field_178257_a[var2.ordinal()]) {
					case 1:
					default:
						return new ModelResourceLocation("quartz_block", "normal");

					case 2:
						return new ModelResourceLocation("chiseled_quartz_block", "normal");

					case 3:
						return new ModelResourceLocation("quartz_column", "axis=y");

					case 4:
						return new ModelResourceLocation("quartz_column", "axis=x");

					case 5:
						return new ModelResourceLocation("quartz_column", "axis=z");
				}
			}
		});
		func_178121_a(Blocks.deadbush, new StateMapperBase() {
			@Override
			protected ModelResourceLocation func_178132_a(final IBlockState p_178132_1_) {
				return new ModelResourceLocation("dead_bush", "normal");
			}
		});
		func_178121_a(Blocks.pumpkin_stem, new StateMapperBase() {
			@Override
			protected ModelResourceLocation func_178132_a(final IBlockState p_178132_1_) {
				final LinkedHashMap var2 = Maps.newLinkedHashMap(p_178132_1_.getProperties());

				if (p_178132_1_.getValue(BlockStem.FACING_PROP) != EnumFacing.UP) {
					var2.remove(BlockStem.AGE_PROP);
				}

				return new ModelResourceLocation(
						(ResourceLocation) Block.blockRegistry.getNameForObject(p_178132_1_.getBlock()),
						func_178131_a(var2));
			}
		});
		func_178121_a(Blocks.melon_stem, new StateMapperBase() {
			@Override
			protected ModelResourceLocation func_178132_a(final IBlockState p_178132_1_) {
				final LinkedHashMap var2 = Maps.newLinkedHashMap(p_178132_1_.getProperties());

				if (p_178132_1_.getValue(BlockStem.FACING_PROP) != EnumFacing.UP) {
					var2.remove(BlockStem.AGE_PROP);
				}

				return new ModelResourceLocation(
						(ResourceLocation) Block.blockRegistry.getNameForObject(p_178132_1_.getBlock()),
						func_178131_a(var2));
			}
		});
		func_178121_a(Blocks.dirt, new StateMapperBase() {
			@Override
			protected ModelResourceLocation func_178132_a(final IBlockState p_178132_1_) {
				final LinkedHashMap var2 = Maps.newLinkedHashMap(p_178132_1_.getProperties());
				final String var3 = BlockDirt.VARIANT.getName((Comparable) var2.remove(BlockDirt.VARIANT));

				if (BlockDirt.DirtType.PODZOL != p_178132_1_.getValue(BlockDirt.VARIANT)) {
					var2.remove(BlockDirt.SNOWY);
				}

				return new ModelResourceLocation(var3, func_178131_a(var2));
			}
		});
		func_178121_a(Blocks.double_stone_slab, new StateMapperBase() {
			@Override
			protected ModelResourceLocation func_178132_a(final IBlockState p_178132_1_) {
				final LinkedHashMap var2 = Maps.newLinkedHashMap(p_178132_1_.getProperties());
				final String var3 = BlockStoneSlab.field_176556_M
						.getName((Comparable) var2.remove(BlockStoneSlab.field_176556_M));
				var2.remove(BlockStoneSlab.field_176555_b);
				final String var4 = ((Boolean) p_178132_1_.getValue(BlockStoneSlab.field_176555_b))
						? "all" : "normal";
				return new ModelResourceLocation(var3 + "_double_slab", var4);
			}
		});
		func_178121_a(Blocks.double_stone_slab2, new StateMapperBase() {
			@Override
			protected ModelResourceLocation func_178132_a(final IBlockState p_178132_1_) {
				final LinkedHashMap var2 = Maps.newLinkedHashMap(p_178132_1_.getProperties());
				final String var3 = BlockStoneSlabNew.field_176559_M
						.getName((Comparable) var2.remove(BlockStoneSlabNew.field_176559_M));
				var2.remove(BlockStoneSlab.field_176555_b);
				final String var4 = ((Boolean) p_178132_1_.getValue(BlockStoneSlabNew.field_176558_b))
						? "all" : "normal";
				return new ModelResourceLocation(var3 + "_double_slab", var4);
			}
		});
	}

	static final class SwitchEnumType {
		static final int[] field_178257_a = new int[BlockQuartz.EnumType.values().length];
		static {
			try {
				field_178257_a[BlockQuartz.EnumType.DEFAULT.ordinal()] = 1;
			} catch (final NoSuchFieldError var5) {
			}

			try {
				field_178257_a[BlockQuartz.EnumType.CHISELED.ordinal()] = 2;
			} catch (final NoSuchFieldError var4) {
			}

			try {
				field_178257_a[BlockQuartz.EnumType.LINES_Y.ordinal()] = 3;
			} catch (final NoSuchFieldError var3) {
			}

			try {
				field_178257_a[BlockQuartz.EnumType.LINES_X.ordinal()] = 4;
			} catch (final NoSuchFieldError var2) {
			}

			try {
				field_178257_a[BlockQuartz.EnumType.LINES_Z.ordinal()] = 5;
			} catch (final NoSuchFieldError var1) {
			}
		}
	}
}
