package net.minecraft.village;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import com.google.common.collect.Lists;

public class Village {

public static final int EaZy = 1668;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private World worldObj;

	/** list of VillageDoorInfo objects */
	private final List villageDoorInfoList = Lists.newArrayList();

	/**
	 * This is the sum of all door coordinates and used to calculate the actual
	 * village center by dividing by the number of doors.
	 */
	private BlockPos centerHelper;

	/** This is the actual village center. */
	private BlockPos center;
	private int villageRadius;
	private int lastAddDoorTimestamp;
	private int tickCounter;
	private int numVillagers;

	/** Timestamp of tick count when villager last bred */
	private int noBreedTicks;

	/** List of player reputations with this village */
	private final TreeMap playerReputation;
	private final List villageAgressors;
	private int numIronGolems;
	// private static final String __OBFID = "http://https://fuckuskid00001631";

	public Village() {
		centerHelper = BlockPos.ORIGIN;
		center = BlockPos.ORIGIN;
		playerReputation = new TreeMap();
		villageAgressors = Lists.newArrayList();
	}

	public Village(final World worldIn) {
		centerHelper = BlockPos.ORIGIN;
		center = BlockPos.ORIGIN;
		playerReputation = new TreeMap();
		villageAgressors = Lists.newArrayList();
		worldObj = worldIn;
	}

	public void func_82691_a(final World worldIn) {
		worldObj = worldIn;
	}

	/**
	 * Called periodically by VillageCollection
	 */
	public void tick(final int p_75560_1_) {
		tickCounter = p_75560_1_;
		removeDeadAndOutOfRangeDoors();
		removeDeadAndOldAgressors();

		if (p_75560_1_ % 20 == 0) {
			updateNumVillagers();
		}

		if (p_75560_1_ % 30 == 0) {
			updateNumIronGolems();
		}

		final int var2 = numVillagers / 10;

		if (numIronGolems < var2 && villageDoorInfoList.size() > 20 && worldObj.rand.nextInt(7000) == 0) {
			final Vec3 var3 = func_179862_a(center, 2, 4, 2);

			if (var3 != null) {
				final EntityIronGolem var4 = new EntityIronGolem(worldObj);
				var4.setPosition(var3.xCoord, var3.yCoord, var3.zCoord);
				worldObj.spawnEntityInWorld(var4);
				++numIronGolems;
			}
		}
	}

	private Vec3 func_179862_a(final BlockPos p_179862_1_, final int p_179862_2_, final int p_179862_3_,
			final int p_179862_4_) {
		for (int var5 = 0; var5 < 10; ++var5) {
			final BlockPos var6 = p_179862_1_.add(worldObj.rand.nextInt(16) - 8, worldObj.rand.nextInt(6) - 3,
					worldObj.rand.nextInt(16) - 8);

			if (func_179866_a(var6) && func_179861_a(new BlockPos(p_179862_2_, p_179862_3_, p_179862_4_), var6)) {
				return new Vec3(var6.getX(), var6.getY(), var6.getZ());
			}
		}

		return null;
	}

	private boolean func_179861_a(final BlockPos p_179861_1_, final BlockPos p_179861_2_) {
		if (!World.doesBlockHaveSolidTopSurface(worldObj, p_179861_2_.offsetDown())) {
			return false;
		} else {
			final int var3 = p_179861_2_.getX() - p_179861_1_.getX() / 2;
			final int var4 = p_179861_2_.getZ() - p_179861_1_.getZ() / 2;

			for (int var5 = var3; var5 < var3 + p_179861_1_.getX(); ++var5) {
				for (int var6 = p_179861_2_.getY(); var6 < p_179861_2_.getY() + p_179861_1_.getY(); ++var6) {
					for (int var7 = var4; var7 < var4 + p_179861_1_.getZ(); ++var7) {
						if (worldObj.getBlockState(new BlockPos(var5, var6, var7)).getBlock().isNormalCube()) {
							return false;
						}
					}
				}
			}

			return true;
		}
	}

	private void updateNumIronGolems() {
		final List var1 = worldObj.getEntitiesWithinAABB(EntityIronGolem.class,
				new AxisAlignedBB(center.getX() - villageRadius, center.getY() - 4, center.getZ() - villageRadius,
						center.getX() + villageRadius, center.getY() + 4, center.getZ() + villageRadius));
		numIronGolems = var1.size();
	}

