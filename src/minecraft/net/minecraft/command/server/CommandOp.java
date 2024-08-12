package net.minecraft.command.server;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;

public class CommandOp extends CommandBase {

public static final int EaZy = 983;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00000694";

	@Override
	public String getCommandName() {
		return "op";
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
		return "commands.op.usage";
	}

	@Override
	public void processCommand(final ICommandSender sender, final String[] args) throws CommandException {
		if (args.length == 1 && args[0].length() > 0) {
			final MinecraftServer var3 = MinecraftServer.getServer();
			final GameProfile var4 = var3.getPlayerProfileCache().getGameProfileForUsername(args[0]);

			if (var4 == null) {
				throw new CommandException("commands.op.failed", new Object[] { args[0] });
			} else {
				var3.getConfigurationManager().addOp(var4);
				notifyOperators(sender, this, "commands.op.success", new Object[] { args[0] });
			}
		} else {
			throw new WrongUsageException("commands.op.usage", new Object[0]);
		}
	}

	@Override
	public List addTabCompletionOptions(final ICommandSender sender, final String[] args, final BlockPos pos) {
		if (args.length == 1) {
			final String var4 = args[args.length - 1];
			final ArrayList var5 = Lists.newArrayList();
			final GameProfile[] var6 = MinecraftServer.getServer().getGameProfiles();
			final int var7 = var6.length;

			for (int var8 = 0; var8 < var7; ++var8) {
				final GameProfile var9 = var6[var8];

				if (!MinecraftServer.getServer().getConfigurationManager().canSendCommands(var9)
						&& doesStringStartWith(var4, var9.getName())) {
					var5.add(var9.getName());
				}
			}

			return var5;
		} else {
			return null;
		}
	}
}
