package net.minecraft.command;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;

import java.util.List;

public class CommandEnchant extends CommandBase {

public static final int EaZy = 934;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00000377";

	@Override
	public String getCommandName() {
		return "enchant";
	}

	/**
	 * Return the required permission level for this command.
	 */
	@Override
	public int getRequiredPermissionLevel() {
		return 2;
	}

	@Override
	public String getCommandUsage(final ICommandSender sender) {
		return "commands.enchant.usage";
	}

	@Override
	public void processCommand(final ICommandSender sender, final String[] args) throws CommandException {
		if (args.length < 2) {
			throw new WrongUsageException("commands.enchant.usage", new Object[0]);
		} else {
			final EntityPlayerMP var3 = getPlayer(sender, args[0]);
			sender.func_174794_a(CommandResultStats.Type.AFFECTED_ITEMS, 0);
			int var4;

			try {
				var4 = parseInt(args[1], 0);
			} catch (final NumberInvalidException var12) {
				final Enchantment var6 = Enchantment.func_180305_b(args[1]);

				if (var6 == null) {
					throw var12;
				}

				var4 = var6.effectId;
			}

			int var5 = 1;
			final ItemStack var13 = var3.getCurrentEquippedItem();

			if (var13 == null) {
				throw new CommandException("commands.enchant.noItem", new Object[0]);
			} else {
				final Enchantment var7 = Enchantment.byID(var4);

				if (var7 == null) {
					throw new NumberInvalidException("commands.enchant.notFound",
							new Object[] { var4});
				} else if (!var7.canApply(var13)) {
					throw new CommandException("commands.enchant.cantEnchant", new Object[0]);
				} else {
					if (args.length >= 3) {
						var5 = parseInt(args[2], var7.getMinLevel(), var7.getMaxLevel());
					}

					if (var13.hasTagCompound()) {
						final NBTTagList var8 = var13.getEnchantmentTagList();

						if (var8 != null) {
							for (int var9 = 0; var9 < var8.tagCount(); ++var9) {
								final short var10 = var8.getCompoundTagAt(var9).getShort("id");

								if (Enchantment.byID(var10) != null) {
									final Enchantment var11 = Enchantment.byID(var10);

									if (!var11.canApplyTogether(var7)) {
										throw new CommandException("commands.enchant.cantCombine", new Object[] {
												var7.getTranslatedName(var5),
												var11.getTranslatedName(var8.getCompoundTagAt(var9).getShort("lvl")) });
									}
								}
							}
						}
					}

					var13.addEnchantment(var7, var5);
					notifyOperators(sender, this, "commands.enchant.success", new Object[0]);
					sender.func_174794_a(CommandResultStats.Type.AFFECTED_ITEMS, 1);
				}
			}
		}
	}

	@Override
	public List addTabCompletionOptions(final ICommandSender sender, final String[] args, final BlockPos pos) {
		return args.length == 1 ? getListOfStringsMatchingLastWord(args, getListOfPlayers())
				: args.length == 2 ? getListOfStringsMatchingLastWord(args, Enchantment.func_180304_c()) : null;
	}

	protected String[] getListOfPlayers() {
		return MinecraftServer.getServer().getAllUsernames();
	}

	/**
	 * Return whether the specified command parameter index is a username
	 * parameter.
	 */
	@Override
	public boolean isUsernameIndex(final String[] args, final int index) {
		return index == 0;
	}
}
