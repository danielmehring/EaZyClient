package net.minecraft.command;

import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.EnumDifficulty;

import java.util.List;

public class CommandDifficulty extends CommandBase {

public static final int EaZy = 932;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	// private static final String __OBFID = "http://https://fuckuskid00000422";

	@Override
	public String getCommandName() {
		return "difficulty";
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
		return "commands.difficulty.usage";
	}

	@Override
	public void processCommand(final ICommandSender sender, final String[] args) throws CommandException {
		if (args.length <= 0) {
			throw new WrongUsageException("commands.difficulty.usage", new Object[0]);
		} else {
			final EnumDifficulty var3 = func_180531_e(args[0]);
			MinecraftServer.getServer().setDifficultyForAllWorlds(var3);
			notifyOperators(sender, this, "commands.difficulty.success",
					new Object[] { new ChatComponentTranslation(var3.getDifficultyResourceKey(), new Object[0]) });
		}
	}

	protected EnumDifficulty func_180531_e(final String p_180531_1_) throws CommandException {
		return !p_180531_1_.equalsIgnoreCase("peaceful") && !p_180531_1_.equalsIgnoreCase("p")
				? !p_180531_1_.equalsIgnoreCase("easy") && !p_180531_1_.equalsIgnoreCase("e")
						? !p_180531_1_.equalsIgnoreCase("normal") && !p_180531_1_.equalsIgnoreCase("n")
								? !p_180531_1_.equalsIgnoreCase("hard") && !p_180531_1_.equalsIgnoreCase("h")
										? EnumDifficulty.getDifficultyEnum(parseInt(p_180531_1_, 0, 3))
										: EnumDifficulty.HARD
								: EnumDifficulty.NORMAL
						: EnumDifficulty.EASY
				: EnumDifficulty.PEACEFUL;
	}

	@Override
	public List addTabCompletionOptions(final ICommandSender sender, final String[] args, final BlockPos pos) {
		return args.length == 1
				? getListOfStringsMatchingLastWord(args, new String[] { "peaceful", "easy", "normal", "hard" }) : null;
	}
}
