package net.minecraft.world;

import net.minecraft.nbt.NBTTagCompound;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;

public class GameRules {

public static final int EaZy = 1724;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private final TreeMap theGameRules = new TreeMap();

	public GameRules() {
		addGameRule("doFireTick", "true", GameRules.ValueType.BOOLEAN_VALUE);
		addGameRule("mobGriefing", "true", GameRules.ValueType.BOOLEAN_VALUE);
		addGameRule("keepInventory", "false", GameRules.ValueType.BOOLEAN_VALUE);
		addGameRule("doMobSpawning", "true", GameRules.ValueType.BOOLEAN_VALUE);
		addGameRule("doMobLoot", "true", GameRules.ValueType.BOOLEAN_VALUE);
		addGameRule("doTileDrops", "true", GameRules.ValueType.BOOLEAN_VALUE);
		addGameRule("commandBlockOutput", "true", GameRules.ValueType.BOOLEAN_VALUE);
		addGameRule("naturalRegeneration", "true", GameRules.ValueType.BOOLEAN_VALUE);
		addGameRule("doDaylightCycle", "true", GameRules.ValueType.BOOLEAN_VALUE);
		addGameRule("logAdminCommands", "true", GameRules.ValueType.BOOLEAN_VALUE);
		addGameRule("showDeathMessages", "true", GameRules.ValueType.BOOLEAN_VALUE);
		addGameRule("randomTickSpeed", "3", GameRules.ValueType.NUMERICAL_VALUE);
		addGameRule("sendCommandFeedback", "true", GameRules.ValueType.BOOLEAN_VALUE);
		addGameRule("reducedDebugInfo", "false", GameRules.ValueType.BOOLEAN_VALUE);
	}

	public void addGameRule(final String key, final String value, final GameRules.ValueType type) {
		theGameRules.put(key, new GameRules.Value(value, type));
	}

	public void setOrCreateGameRule(final String key, final String ruleValue) {
		final GameRules.Value var3 = (GameRules.Value) theGameRules.get(key);

		if (var3 != null) {
			var3.setValue(ruleValue);
		} else {
			addGameRule(key, ruleValue, GameRules.ValueType.ANY_VALUE);
		}
	}

	/**
	 * Gets the string Game Rule value.
	 */
	public String getGameRuleStringValue(final String name) {
		final GameRules.Value var2 = (GameRules.Value) theGameRules.get(name);
		return var2 != null ? var2.getGameRuleStringValue() : "";
	}

	/**
	 * Gets the boolean Game Rule value.
	 */
	public boolean getGameRuleBooleanValue(final String name) {
		final GameRules.Value var2 = (GameRules.Value) theGameRules.get(name);
		return var2 != null ? var2.getGameRuleBooleanValue() : false;
	}

	public int getInt(final String name) {
		final GameRules.Value var2 = (GameRules.Value) theGameRules.get(name);
		return var2 != null ? var2.getInt() : 0;
	}

	/**
	 * Return the defined game rules as NBT.
	 */
	public NBTTagCompound writeGameRulesToNBT() {
		final NBTTagCompound var1 = new NBTTagCompound();
		final Iterator var2 = theGameRules.keySet().iterator();

		while (var2.hasNext()) {
			final String var3 = (String) var2.next();
			final GameRules.Value var4 = (GameRules.Value) theGameRules.get(var3);
			var1.setString(var3, var4.getGameRuleStringValue());
		}

		return var1;
	}

	/**
	 * Set defined game rules from NBT.
	 */
	public void readGameRulesFromNBT(final NBTTagCompound nbt) {
		final Set var2 = nbt.getKeySet();
		final Iterator var3 = var2.iterator();

		while (var3.hasNext()) {
			final String var4 = (String) var3.next();
			final String var6 = nbt.getString(var4);
			setOrCreateGameRule(var4, var6);
		}
	}

	/**
	 * Return the defined game rules.
	 */
	public String[] getRules() {
		return (String[]) theGameRules.keySet().toArray(new String[0]);
	}

	/**
	 * Return whether the specified game rule is defined.
	 */
	public boolean hasRule(final String name) {
		return theGameRules.containsKey(name);
	}

	public boolean areSameType(final String key, final GameRules.ValueType otherValue) {
		final GameRules.Value var3 = (GameRules.Value) theGameRules.get(key);
		return var3 != null && (var3.getType() == otherValue || otherValue == GameRules.ValueType.ANY_VALUE);
	}

	static class Value {
		private String valueString;
		private boolean valueBoolean;
		private int valueInteger;
		private double valueDouble;
		private final GameRules.ValueType type;
		// private static final String __OBFID =
		// "http://https://fuckuskid00000137";

		public Value(final String value, final GameRules.ValueType type) {
			this.type = type;
			setValue(value);
		}

		public void setValue(final String value) {
			valueString = value;

			if (value != null) {
				if (value.equals("false")) {
					valueBoolean = false;
					return;
				}

				if (value.equals("true")) {
					valueBoolean = true;
					return;
				}
			}

			valueBoolean = Boolean.parseBoolean(value);
			valueInteger = valueBoolean ? 1 : 0;

			try {
				valueInteger = Integer.parseInt(value);
			} catch (final NumberFormatException var4) {
			}

			try {
				valueDouble = Double.parseDouble(value);
			} catch (final NumberFormatException var3) {
			}
		}

		public String getGameRuleStringValue() {
			return valueString;
		}

		public boolean getGameRuleBooleanValue() {
			return valueBoolean;
		}

		public int getInt() {
			return valueInteger;
		}

		public GameRules.ValueType getType() {
			return type;
		}
	}

	public static enum ValueType {
		ANY_VALUE("ANY_VALUE", 0, "ANY_VALUE", 0), BOOLEAN_VALUE("BOOLEAN_VALUE", 1, "BOOLEAN_VALUE",
				1), NUMERICAL_VALUE("NUMERICAL_VALUE", 2, "NUMERICAL_VALUE", 2);
		private ValueType(final String p_i46394_1_, final int p_i46394_2_, final String p_i45750_1_,
				final int p_i45750_2_) {}
	}
}
