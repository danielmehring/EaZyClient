package net.minecraft.client.renderer.chunk;

import net.minecraft.util.EnumFacing;

import java.util.BitSet;
import java.util.Iterator;
import java.util.Set;

public class SetVisibility {

public static final int EaZy = 704;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final int field_178623_a = EnumFacing.values().length;
	private final BitSet field_178622_b;
	// private static final String __OBFID = "http://https://fuckuskid00002448";

	public SetVisibility() {
		field_178622_b = new BitSet(field_178623_a * field_178623_a);
	}

	public void func_178620_a(final Set p_178620_1_) {
		final Iterator var2 = p_178620_1_.iterator();

		while (var2.hasNext()) {
			final EnumFacing var3 = (EnumFacing) var2.next();
			final Iterator var4 = p_178620_1_.iterator();

			while (var4.hasNext()) {
				final EnumFacing var5 = (EnumFacing) var4.next();
				func_178619_a(var3, var5, true);
			}
		}
	}

	public void func_178619_a(final EnumFacing p_178619_1_, final EnumFacing p_178619_2_, final boolean p_178619_3_) {
		field_178622_b.set(p_178619_1_.ordinal() + p_178619_2_.ordinal() * field_178623_a, p_178619_3_);
		field_178622_b.set(p_178619_2_.ordinal() + p_178619_1_.ordinal() * field_178623_a, p_178619_3_);
	}

	public void func_178618_a(final boolean p_178618_1_) {
		field_178622_b.set(0, field_178622_b.size(), p_178618_1_);
	}

	public boolean func_178621_a(final EnumFacing p_178621_1_, final EnumFacing p_178621_2_) {
		return field_178622_b.get(p_178621_1_.ordinal() + p_178621_2_.ordinal() * field_178623_a);
	}

	@Override
	public String toString() {
		final StringBuilder var1 = new StringBuilder();
		var1.append(' ');
		EnumFacing[] var2 = EnumFacing.values();
		int var3 = var2.length;
		int var4;
		EnumFacing var5;

		for (var4 = 0; var4 < var3; ++var4) {
			var5 = var2[var4];
			var1.append(' ').append(var5.toString().toUpperCase().charAt(0));
		}

		var1.append('\n');
		var2 = EnumFacing.values();
		var3 = var2.length;

		for (var4 = 0; var4 < var3; ++var4) {
			var5 = var2[var4];
			var1.append(var5.toString().toUpperCase().charAt(0));
			final EnumFacing[] var6 = EnumFacing.values();
			final int var7 = var6.length;

			for (int var8 = 0; var8 < var7; ++var8) {
				final EnumFacing var9 = var6[var8];

				if (var5 == var9) {
					var1.append("  ");
				} else {
					final boolean var10 = func_178621_a(var5, var9);
					var1.append(' ').append(var10 ? 'Y' : 'n');
				}
			}

			var1.append('\n');
		}

		return var1.toString();
	}
}
