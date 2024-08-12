package net.minecraft.entity.ai.attributes;

import net.minecraft.util.MathHelper;

import java.util.UUID;

import org.apache.commons.lang3.Validate;

import io.netty.util.internal.ThreadLocalRandom;

public class AttributeModifier {

public static final int EaZy = 1034;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final double amount;
	private final int operation;
	private final String name;
	private final UUID id;

	/**
	 * If false, this modifier is not saved in NBT. Used for "natural" modifiers
	 * like speed boost from sprinting
	 */
	private boolean isSaved;
	// private static final String __OBFID = "http://https://fuckuskid00001564";

	public AttributeModifier(final String p_i1605_1_, final double p_i1605_2_, final int p_i1605_4_) {
		this(MathHelper.func_180182_a(ThreadLocalRandom.current()), p_i1605_1_, p_i1605_2_, p_i1605_4_);
	}

	public AttributeModifier(final UUID p_i1606_1_, final String p_i1606_2_, final double p_i1606_3_,
			final int p_i1606_5_) {
		isSaved = true;
		id = p_i1606_1_;
		name = p_i1606_2_;
		amount = p_i1606_3_;
		operation = p_i1606_5_;
		Validate.notEmpty(p_i1606_2_, "Modifier name cannot be empty", new Object[0]);
		Validate.inclusiveBetween(0L, 2L, p_i1606_5_, "Invalid operation");
	}

	public UUID getID() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getOperation() {
		return operation;
	}

	public double getAmount() {
		return amount;
	}

	/**
	 * @see #isSaved
	 */
	public boolean isSaved() {
		return isSaved;
	}

	/**
	 * @see #isSaved
	 */
	public AttributeModifier setSaved(final boolean p_111168_1_) {
		isSaved = p_111168_1_;
		return this;
	}

	@Override
	public boolean equals(final Object p_equals_1_) {
		if (this == p_equals_1_) {
			return true;
		} else if (p_equals_1_ != null && this.getClass() == p_equals_1_.getClass()) {
			final AttributeModifier var2 = (AttributeModifier) p_equals_1_;

			if (id != null) {
				if (!id.equals(var2.id)) {
					return false;
				}
			} else if (var2.id != null) {
				return false;
			}

			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return id != null ? id.hashCode() : 0;
	}

	@Override
	public String toString() {
		return "AttributeModifier{amount=" + amount + ", operation=" + operation + ", name=\'" + name + '\'' + ", id="
				+ id + ", serialize=" + isSaved + '}';
	}
}
