package net.minecraft.client.renderer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import net.minecraft.client.util.QuadComparator;
import net.minecraft.util.MathHelper;
import org.apache.logging.log4j.LogManager;

public class WorldRenderer {

   private ByteBuffer field_179001_a;
   private IntBuffer field_178999_b;
   private FloatBuffer field_179000_c;
   private int field_178997_d;
   private double field_178998_e;
   private double field_178995_f;
   private int field_178996_g;
   private int field_179007_h;
   private int field_179008_i;
   private boolean field_78939_q;
   private int field_179006_k;
   private double field_179004_l;
   private double field_179005_m;
   private double field_179002_n;
   private int field_179003_o;
   private int field_179012_p;
   private VertexFormat field_179011_q;
   private boolean field_179010_r;
   private int field_179009_s;
   private static final String __OBFID = "CL_00000942";


   public WorldRenderer(int p_i46275_1_) {
      this.field_179009_s = p_i46275_1_;
      this.field_179001_a = GLAllocation.func_74524_c(p_i46275_1_ * 4);
      this.field_178999_b = this.field_179001_a.asIntBuffer();
      this.field_179000_c = this.field_179001_a.asFloatBuffer();
      this.field_179011_q = new VertexFormat();
      this.field_179011_q.func_177349_a(new VertexFormatElement(0, VertexFormatElement.EnumType.FLOAT, VertexFormatElement.EnumUseage.POSITION, 3));
   }

   private void func_178983_e(int p_178983_1_) {
      LogManager.getLogger().warn("Needed to grow BufferBuilder buffer: Old size " + this.field_179009_s * 4 + " bytes, new size " + (this.field_179009_s * 4 + p_178983_1_) + " bytes.");
      this.field_179009_s += p_178983_1_ / 4;
      ByteBuffer var2 = GLAllocation.func_74524_c(this.field_179009_s * 4);
      this.field_178999_b.position(0);
      var2.asIntBuffer().put(this.field_178999_b);
      this.field_179001_a = var2;
      this.field_178999_b = this.field_179001_a.asIntBuffer();
      this.field_179000_c = this.field_179001_a.asFloatBuffer();
   }

   public WorldRenderer.State func_178971_a(float p_178971_1_, float p_178971_2_, float p_178971_3_) {
      int[] var4 = new int[this.field_179008_i];
      PriorityQueue var5 = new PriorityQueue(this.field_179008_i, new QuadComparator(this.field_179000_c, (float)((double)p_178971_1_ + this.field_179004_l), (float)((double)p_178971_2_ + this.field_179005_m), (float)((double)p_178971_3_ + this.field_179002_n), this.field_179011_q.func_177338_f() / 4));
      int var6 = this.field_179011_q.func_177338_f();

      int var7;
      for(var7 = 0; var7 < this.field_179008_i; var7 += var6) {
         var5.add(Integer.valueOf(var7));
      }

      for(var7 = 0; !var5.isEmpty(); var7 += var6) {
         int var8 = ((Integer)var5.remove()).intValue();

         for(int var9 = 0; var9 < var6; ++var9) {
            var4[var7 + var9] = this.field_178999_b.get(var8 + var9);
         }
      }

      this.field_178999_b.clear();
      this.field_178999_b.put(var4);
      return new WorldRenderer.State(var4, this.field_179008_i, this.field_178997_d, new VertexFormat(this.field_179011_q));
   }

   public void func_178993_a(WorldRenderer.State p_178993_1_) {
      this.field_178999_b.clear();
      this.field_178999_b.put(p_178993_1_.func_179013_a());
      this.field_179008_i = p_178993_1_.func_179015_b();
      this.field_178997_d = p_178993_1_.func_179014_c();
      this.field_179011_q = new VertexFormat(p_178993_1_.func_179016_d());
   }

   public void func_178965_a() {
      this.field_178997_d = 0;
      this.field_179008_i = 0;
      this.field_179011_q.func_177339_a();
      this.field_179011_q.func_177349_a(new VertexFormatElement(0, VertexFormatElement.EnumType.FLOAT, VertexFormatElement.EnumUseage.POSITION, 3));
   }

   public void func_178970_b() {
      this.func_178964_a(7);
   }

