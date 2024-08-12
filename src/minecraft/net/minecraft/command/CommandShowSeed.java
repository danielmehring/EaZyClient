package net.minecraft.command;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;

public class CommandShowSeed extends CommandBase {

public static final int EaZy = 952;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00001053";

	/**
	 * Returns true if the given command sender is allowed to use this command.
	 */
	@Override
	public boolean canCommandSenderUseCommand(final ICommandSender sender) {
		return MinecraftServer.getServer().isSinglePlayer() || super.canCommandSenderUseCommand(sender);
	}

	@Override
	public String getCommandName() {
		return "seed";
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
		return "commands.seed.usage";
	}

	@Override
	public void processCommand(final ICommandSender sender, final String[] args) throws CommandException {
		final Object var3 = sender instanceof EntityPlayer ? ((EntityPlayer) sender).worldObj
				: MinecraftServer.getServer().worldServerForDimension(0);
		sender.addChatMessage(new ChatComponentTranslation("commands.seed.success",
				new Object[] { ((World) var3).getSeed() }));
	}
}
