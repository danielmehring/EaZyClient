package net.minecraft.command.server;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

public class CommandStop extends CommandBase {

public static final int EaZy = 993;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00001132";

	@Override
	public String getCommandName() {
		return "stop";
	}

	@Override
	public String getCommandUsage(final ICommandSender sender) {
		return "commands.stop.usage";
	}

	@Override
	public void processCommand(final ICommandSender sender, final String[] args) throws CommandException {
		if (MinecraftServer.getServer().worldServers != null) {
			notifyOperators(sender, this, "commands.stop.start", new Object[0]);
		}

		MinecraftServer.getServer().initiateShutdown();
	}
}
