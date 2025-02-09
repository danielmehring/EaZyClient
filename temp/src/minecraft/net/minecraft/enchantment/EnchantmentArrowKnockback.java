package net.minecraft.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.util.ResourceLocation;

public class EnchantmentArrowKnockback extends Enchantment {

   private static final String __OBFID = "CL_00000101";


   public EnchantmentArrowKnockback(int p_i45775_1_, ResourceLocation p_i45775_2_, int p_i45775_3_) {
      super(p_i45775_1_, p_i45775_2_, p_i45775_3_, EnumEnchantmentType.BOW);
      this.func_77322_b("arrowKnockback");
   }

   public int func_77321_a(int p_77321_1_) {
      return 12 + (p_77321_1_ - 1) * 20;
   }

   public int func_77317_b(int p_77317_1_) {
      return this.func_77321_a(p_77317_1_) + 25;
   }

   public int func_77325_b() {
      return 2;
   }
}
