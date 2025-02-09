package net.minecraft.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;

public class BlockCompressed extends Block {

   private final MapColor field_150202_a;
   private static final String __OBFID = "CL_00000268";


   public BlockCompressed(MapColor p_i45414_1_) {
      super(Material.field_151573_f);
      this.field_150202_a = p_i45414_1_;
      this.func_149647_a(CreativeTabs.field_78030_b);
   }

   public MapColor func_180659_g(IBlockState p_180659_1_) {
      return this.field_150202_a;
   }
}