   public void func_178964_a(int p_178964_1_) {
      if(this.field_179010_r) {
         throw new IllegalStateException("Already building!");
      } else {
         this.field_179010_r = true;
         this.func_178965_a();
         this.field_179006_k = p_178964_1_;
         this.field_78939_q = false;
      }
   }

   public void func_178992_a(double p_178992_1_, double p_178992_3_) {
      if(!this.field_179011_q.func_177347_a(0) && !this.field_179011_q.func_177347_a(1)) {
         VertexFormatElement var5 = new VertexFormatElement(0, VertexFormatElement.EnumType.FLOAT, VertexFormatElement.EnumUseage.UV, 2);
         this.field_179011_q.func_177349_a(var5);
      }

      this.field_178998_e = p_178992_1_;
      this.field_178995_f = p_178992_3_;
   }

   public void func_178963_b(int p_178963_1_) {
      if(!this.field_179011_q.func_177347_a(1)) {
         if(!this.field_179011_q.func_177347_a(0)) {
            this.field_179011_q.func_177349_a(new VertexFormatElement(0, VertexFormatElement.EnumType.FLOAT, VertexFormatElement.EnumUseage.UV, 2));
         }

         VertexFormatElement var2 = new VertexFormatElement(1, VertexFormatElement.EnumType.SHORT, VertexFormatElement.EnumUseage.UV, 2);
         this.field_179011_q.func_177349_a(var2);
      }

      this.field_178996_g = p_178963_1_;
   }

   public void func_178986_b(float p_178986_1_, float p_178986_2_, float p_178986_3_) {
      this.func_78913_a((int)(p_178986_1_ * 255.0F), (int)(p_178986_2_ * 255.0F), (int)(p_178986_3_ * 255.0F));
   }

   public void func_178960_a(float p_178960_1_, float p_178960_2_, float p_178960_3_, float p_178960_4_) {
      this.func_178961_b((int)(p_178960_1_ * 255.0F), (int)(p_178960_2_ * 255.0F), (int)(p_178960_3_ * 255.0F), (int)(p_178960_4_ * 255.0F));
   }

   public void func_78913_a(int p_78913_1_, int p_78913_2_, int p_78913_3_) {
      this.func_178961_b(p_78913_1_, p_78913_2_, p_78913_3_, 255);
   }

   public void func_178962_a(int p_178962_1_, int p_178962_2_, int p_178962_3_, int p_178962_4_) {
      int var5 = (this.field_178997_d - 4) * (this.field_179011_q.func_177338_f() / 4) + this.field_179011_q.func_177344_b(1) / 4;
      int var6 = this.field_179011_q.func_177338_f() >> 2;
      this.field_178999_b.put(var5, p_178962_1_);
      this.field_178999_b.put(var5 + var6, p_178962_2_);
      this.field_178999_b.put(var5 + var6 * 2, p_178962_3_);
      this.field_178999_b.put(var5 + var6 * 3, p_178962_4_);
   }

   public void func_178987_a(double p_178987_1_, double p_178987_3_, double p_178987_5_) {
      if(this.field_179008_i >= this.field_179009_s - this.field_179011_q.func_177338_f()) {
         this.func_178983_e(2097152);
      }

      int var7 = this.field_179011_q.func_177338_f() / 4;
      int var8 = (this.field_178997_d - 4) * var7;

      for(int var9 = 0; var9 < 4; ++var9) {
         int var10 = var8 + var9 * var7;
         int var11 = var10 + 1;
         int var12 = var11 + 1;
         this.field_178999_b.put(var10, Float.floatToRawIntBits((float)(p_178987_1_ + this.field_179004_l) + Float.intBitsToFloat(this.field_178999_b.get(var10))));
         this.field_178999_b.put(var11, Float.floatToRawIntBits((float)(p_178987_3_ + this.field_179005_m) + Float.intBitsToFloat(this.field_178999_b.get(var11))));
         this.field_178999_b.put(var12, Float.floatToRawIntBits((float)(p_178987_5_ + this.field_179002_n) + Float.intBitsToFloat(this.field_178999_b.get(var12))));
      }

   }

   private int func_78909_a(int p_78909_1_) {
      return ((this.field_178997_d - p_78909_1_) * this.field_179011_q.func_177338_f() + this.field_179011_q.func_177340_e()) / 4;
   }

