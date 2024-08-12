package net.minecraft.entity.ai.attributes;

import net.minecraft.server.management.LowerStringMap;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;

public abstract class BaseAttributeMap {

public static final int EaZy = 1036;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	protected final Map attributes = Maps.newHashMap();
	protected final Map attributesByName = new LowerStringMap();
	protected final Multimap field_180377_c = HashMultimap.create();
	// private static final String __OBFID = "http://https://fuckuskid00001566";

	public IAttributeInstance getAttributeInstance(final IAttribute p_111151_1_) {
		return (IAttributeInstance) attributes.get(p_111151_1_);
	}

	public IAttributeInstance getAttributeInstanceByName(final String p_111152_1_) {
		return (IAttributeInstance) attributesByName.get(p_111152_1_);
	}

	/**
	 * Registers an attribute with this AttributeMap, returns a modifiable
	 * AttributeInstance associated with this map
	 */
	public IAttributeInstance registerAttribute(final IAttribute p_111150_1_) {
		if (attributesByName.containsKey(p_111150_1_.getAttributeUnlocalizedName())) {
			throw new IllegalArgumentException("Attribute is already registered!");
		} else {
			final IAttributeInstance var2 = func_180376_c(p_111150_1_);
			attributesByName.put(p_111150_1_.getAttributeUnlocalizedName(), var2);
			attributes.put(p_111150_1_, var2);

			for (IAttribute var3 = p_111150_1_.func_180372_d(); var3 != null; var3 = var3.func_180372_d()) {
				field_180377_c.put(var3, p_111150_1_);
			}

			return var2;
		}
	}

	protected abstract IAttributeInstance func_180376_c(IAttribute var1);

	public Collection getAllAttributes() {
		return attributesByName.values();
	}

	public void func_180794_a(final IAttributeInstance p_180794_1_) {}

	public void removeAttributeModifiers(final Multimap p_111148_1_) {
		final Iterator var2 = p_111148_1_.entries().iterator();

		while (var2.hasNext()) {
			final Entry var3 = (Entry) var2.next();
			final IAttributeInstance var4 = getAttributeInstanceByName((String) var3.getKey());

			if (var4 != null) {
				var4.removeModifier((AttributeModifier) var3.getValue());
			}
		}
	}

	public void applyAttributeModifiers(final Multimap p_111147_1_) {
		final Iterator var2 = p_111147_1_.entries().iterator();

		while (var2.hasNext()) {
			final Entry var3 = (Entry) var2.next();
			final IAttributeInstance var4 = getAttributeInstanceByName((String) var3.getKey());

			if (var4 != null) {
				var4.removeModifier((AttributeModifier) var3.getValue());
				var4.applyModifier((AttributeModifier) var3.getValue());
			}
		}
	}
}
