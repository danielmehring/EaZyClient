package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelSlime;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderSlime;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySlime;

public class LayerSlimeGel implements LayerRenderer {

   private final RenderSlime field_177161_a;
   private final ModelBase field_177160_b = new ModelSlime(0);
   private static final String __OBFID = "CL_00002412";


   public LayerSlimeGel(RenderSlime p_i46111_1_) {
      this.field_177161_a = p_i46111_1_;
   }

   public void func_177159_a(EntitySlime p_177159_1_, float p_177159_2_, float p_177159_3_, float p_177159_4_, float p_177159_5_, float p_177159_6_, float p_177159_7_, float p_177159_8_) {
      if(!p_177159_1_.func_82150_aj()) {
         GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
         GlStateManager.func_179108_z();
         GlStateManager.func_179147_l();
         GlStateManager.func_179112_b(770, 771);
         this.field_177160_b.func_178686_a(this.field_177161_a.func_177087_b());
         this.field_177160_b.func_78088_a(p_177159_1_, p_177159_2_, p_177159_3_, p_177159_5_, p_177159_6_, p_177159_7_, p_177159_8_);
         GlStateManager.func_179084_k();
         GlStateManager.func_179133_A();
      }
   }

   public boolean func_177142_b() {
      return true;
   }

   // $FF: synthetic method
   // $FF: bridge method
   public void func_177141_a(EntityLivingBase p_177141_1_, float p_177141_2_, float p_177141_3_, float p_177141_4_, float p_177141_5_, float p_177141_6_, float p_177141_7_, float p_177141_8_) {
      this.func_177159_a((EntitySlime)p_177141_1_, p_177141_2_, p_177141_3_, p_177141_4_, p_177141_5_, p_177141_6_, p_177141_7_, p_177141_8_);
   }
}
