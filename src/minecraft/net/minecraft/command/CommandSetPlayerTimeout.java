package net.minecraft.command;

import net.minecraft.server.MinecraftServer;

public class CommandSetPlayerTimeout extends CommandBase {

public static final int EaZy = 950;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00000999";

	@Override
	public String getCommandName() {
		return "setidletimeout";
	}

	/**
	 * Return the required permission level for this command.
	 */
	@Override
	public int getRequiredPermissionLevel() {
		return 3;
	}

	@Override
	public String getCommandUsage(final ICommandSender sender) {
		return "commands.setidletimeout.usage";
	}

	@Override
	public void processCommand(final ICommandSender sender, final String[] args) throws CommandException {
		if (args.length != 1) {
			throw new WrongUsageException("commands.setidletimeout.usage", new Object[0]);
		} else {
			final int var3 = parseInt(args[0], 0);
			MinecraftServer.getServer().setPlayerIdleTimeout(var3);
			notifyOperators(sender, this, "commands.setidletimeout.success", new Object[] { var3});
		}
	}
}
