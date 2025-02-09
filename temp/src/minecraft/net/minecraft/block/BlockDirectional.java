package net.minecraft.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.util.EnumFacing;

public abstract class BlockDirectional extends Block {

   public static final PropertyDirection field_176387_N = PropertyDirection.func_177712_a("facing", EnumFacing.Plane.HORIZONTAL);
   private static final String __OBFID = "CL_00000227";


   protected BlockDirectional(Material p_i45401_1_) {
      super(p_i45401_1_);
   }

}
