package net.minecraft.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.util.ResourceLocation;

public class EnchantmentFishingSpeed extends Enchantment {

   private static final String __OBFID = "CL_00000117";


   protected EnchantmentFishingSpeed(int p_i45769_1_, ResourceLocation p_i45769_2_, int p_i45769_3_, EnumEnchantmentType p_i45769_4_) {
      super(p_i45769_1_, p_i45769_2_, p_i45769_3_, p_i45769_4_);
      this.func_77322_b("fishingSpeed");
   }

   public int func_77321_a(int p_77321_1_) {
      return 15 + (p_77321_1_ - 1) * 9;
   }

   public int func_77317_b(int p_77317_1_) {
      return super.func_77321_a(p_77317_1_) + 50;
   }

   public int func_77325_b() {
      return 3;
   }
}
