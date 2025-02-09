package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerMooshroomMushroom;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.util.ResourceLocation;

public class RenderMooshroom extends RenderLiving {

   private static final ResourceLocation field_110880_a = new ResourceLocation("textures/entity/cow/mooshroom.png");
   private static final String __OBFID = "CL_00001016";


   public RenderMooshroom(RenderManager p_i46152_1_, ModelBase p_i46152_2_, float p_i46152_3_) {
      super(p_i46152_1_, p_i46152_2_, p_i46152_3_);
      this.func_177094_a(new LayerMooshroomMushroom(this));
   }

   protected ResourceLocation func_180582_a(EntityMooshroom p_180582_1_) {
      return field_110880_a;
   }

   // $FF: synthetic method
   // $FF: bridge method
   protected ResourceLocation func_110775_a(Entity p_110775_1_) {
      return this.func_180582_a((EntityMooshroom)p_110775_1_);
   }

}
