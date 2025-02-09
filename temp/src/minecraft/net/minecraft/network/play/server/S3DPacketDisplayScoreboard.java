package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.scoreboard.ScoreObjective;

public class S3DPacketDisplayScoreboard implements Packet {

   private int field_149374_a;
   private String field_149373_b;
   private static final String __OBFID = "CL_00001325";


   public S3DPacketDisplayScoreboard() {}

   public S3DPacketDisplayScoreboard(int p_i45216_1_, ScoreObjective p_i45216_2_) {
      this.field_149374_a = p_i45216_1_;
      if(p_i45216_2_ == null) {
         this.field_149373_b = "";
      } else {
         this.field_149373_b = p_i45216_2_.func_96679_b();
      }

   }

   public void func_148837_a(PacketBuffer p_148837_1_) throws IOException {
      this.field_149374_a = p_148837_1_.readByte();
      this.field_149373_b = p_148837_1_.func_150789_c(16);
   }

   public void func_148840_b(PacketBuffer p_148840_1_) throws IOException {
      p_148840_1_.writeByte(this.field_149374_a);
      p_148840_1_.func_180714_a(this.field_149373_b);
   }

   public void func_180747_a(INetHandlerPlayClient p_180747_1_) {
      p_180747_1_.func_147254_a(this);
   }

   public int func_149371_c() {
      return this.field_149374_a;
   }

   public String func_149370_d() {
      return this.field_149373_b;
   }

   // $FF: synthetic method
   // $FF: bridge method
   public void func_148833_a(INetHandler p_148833_1_) {
      this.func_180747_a((INetHandlerPlayClient)p_148833_1_);
   }
}
