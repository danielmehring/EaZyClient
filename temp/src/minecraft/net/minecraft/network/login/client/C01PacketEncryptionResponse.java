package net.minecraft.network.login.client;

import java.io.IOException;
import java.security.PrivateKey;
import java.security.PublicKey;
import javax.crypto.SecretKey;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.login.INetHandlerLoginServer;
import net.minecraft.util.CryptManager;

public class C01PacketEncryptionResponse implements Packet {

   private byte[] field_149302_a = new byte[0];
   private byte[] field_149301_b = new byte[0];
   private static final String __OBFID = "CL_00001380";


   public C01PacketEncryptionResponse() {}

   public C01PacketEncryptionResponse(SecretKey p_i45271_1_, PublicKey p_i45271_2_, byte[] p_i45271_3_) {
      this.field_149302_a = CryptManager.func_75894_a(p_i45271_2_, p_i45271_1_.getEncoded());
      this.field_149301_b = CryptManager.func_75894_a(p_i45271_2_, p_i45271_3_);
   }

   public void func_148837_a(PacketBuffer p_148837_1_) throws IOException {
      this.field_149302_a = p_148837_1_.func_179251_a();
      this.field_149301_b = p_148837_1_.func_179251_a();
   }

   public void func_148840_b(PacketBuffer p_148840_1_) throws IOException {
      p_148840_1_.func_179250_a(this.field_149302_a);
      p_148840_1_.func_179250_a(this.field_149301_b);
   }

   public void func_148833_a(INetHandlerLoginServer p_148833_1_) {
      p_148833_1_.func_147315_a(this);
   }

   public SecretKey func_149300_a(PrivateKey p_149300_1_) {
      return CryptManager.func_75887_a(p_149300_1_, this.field_149302_a);
   }

   public byte[] func_149299_b(PrivateKey p_149299_1_) {
      return p_149299_1_ == null?this.field_149301_b:CryptManager.func_75889_b(p_149299_1_, this.field_149301_b);
   }

   // $FF: synthetic method
   // $FF: bridge method
   public void func_148833_a(INetHandler p_148833_1_) {
      this.func_148833_a((INetHandlerLoginServer)p_148833_1_);
   }
}
