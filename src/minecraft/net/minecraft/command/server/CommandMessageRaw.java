package net.minecraft.command.server;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.SyntaxErrorException;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentProcessor;
import net.minecraft.util.IChatComponent;

import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.google.gson.JsonParseException;

public class CommandMessageRaw extends CommandBase {

public static final int EaZy = 982;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00000667";

	@Override
	public String getCommandName() {
		return "tellraw";
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
		return "commands.tellraw.usage";
	}

	@Override
	public void processCommand(final ICommandSender sender, final String[] args) throws CommandException {
		if (args.length < 2) {
			throw new WrongUsageException("commands.tellraw.usage", new Object[0]);
		} else {
			final EntityPlayerMP var3 = getPlayer(sender, args[0]);
			final String var4 = func_180529_a(args, 1);

			try {
				final IChatComponent var5 = IChatComponent.Serializer.jsonToComponent(var4);
				var3.addChatMessage(ChatComponentProcessor.func_179985_a(sender, var5, var3));
			} catch (final JsonParseException var7) {
				final Throwable var6 = ExceptionUtils.getRootCause(var7);
				throw new SyntaxErrorException("commands.tellraw.jsonException",
						new Object[] { var6 == null ? "" : var6.getMessage() });
			}
		}
	}

	@Override
	public List addTabCompletionOptions(final ICommandSender sender, final String[] args, final BlockPos pos) {
		return args.length == 1 ? getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames())
				: null;
	}

	/**
	 * Return whether the specified command parameter index is a username
	 * parameter.
	 */
	@Override
	public boolean isUsernameIndex(final String[] args, final int index) {
		return index == 0;
	}
}
