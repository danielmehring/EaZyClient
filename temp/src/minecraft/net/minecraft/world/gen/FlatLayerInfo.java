package net.minecraft.world.gen;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;

public class FlatLayerInfo {

   private final int field_175902_a;
   private IBlockState field_175901_b;
   private int field_82664_a;
   private int field_82661_d;
   private static final String __OBFID = "CL_00000441";


   public FlatLayerInfo(int p_i45467_1_, Block p_i45467_2_) {
      this(3, p_i45467_1_, p_i45467_2_);
   }

   public FlatLayerInfo(int p_i45627_1_, int p_i45627_2_, Block p_i45627_3_) {
      this.field_82664_a = 1;
      this.field_175902_a = p_i45627_1_;
      this.field_82664_a = p_i45627_2_;
      this.field_175901_b = p_i45627_3_.func_176223_P();
   }

   public FlatLayerInfo(int p_i45628_1_, int p_i45628_2_, Block p_i45628_3_, int p_i45628_4_) {
      this(p_i45628_1_, p_i45628_2_, p_i45628_3_);
      this.field_175901_b = p_i45628_3_.func_176203_a(p_i45628_4_);
   }

   public int func_82657_a() {
      return this.field_82664_a;
   }

   public IBlockState func_175900_c() {
      return this.field_175901_b;
   }

   private Block func_151536_b() {
      return this.field_175901_b.func_177230_c();
   }

   private int func_82658_c() {
      return this.field_175901_b.func_177230_c().func_176201_c(this.field_175901_b);
   }

   public int func_82656_d() {
      return this.field_82661_d;
   }

   public void func_82660_d(int p_82660_1_) {
      this.field_82661_d = p_82660_1_;
   }

   public String toString() {
      String var1;
      if(this.field_175902_a >= 3) {
         ResourceLocation var2 = (ResourceLocation)Block.field_149771_c.func_177774_c(this.func_151536_b());
         var1 = var2 == null?"null":var2.toString();
         if(this.field_82664_a > 1) {
            var1 = this.field_82664_a + "*" + var1;
         }
      } else {
         var1 = Integer.toString(Block.func_149682_b(this.func_151536_b()));
         if(this.field_82664_a > 1) {
            var1 = this.field_82664_a + "x" + var1;
         }
      }

      int var3 = this.func_82658_c();
      if(var3 > 0) {
         var1 = var1 + ":" + var3;
      }

      return var1;
   }
}
