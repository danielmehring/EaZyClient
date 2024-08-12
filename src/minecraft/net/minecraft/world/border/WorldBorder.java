package net.minecraft.world.border;

import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.ChunkCoordIntPair;

import java.util.Iterator;
import java.util.List;

import com.google.common.collect.Lists;

public class WorldBorder {

public static final int EaZy = 1699;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final List listeners = Lists.newArrayList();
	private double centerX = 0.0D;
	private double centerZ = 0.0D;
	private double startDiameter = 6.0E7D;
	private double endDiameter;
	private long endTime;
	private long startTime;
	private int worldSize;
	private double damageAmount;
	private double damageBuffer;
	private int warningTime;
	private int warningDistance;
	// private static final String __OBFID = "http://https://fuckuskid00002012";

	public WorldBorder() {
		endDiameter = startDiameter;
		worldSize = 29999984;
		damageAmount = 0.2D;
		damageBuffer = 5.0D;
		warningTime = 15;
		warningDistance = 5;
	}

	public boolean contains(final BlockPos pos) {
		return pos.getX() + 1 > minX() && pos.getX() < maxX() && pos.getZ() + 1 > minZ() && pos.getZ() < maxZ();
	}

	public boolean contains(final ChunkCoordIntPair range) {
		return range.getXEnd() > minX() && range.getXStart() < maxX() && range.getZEnd() > minZ()
				&& range.getZStart() < maxZ();
	}

	public boolean contains(final AxisAlignedBB bb) {
		return bb.maxX > minX() && bb.minX < maxX() && bb.maxZ > minZ() && bb.minZ < maxZ();
	}

	public double getClosestDistance(final Entity p_177745_1_) {
		return this.getClosestDistance(p_177745_1_.posX, p_177745_1_.posZ);
	}

	public double getClosestDistance(final double x, final double z) {
		final double var5 = z - minZ();
		final double var7 = maxZ() - z;
		final double var9 = x - minX();
		final double var11 = maxX() - x;
		double var13 = Math.min(var9, var11);
		var13 = Math.min(var13, var5);
		return Math.min(var13, var7);
	}

	public EnumBorderStatus getStatus() {
		return endDiameter < startDiameter ? EnumBorderStatus.SHRINKING
				: endDiameter > startDiameter ? EnumBorderStatus.GROWING : EnumBorderStatus.STATIONARY;
	}

	public double minX() {
		double var1 = getCenterX() - getDiameter() / 2.0D;

		if (var1 < -worldSize) {
			var1 = -worldSize;
		}

		return var1;
	}

	public double minZ() {
		double var1 = getCenterZ() - getDiameter() / 2.0D;

		if (var1 < -worldSize) {
			var1 = -worldSize;
		}

		return var1;
	}

	public double maxX() {
		double var1 = getCenterX() + getDiameter() / 2.0D;

		if (var1 > worldSize) {
			var1 = worldSize;
		}

		return var1;
	}

	public double maxZ() {
		double var1 = getCenterZ() + getDiameter() / 2.0D;

		if (var1 > worldSize) {
			var1 = worldSize;
		}

		return var1;
	}

	public double getCenterX() {
		return centerX;
	}

	public double getCenterZ() {
		return centerZ;
	}

	public void setCenter(final double x, final double z) {
		centerX = x;
		centerZ = z;
		final Iterator var5 = getListeners().iterator();

		while (var5.hasNext()) {
			final IBorderListener var6 = (IBorderListener) var5.next();
			var6.onCenterChanged(this, x, z);
		}
	}

	public double getDiameter() {
		if (getStatus() != EnumBorderStatus.STATIONARY) {
			final double var1 = (float) (System.currentTimeMillis() - startTime) / (float) (endTime - startTime);

			if (var1 < 1.0D) {
				return startDiameter + (endDiameter - startDiameter) * var1;
			}

			this.setTransition(endDiameter);
		}

		return startDiameter;
	}

	public long getTimeUntilTarget() {
		return getStatus() != EnumBorderStatus.STATIONARY ? endTime - System.currentTimeMillis() : 0L;
	}

	public double getTargetSize() {
		return endDiameter;
	}

	public void setTransition(final double newSize) {
		startDiameter = newSize;
		endDiameter = newSize;
		endTime = System.currentTimeMillis();
		startTime = endTime;
		final Iterator var3 = getListeners().iterator();

		while (var3.hasNext()) {
			final IBorderListener var4 = (IBorderListener) var3.next();
			var4.onSizeChanged(this, newSize);
		}
	}

	public void setTransition(final double p_177738_1_, final double p_177738_3_, final long p_177738_5_) {
		startDiameter = p_177738_1_;
		endDiameter = p_177738_3_;
		startTime = System.currentTimeMillis();
		endTime = startTime + p_177738_5_;
		final Iterator var7 = getListeners().iterator();

		while (var7.hasNext()) {
			final IBorderListener var8 = (IBorderListener) var7.next();
			var8.func_177692_a(this, p_177738_1_, p_177738_3_, p_177738_5_);
		}
	}

	protected List getListeners() {
		return Lists.newArrayList(listeners);
	}

	public void addListener(final IBorderListener listener) {
		listeners.add(listener);
	}

	public void setSize(final int size) {
		worldSize = size;
	}

	public int getSize() {
		return worldSize;
	}

	public double getDamageBuffer() {
		return damageBuffer;
	}

	public void setDamageBuffer(final double p_177724_1_) {
		damageBuffer = p_177724_1_;
		final Iterator var3 = getListeners().iterator();

		while (var3.hasNext()) {
			final IBorderListener var4 = (IBorderListener) var3.next();
			var4.func_177695_c(this, p_177724_1_);
		}
	}

	public double func_177727_n() {
		return damageAmount;
	}

	public void func_177744_c(final double p_177744_1_) {
		damageAmount = p_177744_1_;
		final Iterator var3 = getListeners().iterator();

		while (var3.hasNext()) {
			final IBorderListener var4 = (IBorderListener) var3.next();
			var4.func_177696_b(this, p_177744_1_);
		}
	}

	public double func_177749_o() {
		return endTime == startTime ? 0.0D : Math.abs(startDiameter - endDiameter) / (endTime - startTime);
	}

	public int getWarningTime() {
		return warningTime;
	}

	public void setWarningTime(final int warningTime) {
		this.warningTime = warningTime;
		final Iterator var2 = getListeners().iterator();

		while (var2.hasNext()) {
			final IBorderListener var3 = (IBorderListener) var2.next();
			var3.onWarningTimeChanged(this, warningTime);
		}
	}

	public int getWarningDistance() {
		return warningDistance;
	}

	public void setWarningDistance(final int warningDistance) {
		this.warningDistance = warningDistance;
		final Iterator var2 = getListeners().iterator();

		while (var2.hasNext()) {
			final IBorderListener var3 = (IBorderListener) var2.next();
			var3.onWarningDistanceChanged(this, warningDistance);
		}
	}
}
