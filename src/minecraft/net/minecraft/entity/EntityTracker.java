package net.minecraft.entity;

import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.item.EntityEnderEye;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.item.EntityExpBottle;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.network.Packet;
import net.minecraft.util.IntHashMap;
import net.minecraft.util.ReportedException;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Callable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

public class EntityTracker {

public static final int EaZy = 1120;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final Logger logger = LogManager.getLogger();
	private final WorldServer theWorld;

	/**
	 * List of tracked entities, used for iteration operations on tracked
	 * entities.
	 */
	private final Set trackedEntities = Sets.newHashSet();

	/** Used for identity lookup of tracked entities. */
	private final IntHashMap trackedEntityHashTable = new IntHashMap();
	private final int maxTrackingDistanceThreshold;
	// private static final String __OBFID = "http://https://fuckuskid00001431";

	public EntityTracker(final WorldServer p_i1516_1_) {
		theWorld = p_i1516_1_;
		maxTrackingDistanceThreshold = p_i1516_1_.func_73046_m().getConfigurationManager().getEntityViewDistance();
	}

	public void trackEntity(final Entity p_72786_1_) {
		if (p_72786_1_ instanceof EntityPlayerMP) {
			this.trackEntity(p_72786_1_, 512, 2);
			final EntityPlayerMP var2 = (EntityPlayerMP) p_72786_1_;
			final Iterator var3 = trackedEntities.iterator();

			while (var3.hasNext()) {
				final EntityTrackerEntry var4 = (EntityTrackerEntry) var3.next();

				if (var4.trackedEntity != var2) {
					var4.updatePlayerEntity(var2);
				}
			}
		} else if (p_72786_1_ instanceof EntityFishHook) {
			addEntityToTracker(p_72786_1_, 64, 5, true);
		} else if (p_72786_1_ instanceof EntityArrow) {
			addEntityToTracker(p_72786_1_, 64, 20, false);
		} else if (p_72786_1_ instanceof EntitySmallFireball) {
			addEntityToTracker(p_72786_1_, 64, 10, false);
		} else if (p_72786_1_ instanceof EntityFireball) {
			addEntityToTracker(p_72786_1_, 64, 10, false);
		} else if (p_72786_1_ instanceof EntitySnowball) {
			addEntityToTracker(p_72786_1_, 64, 10, true);
		} else if (p_72786_1_ instanceof EntityEnderPearl) {
			addEntityToTracker(p_72786_1_, 64, 10, true);
		} else if (p_72786_1_ instanceof EntityEnderEye) {
			addEntityToTracker(p_72786_1_, 64, 4, true);
		} else if (p_72786_1_ instanceof EntityEgg) {
			addEntityToTracker(p_72786_1_, 64, 10, true);
		} else if (p_72786_1_ instanceof EntityPotion) {
			addEntityToTracker(p_72786_1_, 64, 10, true);
		} else if (p_72786_1_ instanceof EntityExpBottle) {
			addEntityToTracker(p_72786_1_, 64, 10, true);
		} else if (p_72786_1_ instanceof EntityFireworkRocket) {
			addEntityToTracker(p_72786_1_, 64, 10, true);
		} else if (p_72786_1_ instanceof EntityItem) {
			addEntityToTracker(p_72786_1_, 64, 20, true);
		} else if (p_72786_1_ instanceof EntityMinecart) {
			addEntityToTracker(p_72786_1_, 80, 3, true);
		} else if (p_72786_1_ instanceof EntityBoat) {
			addEntityToTracker(p_72786_1_, 80, 3, true);
		} else if (p_72786_1_ instanceof EntitySquid) {
			addEntityToTracker(p_72786_1_, 64, 3, true);
		} else if (p_72786_1_ instanceof EntityWither) {
			addEntityToTracker(p_72786_1_, 80, 3, false);
		} else if (p_72786_1_ instanceof EntityBat) {
			addEntityToTracker(p_72786_1_, 80, 3, false);
		} else if (p_72786_1_ instanceof EntityDragon) {
			addEntityToTracker(p_72786_1_, 160, 3, true);
		} else if (p_72786_1_ instanceof IAnimals) {
			addEntityToTracker(p_72786_1_, 80, 3, true);
		} else if (p_72786_1_ instanceof EntityTNTPrimed) {
			addEntityToTracker(p_72786_1_, 160, 10, true);
		} else if (p_72786_1_ instanceof EntityFallingBlock) {
			addEntityToTracker(p_72786_1_, 160, 20, true);
		} else if (p_72786_1_ instanceof EntityHanging) {
			addEntityToTracker(p_72786_1_, 160, Integer.MAX_VALUE, false);
		} else if (p_72786_1_ instanceof EntityArmorStand) {
			addEntityToTracker(p_72786_1_, 160, 3, true);
		} else if (p_72786_1_ instanceof EntityXPOrb) {
			addEntityToTracker(p_72786_1_, 160, 20, true);
		} else if (p_72786_1_ instanceof EntityEnderCrystal) {
			addEntityToTracker(p_72786_1_, 256, Integer.MAX_VALUE, false);
		}
	}

	public void trackEntity(final Entity p_72791_1_, final int p_72791_2_, final int p_72791_3_) {
		addEntityToTracker(p_72791_1_, p_72791_2_, p_72791_3_, false);
	}

