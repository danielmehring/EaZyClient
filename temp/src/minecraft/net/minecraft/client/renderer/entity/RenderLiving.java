package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;

public abstract class RenderLiving extends RendererLivingEntity {

   private static final String __OBFID = "CL_00001015";


   public RenderLiving(RenderManager p_i46153_1_, ModelBase p_i46153_2_, float p_i46153_3_) {
      super(p_i46153_1_, p_i46153_2_, p_i46153_3_);
   }

   protected boolean func_110813_b(EntityLiving p_110813_1_) {
      return super.func_110813_b(p_110813_1_) && (p_110813_1_.func_94059_bO() || p_110813_1_.func_145818_k_() && p_110813_1_ == this.field_76990_c.field_147941_i);
   }

   public boolean func_177104_a(EntityLiving p_177104_1_, ICamera p_177104_2_, double p_177104_3_, double p_177104_5_, double p_177104_7_) {
      if(super.func_177071_a(p_177104_1_, p_177104_2_, p_177104_3_, p_177104_5_, p_177104_7_)) {
         return true;
      } else if(p_177104_1_.func_110167_bD() && p_177104_1_.func_110166_bE() != null) {
         Entity var9 = p_177104_1_.func_110166_bE();
         return p_177104_2_.func_78546_a(var9.func_174813_aQ());
      } else {
         return false;
      }
   }