   public void func_178978_a(float p_178978_1_, float p_178978_2_, float p_178978_3_, int p_178978_4_) {
      int var5 = this.func_78909_a(p_178978_4_);
      int var6 = this.field_178999_b.get(var5);
      int var7;
      int var8;
      int var9;
      if(ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
         var7 = (int)((float)(var6 & 255) * p_178978_1_);
         var8 = (int)((float)(var6 >> 8 & 255) * p_178978_2_);
         var9 = (int)((float)(var6 >> 16 & 255) * p_178978_3_);
         var6 &= -16777216;
         var6 |= var9 << 16 | var8 << 8 | var7;
      } else {
         var7 = (int)((float)(this.field_179007_h >> 24 & 255) * p_178978_1_);
         var8 = (int)((float)(this.field_179007_h >> 16 & 255) * p_178978_2_);
         var9 = (int)((float)(this.field_179007_h >> 8 & 255) * p_178978_3_);
         var6 &= 255;
         var6 |= var7 << 24 | var8 << 16 | var9 << 8;
      }

      if(this.field_78939_q) {
         var6 = -1;
      }

      this.field_178999_b.put(var5, var6);
   }

   private void func_178988_b(int p_178988_1_, int p_178988_2_) {
      int var3 = this.func_78909_a(p_178988_2_);
      int var4 = p_178988_1_ >> 16 & 255;
      int var5 = p_178988_1_ >> 8 & 255;
      int var6 = p_178988_1_ & 255;
      int var7 = p_178988_1_ >> 24 & 255;
      this.func_178972_a(var3, var4, var5, var6, var7);
   }

   public void func_178994_b(float p_178994_1_, float p_178994_2_, float p_178994_3_, int p_178994_4_) {
      int var5 = this.func_78909_a(p_178994_4_);
      int var6 = MathHelper.func_76125_a((int)(p_178994_1_ * 255.0F), 0, 255);
      int var7 = MathHelper.func_76125_a((int)(p_178994_2_ * 255.0F), 0, 255);
      int var8 = MathHelper.func_76125_a((int)(p_178994_3_ * 255.0F), 0, 255);
      this.func_178972_a(var5, var6, var7, var8, 255);
   }

   private void func_178972_a(int p_178972_1_, int p_178972_2_, int p_178972_3_, int p_178972_4_, int p_178972_5_) {
      if(ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
         this.field_178999_b.put(p_178972_1_, p_178972_5_ << 24 | p_178972_4_ << 16 | p_178972_3_ << 8 | p_178972_2_);
      } else {
         this.field_178999_b.put(p_178972_1_, p_178972_2_ << 24 | p_178972_3_ << 16 | p_178972_4_ << 8 | p_178972_5_);
      }

   }

   public void func_178961_b(int p_178961_1_, int p_178961_2_, int p_178961_3_, int p_178961_4_) {
      if(!this.field_78939_q) {
         if(p_178961_1_ > 255) {
            p_178961_1_ = 255;
         }

         if(p_178961_2_ > 255) {
            p_178961_2_ = 255;
         }

         if(p_178961_3_ > 255) {
            p_178961_3_ = 255;
         }

         if(p_178961_4_ > 255) {
            p_178961_4_ = 255;
         }

         if(p_178961_1_ < 0) {
            p_178961_1_ = 0;
         }

         if(p_178961_2_ < 0) {
            p_178961_2_ = 0;
         }

         if(p_178961_3_ < 0) {
            p_178961_3_ = 0;
         }

         if(p_178961_4_ < 0) {
            p_178961_4_ = 0;
         }

         if(!this.field_179011_q.func_177346_d()) {
            VertexFormatElement var5 = new VertexFormatElement(0, VertexFormatElement.EnumType.UBYTE, VertexFormatElement.EnumUseage.COLOR, 4);
            this.field_179011_q.func_177349_a(var5);
         }

         if(ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
            this.field_179007_h = p_178961_4_ << 24 | p_178961_3_ << 16 | p_178961_2_ << 8 | p_178961_1_;
         } else {
            this.field_179007_h = p_178961_1_ << 24 | p_178961_2_ << 16 | p_178961_3_ << 8 | p_178961_4_;
         }

      }
   }

   public void func_178982_a(byte p_178982_1_, byte p_178982_2_, byte p_178982_3_) {
      this.func_78913_a(p_178982_1_ & 255, p_178982_2_ & 255, p_178982_3_ & 255);
   }

