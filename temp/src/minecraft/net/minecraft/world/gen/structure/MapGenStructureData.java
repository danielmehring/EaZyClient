package net.minecraft.world.gen.structure;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.WorldSavedData;

public class MapGenStructureData extends WorldSavedData {

   private NBTTagCompound field_143044_a = new NBTTagCompound();
   private static final String __OBFID = "CL_00000510";


   public MapGenStructureData(String p_i43001_1_) {
      super(p_i43001_1_);
   }

   public void func_76184_a(NBTTagCompound p_76184_1_) {
      this.field_143044_a = p_76184_1_.func_74775_l("Features");
   }

   public void func_76187_b(NBTTagCompound p_76187_1_) {
      p_76187_1_.func_74782_a("Features", this.field_143044_a);
   }

   public void func_143043_a(NBTTagCompound p_143043_1_, int p_143043_2_, int p_143043_3_) {
      this.field_143044_a.func_74782_a(func_143042_b(p_143043_2_, p_143043_3_), p_143043_1_);
   }

   public static String func_143042_b(int p_143042_0_, int p_143042_1_) {
      return "[" + p_143042_0_ + "," + p_143042_1_ + "]";
   }

   public NBTTagCompound func_143041_a() {
      return this.field_143044_a;
   }
}
