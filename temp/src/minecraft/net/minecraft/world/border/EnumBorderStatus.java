package net.minecraft.world.border;


public enum EnumBorderStatus {

   GROWING("GROWING", 0, 4259712),
   SHRINKING("SHRINKING", 1, 16724016),
   STATIONARY("STATIONARY", 2, 2138367);
   private final int field_177767_d;
   // $FF: synthetic field
   private static final EnumBorderStatus[] $VALUES = new EnumBorderStatus[]{GROWING, SHRINKING, STATIONARY};
   private static final String __OBFID = "CL_00002013";


   private EnumBorderStatus(String p_i45647_1_, int p_i45647_2_, int p_i45647_3_) {
      this.field_177767_d = p_i45647_3_;
   }

   public int func_177766_a() {
      return this.field_177767_d;
   }

}