	private void updateNumVillagers() {
		final List var1 = worldObj.getEntitiesWithinAABB(EntityVillager.class,
				new AxisAlignedBB(center.getX() - villageRadius, center.getY() - 4, center.getZ() - villageRadius,
						center.getX() + villageRadius, center.getY() + 4, center.getZ() + villageRadius));
		numVillagers = var1.size();

		if (numVillagers == 0) {
			playerReputation.clear();
		}
	}

	public BlockPos func_180608_a() {
		return center;
	}

	public int getVillageRadius() {
		return villageRadius;
	}

	/**
	 * Actually get num village door info entries, but that boils down to number
	 * of doors. Called by EntityAIVillagerMate and VillageSiege
	 */
	public int getNumVillageDoors() {
		return villageDoorInfoList.size();
	}

	public int getTicksSinceLastDoorAdding() {
		return tickCounter - lastAddDoorTimestamp;
	}

	public int getNumVillagers() {
		return numVillagers;
	}

	public boolean func_179866_a(final BlockPos p_179866_1_) {
		return center.distanceSq(p_179866_1_) < villageRadius * villageRadius;
	}

	/**
	 * called only by class EntityAIMoveThroughVillage
	 */
	public List getVillageDoorInfoList() {
		return villageDoorInfoList;
	}

	public VillageDoorInfo func_179865_b(final BlockPos p_179865_1_) {
		VillageDoorInfo var2 = null;
		int var3 = Integer.MAX_VALUE;
		final Iterator var4 = villageDoorInfoList.iterator();

		while (var4.hasNext()) {
			final VillageDoorInfo var5 = (VillageDoorInfo) var4.next();
			final int var6 = var5.func_179848_a(p_179865_1_);

			if (var6 < var3) {
				var2 = var5;
				var3 = var6;
			}
		}

		return var2;
	}

	public VillageDoorInfo func_179863_c(final BlockPos p_179863_1_) {
		VillageDoorInfo var2 = null;
		int var3 = Integer.MAX_VALUE;
		final Iterator var4 = villageDoorInfoList.iterator();

		while (var4.hasNext()) {
			final VillageDoorInfo var5 = (VillageDoorInfo) var4.next();
			int var6 = var5.func_179848_a(p_179863_1_);

			if (var6 > 256) {
				var6 *= 1000;
			} else {
				var6 = var5.getDoorOpeningRestrictionCounter();
			}

			if (var6 < var3) {
				var2 = var5;
				var3 = var6;
			}
		}

		return var2;
	}

	public VillageDoorInfo func_179864_e(final BlockPos p_179864_1_) {
		if (center.distanceSq(p_179864_1_) > villageRadius * villageRadius) {
			return null;
		} else {
			final Iterator var2 = villageDoorInfoList.iterator();
			VillageDoorInfo var3;

			do {
				if (!var2.hasNext()) {
					return null;
				}

				var3 = (VillageDoorInfo) var2.next();
			}
			while (var3.func_179852_d().getX() != p_179864_1_.getX()
					|| var3.func_179852_d().getZ() != p_179864_1_.getZ()
					|| Math.abs(var3.func_179852_d().getY() - p_179864_1_.getY()) > 1);

			return var3;
		}
	}

	public void addVillageDoorInfo(final VillageDoorInfo p_75576_1_) {
		villageDoorInfoList.add(p_75576_1_);
		centerHelper = centerHelper.add(p_75576_1_.func_179852_d());
		updateVillageRadiusAndCenter();
		lastAddDoorTimestamp = p_75576_1_.getInsidePosY();
	}

	/**
	 * Returns true, if there is not a single village door left. Called by
	 * VillageCollection
	 */
	public boolean isAnnihilated() {
		return villageDoorInfoList.isEmpty();
	}

	public void addOrRenewAgressor(final EntityLivingBase p_75575_1_) {
		final Iterator var2 = villageAgressors.iterator();
		Village.VillageAgressor var3;

		do {
			if (!var2.hasNext()) {
				villageAgressors.add(new Village.VillageAgressor(p_75575_1_, tickCounter));
				return;
			}

			var3 = (Village.VillageAgressor) var2.next();
		}
		while (var3.agressor != p_75575_1_);

		var3.agressionTime = tickCounter;
	}

	public EntityLivingBase findNearestVillageAggressor(final EntityLivingBase p_75571_1_) {
		double var2 = Double.MAX_VALUE;
		Village.VillageAgressor var4 = null;

		for (int var5 = 0; var5 < villageAgressors.size(); ++var5) {
			final Village.VillageAgressor var6 = (Village.VillageAgressor) villageAgressors.get(var5);
			final double var7 = var6.agressor.getDistanceSqToEntity(p_75571_1_);

			if (var7 <= var2) {
				var4 = var6;
				var2 = var7;
			}
		}

		return var4 != null ? var4.agressor : null;
	}

