package net.minecraft.network.login.server;

import java.io.IOException;
import java.security.PublicKey;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.login.INetHandlerLoginClient;
import net.minecraft.util.CryptManager;

public class S01PacketEncryptionRequest implements Packet {

   private String field_149612_a;
   private PublicKey field_149610_b;
   private byte[] field_149611_c;
   private static final String __OBFID = "CL_00001376";


   public S01PacketEncryptionRequest() {}

   public S01PacketEncryptionRequest(String p_i45268_1_, PublicKey p_i45268_2_, byte[] p_i45268_3_) {
      this.field_149612_a = p_i45268_1_;
      this.field_149610_b = p_i45268_2_;
      this.field_149611_c = p_i45268_3_;
   }

   public void func_148837_a(PacketBuffer p_148837_1_) throws IOException {
      this.field_149612_a = p_148837_1_.func_150789_c(20);
      this.field_149610_b = CryptManager.func_75896_a(p_148837_1_.func_179251_a());
      this.field_149611_c = p_148837_1_.func_179251_a();
   }

   public void func_148840_b(PacketBuffer p_148840_1_) throws IOException {
      p_148840_1_.func_180714_a(this.field_149612_a);
      p_148840_1_.func_179250_a(this.field_149610_b.getEncoded());
      p_148840_1_.func_179250_a(this.field_149611_c);
   }

   public void func_148833_a(INetHandlerLoginClient p_148833_1_) {
      p_148833_1_.func_147389_a(this);
   }

   public String func_149609_c() {
      return this.field_149612_a;
   }

   public PublicKey func_149608_d() {
      return this.field_149610_b;
   }

   public byte[] func_149607_e() {
      return this.field_149611_c;
   }

   // $FF: synthetic method
   // $FF: bridge method
   public void func_148833_a(INetHandler p_148833_1_) {
      this.func_148833_a((INetHandlerLoginClient)p_148833_1_);
   }
}
