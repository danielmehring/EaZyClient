package net.minecraft.command.server;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;

import java.util.List;

import com.mojang.authlib.GameProfile;

public class CommandPardonPlayer extends CommandBase {

public static final int EaZy = 985;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00000747";

	@Override
	public String getCommandName() {
		return "pardon";
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
		return "commands.unban.usage";
	}

	/**
	 * Returns true if the given command sender is allowed to use this command.
	 */
	@Override
	public boolean canCommandSenderUseCommand(final ICommandSender sender) {
		return MinecraftServer.getServer().getConfigurationManager().getBannedPlayers().isLanServer()
				&& super.canCommandSenderUseCommand(sender);
	}

	@Override
	public void processCommand(final ICommandSender sender, final String[] args) throws CommandException {
		if (args.length == 1 && args[0].length() > 0) {
			final MinecraftServer var3 = MinecraftServer.getServer();
			final GameProfile var4 = var3.getConfigurationManager().getBannedPlayers().isUsernameBanned(args[0]);

			if (var4 == null) {
				throw new CommandException("commands.unban.failed", new Object[] { args[0] });
			} else {
				var3.getConfigurationManager().getBannedPlayers().removeEntry(var4);
				notifyOperators(sender, this, "commands.unban.success", new Object[] { args[0] });
			}
		} else {
			throw new WrongUsageException("commands.unban.usage", new Object[0]);
		}
	}

	@Override
	public List addTabCompletionOptions(final ICommandSender sender, final String[] args, final BlockPos pos) {
		return args.length == 1 ? getListOfStringsMatchingLastWord(args,
				MinecraftServer.getServer().getConfigurationManager().getBannedPlayers().getKeys()) : null;
	}
}
