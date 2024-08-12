package net.minecraft.command.server;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.SyntaxErrorException;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;

import java.util.List;
import java.util.regex.Matcher;

public class CommandPardonIp extends CommandBase {

public static final int EaZy = 984;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00000720";

	@Override
	public String getCommandName() {
		return "pardon-ip";
	}

	/**
	 * Return the required permission level for this command.
	 */
	@Override
	public int getRequiredPermissionLevel() {
		return 3;
	}

	/**
	 * Returns true if the given command sender is allowed to use this command.
	 */
	@Override
	public boolean canCommandSenderUseCommand(final ICommandSender sender) {
		return MinecraftServer.getServer().getConfigurationManager().getBannedIPs().isLanServer()
				&& super.canCommandSenderUseCommand(sender);
	}

	@Override
	public String getCommandUsage(final ICommandSender sender) {
		return "commands.unbanip.usage";
	}

	@Override
	public void processCommand(final ICommandSender sender, final String[] args) throws CommandException {
		if (args.length == 1 && args[0].length() > 1) {
			final Matcher var3 = CommandBanIp.field_147211_a.matcher(args[0]);

			if (var3.matches()) {
				MinecraftServer.getServer().getConfigurationManager().getBannedIPs().removeEntry(args[0]);
				notifyOperators(sender, this, "commands.unbanip.success", new Object[] { args[0] });
			} else {
				throw new SyntaxErrorException("commands.unbanip.invalid", new Object[0]);
			}
		} else {
			throw new WrongUsageException("commands.unbanip.usage", new Object[0]);
		}
	}

	@Override
	public List addTabCompletionOptions(final ICommandSender sender, final String[] args, final BlockPos pos) {
		return args.length == 1 ? getListOfStringsMatchingLastWord(args,
				MinecraftServer.getServer().getConfigurationManager().getBannedIPs().getKeys()) : null;
	}
}
