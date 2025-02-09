package net.minecraft.world.gen;

import com.google.common.base.Objects;
import java.util.Random;
import net.minecraft.block.BlockSand;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.MapGenBase;

public class MapGenCaves extends MapGenBase {

   private static final String __OBFID = "CL_00000393";


   protected void func_180703_a(long p_180703_1_, int p_180703_3_, int p_180703_4_, ChunkPrimer p_180703_5_, double p_180703_6_, double p_180703_8_, double p_180703_10_) {
      this.func_180702_a(p_180703_1_, p_180703_3_, p_180703_4_, p_180703_5_, p_180703_6_, p_180703_8_, p_180703_10_, 1.0F + this.field_75038_b.nextFloat() * 6.0F, 0.0F, 0.0F, -1, -1, 0.5D);
   }

   protected void func_180702_a(long p_180702_1_, int p_180702_3_, int p_180702_4_, ChunkPrimer p_180702_5_, double p_180702_6_, double p_180702_8_, double p_180702_10_, float p_180702_12_, float p_180702_13_, float p_180702_14_, int p_180702_15_, int p_180702_16_, double p_180702_17_) {
      double var19 = (double)(p_180702_3_ * 16 + 8);
      double var21 = (double)(p_180702_4_ * 16 + 8);
      float var23 = 0.0F;
      float var24 = 0.0F;
      Random var25 = new Random(p_180702_1_);
      if(p_180702_16_ <= 0) {
         int var26 = this.field_75040_a * 16 - 16;
         p_180702_16_ = var26 - var25.nextInt(var26 / 4);
      }

      boolean var54 = false;
      if(p_180702_15_ == -1) {
         p_180702_15_ = p_180702_16_ / 2;
         var54 = true;
      }

      int var27 = var25.nextInt(p_180702_16_ / 2) + p_180702_16_ / 4;

      for(boolean var28 = var25.nextInt(6) == 0; p_180702_15_ < p_180702_16_; ++p_180702_15_) {
         double var29 = 1.5D + (double)(MathHelper.func_76126_a((float)p_180702_15_ * 3.1415927F / (float)p_180702_16_) * p_180702_12_ * 1.0F);
         double var31 = var29 * p_180702_17_;
         float var33 = MathHelper.func_76134_b(p_180702_14_);
         float var34 = MathHelper.func_76126_a(p_180702_14_);
         p_180702_6_ += (double)(MathHelper.func_76134_b(p_180702_13_) * var33);
         p_180702_8_ += (double)var34;
         p_180702_10_ += (double)(MathHelper.func_76126_a(p_180702_13_) * var33);
         if(var28) {
            p_180702_14_ *= 0.92F;
         } else {
            p_180702_14_ *= 0.7F;
         }

         p_180702_14_ += var24 * 0.1F;
         p_180702_13_ += var23 * 0.1F;
         var24 *= 0.9F;
         var23 *= 0.75F;
         var24 += (var25.nextFloat() - var25.nextFloat()) * var25.nextFloat() * 2.0F;
         var23 += (var25.nextFloat() - var25.nextFloat()) * var25.nextFloat() * 4.0F;
         if(!var54 && p_180702_15_ == var27 && p_180702_12_ > 1.0F && p_180702_16_ > 0) {
            this.func_180702_a(var25.nextLong(), p_180702_3_, p_180702_4_, p_180702_5_, p_180702_6_, p_180702_8_, p_180702_10_, var25.nextFloat() * 0.5F + 0.5F, p_180702_13_ - 1.5707964F, p_180702_14_ / 3.0F, p_180702_15_, p_180702_16_, 1.0D);
            this.func_180702_a(var25.nextLong(), p_180702_3_, p_180702_4_, p_180702_5_, p_180702_6_, p_180702_8_, p_180702_10_, var25.nextFloat() * 0.5F + 0.5F, p_180702_13_ + 1.5707964F, p_180702_14_ / 3.0F, p_180702_15_, p_180702_16_, 1.0D);
            return;
         }

         if(var54 || var25.nextInt(4) != 0) {
            double var35 = p_180702_6_ - var19;
            double var37 = p_180702_10_ - var21;
            double var39 = (double)(p_180702_16_ - p_180702_15_);
            double var41 = (double)(p_180702_12_ + 2.0F + 16.0F);
            if(var35 * var35 + var37 * var37 - var39 * var39 > var41 * var41) {
               return;
            }

            if(p_180702_6_ >= var19 - 16.0D - var29 * 2.0D && p_180702_10_ >= var21 - 16.0D - var29 * 2.0D && p_180702_6_ <= var19 + 16.0D + var29 * 2.0D && p_180702_10_ <= var21 + 16.0D + var29 * 2.0D) {
               int var55 = MathHelper.func_76128_c(p_180702_6_ - var29) - p_180702_3_ * 16 - 1;
               int var36 = MathHelper.func_76128_c(p_180702_6_ + var29) - p_180702_3_ * 16 + 1;
               int var56 = MathHelper.func_76128_c(p_180702_8_ - var31) - 1;
               int var38 = MathHelper.func_76128_c(p_180702_8_ + var31) + 1;
               int var57 = MathHelper.func_76128_c(p_180702_10_ - var29) - p_180702_4_ * 16 - 1;
               int var40 = MathHelper.func_76128_c(p_180702_10_ + var29) - p_180702_4_ * 16 + 1;
               if(var55 < 0) {
                  var55 = 0;
               }

               if(var36 > 16) {
                  var36 = 16;
               }

               if(var56 < 1) {
                  var56 = 1;
               }

               if(var38 > 248) {
                  var38 = 248;
               }

               if(var57 < 0) {
                  var57 = 0;
               }

               if(var40 > 16) {
                  var40 = 16;
               }

               boolean var58 = false;

               int var42;
               for(var42 = var55; !var58 && var42 < var36; ++var42) {
                  for(int var43 = var57; !var58 && var43 < var40; ++var43) {
                     for(int var44 = var38 + 1; !var58 && var44 >= var56 - 1; --var44) {
                        if(var44 >= 0 && var44 < 256) {
                           IBlockState var45 = p_180702_5_.func_177856_a(var42, var44, var43);
                           if(var45.func_177230_c() == Blocks.field_150358_i || var45.func_177230_c() == Blocks.field_150355_j) {
                              var58 = true;
                           }

                           if(var44 != var56 - 1 && var42 != var55 && var42 != var36 - 1 && var43 != var57 && var43 != var40 - 1) {
                              var44 = var56;
                           }
                        }
                     }
                  }
               }

               if(!var58) {
                  for(var42 = var55; var42 < var36; ++var42) {
                     double var59 = ((double)(var42 + p_180702_3_ * 16) + 0.5D - p_180702_6_) / var29;

                     for(int var60 = var57; var60 < var40; ++var60) {
                        double var46 = ((double)(var60 + p_180702_4_ * 16) + 0.5D - p_180702_10_) / var29;
                        boolean var48 = false;
                        if(var59 * var59 + var46 * var46 < 1.0D) {
                           for(int var49 = var38; var49 > var56; --var49) {
                              double var50 = ((double)(var49 - 1) + 0.5D - p_180702_8_) / var31;
                              if(var50 > -0.7D && var59 * var59 + var50 * var50 + var46 * var46 < 1.0D) {
                                 IBlockState var52 = p_180702_5_.func_177856_a(var42, var49, var60);
                                 IBlockState var53 = (IBlockState)Objects.firstNonNull(p_180702_5_.func_177856_a(var42, var49 + 1, var60), Blocks.field_150350_a.func_176223_P());
                                 if(var52.func_177230_c() == Blocks.field_150349_c || var52.func_177230_c() == Blocks.field_150391_bh) {
                                    var48 = true;
                                 }

                                 if(this.func_175793_a(var52, var53)) {
                                    if(var49 - 1 < 10) {
                                       p_180702_5_.func_177855_a(var42, var49, var60, Blocks.field_150353_l.func_176223_P());
                                    } else {
                                       p_180702_5_.func_177855_a(var42, var49, var60, Blocks.field_150350_a.func_176223_P());
                                       if(var53.func_177230_c() == Blocks.field_150354_m) {
                                          p_180702_5_.func_177855_a(var42, var49 + 1, var60, var53.func_177229_b(BlockSand.field_176504_a) == BlockSand.EnumType.RED_SAND?Blocks.field_180395_cM.func_176223_P():Blocks.field_150322_A.func_176223_P());
                                       }

                                       if(var48 && p_180702_5_.func_177856_a(var42, var49 - 1, var60).func_177230_c() == Blocks.field_150346_d) {
                                          p_180702_5_.func_177855_a(var42, var49 - 1, var60, this.field_75039_c.func_180494_b(new BlockPos(var42 + p_180702_3_ * 16, 0, var60 + p_180702_4_ * 16)).field_76752_A.func_177230_c().func_176223_P());
                                       }
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }

                  if(var54) {
                     break;
                  }
               }
            }
         }
      }

   }

   protected boolean func_175793_a(IBlockState p_175793_1_, IBlockState p_175793_2_) {
      return p_175793_1_.func_177230_c() == Blocks.field_150348_b?true:(p_175793_1_.func_177230_c() == Blocks.field_150346_d?true:(p_175793_1_.func_177230_c() == Blocks.field_150349_c?true:(p_175793_1_.func_177230_c() == Blocks.field_150405_ch?true:(p_175793_1_.func_177230_c() == Blocks.field_150406_ce?true:(p_175793_1_.func_177230_c() == Blocks.field_150322_A?true:(p_175793_1_.func_177230_c() == Blocks.field_180395_cM?true:(p_175793_1_.func_177230_c() == Blocks.field_150391_bh?true:(p_175793_1_.func_177230_c() == Blocks.field_150431_aC?true:(p_175793_1_.func_177230_c() == Blocks.field_150354_m || p_175793_1_.func_177230_c() == Blocks.field_150351_n) && p_175793_2_.func_177230_c().func_149688_o() != Material.field_151586_h))))))));
   }

   protected void func_180701_a(World p_180701_1_, int p_180701_2_, int p_180701_3_, int p_180701_4_, int p_180701_5_, ChunkPrimer p_180701_6_) {
      int var7 = this.field_75038_b.nextInt(this.field_75038_b.nextInt(this.field_75038_b.nextInt(15) + 1) + 1);
      if(this.field_75038_b.nextInt(7) != 0) {
         var7 = 0;
      }

      for(int var8 = 0; var8 < var7; ++var8) {
         double var9 = (double)(p_180701_2_ * 16 + this.field_75038_b.nextInt(16));
         double var11 = (double)this.field_75038_b.nextInt(this.field_75038_b.nextInt(120) + 8);
         double var13 = (double)(p_180701_3_ * 16 + this.field_75038_b.nextInt(16));
         int var15 = 1;
         if(this.field_75038_b.nextInt(4) == 0) {
            this.func_180703_a(this.field_75038_b.nextLong(), p_180701_4_, p_180701_5_, p_180701_6_, var9, var11, var13);
            var15 += this.field_75038_b.nextInt(4);
         }

         for(int var16 = 0; var16 < var15; ++var16) {
            float var17 = this.field_75038_b.nextFloat() * 3.1415927F * 2.0F;
            float var18 = (this.field_75038_b.nextFloat() - 0.5F) * 2.0F / 8.0F;
            float var19 = this.field_75038_b.nextFloat() * 2.0F + this.field_75038_b.nextFloat();
            if(this.field_75038_b.nextInt(10) == 0) {
               var19 *= this.field_75038_b.nextFloat() * this.field_75038_b.nextFloat() * 3.0F + 1.0F;
            }

            this.func_180702_a(this.field_75038_b.nextLong(), p_180701_4_, p_180701_5_, p_180701_6_, var9, var11, var13, var19, var17, var18, 0, 0, 1.0D);
         }
      }

   }
}
