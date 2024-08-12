package net.minecraft.entity.ai.attributes;

import net.minecraft.server.management.LowerStringMap;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Sets;

public class ServersideAttributeMap extends BaseAttributeMap {

public static final int EaZy = 1041;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final Set attributeInstanceSet = Sets.newHashSet();
	protected final Map descriptionToAttributeInstanceMap = new LowerStringMap();
	// private static final String __OBFID = "http://https://fuckuskid00001569";

	public ModifiableAttributeInstance func_180795_e(final IAttribute p_180795_1_) {
		return (ModifiableAttributeInstance) super.getAttributeInstance(p_180795_1_);
	}

	public ModifiableAttributeInstance func_180796_b(final String p_180796_1_) {
		IAttributeInstance var2 = super.getAttributeInstanceByName(p_180796_1_);

		if (var2 == null) {
			var2 = (IAttributeInstance) descriptionToAttributeInstanceMap.get(p_180796_1_);
		}

		return (ModifiableAttributeInstance) var2;
	}

	/**
	 * Registers an attribute with this AttributeMap, returns a modifiable
	 * AttributeInstance associated with this map
	 */
	@Override
	public IAttributeInstance registerAttribute(final IAttribute p_111150_1_) {
		final IAttributeInstance var2 = super.registerAttribute(p_111150_1_);

		if (p_111150_1_ instanceof RangedAttribute && ((RangedAttribute) p_111150_1_).getDescription() != null) {
			descriptionToAttributeInstanceMap.put(((RangedAttribute) p_111150_1_).getDescription(), var2);
		}

		return var2;
	}

	@Override
	protected IAttributeInstance func_180376_c(final IAttribute p_180376_1_) {
		return new ModifiableAttributeInstance(this, p_180376_1_);
	}

	@Override
	public void func_180794_a(final IAttributeInstance p_180794_1_) {
		if (p_180794_1_.getAttribute().getShouldWatch()) {
			attributeInstanceSet.add(p_180794_1_);
		}

		final Iterator var2 = field_180377_c.get(p_180794_1_.getAttribute()).iterator();

		while (var2.hasNext()) {
			final IAttribute var3 = (IAttribute) var2.next();
			final ModifiableAttributeInstance var4 = func_180795_e(var3);

			if (var4 != null) {
				var4.flagForUpdate();
			}
		}
	}

	public Set getAttributeInstanceSet() {
		return attributeInstanceSet;
	}

	public Collection getWatchedAttributes() {
		final HashSet var1 = Sets.newHashSet();
		final Iterator var2 = getAllAttributes().iterator();

		while (var2.hasNext()) {
			final IAttributeInstance var3 = (IAttributeInstance) var2.next();

			if (var3.getAttribute().getShouldWatch()) {
				var1.add(var3);
			}
		}

		return var1;
	}

	@Override
	public IAttributeInstance getAttributeInstanceByName(final String p_111152_1_) {
		return func_180796_b(p_111152_1_);
	}

	@Override
	public IAttributeInstance getAttributeInstance(final IAttribute p_111151_1_) {
		return func_180795_e(p_111151_1_);
	}
}
