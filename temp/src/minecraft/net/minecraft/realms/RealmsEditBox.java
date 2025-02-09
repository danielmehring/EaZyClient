package net.minecraft.realms;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiTextField;

public class RealmsEditBox {

   private final GuiTextField editBox;
   private static final String __OBFID = "CL_00001858";


   public RealmsEditBox(int p_i45743_1_, int p_i45743_2_, int p_i45743_3_, int p_i45743_4_, int p_i45743_5_) {
      this.editBox = new GuiTextField(p_i45743_1_, Minecraft.func_71410_x().field_71466_p, p_i45743_2_, p_i45743_3_, p_i45743_4_, p_i45743_5_);
   }

   public String getValue() {
      return this.editBox.func_146179_b();
   }

   public void tick() {
      this.editBox.func_146178_a();
   }

   public void setFocus(boolean p_setFocus_1_) {
      this.editBox.func_146195_b(p_setFocus_1_);
   }

   public void setValue(String p_setValue_1_) {
      this.editBox.func_146180_a(p_setValue_1_);
   }

   public void keyPressed(char p_keyPressed_1_, int p_keyPressed_2_) {
      this.editBox.func_146201_a(p_keyPressed_1_, p_keyPressed_2_);
   }

   public boolean isFocused() {
      return this.editBox.func_146206_l();
   }

   public void mouseClicked(int p_mouseClicked_1_, int p_mouseClicked_2_, int p_mouseClicked_3_) {
      this.editBox.func_146192_a(p_mouseClicked_1_, p_mouseClicked_2_, p_mouseClicked_3_);
   }

   public void render() {
      this.editBox.func_146194_f();
   }

   public void setMaxLength(int p_setMaxLength_1_) {
      this.editBox.func_146203_f(p_setMaxLength_1_);
   }

   public void setIsEditable(boolean p_setIsEditable_1_) {
      this.editBox.func_146184_c(p_setIsEditable_1_);
   }
}
