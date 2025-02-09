package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderEnderman;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.util.ResourceLocation;

public class LayerEndermanEyes implements LayerRenderer {

   private static final ResourceLocation field_177203_a = new ResourceLocation("textures/entity/enderman/enderman_eyes.png");
   private final RenderEnderman field_177202_b;
   private static final String __OBFID = "CL_00002418";


   public LayerEndermanEyes(RenderEnderman p_i46117_1_) {
      this.field_177202_b = p_i46117_1_;
   }

   public void func_177201_a(EntityEnderman p_177201_1_, float p_177201_2_, float p_177201_3_, float p_177201_4_, float p_177201_5_, float p_177201_6_, float p_177201_7_, float p_177201_8_) {
      this.field_177202_b.func_110776_a(field_177203_a);
      GlStateManager.func_179147_l();
      GlStateManager.func_179118_c();
      GlStateManager.func_179112_b(1, 1);
      GlStateManager.func_179140_f();
      if(p_177201_1_.func_82150_aj()) {
         GlStateManager.func_179132_a(false);
      } else {
         GlStateManager.func_179132_a(true);
      }

      char var9 = '\uf0f0';
      int var10 = var9 % 65536;
      int var11 = var9 / 65536;
      OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, (float)var10 / 1.0F, (float)var11 / 1.0F);
      GlStateManager.func_179145_e();
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      this.field_177202_b.func_177087_b().func_78088_a(p_177201_1_, p_177201_2_, p_177201_3_, p_177201_5_, p_177201_6_, p_177201_7_, p_177201_8_);
      this.field_177202_b.func_177105_a(p_177201_1_, p_177201_4_);
      GlStateManager.func_179084_k();
      GlStateManager.func_179141_d();
   }

   public boolean func_177142_b() {
      return false;
   }

   // $FF: synthetic method
   // $FF: bridge method
   public void func_177141_a(EntityLivingBase p_177141_1_, float p_177141_2_, float p_177141_3_, float p_177141_4_, float p_177141_5_, float p_177141_6_, float p_177141_7_, float p_177141_8_) {
      this.func_177201_a((EntityEnderman)p_177141_1_, p_177141_2_, p_177141_3_, p_177141_4_, p_177141_5_, p_177141_6_, p_177141_7_, p_177141_8_);
   }

}
