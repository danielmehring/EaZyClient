package net.minecraft.world.gen.structure;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureNetherBridgePieces;
import net.minecraft.world.gen.structure.StructureStart;

public class MapGenNetherBridge extends MapGenStructure {

   private List field_75060_e = Lists.newArrayList();
   private static final String __OBFID = "CL_00000451";


   public MapGenNetherBridge() {
      this.field_75060_e.add(new BiomeGenBase.SpawnListEntry(EntityBlaze.class, 10, 2, 3));
      this.field_75060_e.add(new BiomeGenBase.SpawnListEntry(EntityPigZombie.class, 5, 4, 4));
      this.field_75060_e.add(new BiomeGenBase.SpawnListEntry(EntitySkeleton.class, 10, 4, 4));
      this.field_75060_e.add(new BiomeGenBase.SpawnListEntry(EntityMagmaCube.class, 3, 4, 4));
   }

   public String func_143025_a() {
      return "Fortress";
   }

   public List func_75059_a() {
      return this.field_75060_e;
   }

   protected boolean func_75047_a(int p_75047_1_, int p_75047_2_) {
      int var3 = p_75047_1_ >> 4;
      int var4 = p_75047_2_ >> 4;
      this.field_75038_b.setSeed((long)(var3 ^ var4 << 4) ^ this.field_75039_c.func_72905_C());
      this.field_75038_b.nextInt();
      return this.field_75038_b.nextInt(3) != 0?false:(p_75047_1_ != (var3 << 4) + 4 + this.field_75038_b.nextInt(8)?false:p_75047_2_ == (var4 << 4) + 4 + this.field_75038_b.nextInt(8));
   }

   protected StructureStart func_75049_b(int p_75049_1_, int p_75049_2_) {
      return new MapGenNetherBridge.Start(this.field_75039_c, this.field_75038_b, p_75049_1_, p_75049_2_);
   }

   public static class Start extends StructureStart {

      private static final String __OBFID = "CL_00000452";


      public Start() {}

      public Start(World p_i2040_1_, Random p_i2040_2_, int p_i2040_3_, int p_i2040_4_) {
         super(p_i2040_3_, p_i2040_4_);
         StructureNetherBridgePieces.Start var5 = new StructureNetherBridgePieces.Start(p_i2040_2_, (p_i2040_3_ << 4) + 2, (p_i2040_4_ << 4) + 2);
         this.field_75075_a.add(var5);
         var5.func_74861_a(var5, this.field_75075_a, p_i2040_2_);
         List var6 = var5.field_74967_d;

         while(!var6.isEmpty()) {
            int var7 = p_i2040_2_.nextInt(var6.size());
            StructureComponent var8 = (StructureComponent)var6.remove(var7);
            var8.func_74861_a(var5, this.field_75075_a, p_i2040_2_);
         }

         this.func_75072_c();
         this.func_75070_a(p_i2040_1_, p_i2040_2_, 48, 70);
      }
   }
}
