package net.minecraft.client.gui;

import java.io.IOException;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.play.client.C0BPacketEntityAction;

public class GuiSleepMP extends GuiChat {

   private static final String __OBFID = "CL_00000697";


   public void func_73866_w_() {
      super.func_73866_w_();
      this.field_146292_n.add(new GuiButton(1, this.field_146294_l / 2 - 100, this.field_146295_m - 40, I18n.func_135052_a("multiplayer.stopSleeping", new Object[0])));
   }

   protected void func_73869_a(char p_73869_1_, int p_73869_2_) throws IOException {
      if(p_73869_2_ == 1) {
         this.func_146418_g();
      } else if(p_73869_2_ != 28 && p_73869_2_ != 156) {
         super.func_73869_a(p_73869_1_, p_73869_2_);
      } else {
         String var3 = this.field_146415_a.func_146179_b().trim();
         if(!var3.isEmpty()) {
            this.field_146297_k.field_71439_g.func_71165_d(var3);
         }

         this.field_146415_a.func_146180_a("");
         this.field_146297_k.field_71456_v.func_146158_b().func_146240_d();
      }

   }

   protected void func_146284_a(GuiButton p_146284_1_) throws IOException {
      if(p_146284_1_.field_146127_k == 1) {
         this.func_146418_g();
      } else {
         super.func_146284_a(p_146284_1_);
      }

   }

   private void func_146418_g() {
      NetHandlerPlayClient var1 = this.field_146297_k.field_71439_g.field_71174_a;
      var1.func_147297_a(new C0BPacketEntityAction(this.field_146297_k.field_71439_g, C0BPacketEntityAction.Action.STOP_SLEEPING));
   }
}
