package net.minecraft.realms;

import net.minecraft.world.storage.SaveFormatComparator;

public class RealmsLevelSummary implements Comparable {

   private SaveFormatComparator levelSummary;
   private static final String __OBFID = "CL_00001857";


   public RealmsLevelSummary(SaveFormatComparator p_i1109_1_) {
      this.levelSummary = p_i1109_1_;
   }

   public int getGameMode() {
      return this.levelSummary.func_75790_f().func_77148_a();
   }

   public String getLevelId() {
      return this.levelSummary.func_75786_a();
   }

   public boolean hasCheats() {
      return this.levelSummary.func_75783_h();
   }

   public boolean isHardcore() {
      return this.levelSummary.func_75789_g();
   }

   public boolean isRequiresConversion() {
      return this.levelSummary.func_75785_d();
   }

   public String getLevelName() {
      return this.levelSummary.func_75788_b();
   }

   public long getLastPlayed() {
      return this.levelSummary.func_75784_e();
   }

   public int compareTo(SaveFormatComparator p_compareTo_1_) {
      return this.levelSummary.compareTo(p_compareTo_1_);
   }

   public long getSizeOnDisk() {
      return this.levelSummary.func_154336_c();
   }

   public int compareTo(RealmsLevelSummary p_compareTo_1_) {
      return this.levelSummary.func_75784_e() < p_compareTo_1_.getLastPlayed()?1:(this.levelSummary.func_75784_e() > p_compareTo_1_.getLastPlayed()?-1:this.levelSummary.func_75786_a().compareTo(p_compareTo_1_.getLevelId()));
   }

   // $FF: synthetic method
   // $FF: bridge method
   public int compareTo(Object p_compareTo_1_) {
      return this.compareTo((RealmsLevelSummary)p_compareTo_1_);
   }
}
