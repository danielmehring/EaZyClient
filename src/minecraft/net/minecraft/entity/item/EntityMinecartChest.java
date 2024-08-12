package net.minecraft.entity.item;

import net.minecraft.block.BlockChest;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class EntityMinecartChest extends EntityMinecartContainer {

public static final int EaZy = 1142;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00001671";

	public EntityMinecartChest(final World worldIn) {
		super(worldIn);
	}

	public EntityMinecartChest(final World worldIn, final double p_i1715_2_, final double p_i1715_4_,
			final double p_i1715_6_) {
		super(worldIn, p_i1715_2_, p_i1715_4_, p_i1715_6_);
	}

	@Override
	public void killMinecart(final DamageSource p_94095_1_) {
		super.killMinecart(p_94095_1_);
		dropItemWithOffset(Item.getItemFromBlock(Blocks.chest), 1, 0.0F);
	}

	/**
	 * Returns the number of slots in the inventory.
	 */
	@Override
	public int getSizeInventory() {
		return 27;
	}

	@Override
	public EntityMinecart.EnumMinecartType func_180456_s() {
		return EntityMinecart.EnumMinecartType.CHEST;
	}

	@Override
	public IBlockState func_180457_u() {
		return Blocks.chest.getDefaultState().withProperty(BlockChest.FACING_PROP, EnumFacing.NORTH);
	}

	@Override
	public int getDefaultDisplayTileOffset() {
		return 8;
	}

	@Override
	public String getGuiID() {
		return "minecraft:chest";
	}

	@Override
	public Container createContainer(final InventoryPlayer playerInventory, final EntityPlayer playerIn) {
		return new ContainerChest(playerInventory, this, playerIn);
	}
}
