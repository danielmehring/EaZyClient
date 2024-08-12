package net.minecraft.entity.ai.attributes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

public class ModifiableAttributeInstance implements IAttributeInstance {

public static final int EaZy = 1039;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	/** The BaseAttributeMap this attributeInstance can be found in */
	private final BaseAttributeMap attributeMap;

	/** The Attribute this is an instance of */
	private final IAttribute genericAttribute;
	private final Map mapByOperation = Maps.newHashMap();
	private final Map mapByName = Maps.newHashMap();
	private final Map mapByUUID = Maps.newHashMap();
	private double baseValue;
	private boolean needsUpdate = true;
	private double cachedValue;
	// private static final String __OBFID = "http://https://fuckuskid00001567";

	public ModifiableAttributeInstance(final BaseAttributeMap p_i1608_1_, final IAttribute p_i1608_2_) {
		attributeMap = p_i1608_1_;
		genericAttribute = p_i1608_2_;
		baseValue = p_i1608_2_.getDefaultValue();

		for (int var3 = 0; var3 < 3; ++var3) {
			mapByOperation.put(var3, Sets.newHashSet());
		}
	}

	/**
	 * Get the Attribute this is an instance of
	 */
	@Override
	public IAttribute getAttribute() {
		return genericAttribute;
	}

	@Override
	public double getBaseValue() {
		return baseValue;
	}

	@Override
	public void setBaseValue(final double p_111128_1_) {
		if (p_111128_1_ != getBaseValue()) {
			baseValue = p_111128_1_;
			flagForUpdate();
		}
	}

	@Override
	public Collection getModifiersByOperation(final int p_111130_1_) {
		return (Collection) mapByOperation.get(p_111130_1_);
	}

	@Override
	public Collection func_111122_c() {
		final HashSet var1 = Sets.newHashSet();

		for (int var2 = 0; var2 < 3; ++var2) {
			var1.addAll(getModifiersByOperation(var2));
		}

		return var1;
	}

	/**
	 * Returns attribute modifier, if any, by the given UUID
	 */
	@Override
	public AttributeModifier getModifier(final UUID p_111127_1_) {
		return (AttributeModifier) mapByUUID.get(p_111127_1_);
	}

	@Override
	public boolean func_180374_a(final AttributeModifier p_180374_1_) {
		return mapByUUID.get(p_180374_1_.getID()) != null;
	}

	@Override
	public void applyModifier(final AttributeModifier p_111121_1_) {
		if (getModifier(p_111121_1_.getID()) != null) {
			throw new IllegalArgumentException("Modifier is already applied on this attribute!");
		} else {
			Object var2 = mapByName.get(p_111121_1_.getName());

			if (var2 == null) {
				var2 = Sets.newHashSet();
				mapByName.put(p_111121_1_.getName(), var2);
			}

			((Set) mapByOperation.get(p_111121_1_.getOperation())).add(p_111121_1_);
			((Set) var2).add(p_111121_1_);
			mapByUUID.put(p_111121_1_.getID(), p_111121_1_);
			flagForUpdate();
		}
	}

	protected void flagForUpdate() {
		needsUpdate = true;
		attributeMap.func_180794_a(this);
	}

	@Override
	public void removeModifier(final AttributeModifier p_111124_1_) {
		for (int var2 = 0; var2 < 3; ++var2) {
			final Set var3 = (Set) mapByOperation.get(var2);
			var3.remove(p_111124_1_);
		}

		final Set var4 = (Set) mapByName.get(p_111124_1_.getName());

		if (var4 != null) {
			var4.remove(p_111124_1_);

			if (var4.isEmpty()) {
				mapByName.remove(p_111124_1_.getName());
			}
		}

		mapByUUID.remove(p_111124_1_.getID());
		flagForUpdate();
	}

	@Override
	public void removeAllModifiers() {
		final Collection var1 = func_111122_c();

		if (var1 != null) {
			final ArrayList var4 = Lists.newArrayList(var1);
			final Iterator var2 = var4.iterator();

			while (var2.hasNext()) {
				final AttributeModifier var3 = (AttributeModifier) var2.next();
				removeModifier(var3);
			}
		}
	}

	@Override
	public double getAttributeValue() {
		if (needsUpdate) {
			cachedValue = computeValue();
			needsUpdate = false;
		}

		return cachedValue;
	}

	private double computeValue() {
		double var1 = getBaseValue();
		AttributeModifier var4;

		for (final Iterator var3 = func_180375_b(0).iterator(); var3.hasNext(); var1 += var4.getAmount()) {
			var4 = (AttributeModifier) var3.next();
		}

		double var7 = var1;
		Iterator var5;
		AttributeModifier var6;

		for (var5 = func_180375_b(1).iterator(); var5.hasNext(); var7 += var1 * var6.getAmount()) {
			var6 = (AttributeModifier) var5.next();
		}

		for (var5 = func_180375_b(2).iterator(); var5.hasNext(); var7 *= 1.0D + var6.getAmount()) {
			var6 = (AttributeModifier) var5.next();
		}

		return genericAttribute.clampValue(var7);
	}

	private Collection func_180375_b(final int p_180375_1_) {
		final HashSet var2 = Sets.newHashSet(getModifiersByOperation(p_180375_1_));

		for (IAttribute var3 = genericAttribute.func_180372_d(); var3 != null; var3 = var3.func_180372_d()) {
			final IAttributeInstance var4 = attributeMap.getAttributeInstance(var3);

			if (var4 != null) {
				var2.addAll(var4.getModifiersByOperation(p_180375_1_));
			}
		}

		return var2;
	}
}
