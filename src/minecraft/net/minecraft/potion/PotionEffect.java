package net.minecraft.potion;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PotionEffect {

public static final int EaZy = 1493;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final Logger LOGGER = LogManager.getLogger();

	/** ID value of the potion this effect matches. */
	private final int potionID;

	/** The duration of the potion effect */
	private int duration;

	/** The amplifier of the potion effect */
	private int amplifier;

	/** Whether the potion is a splash potion */
	private boolean isSplashPotion;

	/** Whether the potion effect came from a beacon */
	private boolean isAmbient;

	/** True if potion effect duration is at maximum, false otherwise. */
	private boolean isPotionDurationMax;
	private boolean showParticles;
	// private static final String __OBFID = "http://https://fuckuskid00001529";

	public PotionEffect(final int id, final int effectDuration) {
		this(id, effectDuration, 0);
	}

	public PotionEffect(final int id, final int effectDuration, final int effectAmplifier) {
		this(id, effectDuration, effectAmplifier, false, true);
	}

	public PotionEffect(final int id, final int effectDuration, final int effectAmplifier, final boolean ambient,
			final boolean showParticles) {
		potionID = id;
		duration = effectDuration;
		amplifier = effectAmplifier;
		isAmbient = ambient;
		this.showParticles = showParticles;
	}

	public PotionEffect(final PotionEffect other) {
		potionID = other.potionID;
		duration = other.duration;
		amplifier = other.amplifier;
		isAmbient = other.isAmbient;
		showParticles = other.showParticles;
	}

	/**
	 * merges the input PotionEffect into this one if this.amplifier <=
	 * tomerge.amplifier. The duration in the supplied potion effect is assumed
	 * to be greater.
	 */
	public void combine(final PotionEffect other) {
		if (potionID != other.potionID) {
			LOGGER.warn("This method should only be called for matching effects!");
		}

		if (other.amplifier > amplifier) {
			amplifier = other.amplifier;
			duration = other.duration;
		} else if (other.amplifier == amplifier && duration < other.duration) {
			duration = other.duration;
		} else if (!other.isAmbient && isAmbient) {
			isAmbient = other.isAmbient;
		}

		showParticles = other.showParticles;
	}

	/**
	 * Retrieve the ID of the potion this effect matches.
	 */
	public int getPotionID() {
		return potionID;
	}

	public int getDuration() {
		return duration;
	}

	public int getAmplifier() {
		return amplifier;
	}

	/**
	 * Set whether this potion is a splash potion.
	 */
	public void setSplashPotion(final boolean splashPotion) {
		isSplashPotion = splashPotion;
	}

	/**
	 * Gets whether this potion effect originated from a beacon
	 */
	public boolean getIsAmbient() {
		return isAmbient;
	}

	public boolean func_180154_f() {
		return showParticles;
	}

	public boolean onUpdate(final EntityLivingBase entityIn) {
		if (duration > 0) {
			if (Potion.potionTypes[potionID].isReady(duration, amplifier)) {
				performEffect(entityIn);
			}

			deincrementDuration();
		}

		return duration > 0;
	}

	public int deincrementDuration() {
		return --duration;
	}

	public void performEffect(final EntityLivingBase entityIn) {
		if (duration > 0) {
			Potion.potionTypes[potionID].performEffect(entityIn, amplifier);
		}
	}

	public String getEffectName() {
		return Potion.potionTypes[potionID].getName();
	}

	@Override
	public int hashCode() {
		return potionID;
	}

	@Override
	public String toString() {
		String var1 = "";

		if (getAmplifier() > 0) {
			var1 = getEffectName() + " x " + (getAmplifier() + 1) + ", Duration: " + getDuration();
		} else {
			var1 = getEffectName() + ", Duration: " + getDuration();
		}

		if (isSplashPotion) {
			var1 = var1 + ", Splash: true";
		}

		if (!showParticles) {
			var1 = var1 + ", Particles: false";
		}

		return Potion.potionTypes[potionID].isUsable() ? "(" + var1 + ")" : var1;
	}

	@Override
	public boolean equals(final Object p_equals_1_) {
		if (!(p_equals_1_ instanceof PotionEffect)) {
			return false;
		} else {
			final PotionEffect var2 = (PotionEffect) p_equals_1_;
			return potionID == var2.potionID && amplifier == var2.amplifier && duration == var2.duration
					&& isSplashPotion == var2.isSplashPotion && isAmbient == var2.isAmbient;
		}
	}

	/**
	 * Write a custom potion effect to a potion item's NBT data.
	 */
	public NBTTagCompound writeCustomPotionEffectToNBT(final NBTTagCompound nbt) {
		nbt.setByte("Id", (byte) getPotionID());
		nbt.setByte("Amplifier", (byte) getAmplifier());
		nbt.setInteger("Duration", getDuration());
		nbt.setBoolean("Ambient", getIsAmbient());
		nbt.setBoolean("ShowParticles", func_180154_f());
		return nbt;
	}

	/**
	 * Read a custom potion effect from a potion item's NBT data.
	 */
	public static PotionEffect readCustomPotionEffectFromNBT(final NBTTagCompound nbt) {
		final byte var1 = nbt.getByte("Id");

		if (var1 >= 0 && var1 < Potion.potionTypes.length && Potion.potionTypes[var1] != null) {
			final byte var2 = nbt.getByte("Amplifier");
			final int var3 = nbt.getInteger("Duration");
			final boolean var4 = nbt.getBoolean("Ambient");
			boolean var5 = true;

			if (nbt.hasKey("ShowParticles", 1)) {
				var5 = nbt.getBoolean("ShowParticles");
			}

			return new PotionEffect(var1, var3, var2, var4, var5);
		} else {
			return null;
		}
	}

	/**
	 * Toggle the isPotionDurationMax field.
	 */
	public void setPotionDurationMax(final boolean maxDuration) {
		isPotionDurationMax = maxDuration;
	}

	public boolean getIsPotionDurationMax() {
		return isPotionDurationMax;
	}
}
