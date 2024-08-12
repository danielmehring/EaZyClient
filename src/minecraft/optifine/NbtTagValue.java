package optifine;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagLong;
import net.minecraft.nbt.NBTTagShort;
import net.minecraft.nbt.NBTTagString;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.lang3.StringEscapeUtils;

public class NbtTagValue {

public static final int EaZy = 1938;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private String[] parents = null;
	private String name = null;
	private int type = 0;
	private String value = null;

	public NbtTagValue(final String tag, String value) {
		final String[] names = Config.tokenize(tag, ".");
		parents = Arrays.copyOfRange(names, 0, names.length - 1);
		name = names[names.length - 1];

		if (value.startsWith("pattern:")) {
			type = 1;
			value = value.substring("pattern:".length());
		} else if (value.startsWith("ipattern:")) {
			type = 2;
			value = value.substring("ipattern:".length()).toLowerCase();
		} else if (value.startsWith("regex:")) {
			type = 3;
			value = value.substring("regex:".length());
		} else if (value.startsWith("iregex:")) {
			type = 4;
			value = value.substring("iregex:".length()).toLowerCase();
		} else {
			type = 0;
		}

		value = StringEscapeUtils.unescapeJava(value);
		this.value = value;
	}

	public boolean matches(final NBTTagCompound nbt) {
		if (nbt == null) {
			return false;
		} else {
			Object tagBase = nbt;

			for (final String tag : parents) {
				tagBase = getChildTag((NBTBase) tagBase, tag);

				if (tagBase == null) {
					return false;
				}
			}

			if (name.equals("*")) {
				return matchesAnyChild((NBTBase) tagBase);
			} else {
				final NBTBase var5 = getChildTag((NBTBase) tagBase, name);

				if (var5 == null) {
					return false;
				} else return this.matches(var5);
			}
		}
	}

	private boolean matchesAnyChild(final NBTBase tagBase) {
		if (tagBase instanceof NBTTagCompound) {
			final NBTTagCompound tagList = (NBTTagCompound) tagBase;
			final Set count = tagList.getKeySet();
			final Iterator i = count.iterator();

			while (i.hasNext()) {
				final String nbtBase = (String) i.next();
				final NBTBase nbtBase1 = tagList.getTag(nbtBase);

				if (this.matches(nbtBase1)) {
					return true;
				}
			}
		}

		if (tagBase instanceof NBTTagList) {
			final NBTTagList var7 = (NBTTagList) tagBase;
			final int var8 = var7.tagCount();

			for (int var9 = 0; var9 < var8; ++var9) {
				final NBTBase var10 = var7.get(var9);

				if (this.matches(var10)) {
					return true;
				}
			}
		}

		return false;
	}

	private static NBTBase getChildTag(final NBTBase tagBase, final String tag) {
		if (tagBase instanceof NBTTagCompound) {
			final NBTTagCompound tagList1 = (NBTTagCompound) tagBase;
			return tagList1.getTag(tag);
		} else if (tagBase instanceof NBTTagList) {
			final NBTTagList tagList = (NBTTagList) tagBase;
			final int index = Config.parseInt(tag, -1);
			return index < 0 ? null : tagList.get(index);
		} else {
			return null;
		}
	}

	private boolean matches(final NBTBase nbtBase) {
		if (nbtBase == null) {
			return false;
		} else {
			final String nbtValue = getValue(nbtBase);

			if (nbtValue == null) {
				return false;
			} else {
				switch (type) {
					case 0:
						return nbtValue.equals(value);

					case 1:
						return matchesPattern(nbtValue, value);

					case 2:
						return matchesPattern(nbtValue.toLowerCase(), value);

					case 3:
						return matchesRegex(nbtValue, value);

					case 4:
						return matchesRegex(nbtValue.toLowerCase(), value);

					default:
						throw new IllegalArgumentException("Unknown NbtTagValue type: " + type);
				}
			}
		}
	}

	private boolean matchesPattern(final String str, final String pattern) {
		return StrUtils.equalsMask(str, pattern, '*', '?');
	}

	private boolean matchesRegex(final String str, final String regex) {
		return str.matches(regex);
	}

	private static String getValue(final NBTBase nbtBase) {
		if (nbtBase == null) {
			return null;
		} else if (nbtBase instanceof NBTTagString) {
			final NBTTagString d6 = (NBTTagString) nbtBase;
			return d6.getString();
		} else if (nbtBase instanceof NBTTagInt) {
			final NBTTagInt d5 = (NBTTagInt) nbtBase;
			return Integer.toString(d5.getInt());
		} else if (nbtBase instanceof NBTTagByte) {
			final NBTTagByte d4 = (NBTTagByte) nbtBase;
			return Byte.toString(d4.getByte());
		} else if (nbtBase instanceof NBTTagShort) {
			final NBTTagShort d3 = (NBTTagShort) nbtBase;
			return Short.toString(d3.getShort());
		} else if (nbtBase instanceof NBTTagLong) {
			final NBTTagLong d2 = (NBTTagLong) nbtBase;
			return Long.toString(d2.getLong());
		} else if (nbtBase instanceof NBTTagFloat) {
			final NBTTagFloat d1 = (NBTTagFloat) nbtBase;
			return Float.toString(d1.getFloat());
		} else if (nbtBase instanceof NBTTagDouble) {
			final NBTTagDouble d = (NBTTagDouble) nbtBase;
			return Double.toString(d.getDouble());
		} else {
			return nbtBase.toString();
		}
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer();

		for (int i = 0; i < parents.length; ++i) {
			final String parent = parents[i];

			if (i > 0) {
				sb.append(".");
			}

			sb.append(parent);
		}

		if (sb.length() > 0) {
			sb.append(".");
		}

		sb.append(name);
		sb.append(" = ");
		sb.append(value);
		return sb.toString();
	}
}
