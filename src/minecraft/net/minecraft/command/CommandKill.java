package net.minecraft.command;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;

public class CommandKill extends CommandBase {

public static final int EaZy = 944;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00000570";

	@Override
	public String getCommandName() {
		return "kill";
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
		return "commands.kill.usage";
	}

	@Override
	public void processCommand(final ICommandSender sender, final String[] args) throws CommandException {
		if (args.length == 0) {
			final EntityPlayerMP var4 = getCommandSenderAsPlayer(sender);
			var4.func_174812_G();
			notifyOperators(sender, this, "commands.kill.successful", new Object[] { var4.getDisplayName() });
		} else {
			final Entity var3 = func_175768_b(sender, args[0]);
			var3.func_174812_G();
			notifyOperators(sender, this, "commands.kill.successful", new Object[] { var3.getDisplayName() });
		}
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
