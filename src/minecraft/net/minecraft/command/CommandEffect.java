package net.minecraft.command;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;

import java.util.List;

public class CommandEffect extends CommandBase {

public static final int EaZy = 933;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00000323";

	@Override
	public String getCommandName() {
		return "effect";
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
		return "commands.effect.usage";
	}

	@Override
	public void processCommand(final ICommandSender sender, final String[] args) throws CommandException {
		if (args.length < 2) {
			throw new WrongUsageException("commands.effect.usage", new Object[0]);
		} else {
			final EntityLivingBase var3 = (EntityLivingBase) func_175759_a(sender, args[0], EntityLivingBase.class);

			if (args[1].equals("clear")) {
				if (var3.getActivePotionEffects().isEmpty()) {
					throw new CommandException("commands.effect.failure.notActive.all",
							new Object[] { var3.getName() });
				} else {
					var3.clearActivePotions();
					notifyOperators(sender, this, "commands.effect.success.removed.all",
							new Object[] { var3.getName() });
				}
			} else {
				int var4;

				try {
					var4 = parseInt(args[1], 1);
				} catch (final NumberInvalidException var11) {
					final Potion var6 = Potion.func_180142_b(args[1]);

					if (var6 == null) {
						throw var11;
					}

					var4 = var6.id;
				}

				int var5 = 600;
				int var12 = 30;
				int var7 = 0;

				if (var4 >= 0 && var4 < Potion.potionTypes.length && Potion.potionTypes[var4] != null) {
					final Potion var8 = Potion.potionTypes[var4];

					if (args.length >= 3) {
						var12 = parseInt(args[2], 0, 1000000);

						if (var8.isInstant()) {
							var5 = var12;
						} else {
							var5 = var12 * 20;
						}
					} else if (var8.isInstant()) {
						var5 = 1;
					}

					if (args.length >= 4) {
						var7 = parseInt(args[3], 0, 255);
					}

					boolean var9 = true;

					if (args.length >= 5 && "true".equalsIgnoreCase(args[4])) {
						var9 = false;
					}

					if (var12 > 0) {
						final PotionEffect var10 = new PotionEffect(var4, var5, var7, false, var9);
						var3.addPotionEffect(var10);
						notifyOperators(sender, this, "commands.effect.success",
								new Object[] { new ChatComponentTranslation(var10.getEffectName(), new Object[0]), var4, var7, var3.getName(), var12});
					} else if (var3.isPotionActive(var4)) {
						var3.removePotionEffect(var4);
						notifyOperators(sender, this, "commands.effect.success.removed", new Object[] {
								new ChatComponentTranslation(var8.getName(), new Object[0]), var3.getName() });
					} else {
						throw new CommandException("commands.effect.failure.notActive", new Object[] {
								new ChatComponentTranslation(var8.getName(), new Object[0]), var3.getName() });
					}
				} else {
					throw new NumberInvalidException("commands.effect.notFound",
							new Object[] { var4});
				}
			}
		}
	}

	@Override
	public List addTabCompletionOptions(final ICommandSender sender, final String[] args, final BlockPos pos) {
		return args.length == 1 ? getListOfStringsMatchingLastWord(args, getAllUsernames())
				: args.length == 2 ? getListOfStringsMatchingLastWord(args, Potion.func_180141_c())
						: args.length == 5 ? getListOfStringsMatchingLastWord(args, new String[] { "true", "false" })
								: null;
	}

	protected String[] getAllUsernames() {
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
