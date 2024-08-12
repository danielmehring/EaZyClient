package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.World;

public class BlockWorkbench extends Block {

public static final int EaZy = 405;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00000221";

	protected BlockWorkbench() {
		super(Material.wood);
		setCreativeTab(CreativeTabs.tabDecorations);
	}

	@Override
	public boolean onBlockActivated(final World worldIn, final BlockPos pos, final IBlockState state,
			final EntityPlayer playerIn, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
		if (worldIn.isRemote) {
			return true;
		} else {
			playerIn.displayGui(new BlockWorkbench.InterfaceCraftingTable(worldIn, pos));
			return true;
		}
	}

	public static class InterfaceCraftingTable implements IInteractionObject {
		private final World world;
		private final BlockPos position;
		// private static final String __OBFID =
		// "http://https://fuckuskid00002127";

		public InterfaceCraftingTable(final World worldIn, final BlockPos p_i45730_2_) {
			world = worldIn;
			position = p_i45730_2_;
		}

		@Override
		public String getName() {
			return null;
		}

		@Override
		public boolean hasCustomName() {
			return false;
		}

		@Override
		public IChatComponent getDisplayName() {
			return new ChatComponentTranslation(Blocks.crafting_table.getUnlocalizedName() + ".name", new Object[0]);
		}

		@Override
		public Container createContainer(final InventoryPlayer playerInventory, final EntityPlayer playerIn) {
			return new ContainerWorkbench(playerInventory, world, position);
		}

		@Override
		public String getGuiID() {
			return "minecraft:crafting_table";
		}
	}
}
