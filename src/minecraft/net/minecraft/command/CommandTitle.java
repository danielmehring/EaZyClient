package net.minecraft.command;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.S45PacketTitle;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentProcessor;
import net.minecraft.util.IChatComponent;

import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.JsonParseException;

public class CommandTitle extends CommandBase {

public static final int EaZy = 956;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final Logger field_175774_a = LogManager.getLogger();
	// private static final String __OBFID = "http://https://fuckuskid00002338";

	@Override
	public String getCommandName() {
		return "title";
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
		return "commands.title.usage";
	}

	@Override
	public void processCommand(final ICommandSender sender, final String[] args) throws CommandException {
		if (args.length < 2) {
			throw new WrongUsageException("commands.title.usage", new Object[0]);
		} else {
			if (args.length < 3) {
				if ("title".equals(args[1]) || "subtitle".equals(args[1])) {
					throw new WrongUsageException("commands.title.usage.title", new Object[0]);
				}

				if ("times".equals(args[1])) {
					throw new WrongUsageException("commands.title.usage.times", new Object[0]);
				}
			}

			final EntityPlayerMP var3 = getPlayer(sender, args[0]);
			final S45PacketTitle.Type var4 = S45PacketTitle.Type.func_179969_a(args[1]);

			if (var4 != S45PacketTitle.Type.CLEAR && var4 != S45PacketTitle.Type.RESET) {
				if (var4 == S45PacketTitle.Type.TIMES) {
					if (args.length != 5) {
						throw new WrongUsageException("commands.title.usage", new Object[0]);
					} else {
						final int var11 = parseInt(args[2]);
						final int var12 = parseInt(args[3]);
						final int var13 = parseInt(args[4]);
						final S45PacketTitle var14 = new S45PacketTitle(var11, var12, var13);
						var3.playerNetServerHandler.sendPacket(var14);
						notifyOperators(sender, this, "commands.title.success", new Object[0]);
					}
				} else if (args.length < 3) {
					throw new WrongUsageException("commands.title.usage", new Object[0]);
				} else {
					final String var10 = func_180529_a(args, 2);
					IChatComponent var6;

					try {
						var6 = IChatComponent.Serializer.jsonToComponent(var10);
					} catch (final JsonParseException var9) {
						final Throwable var8 = ExceptionUtils.getRootCause(var9);
						throw new SyntaxErrorException("commands.tellraw.jsonException",
								new Object[] { var8 == null ? "" : var8.getMessage() });
					}

					final S45PacketTitle var7 = new S45PacketTitle(var4,
							ChatComponentProcessor.func_179985_a(sender, var6, var3));
					var3.playerNetServerHandler.sendPacket(var7);
					notifyOperators(sender, this, "commands.title.success", new Object[0]);
				}
			} else if (args.length != 2) {
				throw new WrongUsageException("commands.title.usage", new Object[0]);
			} else {
				final S45PacketTitle var5 = new S45PacketTitle(var4, (IChatComponent) null);
				var3.playerNetServerHandler.sendPacket(var5);
				notifyOperators(sender, this, "commands.title.success", new Object[0]);
			}
		}
	}

	@Override
	public List addTabCompletionOptions(final ICommandSender sender, final String[] args, final BlockPos pos) {
		return args.length == 1 ? getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames())
				: args.length == 2 ? getListOfStringsMatchingLastWord(args, S45PacketTitle.Type.func_179971_a()) : null;
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
