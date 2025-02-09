package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.realms.RealmsButton;

public class GuiButtonRealmsProxy extends GuiButton {

   private RealmsButton field_154318_o;
   private static final String __OBFID = "CL_00001848";


   public GuiButtonRealmsProxy(RealmsButton p_i46321_1_, int p_i46321_2_, int p_i46321_3_, int p_i46321_4_, String p_i46321_5_) {
      super(p_i46321_2_, p_i46321_3_, p_i46321_4_, p_i46321_5_);
      this.field_154318_o = p_i46321_1_;
   }

   public GuiButtonRealmsProxy(RealmsButton p_i1090_1_, int p_i1090_2_, int p_i1090_3_, int p_i1090_4_, String p_i1090_5_, int p_i1090_6_, int p_i1090_7_) {
      super(p_i1090_2_, p_i1090_3_, p_i1090_4_, p_i1090_6_, p_i1090_7_, p_i1090_5_);
      this.field_154318_o = p_i1090_1_;
   }

   public int func_154314_d() {
      return super.field_146127_k;
   }

   public boolean func_154315_e() {
      return super.field_146124_l;
   }

   public void func_154313_b(boolean p_154313_1_) {
      super.field_146124_l = p_154313_1_;
   }

   public void func_154311_a(String p_154311_1_) {
      super.field_146126_j = p_154311_1_;
   }

   public int func_146117_b() {
      return super.func_146117_b();
   }

   public int func_154316_f() {
      return super.field_146129_i;
   }

   public boolean func_146116_c(Minecraft p_146116_1_, int p_146116_2_, int p_146116_3_) {
      if(super.func_146116_c(p_146116_1_, p_146116_2_, p_146116_3_)) {
         this.field_154318_o.clicked(p_146116_2_, p_146116_3_);
      }

      return super.func_146116_c(p_146116_1_, p_146116_2_, p_146116_3_);
   }

   public void func_146118_a(int p_146118_1_, int p_146118_2_) {
      this.field_154318_o.released(p_146118_1_, p_146118_2_);
   }

   public void func_146119_b(Minecraft p_146119_1_, int p_146119_2_, int p_146119_3_) {
      this.field_154318_o.renderBg(p_146119_2_, p_146119_3_);
   }

   public RealmsButton func_154317_g() {
      return this.field_154318_o;
   }

   public int func_146114_a(boolean p_146114_1_) {
      return this.field_154318_o.getYImage(p_146114_1_);
   }

   public int func_154312_c(boolean p_154312_1_) {
      return super.func_146114_a(p_154312_1_);
   }

   public int func_175232_g() {
      return this.field_146121_g;
   }
}
