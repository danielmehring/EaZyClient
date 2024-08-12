package net.minecraft.command;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.S19PacketEntityStatus;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.GameRules;

import java.util.Iterator;
import java.util.List;

public class CommandGameRule extends CommandBase {

public static final int EaZy = 940;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00000475";

	@Override
	public String getCommandName() {
		return "gamerule";
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
		return "commands.gamerule.usage";
	}

	@Override
	public void processCommand(final ICommandSender sender, final String[] args) throws CommandException {
		final GameRules var3 = getGameRules();
		final String var4 = args.length > 0 ? args[0] : "";
		final String var5 = args.length > 1 ? func_180529_a(args, 1) : "";

		switch (args.length) {
			case 0:
				sender.addChatMessage(new ChatComponentText(joinNiceString(var3.getRules())));
				break;

			case 1:
				if (!var3.hasRule(var4)) {
					throw new CommandException("commands.gamerule.norule", new Object[] { var4 });
				}

				final String var6 = var3.getGameRuleStringValue(var4);
				sender.addChatMessage(new ChatComponentText(var4).appendText(" = ").appendText(var6));
				sender.func_174794_a(CommandResultStats.Type.QUERY_RESULT, var3.getInt(var4));
				break;

			default:
				if (var3.areSameType(var4, GameRules.ValueType.BOOLEAN_VALUE) && !"true".equals(var5)
						&& !"false".equals(var5)) {
					throw new CommandException("commands.generic.boolean.invalid", new Object[] { var5 });
				}

				var3.setOrCreateGameRule(var4, var5);
				func_175773_a(var3, var4);
				notifyOperators(sender, this, "commands.gamerule.success", new Object[0]);
		}
	}

	public static void func_175773_a(final GameRules p_175773_0_, final String p_175773_1_) {
		if ("reducedDebugInfo".equals(p_175773_1_)) {
			final int var2 = p_175773_0_.getGameRuleBooleanValue(p_175773_1_) ? 22 : 23;
			final Iterator var3 = MinecraftServer.getServer().getConfigurationManager().playerEntityList.iterator();

			while (var3.hasNext()) {
				final EntityPlayerMP var4 = (EntityPlayerMP) var3.next();
				var4.playerNetServerHandler.sendPacket(new S19PacketEntityStatus(var4, (byte) var2));
			}
		}
	}

	@Override
	public List addTabCompletionOptions(final ICommandSender sender, final String[] args, final BlockPos pos) {
		if (args.length == 1) {
			return getListOfStringsMatchingLastWord(args, getGameRules().getRules());
		} else {
			if (args.length == 2) {
				final GameRules var4 = getGameRules();

				if (var4.areSameType(args[0], GameRules.ValueType.BOOLEAN_VALUE)) {
					return getListOfStringsMatchingLastWord(args, new String[] { "true", "false" });
				}
			}

			return null;
		}
	}

	/**
	 * Return the game rule set this command should be able to manipulate.
	 */
	private GameRules getGameRules() {
		return MinecraftServer.getServer().worldServerForDimension(0).getGameRules();
	}
}
