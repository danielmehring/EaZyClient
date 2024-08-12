package net.minecraft.command.server;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldSettings;

public class CommandPublishLocalServer extends CommandBase {

public static final int EaZy = 986;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00000799";

	@Override
	public String getCommandName() {
		return "publish";
	}

	@Override
	public String getCommandUsage(final ICommandSender sender) {
		return "commands.publish.usage";
	}

	@Override
	public void processCommand(final ICommandSender sender, final String[] args) throws CommandException {
		final String var3 = MinecraftServer.getServer().shareToLAN(WorldSettings.GameType.SURVIVAL, false);

		if (var3 != null) {
			notifyOperators(sender, this, "commands.publish.started", new Object[] { var3 });
		} else {
			notifyOperators(sender, this, "commands.publish.failed", new Object[0]);
		}
	}
}
