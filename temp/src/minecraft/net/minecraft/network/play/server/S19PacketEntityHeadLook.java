package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.entity.Entity;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.world.World;

public class S19PacketEntityHeadLook implements Packet {

   private int field_149384_a;
   private byte field_149383_b;
   private static final String __OBFID = "CL_00001323";


   public S19PacketEntityHeadLook() {}

   public S19PacketEntityHeadLook(Entity p_i45214_1_, byte p_i45214_2_) {
      this.field_149384_a = p_i45214_1_.func_145782_y();
      this.field_149383_b = p_i45214_2_;
   }

   public void func_148837_a(PacketBuffer p_148837_1_) throws IOException {
      this.field_149384_a = p_148837_1_.func_150792_a();
      this.field_149383_b = p_148837_1_.readByte();
   }

   public void func_148840_b(PacketBuffer p_148840_1_) throws IOException {
      p_148840_1_.func_150787_b(this.field_149384_a);
      p_148840_1_.writeByte(this.field_149383_b);
   }

   public void func_180745_a(INetHandlerPlayClient p_180745_1_) {
      p_180745_1_.func_147267_a(this);
   }

   public Entity func_149381_a(World p_149381_1_) {
      return p_149381_1_.func_73045_a(this.field_149384_a);
   }

   public byte func_149380_c() {
      return this.field_149383_b;
   }

   // $FF: synthetic method
   // $FF: bridge method
   public void func_148833_a(INetHandler p_148833_1_) {
      this.func_180745_a((INetHandlerPlayClient)p_148833_1_);
   }
}
