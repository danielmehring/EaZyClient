package net.minecraft.command;

import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.WorldServer;

import java.util.List;

public class CommandTime extends CommandBase {

public static final int EaZy = 955;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00001183";

	@Override
	public String getCommandName() {
		return "time";
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
		return "commands.time.usage";
	}

	@Override
	public void processCommand(final ICommandSender sender, final String[] args) throws CommandException {
		if (args.length > 1) {
			int var3;

			if (args[0].equals("set")) {
				if (args[1].equals("day")) {
					var3 = 1000;
				} else if (args[1].equals("night")) {
					var3 = 13000;
				} else {
					var3 = parseInt(args[1], 0);
				}

				setTime(sender, var3);
				notifyOperators(sender, this, "commands.time.set", new Object[] { var3});
				return;
			}

			if (args[0].equals("add")) {
				var3 = parseInt(args[1], 0);
				addTime(sender, var3);
				notifyOperators(sender, this, "commands.time.added", new Object[] { var3});
				return;
			}

			if (args[0].equals("query")) {
				if (args[1].equals("daytime")) {
					var3 = (int) (sender.getEntityWorld().getWorldTime() % 2147483647L);
					sender.func_174794_a(CommandResultStats.Type.QUERY_RESULT, var3);
					notifyOperators(sender, this, "commands.time.query", new Object[] { var3});
					return;
				}

				if (args[1].equals("gametime")) {
					var3 = (int) (sender.getEntityWorld().getTotalWorldTime() % 2147483647L);
					sender.func_174794_a(CommandResultStats.Type.QUERY_RESULT, var3);
					notifyOperators(sender, this, "commands.time.query", new Object[] { var3});
					return;
				}
			}
		}

		throw new WrongUsageException("commands.time.usage", new Object[0]);
	}

	@Override
	public List addTabCompletionOptions(final ICommandSender sender, final String[] args, final BlockPos pos) {
		return args.length == 1 ? getListOfStringsMatchingLastWord(args, new String[] { "set", "add", "query" })
				: args.length == 2 && args[0].equals("set")
						? getListOfStringsMatchingLastWord(args, new String[] { "day", "night" })
						: args.length == 2 && args[0].equals("query")
								? getListOfStringsMatchingLastWord(args, new String[] { "daytime", "gametime" }) : null;
	}

	/**
	 * Set the time in the server object.
	 */
	protected void setTime(final ICommandSender p_71552_1_, final int p_71552_2_) {
		for (final WorldServer worldServer : MinecraftServer.getServer().worldServers) {
			worldServer.setWorldTime(p_71552_2_);
		}
	}

	/**
	 * Adds (or removes) time in the server object.
	 */
	protected void addTime(final ICommandSender p_71553_1_, final int p_71553_2_) {
		for (final WorldServer var4 : MinecraftServer.getServer().worldServers) {
			var4.setWorldTime(var4.getWorldTime() + p_71553_2_);
		}
	}
}