   public void func_178985_a(double p_178985_1_, double p_178985_3_, double p_178985_5_, double p_178985_7_, double p_178985_9_) {
      this.func_178992_a(p_178985_7_, p_178985_9_);
      this.func_178984_b(p_178985_1_, p_178985_3_, p_178985_5_);
   }

   public void func_178967_a(VertexFormat p_178967_1_) {
      this.field_179011_q = new VertexFormat(p_178967_1_);
   }

   public void func_178981_a(int[] p_178981_1_) {
      int var2 = this.field_179011_q.func_177338_f() / 4;
      this.field_178997_d += p_178981_1_.length / var2;
      this.field_178999_b.position(this.field_179008_i);
      this.field_178999_b.put(p_178981_1_);
      this.field_179008_i += p_178981_1_.length;
   }

   public void func_178984_b(double p_178984_1_, double p_178984_3_, double p_178984_5_) {
      if(this.field_179008_i >= this.field_179009_s - this.field_179011_q.func_177338_f()) {
         this.func_178983_e(2097152);
      }

      List var7 = this.field_179011_q.func_177343_g();
      Iterator var8 = var7.iterator();

      while(var8.hasNext()) {
         VertexFormatElement var9 = (VertexFormatElement)var8.next();
         int var10 = var9.func_177373_a() >> 2;
         int var11 = this.field_179008_i + var10;
         switch(WorldRenderer.SwitchEnumUseage.field_178959_a[var9.func_177375_c().ordinal()]) {
         case 1:
            this.field_178999_b.put(var11, Float.floatToRawIntBits((float)(p_178984_1_ + this.field_179004_l)));
            this.field_178999_b.put(var11 + 1, Float.floatToRawIntBits((float)(p_178984_3_ + this.field_179005_m)));
            this.field_178999_b.put(var11 + 2, Float.floatToRawIntBits((float)(p_178984_5_ + this.field_179002_n)));
            break;
         case 2:
            this.field_178999_b.put(var11, this.field_179007_h);
            break;
         case 3:
            if(var9.func_177369_e() == 0) {
               this.field_178999_b.put(var11, Float.floatToRawIntBits((float)this.field_178998_e));
               this.field_178999_b.put(var11 + 1, Float.floatToRawIntBits((float)this.field_178995_f));
            } else {
               this.field_178999_b.put(var11, this.field_178996_g);
            }
            break;
         case 4:
            this.field_178999_b.put(var11, this.field_179003_o);
         }
      }

      this.field_179008_i += this.field_179011_q.func_177338_f() >> 2;
      ++this.field_178997_d;
   }

   public void func_178991_c(int p_178991_1_) {
      int var2 = p_178991_1_ >> 16 & 255;
      int var3 = p_178991_1_ >> 8 & 255;
      int var4 = p_178991_1_ & 255;
      this.func_78913_a(var2, var3, var4);
   }

   public void func_178974_a(int p_178974_1_, int p_178974_2_) {
      int var3 = p_178974_1_ >> 16 & 255;
      int var4 = p_178974_1_ >> 8 & 255;
      int var5 = p_178974_1_ & 255;
      this.func_178961_b(var3, var4, var5, p_178974_2_);
   }

   public void func_78914_f() {
      this.field_78939_q = true;
   }

   public void func_178980_d(float p_178980_1_, float p_178980_2_, float p_178980_3_) {
      if(!this.field_179011_q.func_177350_b()) {
         VertexFormatElement var4 = new VertexFormatElement(0, VertexFormatElement.EnumType.BYTE, VertexFormatElement.EnumUseage.NORMAL, 3);
         this.field_179011_q.func_177349_a(var4);
         this.field_179011_q.func_177349_a(new VertexFormatElement(0, VertexFormatElement.EnumType.UBYTE, VertexFormatElement.EnumUseage.PADDING, 1));
      }

      byte var7 = (byte)((int)(p_178980_1_ * 127.0F));
      byte var5 = (byte)((int)(p_178980_2_ * 127.0F));
      byte var6 = (byte)((int)(p_178980_3_ * 127.0F));
      this.field_179003_o = var7 & 255 | (var5 & 255) << 8 | (var6 & 255) << 16;
   }