	public EntityPlayer func_82685_c(final EntityLivingBase p_82685_1_) {
		double var2 = Double.MAX_VALUE;
		EntityPlayer var4 = null;
		final Iterator var5 = playerReputation.keySet().iterator();

		while (var5.hasNext()) {
			final String var6 = (String) var5.next();

			if (isPlayerReputationTooLow(var6)) {
				final EntityPlayer var7 = worldObj.getPlayerEntityByName(var6);

				if (var7 != null) {
					final double var8 = var7.getDistanceSqToEntity(p_82685_1_);

					if (var8 <= var2) {
						var4 = var7;
						var2 = var8;
					}
				}
			}
		}

		return var4;
	}

	private void removeDeadAndOldAgressors() {
		final Iterator var1 = villageAgressors.iterator();

		while (var1.hasNext()) {
			final Village.VillageAgressor var2 = (Village.VillageAgressor) var1.next();

			if (!var2.agressor.isEntityAlive() || Math.abs(tickCounter - var2.agressionTime) > 300) {
				var1.remove();
			}
		}
	}

	private void removeDeadAndOutOfRangeDoors() {
		boolean var1 = false;
		final boolean var2 = worldObj.rand.nextInt(50) == 0;
		final Iterator var3 = villageDoorInfoList.iterator();

		while (var3.hasNext()) {
			final VillageDoorInfo var4 = (VillageDoorInfo) var3.next();

			if (var2) {
				var4.resetDoorOpeningRestrictionCounter();
			}

			if (!func_179860_f(var4.func_179852_d()) || Math.abs(tickCounter - var4.getInsidePosY()) > 1200) {
				centerHelper = centerHelper.add(var4.func_179852_d().multiply(-1));
				var1 = true;
				var4.func_179853_a(true);
				var3.remove();
			}
		}

		if (var1) {
			updateVillageRadiusAndCenter();
		}
	}

	private boolean func_179860_f(final BlockPos p_179860_1_) {
		final Block var2 = worldObj.getBlockState(p_179860_1_).getBlock();
		return var2 instanceof BlockDoor ? var2.getMaterial() == Material.wood : false;
	}

	private void updateVillageRadiusAndCenter() {
		final int var1 = villageDoorInfoList.size();

		if (var1 == 0) {
			center = new BlockPos(0, 0, 0);
			villageRadius = 0;
		} else {
			center = new BlockPos(centerHelper.getX() / var1, centerHelper.getY() / var1, centerHelper.getZ() / var1);
			int var2 = 0;
			VillageDoorInfo var4;

			for (final Iterator var3 = villageDoorInfoList.iterator(); var3
					.hasNext(); var2 = Math.max(var4.func_179848_a(center), var2)) {
				var4 = (VillageDoorInfo) var3.next();
			}

			villageRadius = Math.max(32, (int) Math.sqrt(var2) + 1);
		}
	}

	/**
	 * Return the village reputation for a player
	 */
	public int getReputationForPlayer(final String p_82684_1_) {
		final Integer var2 = (Integer) playerReputation.get(p_82684_1_);
		return var2 != null ? var2 : 0;
	}

	/**
	 * Set the village reputation for a player.
	 */
	public int setReputationForPlayer(final String p_82688_1_, final int p_82688_2_) {
		final int var3 = getReputationForPlayer(p_82688_1_);
		final int var4 = MathHelper.clamp_int(var3 + p_82688_2_, -30, 10);
		playerReputation.put(p_82688_1_, var4);
		return var4;
	}

	/**
	 * Return whether this player has a too low reputation with this village.
	 */
	public boolean isPlayerReputationTooLow(final String p_82687_1_) {
		return getReputationForPlayer(p_82687_1_) <= -15;
	}

