package net.minecraft.command.server;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;

public class CommandSaveOn extends CommandBase {

public static final int EaZy = 989;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00000873";

	@Override
	public String getCommandName() {
		return "save-on";
	}

	@Override
	public String getCommandUsage(final ICommandSender sender) {
		return "commands.save-on.usage";
	}

	@Override
	public void processCommand(final ICommandSender sender, final String[] args) throws CommandException {
		final MinecraftServer var3 = MinecraftServer.getServer();
		boolean var4 = false;

		for (final WorldServer worldServer : var3.worldServers) {
			if (worldServer != null) {
				final WorldServer var6 = worldServer;

				if (var6.disableLevelSaving) {
					var6.disableLevelSaving = false;
					var4 = true;
				}
			}
		}

		if (var4) {
			notifyOperators(sender, this, "commands.save.enabled", new Object[0]);
		} else {
			throw new CommandException("commands.save-on.alreadyOn", new Object[0]);
		}
	}
}
