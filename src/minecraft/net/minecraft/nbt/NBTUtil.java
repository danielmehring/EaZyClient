package net.minecraft.nbt;

import net.minecraft.util.StringUtils;

import java.util.Iterator;
import java.util.UUID;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

public final class NBTUtil {

public static final int EaZy = 1351;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00001901";

	/**
	 * Reads and returns a GameProfile that has been saved to the passed in
	 * NBTTagCompound
	 */
	public static GameProfile readGameProfileFromNBT(final NBTTagCompound compound) {
		String var1 = null;
		String var2 = null;

		if (compound.hasKey("Name", 8)) {
			var1 = compound.getString("Name");
		}

		if (compound.hasKey("Id", 8)) {
			var2 = compound.getString("Id");
		}

		if (StringUtils.isNullOrEmpty(var1) && StringUtils.isNullOrEmpty(var2)) {
			return null;
		} else {
			UUID var3;

			try {
				var3 = UUID.fromString(var2);
			} catch (final Throwable var12) {
				var3 = null;
			}

			final GameProfile var4 = new GameProfile(var3, var1);

			if (compound.hasKey("Properties", 10)) {
				final NBTTagCompound var5 = compound.getCompoundTag("Properties");
				final Iterator var6 = var5.getKeySet().iterator();

				while (var6.hasNext()) {
					final String var7 = (String) var6.next();
					final NBTTagList var8 = var5.getTagList(var7, 10);

					for (int var9 = 0; var9 < var8.tagCount(); ++var9) {
						final NBTTagCompound var10 = var8.getCompoundTagAt(var9);
						final String var11 = var10.getString("Value");

						if (var10.hasKey("Signature", 8)) {
							var4.getProperties().put(var7, new Property(var7, var11, var10.getString("Signature")));
						} else {
							var4.getProperties().put(var7, new Property(var7, var11));
						}
					}
				}
			}

			return var4;
		}
	}

	public static NBTTagCompound writeGameProfile(final NBTTagCompound p_180708_0_, final GameProfile p_180708_1_) {
		if (!StringUtils.isNullOrEmpty(p_180708_1_.getName())) {
			p_180708_0_.setString("Name", p_180708_1_.getName());
		}

		if (p_180708_1_.getId() != null) {
			p_180708_0_.setString("Id", p_180708_1_.getId().toString());
		}

		if (!p_180708_1_.getProperties().isEmpty()) {
			final NBTTagCompound var2 = new NBTTagCompound();
			final Iterator var3 = p_180708_1_.getProperties().keySet().iterator();

			while (var3.hasNext()) {
				final String var4 = (String) var3.next();
				final NBTTagList var5 = new NBTTagList();
				NBTTagCompound var8;

				for (final Iterator var6 = p_180708_1_.getProperties().get(var4).iterator(); var6.hasNext(); var5
						.appendTag(var8)) {
					final Property var7 = (Property) var6.next();
					var8 = new NBTTagCompound();
					var8.setString("Value", var7.getValue());

					if (var7.hasSignature()) {
						var8.setString("Signature", var7.getSignature());
					}
				}

				var2.setTag(var4, var5);
			}

			p_180708_0_.setTag("Properties", var2);
		}

		return p_180708_0_;
	}
}
