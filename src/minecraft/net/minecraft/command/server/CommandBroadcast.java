package net.minecraft.command.server;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;

import java.util.List;

public class CommandBroadcast extends CommandBase {

public static final int EaZy = 976;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00000191";

	@Override
	public String getCommandName() {
		return "say";
	}

	/**
	 * Return the required permission level for this command.
	 */
	@Override
	public int getRequiredPermissionLevel() {
		return 1;
	}

	@Override
	public String getCommandUsage(final ICommandSender sender) {
		return "commands.say.usage";
	}

	@Override
	public void processCommand(final ICommandSender sender, final String[] args) throws CommandException {
		if (args.length > 0 && args[0].length() > 0) {
			final IChatComponent var3 = getChatComponentFromNthArg(sender, args, 0, true);
			MinecraftServer.getServer().getConfigurationManager().sendChatMsg(new ChatComponentTranslation(
					"chat.type.announcement", new Object[] { sender.getDisplayName(), var3 }));
		} else {
			throw new WrongUsageException("commands.say.usage", new Object[0]);
		}
	}

	@Override
	public List addTabCompletionOptions(final ICommandSender sender, final String[] args, final BlockPos pos) {
		return args.length >= 1 ? getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames())
				: null;
	}
}
