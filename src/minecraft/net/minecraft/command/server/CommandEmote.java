package net.minecraft.command.server;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;

import java.util.List;

public class CommandEmote extends CommandBase {

public static final int EaZy = 978;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00000351";

	@Override
	public String getCommandName() {
		return "me";
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
		return "commands.me.usage";
	}

	@Override
	public void processCommand(final ICommandSender sender, final String[] args) throws CommandException {
		if (args.length <= 0) {
			throw new WrongUsageException("commands.me.usage", new Object[0]);
		} else {
			final IChatComponent var3 = getChatComponentFromNthArg(sender, args, 0, !(sender instanceof EntityPlayer));
			MinecraftServer.getServer().getConfigurationManager().sendChatMsg(
					new ChatComponentTranslation("chat.type.emote", new Object[] { sender.getDisplayName(), var3 }));
		}
	}

	@Override
	public List addTabCompletionOptions(final ICommandSender sender, final String[] args, final BlockPos pos) {
		return getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames());
	}
}