   public void func_178975_e(float p_178975_1_, float p_178975_2_, float p_178975_3_) {
      byte var4 = (byte)((int)(p_178975_1_ * 127.0F));
      byte var5 = (byte)((int)(p_178975_2_ * 127.0F));
      byte var6 = (byte)((int)(p_178975_3_ * 127.0F));
      int var7 = this.field_179011_q.func_177338_f() >> 2;
      int var8 = (this.field_178997_d - 4) * var7 + this.field_179011_q.func_177342_c() / 4;
      this.field_179003_o = var4 & 255 | (var5 & 255) << 8 | (var6 & 255) << 16;
      this.field_178999_b.put(var8, this.field_179003_o);
      this.field_178999_b.put(var8 + var7, this.field_179003_o);
      this.field_178999_b.put(var8 + var7 * 2, this.field_179003_o);
      this.field_178999_b.put(var8 + var7 * 3, this.field_179003_o);
   }

   public void func_178969_c(double p_178969_1_, double p_178969_3_, double p_178969_5_) {
      this.field_179004_l = p_178969_1_;
      this.field_179005_m = p_178969_3_;
      this.field_179002_n = p_178969_5_;
   }

   public int func_178977_d() {
      if(!this.field_179010_r) {
         throw new IllegalStateException("Not building!");
      } else {
         this.field_179010_r = false;
         if(this.field_178997_d > 0) {
            this.field_179001_a.position(0);
            this.field_179001_a.limit(this.field_179008_i * 4);
         }

         this.field_179012_p = this.field_179008_i * 4;
         return this.field_179012_p;
      }
   }

   public int func_178976_e() {
      return this.field_179012_p;
   }

   public ByteBuffer func_178966_f() {
      return this.field_179001_a;
   }

   public VertexFormat func_178973_g() {
      return this.field_179011_q;
   }

   public int func_178989_h() {
      return this.field_178997_d;
   }

   public int func_178979_i() {
      return this.field_179006_k;
   }

   public void func_178968_d(int p_178968_1_) {
      for(int var2 = 0; var2 < 4; ++var2) {
         this.func_178988_b(p_178968_1_, var2 + 1);
      }

   }

   public void func_178990_f(float p_178990_1_, float p_178990_2_, float p_178990_3_) {
      for(int var4 = 0; var4 < 4; ++var4) {
         this.func_178994_b(p_178990_1_, p_178990_2_, p_178990_3_, var4 + 1);
      }

   }

   public class State {

      private final int[] field_179019_b;
      private final int field_179020_c;
      private final int field_179017_d;
      private final VertexFormat field_179018_e;
      private static final String __OBFID = "CL_00002568";


      public State(int[] p_i46274_2_, int p_i46274_3_, int p_i46274_4_, VertexFormat p_i46274_5_) {
         this.field_179019_b = p_i46274_2_;
         this.field_179020_c = p_i46274_3_;
         this.field_179017_d = p_i46274_4_;
         this.field_179018_e = p_i46274_5_;
      }

      public int[] func_179013_a() {
         return this.field_179019_b;
      }

      public int func_179015_b() {
         return this.field_179020_c;
      }

      public int func_179014_c() {
         return this.field_179017_d;
      }

      public VertexFormat func_179016_d() {
         return this.field_179018_e;
      }
   }

   // $FF: synthetic class
   static final class SwitchEnumUseage {

      // $FF: synthetic field
      static final int[] field_178959_a = new int[VertexFormatElement.EnumUseage.values().length];
      private static final String __OBFID = "CL_00002569";


      static {
         try {
            field_178959_a[VertexFormatElement.EnumUseage.POSITION.ordinal()] = 1;
         } catch (NoSuchFieldError var4) {
            ;
         }

         try {
            field_178959_a[VertexFormatElement.EnumUseage.COLOR.ordinal()] = 2;
         } catch (NoSuchFieldError var3) {
            ;
         }

         try {
            field_178959_a[VertexFormatElement.EnumUseage.UV.ordinal()] = 3;
         } catch (NoSuchFieldError var2) {
            ;
         }

         try {
            field_178959_a[VertexFormatElement.EnumUseage.NORMAL.ordinal()] = 4;
         } catch (NoSuchFieldError var1) {
            ;
         }

      }
   }
}
