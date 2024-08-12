package net.minecraft.command.server;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.network.play.server.S05PacketSpawnPosition;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;

import java.util.List;

public class CommandSetDefaultSpawnpoint extends CommandBase {

public static final int EaZy = 992;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00000973";

	@Override
	public String getCommandName() {
		return "setworldspawn";
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
		return "commands.setworldspawn.usage";
	}

	@Override
	public void processCommand(final ICommandSender sender, final String[] args) throws CommandException {
		BlockPos var3;

		if (args.length == 0) {
			var3 = getCommandSenderAsPlayer(sender).getPosition();
		} else {
			if (args.length != 3 || sender.getEntityWorld() == null) {
				throw new WrongUsageException("commands.setworldspawn.usage", new Object[0]);
			}

			var3 = func_175757_a(sender, args, 0, true);
		}

		sender.getEntityWorld().setSpawnLocation(var3);
		MinecraftServer.getServer().getConfigurationManager().sendPacketToAllPlayers(new S05PacketSpawnPosition(var3));
		notifyOperators(sender, this, "commands.setworldspawn.success", new Object[] { var3.getX(),
                    var3.getY(), var3.getZ() });
	}

	@Override
	public List addTabCompletionOptions(final ICommandSender sender, final String[] args, final BlockPos pos) {
		return args.length > 0 && args.length <= 3 ? func_175771_a(args, 0, pos) : null;
	}
}
