package net.minecraft.util;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;

import com.google.common.base.Function;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;

public abstract class ChatComponentStyle implements IChatComponent {

public static final int EaZy = 1597;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	/**
	 * The later siblings of this component. If this component turns the text
	 * bold, that will apply to all the siblings until a later sibling turns the
	 * text something else.
	 */
	protected List siblings = Lists.newArrayList();
	private ChatStyle style;
	// private static final String __OBFID = "http://https://fuckuskid00001257";

	/**
	 * Appends the given component to the end of this one.
	 */
	@Override
	public IChatComponent appendSibling(final IChatComponent component) {
		component.getChatStyle().setParentStyle(getChatStyle());
		siblings.add(component);
		return this;
	}

	/**
	 * Gets the sibling components of this one.
	 */
	@Override
	public List getSiblings() {
		return siblings;
	}

	/**
	 * Appends the given text to the end of this component.
	 */
	@Override
	public IChatComponent appendText(final String text) {
		return appendSibling(new ChatComponentText(text));
	}

	@Override
	public IChatComponent setChatStyle(final ChatStyle style) {
		this.style = style;
		final Iterator var2 = siblings.iterator();

		while (var2.hasNext()) {
			final IChatComponent var3 = (IChatComponent) var2.next();
			var3.getChatStyle().setParentStyle(getChatStyle());
		}

		return this;
	}

	@Override
	public ChatStyle getChatStyle() {
		if (style == null) {
			style = new ChatStyle();
			final Iterator var1 = siblings.iterator();

			while (var1.hasNext()) {
				final IChatComponent var2 = (IChatComponent) var1.next();
				var2.getChatStyle().setParentStyle(style);
			}
		}

		return style;
	}

	@Override
	public Iterator iterator() {
		return Iterators.concat(Iterators.forArray(new ChatComponentStyle[] { this }),
				createDeepCopyIterator(siblings));
	}

	/**
	 * Gets the text of this component, without any special formatting codes
	 * added. TODO: why is this two different methods?
	 */
	@Override
	public final String getUnformattedText() {
		final StringBuilder var1 = new StringBuilder();
		final Iterator var2 = iterator();

		while (var2.hasNext()) {
			final IChatComponent var3 = (IChatComponent) var2.next();
			var1.append(var3.getUnformattedTextForChat());
		}

		return var1.toString();
	}

	/**
	 * Gets the text of this component, with formatting codes added for
	 * rendering.
	 */
	@Override
	public final String getFormattedText() {
		try {
			final StringBuilder var1 = new StringBuilder();
			final Iterator var2 = iterator();

			while (var2.hasNext()) {
				final IChatComponent var3 = (IChatComponent) var2.next();
				var1.append(var3.getChatStyle().getFormattingCode());
				var1.append(var3.getUnformattedTextForChat());
				var1.append(EnumChatFormatting.RESET);
			}

			return var1.toString();
		} catch (final ConcurrentModificationException e) {
			return "*ERROR GETTING STRING*";
		}
	}

	/**
	 * Creates an iterator that iterates over the given components, returning
	 * deep copies of each component in turn so that the properties of the
	 * returned objects will remain externally consistent after being returned.
	 */
	public static Iterator createDeepCopyIterator(final Iterable components) {
		Iterator var1 = Iterators.concat(Iterators.transform(components.iterator(), new Function() {
			// private static final String __OBFID =
			// "http://https://fuckuskid00001258";

			public Iterator apply(final IChatComponent p_apply_1_) {
				return p_apply_1_.iterator();
			}

			@Override
			public Object apply(final Object p_apply_1_) {
				return this.apply((IChatComponent) p_apply_1_);
			}
		}));
		var1 = Iterators.transform(var1, new Function() {
			// private static final String __OBFID =
			// "http://https://fuckuskid00001259";

			public IChatComponent apply(final IChatComponent p_apply_1_) {
				final IChatComponent var2 = p_apply_1_.createCopy();
				var2.setChatStyle(var2.getChatStyle().createDeepCopy());
				return var2;
			}

			@Override
			public Object apply(final Object p_apply_1_) {
				return this.apply((IChatComponent) p_apply_1_);
			}
		});
		return var1;
	}

	@Override
	public boolean equals(final Object p_equals_1_) {
		if (this == p_equals_1_) {
			return true;
		} else if (!(p_equals_1_ instanceof ChatComponentStyle)) {
			return false;
		} else {
			final ChatComponentStyle var2 = (ChatComponentStyle) p_equals_1_;
			return siblings.equals(var2.siblings) && getChatStyle().equals(var2.getChatStyle());
		}
	}

	@Override
	public int hashCode() {
		return 31 * style.hashCode() + siblings.hashCode();
	}

	@Override
	public String toString() {
		return "BaseComponent{style=" + style + ", siblings=" + siblings + '}';
	}
}
