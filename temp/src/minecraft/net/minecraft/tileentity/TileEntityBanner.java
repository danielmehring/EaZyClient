package net.minecraft.tileentity;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.block.BlockFlower;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileEntityBanner extends TileEntity {

   private int field_175120_a;
   private NBTTagList field_175118_f;
   private boolean field_175119_g;
   private List field_175122_h;
   private List field_175123_i;
   private String field_175121_j;
   private static final String __OBFID = "CL_00002044";


   public void func_175112_a(ItemStack p_175112_1_) {
      this.field_175118_f = null;
      if(p_175112_1_.func_77942_o() && p_175112_1_.func_77978_p().func_150297_b("BlockEntityTag", 10)) {
         NBTTagCompound var2 = p_175112_1_.func_77978_p().func_74775_l("BlockEntityTag");
         if(var2.func_74764_b("Patterns")) {
            this.field_175118_f = (NBTTagList)var2.func_150295_c("Patterns", 10).func_74737_b();
         }

         if(var2.func_150297_b("Base", 99)) {
            this.field_175120_a = var2.func_74762_e("Base");
         } else {
            this.field_175120_a = p_175112_1_.func_77960_j() & 15;
         }
      } else {
         this.field_175120_a = p_175112_1_.func_77960_j() & 15;
      }

      this.field_175122_h = null;
      this.field_175123_i = null;
      this.field_175121_j = "";
      this.field_175119_g = true;
   }

   public void func_145841_b(NBTTagCompound p_145841_1_) {
      super.func_145841_b(p_145841_1_);
      p_145841_1_.func_74768_a("Base", this.field_175120_a);
      if(this.field_175118_f != null) {
         p_145841_1_.func_74782_a("Patterns", this.field_175118_f);
      }

   }

   public void func_145839_a(NBTTagCompound p_145839_1_) {
      super.func_145839_a(p_145839_1_);
      this.field_175120_a = p_145839_1_.func_74762_e("Base");
      this.field_175118_f = p_145839_1_.func_150295_c("Patterns", 10);
      this.field_175122_h = null;
      this.field_175123_i = null;
      this.field_175121_j = null;
      this.field_175119_g = true;
   }

   public Packet func_145844_m() {
      NBTTagCompound var1 = new NBTTagCompound();
      this.func_145841_b(var1);
      return new S35PacketUpdateTileEntity(this.field_174879_c, 6, var1);
   }

   public int func_175115_b() {
      return this.field_175120_a;
   }

   public static int func_175111_b(ItemStack p_175111_0_) {
      NBTTagCompound var1 = p_175111_0_.func_179543_a("BlockEntityTag", false);
      return var1 != null && var1.func_74764_b("Base")?var1.func_74762_e("Base"):p_175111_0_.func_77960_j();
   }

   public static int func_175113_c(ItemStack p_175113_0_) {
      NBTTagCompound var1 = p_175113_0_.func_179543_a("BlockEntityTag", false);
      return var1 != null && var1.func_74764_b("Patterns")?var1.func_150295_c("Patterns", 10).func_74745_c():0;
   }

   public List func_175114_c() {
      this.func_175109_g();
      return this.field_175122_h;
   }

   public List func_175110_d() {
      this.func_175109_g();
      return this.field_175123_i;
   }

   public String func_175116_e() {
      this.func_175109_g();
      return this.field_175121_j;
   }

   private void func_175109_g() {
      if(this.field_175122_h == null || this.field_175123_i == null || this.field_175121_j == null) {
         if(!this.field_175119_g) {
            this.field_175121_j = "";
         } else {
            this.field_175122_h = Lists.newArrayList();
            this.field_175123_i = Lists.newArrayList();
            this.field_175122_h.add(TileEntityBanner.EnumBannerPattern.BASE);
            this.field_175123_i.add(EnumDyeColor.func_176766_a(this.field_175120_a));
            this.field_175121_j = "b" + this.field_175120_a;
            if(this.field_175118_f != null) {
               for(int var1 = 0; var1 < this.field_175118_f.func_74745_c(); ++var1) {
                  NBTTagCompound var2 = this.field_175118_f.func_150305_b(var1);
                  TileEntityBanner.EnumBannerPattern var3 = TileEntityBanner.EnumBannerPattern.func_177268_a(var2.func_74779_i("Pattern"));
                  if(var3 != null) {
                     this.field_175122_h.add(var3);
                     int var4 = var2.func_74762_e("Color");
                     this.field_175123_i.add(EnumDyeColor.func_176766_a(var4));
                     this.field_175121_j = this.field_175121_j + var3.func_177273_b() + var4;
                  }
               }
            }

         }
      }
   }

   public static void func_175117_e(ItemStack p_175117_0_) {
      NBTTagCompound var1 = p_175117_0_.func_179543_a("BlockEntityTag", false);
      if(var1 != null && var1.func_150297_b("Patterns", 9)) {
         NBTTagList var2 = var1.func_150295_c("Patterns", 10);
         if(var2.func_74745_c() > 0) {
            var2.func_74744_a(var2.func_74745_c() - 1);
            if(var2.func_82582_d()) {
               p_175117_0_.func_77978_p().func_82580_o("BlockEntityTag");
               if(p_175117_0_.func_77978_p().func_82582_d()) {
                  p_175117_0_.func_77982_d((NBTTagCompound)null);
               }
            }

         }
      }
   }

   public static enum EnumBannerPattern {

      BASE("BASE", 0, "base", "b"),
      SQUARE_BOTTOM_LEFT("SQUARE_BOTTOM_LEFT", 1, "square_bottom_left", "bl", "   ", "   ", "#  "),
      SQUARE_BOTTOM_RIGHT("SQUARE_BOTTOM_RIGHT", 2, "square_bottom_right", "br", "   ", "   ", "  #"),
      SQUARE_TOP_LEFT("SQUARE_TOP_LEFT", 3, "square_top_left", "tl", "#  ", "   ", "   "),
      SQUARE_TOP_RIGHT("SQUARE_TOP_RIGHT", 4, "square_top_right", "tr", "  #", "   ", "   "),
      STRIPE_BOTTOM("STRIPE_BOTTOM", 5, "stripe_bottom", "bs", "   ", "   ", "###"),
      STRIPE_TOP("STRIPE_TOP", 6, "stripe_top", "ts", "###", "   ", "   "),
      STRIPE_LEFT("STRIPE_LEFT", 7, "stripe_left", "ls", "#  ", "#  ", "#  "),
      STRIPE_RIGHT("STRIPE_RIGHT", 8, "stripe_right", "rs", "  #", "  #", "  #"),
      STRIPE_CENTER("STRIPE_CENTER", 9, "stripe_center", "cs", " # ", " # ", " # "),
      STRIPE_MIDDLE("STRIPE_MIDDLE", 10, "stripe_middle", "ms", "   ", "###", "   "),
      STRIPE_DOWNRIGHT("STRIPE_DOWNRIGHT", 11, "stripe_downright", "drs", "#  ", " # ", "  #"),
      STRIPE_DOWNLEFT("STRIPE_DOWNLEFT", 12, "stripe_downleft", "dls", "  #", " # ", "#  "),
      STRIPE_SMALL("STRIPE_SMALL", 13, "small_stripes", "ss", "# #", "# #", "   "),
      CROSS("CROSS", 14, "cross", "cr", "# #", " # ", "# #"),
      STRAIGHT_CROSS("STRAIGHT_CROSS", 15, "straight_cross", "sc", " # ", "###", " # "),
      TRIANGLE_BOTTOM("TRIANGLE_BOTTOM", 16, "triangle_bottom", "bt", "   ", " # ", "# #"),
      TRIANGLE_TOP("TRIANGLE_TOP", 17, "triangle_top", "tt", "# #", " # ", "   "),
      TRIANGLES_BOTTOM("TRIANGLES_BOTTOM", 18, "triangles_bottom", "bts", "   ", "# #", " # "),
      TRIANGLES_TOP("TRIANGLES_TOP", 19, "triangles_top", "tts", " # ", "# #", "   "),
      DIAGONAL_LEFT("DIAGONAL_LEFT", 20, "diagonal_left", "ld", "## ", "#  ", "   "),
      DIAGONAL_RIGHT("DIAGONAL_RIGHT", 21, "diagonal_up_right", "rd", "   ", "  #", " ##"),
      DIAGONAL_LEFT_MIRROR("DIAGONAL_LEFT_MIRROR", 22, "diagonal_up_left", "lud", "   ", "#  ", "## "),
      DIAGONAL_RIGHT_MIRROR("DIAGONAL_RIGHT_MIRROR", 23, "diagonal_right", "rud", " ##", "  #", "   "),
      CIRCLE_MIDDLE("CIRCLE_MIDDLE", 24, "circle", "mc", "   ", " # ", "   "),
      RHOMBUS_MIDDLE("RHOMBUS_MIDDLE", 25, "rhombus", "mr", " # ", "# #", " # "),
      HALF_VERTICAL("HALF_VERTICAL", 26, "half_vertical", "vh", "## ", "## ", "## "),
      HALF_HORIZONTAL("HALF_HORIZONTAL", 27, "half_horizontal", "hh", "###", "###", "   "),
      HALF_VERTICAL_MIRROR("HALF_VERTICAL_MIRROR", 28, "half_vertical_right", "vhr", " ##", " ##", " ##"),
      HALF_HORIZONTAL_MIRROR("HALF_HORIZONTAL_MIRROR", 29, "half_horizontal_bottom", "hhb", "   ", "###", "###"),
      BORDER("BORDER", 30, "border", "bo", "###", "# #", "###"),
      CURLY_BORDER("CURLY_BORDER", 31, "curly_border", "cbo", new ItemStack(Blocks.field_150395_bd)),
      CREEPER("CREEPER", 32, "creeper", "cre", new ItemStack(Items.field_151144_bL, 1, 4)),
      GRADIENT("GRADIENT", 33, "gradient", "gra", "# #", " # ", " # "),
      GRADIENT_UP("GRADIENT_UP", 34, "gradient_up", "gru", " # ", " # ", "# #"),
      BRICKS("BRICKS", 35, "bricks", "bri", new ItemStack(Blocks.field_150336_V)),
      SKULL("SKULL", 36, "skull", "sku", new ItemStack(Items.field_151144_bL, 1, 1)),
      FLOWER("FLOWER", 37, "flower", "flo", new ItemStack(Blocks.field_150328_O, 1, BlockFlower.EnumFlowerType.OXEYE_DAISY.func_176968_b())),
      MOJANG("MOJANG", 38, "mojang", "moj", new ItemStack(Items.field_151153_ao, 1, 1));
      private String field_177284_N;
      private String field_177285_O;
      private String[] field_177291_P;
      private ItemStack field_177290_Q;
      // $FF: synthetic field
      private static final TileEntityBanner.EnumBannerPattern[] $VALUES = new TileEntityBanner.EnumBannerPattern[]{BASE, SQUARE_BOTTOM_LEFT, SQUARE_BOTTOM_RIGHT, SQUARE_TOP_LEFT, SQUARE_TOP_RIGHT, STRIPE_BOTTOM, STRIPE_TOP, STRIPE_LEFT, STRIPE_RIGHT, STRIPE_CENTER, STRIPE_MIDDLE, STRIPE_DOWNRIGHT, STRIPE_DOWNLEFT, STRIPE_SMALL, CROSS, STRAIGHT_CROSS, TRIANGLE_BOTTOM, TRIANGLE_TOP, TRIANGLES_BOTTOM, TRIANGLES_TOP, DIAGONAL_LEFT, DIAGONAL_RIGHT, DIAGONAL_LEFT_MIRROR, DIAGONAL_RIGHT_MIRROR, CIRCLE_MIDDLE, RHOMBUS_MIDDLE, HALF_VERTICAL, HALF_HORIZONTAL, HALF_VERTICAL_MIRROR, HALF_HORIZONTAL_MIRROR, BORDER, CURLY_BORDER, CREEPER, GRADIENT, GRADIENT_UP, BRICKS, SKULL, FLOWER, MOJANG};
      private static final String __OBFID = "CL_00002043";


      private EnumBannerPattern(String p_i45670_1_, int p_i45670_2_, String p_i45670_3_, String p_i45670_4_) {
         this.field_177291_P = new String[3];
         this.field_177284_N = p_i45670_3_;
         this.field_177285_O = p_i45670_4_;
      }

      private EnumBannerPattern(String p_i45671_1_, int p_i45671_2_, String p_i45671_3_, String p_i45671_4_, ItemStack p_i45671_5_) {
         this(p_i45671_1_, p_i45671_2_, p_i45671_3_, p_i45671_4_);
         this.field_177290_Q = p_i45671_5_;
      }

      private EnumBannerPattern(String p_i45672_1_, int p_i45672_2_, String p_i45672_3_, String p_i45672_4_, String p_i45672_5_, String p_i45672_6_, String p_i45672_7_) {
         this(p_i45672_1_, p_i45672_2_, p_i45672_3_, p_i45672_4_);
         this.field_177291_P[0] = p_i45672_5_;
         this.field_177291_P[1] = p_i45672_6_;
         this.field_177291_P[2] = p_i45672_7_;
      }

      public String func_177271_a() {
         return this.field_177284_N;
      }

      public String func_177273_b() {
         return this.field_177285_O;
      }

      public String[] func_177267_c() {
         return this.field_177291_P;
      }

      public boolean func_177270_d() {
         return this.field_177290_Q != null || this.field_177291_P[0] != null;
      }

      public boolean func_177269_e() {
         return this.field_177290_Q != null;
      }

      public ItemStack func_177272_f() {
         return this.field_177290_Q;
      }

      public static TileEntityBanner.EnumBannerPattern func_177268_a(String p_177268_0_) {
         TileEntityBanner.EnumBannerPattern[] var1 = values();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            TileEntityBanner.EnumBannerPattern var4 = var1[var3];
            if(var4.field_177285_O.equals(p_177268_0_)) {
               return var4;
            }
         }

         return null;
      }

   }
}
