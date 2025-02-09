package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class BlockNetherWart extends BlockBush {

   public static final PropertyInteger field_176486_a = PropertyInteger.func_177719_a("age", 0, 3);
   private static final String __OBFID = "CL_00000274";


   protected BlockNetherWart() {
      this.func_180632_j(this.field_176227_L.func_177621_b().func_177226_a(field_176486_a, Integer.valueOf(0)));
      this.func_149675_a(true);
      float var1 = 0.5F;
      this.func_149676_a(0.5F - var1, 0.0F, 0.5F - var1, 0.5F + var1, 0.25F, 0.5F + var1);
      this.func_149647_a((CreativeTabs)null);
   }

   protected boolean func_149854_a(Block p_149854_1_) {
      return p_149854_1_ == Blocks.field_150425_aM;
   }

   public boolean func_180671_f(World p_180671_1_, BlockPos p_180671_2_, IBlockState p_180671_3_) {
      return this.func_149854_a(p_180671_1_.func_180495_p(p_180671_2_.func_177977_b()).func_177230_c());
   }

   public void func_180650_b(World p_180650_1_, BlockPos p_180650_2_, IBlockState p_180650_3_, Random p_180650_4_) {
      int var5 = ((Integer)p_180650_3_.func_177229_b(field_176486_a)).intValue();
      if(var5 < 3 && p_180650_4_.nextInt(10) == 0) {
         p_180650_3_ = p_180650_3_.func_177226_a(field_176486_a, Integer.valueOf(var5 + 1));
         p_180650_1_.func_180501_a(p_180650_2_, p_180650_3_, 2);
      }

      super.func_180650_b(p_180650_1_, p_180650_2_, p_180650_3_, p_180650_4_);
   }

   public void func_180653_a(World p_180653_1_, BlockPos p_180653_2_, IBlockState p_180653_3_, float p_180653_4_, int p_180653_5_) {
      if(!p_180653_1_.field_72995_K) {
         int var6 = 1;
         if(((Integer)p_180653_3_.func_177229_b(field_176486_a)).intValue() >= 3) {
            var6 = 2 + p_180653_1_.field_73012_v.nextInt(3);
            if(p_180653_5_ > 0) {
               var6 += p_180653_1_.field_73012_v.nextInt(p_180653_5_ + 1);
            }
         }

         for(int var7 = 0; var7 < var6; ++var7) {
            func_180635_a(p_180653_1_, p_180653_2_, new ItemStack(Items.field_151075_bm));
         }

      }
   }

   public Item func_180660_a(IBlockState p_180660_1_, Random p_180660_2_, int p_180660_3_) {
      return null;
   }

   public int func_149745_a(Random p_149745_1_) {
      return 0;
   }

   public Item func_180665_b(World p_180665_1_, BlockPos p_180665_2_) {
      return Items.field_151075_bm;
   }

   public IBlockState func_176203_a(int p_176203_1_) {
      return this.func_176223_P().func_177226_a(field_176486_a, Integer.valueOf(p_176203_1_));
   }

   public int func_176201_c(IBlockState p_176201_1_) {
      return ((Integer)p_176201_1_.func_177229_b(field_176486_a)).intValue();
   }

   protected BlockState func_180661_e() {
      return new BlockState(this, new IProperty[]{field_176486_a});
   }

}
