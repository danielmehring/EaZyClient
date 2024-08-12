package net.minecraft.util;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.ClassUtils.Interfaces;

import com.google.common.collect.AbstractIterator;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Iterators;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;

public class ClassInheratanceMultiMap extends AbstractSet {

public static final int EaZy = 1602;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final Multimap field_180218_a = HashMultimap.create();
	private final Set field_180216_b = Sets.newIdentityHashSet();
	private final Class field_180217_c;
	// private static final String __OBFID = "http://https://fuckuskid00002266";

	public ClassInheratanceMultiMap(final Class p_i45909_1_) {
		field_180217_c = p_i45909_1_;
		field_180216_b.add(p_i45909_1_);
	}

	public void func_180213_a(final Class p_180213_1_) {
		final Iterator var2 = field_180218_a.get(func_180212_a(p_180213_1_, false)).iterator();

		while (var2.hasNext()) {
			final Object var3 = var2.next();

			if (p_180213_1_.isAssignableFrom(var3.getClass())) {
				field_180218_a.put(p_180213_1_, var3);
			}
		}

		field_180216_b.add(p_180213_1_);
	}

	protected Class func_180212_a(final Class p_180212_1_, final boolean p_180212_2_) {
		final Iterator var3 = ClassUtils.hierarchy(p_180212_1_, Interfaces.INCLUDE).iterator();
		Class var4;

		do {
			if (!var3.hasNext()) {
				throw new IllegalArgumentException("Don\'t know how to search for " + p_180212_1_);
			}

			var4 = (Class) var3.next();
		}
		while (!field_180216_b.contains(var4));

		if (var4 == field_180217_c && p_180212_2_) {
			func_180213_a(p_180212_1_);
		}

		return var4;
	}

	@Override
	public boolean add(final Object p_add_1_) {
		final Iterator var2 = field_180216_b.iterator();

		while (var2.hasNext()) {
			final Class var3 = (Class) var2.next();

			if (var3.isAssignableFrom(p_add_1_.getClass())) {
				field_180218_a.put(var3, p_add_1_);
			}
		}

		return true;
	}

	@Override
	public boolean remove(final Object p_remove_1_) {
		final Object var2 = p_remove_1_;
		boolean var3 = false;
		final Iterator var4 = field_180216_b.iterator();

		while (var4.hasNext()) {
			final Class var5 = (Class) var4.next();

			if (var5.isAssignableFrom(var2.getClass())) {
				var3 |= field_180218_a.remove(var5, var2);
			}
		}

		return var3;
	}

	public Iterable func_180215_b(final Class p_180215_1_) {
		return new Iterable() {
			// private static final String __OBFID =
			// "http://https://fuckuskid00002265";
			@Override
			public Iterator iterator() {
				final Iterator var1 = field_180218_a.get(ClassInheratanceMultiMap.this.func_180212_a(p_180215_1_, true))
						.iterator();
				return Iterators.filter(var1, p_180215_1_);
			}
		};
	}

	@Override
	public Iterator iterator() {
		final Iterator var1 = field_180218_a.get(field_180217_c).iterator();
		return new AbstractIterator() {
			// private static final String __OBFID =
			// "http://https://fuckuskid00002264";
			@Override
			protected Object computeNext() {
				return !var1.hasNext() ? endOfData() : var1.next();
			}
		};
	}

	@Override
	public int size() {
		return field_180218_a.get(field_180217_c).size();
	}
}
