package net.minecraft.network.play.server;

import java.io.IOException;
import java.util.List;
import net.minecraft.item.ItemStack;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class S30PacketWindowItems implements Packet {

   private int field_148914_a;
   private ItemStack[] field_148913_b;
   private static final String __OBFID = "CL_00001294";


   public S30PacketWindowItems() {}

   public S30PacketWindowItems(int p_i45186_1_, List p_i45186_2_) {
      this.field_148914_a = p_i45186_1_;
      this.field_148913_b = new ItemStack[p_i45186_2_.size()];

      for(int var3 = 0; var3 < this.field_148913_b.length; ++var3) {
         ItemStack var4 = (ItemStack)p_i45186_2_.get(var3);
         this.field_148913_b[var3] = var4 == null?null:var4.func_77946_l();
      }

   }

   public void func_148837_a(PacketBuffer p_148837_1_) throws IOException {
      this.field_148914_a = p_148837_1_.readUnsignedByte();
      short var2 = p_148837_1_.readShort();
      this.field_148913_b = new ItemStack[var2];

      for(int var3 = 0; var3 < var2; ++var3) {
         this.field_148913_b[var3] = p_148837_1_.func_150791_c();
      }

   }

   public void func_148840_b(PacketBuffer p_148840_1_) throws IOException {
      p_148840_1_.writeByte(this.field_148914_a);
      p_148840_1_.writeShort(this.field_148913_b.length);
      ItemStack[] var2 = this.field_148913_b;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         ItemStack var5 = var2[var4];
         p_148840_1_.func_150788_a(var5);
      }

   }

   public void func_180732_a(INetHandlerPlayClient p_180732_1_) {
      p_180732_1_.func_147241_a(this);
   }

   public int func_148911_c() {
      return this.field_148914_a;
   }

   public ItemStack[] func_148910_d() {
      return this.field_148913_b;
   }

   // $FF: synthetic method
   // $FF: bridge method
   public void func_148833_a(INetHandler p_148833_1_) {
      this.func_180732_a((INetHandlerPlayClient)p_148833_1_);
   }
}
