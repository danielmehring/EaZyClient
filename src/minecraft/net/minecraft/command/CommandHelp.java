package net.minecraft.command;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.event.ClickEvent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CommandHelp extends CommandBase {

public static final int EaZy = 943;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00000529";

	@Override
	public String getCommandName() {
		return "help";
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
		return "commands.help.usage";
	}

	@Override
	public List getCommandAliases() {
		return Arrays.asList(new String[] { "?" });
	}

	@Override
	public void processCommand(final ICommandSender sender, final String[] args) throws CommandException {
		final List var3 = getSortedPossibleCommands(sender);
		final int var5 = (var3.size() - 1) / 7;
		int var13;

		try {
			var13 = args.length == 0 ? 0 : parseInt(args[0], 1, var5 + 1) - 1;
		} catch (final NumberInvalidException var12) {
			final Map var8 = getCommands();
			final ICommand var9 = (ICommand) var8.get(args[0]);

			if (var9 != null) {
				throw new WrongUsageException(var9.getCommandUsage(sender), new Object[0]);
			}

			if (MathHelper.parseIntWithDefault(args[0], -1) != -1) {
				throw var12;
			}

			throw new CommandNotFoundException();
		}

		final int var7 = Math.min((var13 + 1) * 7, var3.size());
		final ChatComponentTranslation var14 = new ChatComponentTranslation("commands.help.header",
				new Object[] { var13 + 1, var5 + 1});
		var14.getChatStyle().setColor(EnumChatFormatting.DARK_GREEN);
		sender.addChatMessage(var14);

		for (int var15 = var13 * 7; var15 < var7; ++var15) {
			final ICommand var10 = (ICommand) var3.get(var15);
			final ChatComponentTranslation var11 = new ChatComponentTranslation(var10.getCommandUsage(sender),
					new Object[0]);
			var11.getChatStyle().setChatClickEvent(
					new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/" + var10.getCommandName() + " "));
			sender.addChatMessage(var11);
		}

		if (var13 == 0 && sender instanceof EntityPlayer) {
			final ChatComponentTranslation var16 = new ChatComponentTranslation("commands.help.footer", new Object[0]);
			var16.getChatStyle().setColor(EnumChatFormatting.GREEN);
			sender.addChatMessage(var16);
		}
	}

	/**
	 * Returns a sorted list of all possible commands for the given
	 * ICommandSender.
	 */
	protected List getSortedPossibleCommands(final ICommandSender p_71534_1_) {
		final List var2 = MinecraftServer.getServer().getCommandManager().getPossibleCommands(p_71534_1_);
		Collections.sort(var2);
		return var2;
	}

	protected Map getCommands() {
		return MinecraftServer.getServer().getCommandManager().getCommands();
	}

	@Override
	public List addTabCompletionOptions(final ICommandSender sender, final String[] args, final BlockPos pos) {
		if (args.length == 1) {
			final Set var4 = getCommands().keySet();
			return getListOfStringsMatchingLastWord(args, (String[]) var4.toArray(new String[var4.size()]));
		} else {
			return null;
		}
	}
}
