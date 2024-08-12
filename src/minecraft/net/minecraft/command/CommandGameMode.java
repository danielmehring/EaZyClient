package net.minecraft.command;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.WorldSettings;

import java.util.List;

public class CommandGameMode extends CommandBase {

public static final int EaZy = 939;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00000448";

	@Override
	public String getCommandName() {
		return "gamemode";
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
		return "commands.gamemode.usage";
	}

	@Override
	public void processCommand(final ICommandSender sender, final String[] args) throws CommandException {
		if (args.length <= 0) {
			throw new WrongUsageException("commands.gamemode.usage", new Object[0]);
		} else {
			final WorldSettings.GameType var3 = getGameModeFromCommand(sender, args[0]);
			final EntityPlayerMP var4 = args.length >= 2 ? getPlayer(sender, args[1])
					: getCommandSenderAsPlayer(sender);
			var4.setGameType(var3);
			var4.fallDistance = 0.0F;

			if (sender.getEntityWorld().getGameRules().getGameRuleBooleanValue("sendCommandFeedback")) {
				var4.addChatMessage(new ChatComponentTranslation("gameMode.changed", new Object[0]));
			}

			final ChatComponentTranslation var5 = new ChatComponentTranslation("gameMode." + var3.getName(),
					new Object[0]);

			if (var4 != sender) {
				notifyOperators(sender, this, 1, "commands.gamemode.success.other",
						new Object[] { var4.getName(), var5 });
			} else {
				notifyOperators(sender, this, 1, "commands.gamemode.success.self", new Object[] { var5 });
			}
		}
	}

	/**
	 * Gets the Game Mode specified in the command.
	 */
	protected WorldSettings.GameType getGameModeFromCommand(final ICommandSender p_71539_1_, final String p_71539_2_)
			throws CommandException {
		return !p_71539_2_.equalsIgnoreCase(WorldSettings.GameType.SURVIVAL.getName()) && !p_71539_2_
				.equalsIgnoreCase("s")
						? !p_71539_2_.equalsIgnoreCase(WorldSettings.GameType.CREATIVE.getName())
								&& !p_71539_2_.equalsIgnoreCase("c")
										? !p_71539_2_
												.equalsIgnoreCase(WorldSettings.GameType.ADVENTURE.getName())
												&& !p_71539_2_.equalsIgnoreCase("a")
														? !p_71539_2_.equalsIgnoreCase(
																WorldSettings.GameType.SPECTATOR.getName())
																&& !p_71539_2_.equalsIgnoreCase("sp")
																		? WorldSettings
																				.getGameTypeById(parseInt(p_71539_2_, 0,
																						WorldSettings.GameType
																								.values().length - 2))
																		: WorldSettings.GameType.SPECTATOR
														: WorldSettings.GameType.ADVENTURE
										: WorldSettings.GameType.CREATIVE
						: WorldSettings.GameType.SURVIVAL;
	}

	@Override
	public List addTabCompletionOptions(final ICommandSender sender, final String[] args, final BlockPos pos) {
		return args.length == 1
				? getListOfStringsMatchingLastWord(args,
						new String[] { "survival", "creative", "adventure", "spectator" })
				: args.length == 2 ? getListOfStringsMatchingLastWord(args, getListOfPlayerUsernames()) : null;
	}

	/**
	 * Returns String array containing all player usernames in the server.
	 */
	protected String[] getListOfPlayerUsernames() {
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
