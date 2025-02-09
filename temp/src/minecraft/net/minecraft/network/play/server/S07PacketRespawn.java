package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldType;

public class S07PacketRespawn implements Packet {

   private int field_149088_a;
   private EnumDifficulty field_149086_b;
   private WorldSettings.GameType field_149087_c;
   private WorldType field_149085_d;
   private static final String __OBFID = "CL_00001322";


   public S07PacketRespawn() {}

   public S07PacketRespawn(int p_i45213_1_, EnumDifficulty p_i45213_2_, WorldType p_i45213_3_, WorldSettings.GameType p_i45213_4_) {
      this.field_149088_a = p_i45213_1_;
      this.field_149086_b = p_i45213_2_;
      this.field_149087_c = p_i45213_4_;
      this.field_149085_d = p_i45213_3_;
   }

   public void func_148833_a(INetHandlerPlayClient p_148833_1_) {
      p_148833_1_.func_147280_a(this);
   }

   public void func_148837_a(PacketBuffer p_148837_1_) throws IOException {
      this.field_149088_a = p_148837_1_.readInt();
      this.field_149086_b = EnumDifficulty.func_151523_a(p_148837_1_.readUnsignedByte());
      this.field_149087_c = WorldSettings.GameType.func_77146_a(p_148837_1_.readUnsignedByte());
      this.field_149085_d = WorldType.func_77130_a(p_148837_1_.func_150789_c(16));
      if(this.field_149085_d == null) {
         this.field_149085_d = WorldType.field_77137_b;
      }

   }

   public void func_148840_b(PacketBuffer p_148840_1_) throws IOException {
      p_148840_1_.writeInt(this.field_149088_a);
      p_148840_1_.writeByte(this.field_149086_b.func_151525_a());
      p_148840_1_.writeByte(this.field_149087_c.func_77148_a());
      p_148840_1_.func_180714_a(this.field_149085_d.func_77127_a());
   }

   public int func_149082_c() {
      return this.field_149088_a;
   }

   public EnumDifficulty func_149081_d() {
      return this.field_149086_b;
   }

   public WorldSettings.GameType func_149083_e() {
      return this.field_149087_c;
   }

   public WorldType func_149080_f() {
      return this.field_149085_d;
   }

   // $FF: synthetic method
   // $FF: bridge method
   public void func_148833_a(INetHandler p_148833_1_) {
      this.func_148833_a((INetHandlerPlayClient)p_148833_1_);
   }
}
