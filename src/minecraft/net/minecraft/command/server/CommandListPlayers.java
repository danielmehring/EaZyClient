package net.minecraft.command.server;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandResultStats;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;

public class CommandListPlayers extends CommandBase {

public static final int EaZy = 980;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00000615";

	@Override
	public String getCommandName() {
		return "list";
	}

	/**
	 * Return the required permission level for this command.
	 */
	@Override
	public int getRequiredPermissionLevel() {
		return 0;
	}

	@Override
	public String getCommandUsage(final ICommandSender sender) {
		return "commands.players.usage";
	}

	@Override
	public void processCommand(final ICommandSender sender, final String[] args) throws CommandException {
		final int var3 = MinecraftServer.getServer().getCurrentPlayerCount();
		sender.addChatMessage(new ChatComponentTranslation("commands.players.list",
				new Object[] { var3,MinecraftServer.getServer().getMaxPlayers()}));
		sender.addChatMessage(
				new ChatComponentText(MinecraftServer.getServer().getConfigurationManager().func_180602_f()));
		sender.func_174794_a(CommandResultStats.Type.QUERY_RESULT, var3);
	}
}