   public void func_76986_a(EntityLiving p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
      super.func_76986_a((EntityLivingBase)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
      this.func_110827_b(p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
   }

   public void func_177105_a(EntityLiving p_177105_1_, float p_177105_2_) {
      int var3 = p_177105_1_.func_70070_b(p_177105_2_);
      int var4 = var3 % 65536;
      int var5 = var3 / 65536;
      OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, (float)var4 / 1.0F, (float)var5 / 1.0F);
   }

   private double func_110828_a(double p_110828_1_, double p_110828_3_, double p_110828_5_) {
      return p_110828_1_ + (p_110828_3_ - p_110828_1_) * p_110828_5_;
   }

   protected void func_110827_b(EntityLiving p_110827_1_, double p_110827_2_, double p_110827_4_, double p_110827_6_, float p_110827_8_, float p_110827_9_) {
      Entity var10 = p_110827_1_.func_110166_bE();
      if(var10 != null) {
         p_110827_4_ -= (1.6D - (double)p_110827_1_.field_70131_O) * 0.5D;
         Tessellator var11 = Tessellator.func_178181_a();
         WorldRenderer var12 = var11.func_178180_c();
         double var13 = this.func_110828_a((double)var10.field_70126_B, (double)var10.field_70177_z, (double)(p_110827_9_ * 0.5F)) * 0.01745329238474369D;
         double var15 = this.func_110828_a((double)var10.field_70127_C, (double)var10.field_70125_A, (double)(p_110827_9_ * 0.5F)) * 0.01745329238474369D;
         double var17 = Math.cos(var13);
         double var19 = Math.sin(var13);
         double var21 = Math.sin(var15);
         if(var10 instanceof EntityHanging) {
            var17 = 0.0D;
            var19 = 0.0D;
            var21 = -1.0D;
         }

         double var23 = Math.cos(var15);
         double var25 = this.func_110828_a(var10.field_70169_q, var10.field_70165_t, (double)p_110827_9_) - var17 * 0.7D - var19 * 0.5D * var23;
         double var27 = this.func_110828_a(var10.field_70167_r + (double)var10.func_70047_e() * 0.7D, var10.field_70163_u + (double)var10.func_70047_e() * 0.7D, (double)p_110827_9_) - var21 * 0.5D - 0.25D;
         double var29 = this.func_110828_a(var10.field_70166_s, var10.field_70161_v, (double)p_110827_9_) - var19 * 0.7D + var17 * 0.5D * var23;
         double var31 = this.func_110828_a((double)p_110827_1_.field_70760_ar, (double)p_110827_1_.field_70761_aq, (double)p_110827_9_) * 0.01745329238474369D + 1.5707963267948966D;
         var17 = Math.cos(var31) * (double)p_110827_1_.field_70130_N * 0.4D;
         var19 = Math.sin(var31) * (double)p_110827_1_.field_70130_N * 0.4D;
         double var33 = this.func_110828_a(p_110827_1_.field_70169_q, p_110827_1_.field_70165_t, (double)p_110827_9_) + var17;
         double var35 = this.func_110828_a(p_110827_1_.field_70167_r, p_110827_1_.field_70163_u, (double)p_110827_9_);
         double var37 = this.func_110828_a(p_110827_1_.field_70166_s, p_110827_1_.field_70161_v, (double)p_110827_9_) + var19;
         p_110827_2_ += var17;
         p_110827_6_ += var19;
         double var39 = (double)((float)(var25 - var33));
         double var41 = (double)((float)(var27 - var35));
         double var43 = (double)((float)(var29 - var37));
         GlStateManager.func_179090_x();
         GlStateManager.func_179140_f();
         GlStateManager.func_179129_p();
         boolean var45 = true;
         double var46 = 0.025D;
         var12.func_178964_a(5);

         int var48;
         float var49;
         for(var48 = 0; var48 <= 24; ++var48) {
            if(var48 % 2 == 0) {
               var12.func_178960_a(0.5F, 0.4F, 0.3F, 1.0F);
            } else {
               var12.func_178960_a(0.35F, 0.28F, 0.21000001F, 1.0F);
            }

            var49 = (float)var48 / 24.0F;
            var12.func_178984_b(p_110827_2_ + var39 * (double)var49 + 0.0D, p_110827_4_ + var41 * (double)(var49 * var49 + var49) * 0.5D + (double)((24.0F - (float)var48) / 18.0F + 0.125F), p_110827_6_ + var43 * (double)var49);
            var12.func_178984_b(p_110827_2_ + var39 * (double)var49 + 0.025D, p_110827_4_ + var41 * (double)(var49 * var49 + var49) * 0.5D + (double)((24.0F - (float)var48) / 18.0F + 0.125F) + 0.025D, p_110827_6_ + var43 * (double)var49);
         }

         var11.func_78381_a();
         var12.func_178964_a(5);

         for(var48 = 0; var48 <= 24; ++var48) {
            if(var48 % 2 == 0) {
               var12.func_178960_a(0.5F, 0.4F, 0.3F, 1.0F);
            } else {
               var12.func_178960_a(0.35F, 0.28F, 0.21000001F, 1.0F);
            }

            var49 = (float)var48 / 24.0F;
            var12.func_178984_b(p_110827_2_ + var39 * (double)var49 + 0.0D, p_110827_4_ + var41 * (double)(var49 * var49 + var49) * 0.5D + (double)((24.0F - (float)var48) / 18.0F + 0.125F) + 0.025D, p_110827_6_ + var43 * (double)var49);
            var12.func_178984_b(p_110827_2_ + var39 * (double)var49 + 0.025D, p_110827_4_ + var41 * (double)(var49 * var49 + var49) * 0.5D + (double)((24.0F - (float)var48) / 18.0F + 0.125F), p_110827_6_ + var43 * (double)var49 + 0.025D);
         }

         var11.func_78381_a();
         GlStateManager.func_179145_e();
         GlStateManager.func_179098_w();
         GlStateManager.func_179089_o();
      }

   }

   // $FF: synthetic method
   protected boolean func_110813_b(EntityLivingBase p_110813_1_) {
      return this.func_110813_b((EntityLiving)p_110813_1_);
   }

   // $FF: synthetic method
   // $FF: bridge method
   public void func_76986_a(EntityLivingBase p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
      this.func_76986_a((EntityLiving)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
   }

   // $FF: synthetic method
   // $FF: bridge method
   protected boolean func_177070_b(Entity p_177070_1_) {
      return this.func_110813_b((EntityLiving)p_177070_1_);
   }

   // $FF: synthetic method
   // $FF: bridge method
   public void func_76986_a(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
      this.func_76986_a((EntityLiving)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
   }

   // $FF: synthetic method
   // $FF: bridge method
   public boolean func_177071_a(Entity p_177071_1_, ICamera p_177071_2_, double p_177071_3_, double p_177071_5_, double p_177071_7_) {
      return this.func_177104_a((EntityLiving)p_177071_1_, p_177071_2_, p_177071_3_, p_177071_5_, p_177071_7_);
   }
}
