package net.minecraft.command;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;

import java.util.List;

public class CommandXP extends CommandBase {

public static final int EaZy = 961;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00000398";

	@Override
	public String getCommandName() {
		return "xp";
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
		return "commands.xp.usage";
	}

	@Override
	public void processCommand(final ICommandSender sender, final String[] args) throws CommandException {
		if (args.length <= 0) {
			throw new WrongUsageException("commands.xp.usage", new Object[0]);
		} else {
			String var3 = args[0];
			final boolean var4 = var3.endsWith("l") || var3.endsWith("L");

			if (var4 && var3.length() > 1) {
				var3 = var3.substring(0, var3.length() - 1);
			}

			int var5 = parseInt(var3);
			final boolean var6 = var5 < 0;

			if (var6) {
				var5 *= -1;
			}

			final EntityPlayerMP var7 = args.length > 1 ? getPlayer(sender, args[1]) : getCommandSenderAsPlayer(sender);

			if (var4) {
				sender.func_174794_a(CommandResultStats.Type.QUERY_RESULT, var7.experienceLevel);

				if (var6) {
					var7.addExperienceLevel(-var5);
					notifyOperators(sender, this, "commands.xp.success.negative.levels",
							new Object[] { var5, var7.getName() });
				} else {
					var7.addExperienceLevel(var5);
					notifyOperators(sender, this, "commands.xp.success.levels",
							new Object[] { var5, var7.getName() });
				}
			} else {
				sender.func_174794_a(CommandResultStats.Type.QUERY_RESULT, var7.experienceTotal);

				if (var6) {
					throw new CommandException("commands.xp.failure.widthdrawXp", new Object[0]);
				}

				var7.addExperience(var5);
				notifyOperators(sender, this, "commands.xp.success",
						new Object[] { var5, var7.getName() });
			}
		}
	}

	@Override
	public List addTabCompletionOptions(final ICommandSender sender, final String[] args, final BlockPos pos) {
		return args.length == 2 ? getListOfStringsMatchingLastWord(args, getAllUsernames()) : null;
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
		return index == 1;
	}
}