	/**
	 * Read this village's data from NBT.
	 */
	public void readVillageDataFromNBT(final NBTTagCompound p_82690_1_) {
		numVillagers = p_82690_1_.getInteger("PopSize");
		villageRadius = p_82690_1_.getInteger("Radius");
		numIronGolems = p_82690_1_.getInteger("Golems");
		lastAddDoorTimestamp = p_82690_1_.getInteger("Stable");
		tickCounter = p_82690_1_.getInteger("Tick");
		noBreedTicks = p_82690_1_.getInteger("MTick");
		center = new BlockPos(p_82690_1_.getInteger("CX"), p_82690_1_.getInteger("CY"), p_82690_1_.getInteger("CZ"));
		centerHelper = new BlockPos(p_82690_1_.getInteger("ACX"), p_82690_1_.getInteger("ACY"),
				p_82690_1_.getInteger("ACZ"));
		final NBTTagList var2 = p_82690_1_.getTagList("Doors", 10);

		for (int var3 = 0; var3 < var2.tagCount(); ++var3) {
			final NBTTagCompound var4 = var2.getCompoundTagAt(var3);
			final VillageDoorInfo var5 = new VillageDoorInfo(
					new BlockPos(var4.getInteger("X"), var4.getInteger("Y"), var4.getInteger("Z")),
					var4.getInteger("IDX"), var4.getInteger("IDZ"), var4.getInteger("TS"));
			villageDoorInfoList.add(var5);
		}

		final NBTTagList var6 = p_82690_1_.getTagList("Players", 10);

		for (int var7 = 0; var7 < var6.tagCount(); ++var7) {
			final NBTTagCompound var8 = var6.getCompoundTagAt(var7);
			playerReputation.put(var8.getString("Name"), var8.getInteger("S"));
		}
	}

	/**
	 * Write this village's data to NBT.
	 */
	public void writeVillageDataToNBT(final NBTTagCompound p_82689_1_) {
		p_82689_1_.setInteger("PopSize", numVillagers);
		p_82689_1_.setInteger("Radius", villageRadius);
		p_82689_1_.setInteger("Golems", numIronGolems);
		p_82689_1_.setInteger("Stable", lastAddDoorTimestamp);
		p_82689_1_.setInteger("Tick", tickCounter);
		p_82689_1_.setInteger("MTick", noBreedTicks);
		p_82689_1_.setInteger("CX", center.getX());
		p_82689_1_.setInteger("CY", center.getY());
		p_82689_1_.setInteger("CZ", center.getZ());
		p_82689_1_.setInteger("ACX", centerHelper.getX());
		p_82689_1_.setInteger("ACY", centerHelper.getY());
		p_82689_1_.setInteger("ACZ", centerHelper.getZ());
		final NBTTagList var2 = new NBTTagList();
		final Iterator var3 = villageDoorInfoList.iterator();

		while (var3.hasNext()) {
			final VillageDoorInfo var4 = (VillageDoorInfo) var3.next();
			final NBTTagCompound var5 = new NBTTagCompound();
			var5.setInteger("X", var4.func_179852_d().getX());
			var5.setInteger("Y", var4.func_179852_d().getY());
			var5.setInteger("Z", var4.func_179852_d().getZ());
			var5.setInteger("IDX", var4.func_179847_f());
			var5.setInteger("IDZ", var4.func_179855_g());
			var5.setInteger("TS", var4.getInsidePosY());
			var2.appendTag(var5);
		}

		p_82689_1_.setTag("Doors", var2);
		final NBTTagList var7 = new NBTTagList();
		final Iterator var8 = playerReputation.keySet().iterator();

		while (var8.hasNext()) {
			final String var9 = (String) var8.next();
			final NBTTagCompound var6 = new NBTTagCompound();
			var6.setString("Name", var9);
			var6.setInteger("S", ((Integer) playerReputation.get(var9)));
			var7.appendTag(var6);
		}

		p_82689_1_.setTag("Players", var7);
	}

	/**
	 * Prevent villager breeding for a fixed interval of time
	 */
	public void endMatingSeason() {
		noBreedTicks = tickCounter;
	}

	/**
	 * Return whether villagers mating refractory period has passed
	 */
	public boolean isMatingSeason() {
		return noBreedTicks == 0 || tickCounter - noBreedTicks >= 3600;
	}

	public void setDefaultPlayerReputation(final int p_82683_1_) {
		final Iterator var2 = playerReputation.keySet().iterator();

		while (var2.hasNext()) {
			final String var3 = (String) var2.next();
			setReputationForPlayer(var3, p_82683_1_);
		}
	}

	class VillageAgressor {
		public EntityLivingBase agressor;
		public int agressionTime;
		// private static final String __OBFID =
		// "http://https://fuckuskid00001632";

		VillageAgressor(final EntityLivingBase p_i1674_2_, final int p_i1674_3_) {
			agressor = p_i1674_2_;
			agressionTime = p_i1674_3_;
		}
	}
}