	/**
	 * Args : Entity, trackingRange, updateFrequency, sendVelocityUpdates
	 */
	public void addEntityToTracker(final Entity p_72785_1_, int p_72785_2_, final int p_72785_3_,
			final boolean p_72785_4_) {
		if (p_72785_2_ > maxTrackingDistanceThreshold) {
			p_72785_2_ = maxTrackingDistanceThreshold;
		}

		try {
			if (trackedEntityHashTable.containsItem(p_72785_1_.getEntityId())) {
				throw new IllegalStateException("Entity is already tracked!");
			}

			final EntityTrackerEntry var5 = new EntityTrackerEntry(p_72785_1_, p_72785_2_, p_72785_3_, p_72785_4_);
			trackedEntities.add(var5);
			trackedEntityHashTable.addKey(p_72785_1_.getEntityId(), var5);
			var5.updatePlayerEntities(theWorld.playerEntities);
		} catch (final Throwable var11) {
			final CrashReport var6 = CrashReport.makeCrashReport(var11, "Adding entity to track");
			final CrashReportCategory var7 = var6.makeCategory("Entity To Track");
			var7.addCrashSection("Tracking range", p_72785_2_ + " blocks");
			var7.addCrashSectionCallable("Update interval", new Callable() {
				// private static final String __OBFID =
				// "http://https://fuckuskid00001432";
				@Override
				public String call() {
					String var1 = "Once per " + p_72785_3_ + " ticks";

					if (p_72785_3_ == Integer.MAX_VALUE) {
						var1 = "Maximum (" + var1 + ")";
					}

					return var1;
				}
			});
			p_72785_1_.addEntityCrashInfo(var7);
			final CrashReportCategory var8 = var6.makeCategory("Entity That Is Already Tracked");
			((EntityTrackerEntry) trackedEntityHashTable.lookup(p_72785_1_.getEntityId())).trackedEntity
					.addEntityCrashInfo(var8);

			try {
				throw new ReportedException(var6);
			} catch (final ReportedException var10) {
				logger.error("\"Silently\" catching entity tracking error.", var10);
			}
		}
	}

	public void untrackEntity(final Entity p_72790_1_) {
		if (p_72790_1_ instanceof EntityPlayerMP) {
			final EntityPlayerMP var2 = (EntityPlayerMP) p_72790_1_;
			final Iterator var3 = trackedEntities.iterator();

			while (var3.hasNext()) {
				final EntityTrackerEntry var4 = (EntityTrackerEntry) var3.next();
				var4.removeFromTrackedPlayers(var2);
			}
		}

		final EntityTrackerEntry var5 = (EntityTrackerEntry) trackedEntityHashTable
				.removeObject(p_72790_1_.getEntityId());

		if (var5 != null) {
			trackedEntities.remove(var5);
			var5.sendDestroyEntityPacketToTrackedPlayers();
		}
	}

	public void updateTrackedEntities() {
		final ArrayList var1 = Lists.newArrayList();
		final Iterator var2 = trackedEntities.iterator();

		while (var2.hasNext()) {
			final EntityTrackerEntry var3 = (EntityTrackerEntry) var2.next();
			var3.updatePlayerList(theWorld.playerEntities);

			if (var3.playerEntitiesUpdated && var3.trackedEntity instanceof EntityPlayerMP) {
				var1.add(var3.trackedEntity);
			}
		}

		for (int var6 = 0; var6 < var1.size(); ++var6) {
			final EntityPlayerMP var7 = (EntityPlayerMP) var1.get(var6);
			final Iterator var4 = trackedEntities.iterator();

			while (var4.hasNext()) {
				final EntityTrackerEntry var5 = (EntityTrackerEntry) var4.next();

				if (var5.trackedEntity != var7) {
					var5.updatePlayerEntity(var7);
				}
			}
		}
	}

	public void func_180245_a(final EntityPlayerMP p_180245_1_) {
		final Iterator var2 = trackedEntities.iterator();

		while (var2.hasNext()) {
			final EntityTrackerEntry var3 = (EntityTrackerEntry) var2.next();

			if (var3.trackedEntity == p_180245_1_) {
				var3.updatePlayerEntities(theWorld.playerEntities);
			} else {
				var3.updatePlayerEntity(p_180245_1_);
			}
		}
	}

	public void sendToAllTrackingEntity(final Entity p_151247_1_, final Packet p_151247_2_) {
		final EntityTrackerEntry var3 = (EntityTrackerEntry) trackedEntityHashTable.lookup(p_151247_1_.getEntityId());

		if (var3 != null) {
			var3.func_151259_a(p_151247_2_);
		}
	}

	public void func_151248_b(final Entity p_151248_1_, final Packet p_151248_2_) {
		final EntityTrackerEntry var3 = (EntityTrackerEntry) trackedEntityHashTable.lookup(p_151248_1_.getEntityId());

		if (var3 != null) {
			var3.func_151261_b(p_151248_2_);
		}
	}

	public void removePlayerFromTrackers(final EntityPlayerMP p_72787_1_) {
		final Iterator var2 = trackedEntities.iterator();

		while (var2.hasNext()) {
			final EntityTrackerEntry var3 = (EntityTrackerEntry) var2.next();
			var3.removeTrackedPlayerSymmetric(p_72787_1_);
		}
	}

	public void func_85172_a(final EntityPlayerMP p_85172_1_, final Chunk p_85172_2_) {
		final Iterator var3 = trackedEntities.iterator();

		while (var3.hasNext()) {
			final EntityTrackerEntry var4 = (EntityTrackerEntry) var3.next();

			if (var4.trackedEntity != p_85172_1_ && var4.trackedEntity.chunkCoordX == p_85172_2_.xPosition
					&& var4.trackedEntity.chunkCoordZ == p_85172_2_.zPosition) {
				var4.updatePlayerEntity(p_85172_1_);
			}
		}
	}
}
