package net.minecraft.command.server;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.UserListBansEntry;
import net.minecraft.util.BlockPos;

import java.util.Date;
import java.util.List;

import com.mojang.authlib.GameProfile;

public class CommandBanPlayer extends CommandBase {

public static final int EaZy = 974;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00000165";

	@Override
	public String getCommandName() {
		return "ban";
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
		return "commands.ban.usage";
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
		if (args.length >= 1 && args[0].length() > 0) {
			final MinecraftServer var3 = MinecraftServer.getServer();
			final GameProfile var4 = var3.getPlayerProfileCache().getGameProfileForUsername(args[0]);

			if (var4 == null) {
				throw new CommandException("commands.ban.failed", new Object[] { args[0] });
			} else {
				String var5 = null;

				if (args.length >= 2) {
					var5 = getChatComponentFromNthArg(sender, args, 1).getUnformattedText();
				}

				final UserListBansEntry var6 = new UserListBansEntry(var4, (Date) null, sender.getName(), (Date) null,
						var5);
				var3.getConfigurationManager().getBannedPlayers().addEntry(var6);
				final EntityPlayerMP var7 = var3.getConfigurationManager().getPlayerByUsername(args[0]);

				if (var7 != null) {
					var7.playerNetServerHandler.kickPlayerFromServer("You are banned from this server.");
				}

				notifyOperators(sender, this, "commands.ban.success", new Object[] { args[0] });
			}
		} else {
			throw new WrongUsageException("commands.ban.usage", new Object[0]);
		}
	}

	@Override
	public List addTabCompletionOptions(final ICommandSender sender, final String[] args, final BlockPos pos) {
		return args.length >= 1 ? getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames())
				: null;
	}
}
