package net.minecraft.command.server;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;

import java.util.List;

public class CommandListBans extends CommandBase {

public static final int EaZy = 979;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00000596";

	@Override
	public String getCommandName() {
		return "banlist";
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
		return (MinecraftServer.getServer().getConfigurationManager().getBannedIPs().isLanServer()
				|| MinecraftServer.getServer().getConfigurationManager().getBannedPlayers().isLanServer())
				&& super.canCommandSenderUseCommand(sender);
	}

	@Override
	public String getCommandUsage(final ICommandSender sender) {
		return "commands.banlist.usage";
	}

	@Override
	public void processCommand(final ICommandSender sender, final String[] args) throws CommandException {
		if (args.length >= 1 && args[0].equalsIgnoreCase("ips")) {
			sender.addChatMessage(new ChatComponentTranslation("commands.banlist.ips", new Object[] { MinecraftServer.getServer().getConfigurationManager().getBannedIPs().getKeys().length}));
			sender.addChatMessage(new ChatComponentText(
					joinNiceString(MinecraftServer.getServer().getConfigurationManager().getBannedIPs().getKeys())));
		} else {
			sender.addChatMessage(new ChatComponentTranslation("commands.banlist.players",
					new Object[] { MinecraftServer.getServer().getConfigurationManager()
                                                .getBannedPlayers().getKeys().length}));
			sender.addChatMessage(new ChatComponentText(joinNiceString(
					MinecraftServer.getServer().getConfigurationManager().getBannedPlayers().getKeys())));
		}
	}

	@Override
	public List addTabCompletionOptions(final ICommandSender sender, final String[] args, final BlockPos pos) {
		return args.length == 1 ? getListOfStringsMatchingLastWord(args, new String[] { "players", "ips" }) : null;
	}
}
