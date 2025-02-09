package net.minecraft.world.gen.layer;

import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class GenLayerEdge extends GenLayer {

   private final GenLayerEdge.Mode field_151627_c;
   private static final String __OBFID = "CL_00000547";


   public GenLayerEdge(long p_i45474_1_, GenLayer p_i45474_3_, GenLayerEdge.Mode p_i45474_4_) {
      super(p_i45474_1_);
      this.field_75909_a = p_i45474_3_;
      this.field_151627_c = p_i45474_4_;
   }

   public int[] func_75904_a(int p_75904_1_, int p_75904_2_, int p_75904_3_, int p_75904_4_) {
      switch(GenLayerEdge.SwitchMode.field_151642_a[this.field_151627_c.ordinal()]) {
      case 1:
      default:
         return this.func_151626_c(p_75904_1_, p_75904_2_, p_75904_3_, p_75904_4_);
      case 2:
         return this.func_151624_d(p_75904_1_, p_75904_2_, p_75904_3_, p_75904_4_);
      case 3:
         return this.func_151625_e(p_75904_1_, p_75904_2_, p_75904_3_, p_75904_4_);
      }
   }

   private int[] func_151626_c(int p_151626_1_, int p_151626_2_, int p_151626_3_, int p_151626_4_) {
      int var5 = p_151626_1_ - 1;
      int var6 = p_151626_2_ - 1;
      int var7 = 1 + p_151626_3_ + 1;
      int var8 = 1 + p_151626_4_ + 1;
      int[] var9 = this.field_75909_a.func_75904_a(var5, var6, var7, var8);
      int[] var10 = IntCache.func_76445_a(p_151626_3_ * p_151626_4_);

      for(int var11 = 0; var11 < p_151626_4_; ++var11) {
         for(int var12 = 0; var12 < p_151626_3_; ++var12) {
            this.func_75903_a((long)(var12 + p_151626_1_), (long)(var11 + p_151626_2_));
            int var13 = var9[var12 + 1 + (var11 + 1) * var7];
            if(var13 == 1) {
               int var14 = var9[var12 + 1 + (var11 + 1 - 1) * var7];
               int var15 = var9[var12 + 1 + 1 + (var11 + 1) * var7];
               int var16 = var9[var12 + 1 - 1 + (var11 + 1) * var7];
               int var17 = var9[var12 + 1 + (var11 + 1 + 1) * var7];
               boolean var18 = var14 == 3 || var15 == 3 || var16 == 3 || var17 == 3;
               boolean var19 = var14 == 4 || var15 == 4 || var16 == 4 || var17 == 4;
               if(var18 || var19) {
                  var13 = 2;
               }
            }

            var10[var12 + var11 * p_151626_3_] = var13;
         }
      }

      return var10;
   }

   private int[] func_151624_d(int p_151624_1_, int p_151624_2_, int p_151624_3_, int p_151624_4_) {
      int var5 = p_151624_1_ - 1;
      int var6 = p_151624_2_ - 1;
      int var7 = 1 + p_151624_3_ + 1;
      int var8 = 1 + p_151624_4_ + 1;
      int[] var9 = this.field_75909_a.func_75904_a(var5, var6, var7, var8);
      int[] var10 = IntCache.func_76445_a(p_151624_3_ * p_151624_4_);

      for(int var11 = 0; var11 < p_151624_4_; ++var11) {
         for(int var12 = 0; var12 < p_151624_3_; ++var12) {
            int var13 = var9[var12 + 1 + (var11 + 1) * var7];
            if(var13 == 4) {
               int var14 = var9[var12 + 1 + (var11 + 1 - 1) * var7];
               int var15 = var9[var12 + 1 + 1 + (var11 + 1) * var7];
               int var16 = var9[var12 + 1 - 1 + (var11 + 1) * var7];
               int var17 = var9[var12 + 1 + (var11 + 1 + 1) * var7];
               boolean var18 = var14 == 2 || var15 == 2 || var16 == 2 || var17 == 2;
               boolean var19 = var14 == 1 || var15 == 1 || var16 == 1 || var17 == 1;
               if(var19 || var18) {
                  var13 = 3;
               }
            }

            var10[var12 + var11 * p_151624_3_] = var13;
         }
      }

      return var10;
   }

   private int[] func_151625_e(int p_151625_1_, int p_151625_2_, int p_151625_3_, int p_151625_4_) {
      int[] var5 = this.field_75909_a.func_75904_a(p_151625_1_, p_151625_2_, p_151625_3_, p_151625_4_);
      int[] var6 = IntCache.func_76445_a(p_151625_3_ * p_151625_4_);

      for(int var7 = 0; var7 < p_151625_4_; ++var7) {
         for(int var8 = 0; var8 < p_151625_3_; ++var8) {
            this.func_75903_a((long)(var8 + p_151625_1_), (long)(var7 + p_151625_2_));
            int var9 = var5[var8 + var7 * p_151625_3_];
            if(var9 != 0 && this.func_75902_a(13) == 0) {
               var9 |= 1 + this.func_75902_a(15) << 8 & 3840;
            }

            var6[var8 + var7 * p_151625_3_] = var9;
         }
      }

      return var6;
   }

   public static enum Mode {

      COOL_WARM("COOL_WARM", 0),
      HEAT_ICE("HEAT_ICE", 1),
      SPECIAL("SPECIAL", 2);
      // $FF: synthetic field
      private static final GenLayerEdge.Mode[] $VALUES = new GenLayerEdge.Mode[]{COOL_WARM, HEAT_ICE, SPECIAL};
      private static final String __OBFID = "CL_00000549";


      private Mode(String p_i45473_1_, int p_i45473_2_) {}

   }

   // $FF: synthetic class
   static final class SwitchMode {

      // $FF: synthetic field
      static final int[] field_151642_a = new int[GenLayerEdge.Mode.values().length];
      private static final String __OBFID = "CL_00000548";


      static {
         try {
            field_151642_a[GenLayerEdge.Mode.COOL_WARM.ordinal()] = 1;
         } catch (NoSuchFieldError var3) {
            ;
         }

         try {
            field_151642_a[GenLayerEdge.Mode.HEAT_ICE.ordinal()] = 2;
         } catch (NoSuchFieldError var2) {
            ;
         }

         try {
            field_151642_a[GenLayerEdge.Mode.SPECIAL.ordinal()] = 3;
         } catch (NoSuchFieldError var1) {
            ;
         }

      }
   }
}
