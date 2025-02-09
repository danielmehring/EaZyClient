package net.minecraft.world.biome;

import java.util.Arrays;
import java.util.Random;
import net.minecraft.block.BlockColored;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockSand;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class BiomeGenMesa extends BiomeGenBase {

   private IBlockState[] field_150621_aC;
   private long field_150622_aD;
   private NoiseGeneratorPerlin field_150623_aE;
   private NoiseGeneratorPerlin field_150624_aF;
   private NoiseGeneratorPerlin field_150625_aG;
   private boolean field_150626_aH;
   private boolean field_150620_aI;
   private static final String __OBFID = "CL_00000176";


   public BiomeGenMesa(int p_i45380_1_, boolean p_i45380_2_, boolean p_i45380_3_) {
      super(p_i45380_1_);
      this.field_150626_aH = p_i45380_2_;
      this.field_150620_aI = p_i45380_3_;
      this.func_76745_m();
      this.func_76732_a(2.0F, 0.0F);
      this.field_76762_K.clear();
      this.field_76752_A = Blocks.field_150354_m.func_176223_P().func_177226_a(BlockSand.field_176504_a, BlockSand.EnumType.RED_SAND);
      this.field_76753_B = Blocks.field_150406_ce.func_176223_P();
      this.field_76760_I.field_76832_z = -999;
      this.field_76760_I.field_76804_C = 20;
      this.field_76760_I.field_76799_E = 3;
      this.field_76760_I.field_76800_F = 5;
      this.field_76760_I.field_76802_A = 0;
      this.field_76762_K.clear();
      if(p_i45380_3_) {
         this.field_76760_I.field_76832_z = 5;
      }

   }

   public WorldGenAbstractTree func_150567_a(Random p_150567_1_) {
      return this.field_76757_N;
   }

   public int func_180625_c(BlockPos p_180625_1_) {
      return 10387789;
   }

   public int func_180627_b(BlockPos p_180627_1_) {
      return 9470285;
   }

   public void func_180624_a(World p_180624_1_, Random p_180624_2_, BlockPos p_180624_3_) {
      super.func_180624_a(p_180624_1_, p_180624_2_, p_180624_3_);
   }

   public void func_180622_a(World p_180622_1_, Random p_180622_2_, ChunkPrimer p_180622_3_, int p_180622_4_, int p_180622_5_, double p_180622_6_) {
      if(this.field_150621_aC == null || this.field_150622_aD != p_180622_1_.func_72905_C()) {
         this.func_150619_a(p_180622_1_.func_72905_C());
      }

      if(this.field_150623_aE == null || this.field_150624_aF == null || this.field_150622_aD != p_180622_1_.func_72905_C()) {
         Random var8 = new Random(this.field_150622_aD);
         this.field_150623_aE = new NoiseGeneratorPerlin(var8, 4);
         this.field_150624_aF = new NoiseGeneratorPerlin(var8, 1);
      }

      this.field_150622_aD = p_180622_1_.func_72905_C();
      double var22 = 0.0D;
      int var10;
      int var11;
      if(this.field_150626_aH) {
         var10 = (p_180622_4_ & -16) + (p_180622_5_ & 15);
         var11 = (p_180622_5_ & -16) + (p_180622_4_ & 15);
         double var12 = Math.min(Math.abs(p_180622_6_), this.field_150623_aE.func_151601_a((double)var10 * 0.25D, (double)var11 * 0.25D));
         if(var12 > 0.0D) {
            double var14 = 0.001953125D;
            double var16 = Math.abs(this.field_150624_aF.func_151601_a((double)var10 * var14, (double)var11 * var14));
            var22 = var12 * var12 * 2.5D;
            double var18 = Math.ceil(var16 * 50.0D) + 14.0D;
            if(var22 > var18) {
               var22 = var18;
            }

            var22 += 64.0D;
         }
      }

      var10 = p_180622_4_ & 15;
      var11 = p_180622_5_ & 15;
      boolean var23 = true;
      IBlockState var13 = Blocks.field_150406_ce.func_176223_P();
      IBlockState var24 = this.field_76753_B;
      int var15 = (int)(p_180622_6_ / 3.0D + 3.0D + p_180622_2_.nextDouble() * 0.25D);
      boolean var25 = Math.cos(p_180622_6_ / 3.0D * 3.141592653589793D) > 0.0D;
      int var17 = -1;
      boolean var26 = false;

      for(int var19 = 255; var19 >= 0; --var19) {
         if(p_180622_3_.func_177856_a(var11, var19, var10).func_177230_c().func_149688_o() == Material.field_151579_a && var19 < (int)var22) {
            p_180622_3_.func_177855_a(var11, var19, var10, Blocks.field_150348_b.func_176223_P());
         }

         if(var19 <= p_180622_2_.nextInt(5)) {
            p_180622_3_.func_177855_a(var11, var19, var10, Blocks.field_150357_h.func_176223_P());
         } else {
            IBlockState var20 = p_180622_3_.func_177856_a(var11, var19, var10);
            if(var20.func_177230_c().func_149688_o() == Material.field_151579_a) {
               var17 = -1;
            } else if(var20.func_177230_c() == Blocks.field_150348_b) {
               IBlockState var21;
               if(var17 == -1) {
                  var26 = false;
                  if(var15 <= 0) {
                     var13 = null;
                     var24 = Blocks.field_150348_b.func_176223_P();
                  } else if(var19 >= 59 && var19 <= 64) {
                     var13 = Blocks.field_150406_ce.func_176223_P();
                     var24 = this.field_76753_B;
                  }

                  if(var19 < 63 && (var13 == null || var13.func_177230_c().func_149688_o() == Material.field_151579_a)) {
                     var13 = Blocks.field_150355_j.func_176223_P();
                  }

                  var17 = var15 + Math.max(0, var19 - 63);
                  if(var19 >= 62) {
                     if(this.field_150620_aI && var19 > 86 + var15 * 2) {
                        if(var25) {
                           p_180622_3_.func_177855_a(var11, var19, var10, Blocks.field_150346_d.func_176223_P().func_177226_a(BlockDirt.field_176386_a, BlockDirt.DirtType.COARSE_DIRT));
                        } else {
                           p_180622_3_.func_177855_a(var11, var19, var10, Blocks.field_150349_c.func_176223_P());
                        }
                     } else if(var19 > 66 + var15) {
                        if(var19 >= 64 && var19 <= 127) {
                           if(var25) {
                              var21 = Blocks.field_150405_ch.func_176223_P();
                           } else {
                              var21 = this.func_180629_a(p_180622_4_, var19, p_180622_5_);
                           }
                        } else {
                           var21 = Blocks.field_150406_ce.func_176223_P().func_177226_a(BlockColored.field_176581_a, EnumDyeColor.ORANGE);
                        }

                        p_180622_3_.func_177855_a(var11, var19, var10, var21);
                     } else {
                        p_180622_3_.func_177855_a(var11, var19, var10, this.field_76752_A);
                        var26 = true;
                     }
                  } else {
                     p_180622_3_.func_177855_a(var11, var19, var10, var24);
                     if(var24.func_177230_c() == Blocks.field_150406_ce) {
                        p_180622_3_.func_177855_a(var11, var19, var10, var24.func_177230_c().func_176223_P().func_177226_a(BlockColored.field_176581_a, EnumDyeColor.ORANGE));
                     }
                  }
               } else if(var17 > 0) {
                  --var17;
                  if(var26) {
                     p_180622_3_.func_177855_a(var11, var19, var10, Blocks.field_150406_ce.func_176223_P().func_177226_a(BlockColored.field_176581_a, EnumDyeColor.ORANGE));
                  } else {
                     var21 = this.func_180629_a(p_180622_4_, var19, p_180622_5_);
                     p_180622_3_.func_177855_a(var11, var19, var10, var21);
                  }
               }
            }
         }
      }

   }

   private void func_150619_a(long p_150619_1_) {
      this.field_150621_aC = new IBlockState[64];
      Arrays.fill(this.field_150621_aC, Blocks.field_150405_ch.func_176223_P());
      Random var3 = new Random(p_150619_1_);
      this.field_150625_aG = new NoiseGeneratorPerlin(var3, 1);

      int var4;
      for(var4 = 0; var4 < 64; ++var4) {
         var4 += var3.nextInt(5) + 1;
         if(var4 < 64) {
            this.field_150621_aC[var4] = Blocks.field_150406_ce.func_176223_P().func_177226_a(BlockColored.field_176581_a, EnumDyeColor.ORANGE);
         }
      }

      var4 = var3.nextInt(4) + 2;

      int var5;
      int var6;
      int var7;
      int var8;
      for(var5 = 0; var5 < var4; ++var5) {
         var6 = var3.nextInt(3) + 1;
         var7 = var3.nextInt(64);

         for(var8 = 0; var7 + var8 < 64 && var8 < var6; ++var8) {
            this.field_150621_aC[var7 + var8] = Blocks.field_150406_ce.func_176223_P().func_177226_a(BlockColored.field_176581_a, EnumDyeColor.YELLOW);
         }
      }

      var5 = var3.nextInt(4) + 2;

      int var9;
      for(var6 = 0; var6 < var5; ++var6) {
         var7 = var3.nextInt(3) + 2;
         var8 = var3.nextInt(64);

         for(var9 = 0; var8 + var9 < 64 && var9 < var7; ++var9) {
            this.field_150621_aC[var8 + var9] = Blocks.field_150406_ce.func_176223_P().func_177226_a(BlockColored.field_176581_a, EnumDyeColor.BROWN);
         }
      }

      var6 = var3.nextInt(4) + 2;

      for(var7 = 0; var7 < var6; ++var7) {
         var8 = var3.nextInt(3) + 1;
         var9 = var3.nextInt(64);

         for(int var10 = 0; var9 + var10 < 64 && var10 < var8; ++var10) {
            this.field_150621_aC[var9 + var10] = Blocks.field_150406_ce.func_176223_P().func_177226_a(BlockColored.field_176581_a, EnumDyeColor.RED);
         }
      }

      var7 = var3.nextInt(3) + 3;
      var8 = 0;

      for(var9 = 0; var9 < var7; ++var9) {
         byte var12 = 1;
         var8 += var3.nextInt(16) + 4;

         for(int var11 = 0; var8 + var11 < 64 && var11 < var12; ++var11) {
            this.field_150621_aC[var8 + var11] = Blocks.field_150406_ce.func_176223_P().func_177226_a(BlockColored.field_176581_a, EnumDyeColor.WHITE);
            if(var8 + var11 > 1 && var3.nextBoolean()) {
               this.field_150621_aC[var8 + var11 - 1] = Blocks.field_150406_ce.func_176223_P().func_177226_a(BlockColored.field_176581_a, EnumDyeColor.SILVER);
            }

            if(var8 + var11 < 63 && var3.nextBoolean()) {
               this.field_150621_aC[var8 + var11 + 1] = Blocks.field_150406_ce.func_176223_P().func_177226_a(BlockColored.field_176581_a, EnumDyeColor.SILVER);
            }
         }
      }

   }

   private IBlockState func_180629_a(int p_180629_1_, int p_180629_2_, int p_180629_3_) {
      int var4 = (int)Math.round(this.field_150625_aG.func_151601_a((double)p_180629_1_ * 1.0D / 512.0D, (double)p_180629_1_ * 1.0D / 512.0D) * 2.0D);
      return this.field_150621_aC[(p_180629_2_ + var4 + 64) % 64];
   }

   protected BiomeGenBase func_180277_d(int p_180277_1_) {
      boolean var2 = this.field_76756_M == BiomeGenBase.field_150589_Z.field_76756_M;
      BiomeGenMesa var3 = new BiomeGenMesa(p_180277_1_, var2, this.field_150620_aI);
      if(!var2) {
         var3.func_150570_a(field_150591_g);
         var3.func_76735_a(this.field_76791_y + " M");
      } else {
         var3.func_76735_a(this.field_76791_y + " (Bryce)");
      }

      var3.func_150557_a(this.field_76790_z, true);
      return var3;
   }
}
