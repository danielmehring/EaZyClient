package net.minecraft.network.play.server;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;

public class S21PacketChunkData implements Packet {

   private int field_149284_a;
   private int field_149282_b;
   private S21PacketChunkData.Extracted field_179758_c;
   private boolean field_149279_g;
   private static final String __OBFID = "CL_00001304";


   public S21PacketChunkData() {}

   public S21PacketChunkData(Chunk p_i45196_1_, boolean p_i45196_2_, int p_i45196_3_) {
      this.field_149284_a = p_i45196_1_.field_76635_g;
      this.field_149282_b = p_i45196_1_.field_76647_h;
      this.field_149279_g = p_i45196_2_;
      this.field_179758_c = func_179756_a(p_i45196_1_, p_i45196_2_, !p_i45196_1_.func_177412_p().field_73011_w.func_177495_o(), p_i45196_3_);
   }

   public void func_148837_a(PacketBuffer p_148837_1_) throws IOException {
      this.field_149284_a = p_148837_1_.readInt();
      this.field_149282_b = p_148837_1_.readInt();
      this.field_149279_g = p_148837_1_.readBoolean();
      this.field_179758_c = new S21PacketChunkData.Extracted();
      this.field_179758_c.field_150280_b = p_148837_1_.readShort();
      this.field_179758_c.field_150282_a = p_148837_1_.func_179251_a();
   }

   public void func_148840_b(PacketBuffer p_148840_1_) throws IOException {
      p_148840_1_.writeInt(this.field_149284_a);
      p_148840_1_.writeInt(this.field_149282_b);
      p_148840_1_.writeBoolean(this.field_149279_g);
      p_148840_1_.writeShort((short)(this.field_179758_c.field_150280_b & '\uffff'));
      p_148840_1_.func_179250_a(this.field_179758_c.field_150282_a);
   }

   public void func_148833_a(INetHandlerPlayClient p_148833_1_) {
      p_148833_1_.func_147263_a(this);
   }

   public byte[] func_149272_d() {
      return this.field_179758_c.field_150282_a;
   }

   protected static int func_180737_a(int p_180737_0_, boolean p_180737_1_, boolean p_180737_2_) {
      int var3 = p_180737_0_ * 2 * 16 * 16 * 16;
      int var4 = p_180737_0_ * 16 * 16 * 16 / 2;
      int var5 = p_180737_1_?p_180737_0_ * 16 * 16 * 16 / 2:0;
      int var6 = p_180737_2_?256:0;
      return var3 + var4 + var5 + var6;
   }

   public static S21PacketChunkData.Extracted func_179756_a(Chunk p_179756_0_, boolean p_179756_1_, boolean p_179756_2_, int p_179756_3_) {
      ExtendedBlockStorage[] var4 = p_179756_0_.func_76587_i();
      S21PacketChunkData.Extracted var5 = new S21PacketChunkData.Extracted();
      ArrayList var6 = Lists.newArrayList();

      int var7;
      for(var7 = 0; var7 < var4.length; ++var7) {
         ExtendedBlockStorage var8 = var4[var7];
         if(var8 != null && (!p_179756_1_ || !var8.func_76663_a()) && (p_179756_3_ & 1 << var7) != 0) {
            var5.field_150280_b |= 1 << var7;
            var6.add(var8);
         }
      }

      var5.field_150282_a = new byte[func_180737_a(Integer.bitCount(var5.field_150280_b), p_179756_2_, p_179756_1_)];
      var7 = 0;
      Iterator var15 = var6.iterator();

      ExtendedBlockStorage var9;
      while(var15.hasNext()) {
         var9 = (ExtendedBlockStorage)var15.next();
         char[] var10 = var9.func_177487_g();
         char[] var11 = var10;
         int var12 = var10.length;

         for(int var13 = 0; var13 < var12; ++var13) {
            char var14 = var11[var13];
            var5.field_150282_a[var7++] = (byte)(var14 & 255);
            var5.field_150282_a[var7++] = (byte)(var14 >> 8 & 255);
         }
      }

      for(var15 = var6.iterator(); var15.hasNext(); var7 = func_179757_a(var9.func_76661_k().func_177481_a(), var5.field_150282_a, var7)) {
         var9 = (ExtendedBlockStorage)var15.next();
      }

      if(p_179756_2_) {
         for(var15 = var6.iterator(); var15.hasNext(); var7 = func_179757_a(var9.func_76671_l().func_177481_a(), var5.field_150282_a, var7)) {
            var9 = (ExtendedBlockStorage)var15.next();
         }
      }

      if(p_179756_1_) {
         func_179757_a(p_179756_0_.func_76605_m(), var5.field_150282_a, var7);
      }

      return var5;
   }

   private static int func_179757_a(byte[] p_179757_0_, byte[] p_179757_1_, int p_179757_2_) {
      System.arraycopy(p_179757_0_, 0, p_179757_1_, p_179757_2_, p_179757_0_.length);
      return p_179757_2_ + p_179757_0_.length;
   }

   public int func_149273_e() {
      return this.field_149284_a;
   }

   public int func_149271_f() {
      return this.field_149282_b;
   }

   public int func_149276_g() {
      return this.field_179758_c.field_150280_b;
   }

   public boolean func_149274_i() {
      return this.field_149279_g;
   }

   // $FF: synthetic method
   // $FF: bridge method
   public void func_148833_a(INetHandler p_148833_1_) {
      this.func_148833_a((INetHandlerPlayClient)p_148833_1_);
   }

   public static class Extracted {

      public byte[] field_150282_a;
      public int field_150280_b;
      private static final String __OBFID = "CL_00001305";


   }
}
