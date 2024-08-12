package net.minecraft.command.server;

import net.minecraft.command.CommandResultStats;
import net.minecraft.command.ICommandManager;
import net.minecraft.command.ICommandSender;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ReportedException;
import net.minecraft.world.World;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Callable;

import io.netty.buffer.ByteBuf;

public abstract class CommandBlockLogic implements ICommandSender {

	public static final int EaZy = 975;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	/** The formatting for the timestamp on commands run. */
	private static final SimpleDateFormat timestampFormat = new SimpleDateFormat("HH:mm:ss");

	/** The number of successful commands run. (used for redstone output) */
	private int successCount;
	private boolean trackOutput = true;

	/** The previously run command. */
	private IChatComponent lastOutput = null;

	/** The command stored in the command block. */
	private String commandStored = "";

	/** The custom name of the command block. (defaults to "@") */
	private String customName = "@";
	private final CommandResultStats field_175575_g = new CommandResultStats();
	// private static final String __OBFID = "http://https://fuckuskid00000128";

	/**
	 * returns the successCount int.
	 */
	public int getSuccessCount() {
		return successCount;
	}

	/**
	 * Returns the lastOutput.
	 */
	public IChatComponent getLastOutput() {
		return lastOutput;
	}

	/**
	 * Stores data to NBT format.
	 */
	public void writeDataToNBT(final NBTTagCompound p_145758_1_) {
		p_145758_1_.setString("Command", commandStored);
		p_145758_1_.setInteger("SuccessCount", successCount);
		p_145758_1_.setString("CustomName", customName);
		p_145758_1_.setBoolean("TrackOutput", trackOutput);

		if (lastOutput != null && trackOutput) {
			p_145758_1_.setString("LastOutput", IChatComponent.Serializer.componentToJson(lastOutput));
		}

		field_175575_g.func_179670_b(p_145758_1_);
	}

	/**
	 * Reads NBT formatting and stored data into variables.
	 */
	public void readDataFromNBT(final NBTTagCompound p_145759_1_) {
		commandStored = p_145759_1_.getString("Command");
		successCount = p_145759_1_.getInteger("SuccessCount");

		if (p_145759_1_.hasKey("CustomName", 8)) {
			customName = p_145759_1_.getString("CustomName");
		}

		if (p_145759_1_.hasKey("TrackOutput", 1)) {
			trackOutput = p_145759_1_.getBoolean("TrackOutput");
		}

		if (p_145759_1_.hasKey("LastOutput", 8) && trackOutput) {
			lastOutput = IChatComponent.Serializer.jsonToComponent(p_145759_1_.getString("LastOutput"));
		}

		field_175575_g.func_179668_a(p_145759_1_);
	}

	/**
	 * Returns true if the command sender is allowed to use the given command.
	 */
	@Override
	public boolean canCommandSenderUseCommand(final int permissionLevel, final String command) {
		return permissionLevel <= 2;
	}

	/**
	 * Sets the command.
	 */
	public void setCommand(final String p_145752_1_) {
		commandStored = p_145752_1_;
		successCount = 0;
	}

	/**
	 * Returns the customName of the command block.
	 */
	public String getCustomName() {
		return commandStored;
	}

	public void trigger(final World worldIn) {
		if (worldIn.isRemote) {
			successCount = 0;
		}

		final MinecraftServer var2 = MinecraftServer.getServer();

		if (var2 != null && var2.func_175578_N() && var2.isCommandBlockEnabled()) {
			final ICommandManager var3 = var2.getCommandManager();

			try {
				lastOutput = null;
				successCount = var3.executeCommand(this, commandStored);
			} catch (final Throwable var7) {
				final CrashReport var5 = CrashReport.makeCrashReport(var7, "Executing command block");
				final CrashReportCategory var6 = var5.makeCategory("Command to be executed");
				var6.addCrashSectionCallable("Command", new Callable() {
					// private static final String __OBFID =
					// "http://https://fuckuskid00002154";
					public String func_180324_a() {
						return CommandBlockLogic.this.getCustomName();
					}

					@Override
					public Object call() {
						return func_180324_a();
					}
				});
				var6.addCrashSectionCallable("Name", new Callable() {
					// private static final String __OBFID =
					// "http://https://fuckuskid00002153";
					public String func_180326_a() {
						return CommandBlockLogic.this.getName();
					}

					@Override
					public Object call() {
						return func_180326_a();
					}
				});
				throw new ReportedException(var5);
			}
		} else {
			successCount = 0;
		}
	}

	/**
	 * Gets the name of this command sender (usually username, but possibly
	 * "Rcon")
	 */
	@Override
	public String getName() {
		return customName;
	}

	@Override
	public IChatComponent getDisplayName() {
		return new ChatComponentText(getName());
	}

	public void func_145754_b(final String p_145754_1_) {
		customName = p_145754_1_;
	}

	/**
	 * Notifies this sender of some sort of information. This is for messages
	 * intended to display to the user. Used for typical output (like "you asked
	 * for whether or not this game rule is set, so here's your answer" ),
	 * warnings (like "I fetched this block for you by ID, but I'd like you to
	 * know that every time you do this, I die a little inside "), and errors
	 * (like "it's not called iron_pixacke, silly").
	 */
	@Override
	public void addChatMessage(final IChatComponent message) {
		if (trackOutput && getEntityWorld() != null && !getEntityWorld().isRemote) {
			lastOutput = new ChatComponentText("[" + timestampFormat.format(new Date()) + "] ").appendSibling(message);
			func_145756_e();
		}
	}

	@Override
	public boolean sendCommandFeedback() {
		final MinecraftServer var1 = MinecraftServer.getServer();
		return var1 == null || !var1.func_175578_N()
				|| var1.worldServers[0].getGameRules().getGameRuleBooleanValue("commandBlockOutput");
	}

	@Override
	public void func_174794_a(final CommandResultStats.Type p_174794_1_, final int p_174794_2_) {
		field_175575_g.func_179672_a(this, p_174794_1_, p_174794_2_);
	}

	public abstract void func_145756_e();

	public abstract int func_145751_f();

	public abstract void func_145757_a(ByteBuf var1);

	public void func_145750_b(final IChatComponent p_145750_1_) {
		lastOutput = p_145750_1_;
	}

	public void func_175573_a(final boolean p_175573_1_) {
		trackOutput = p_175573_1_;
	}

	public boolean func_175571_m() {
		return trackOutput;
	}

	public boolean func_175574_a(final EntityPlayer player) {
		if (player.getEntityWorld().isRemote) {
			player.displayCommandBlockGUI(this);
		}
		if (!player.capabilities.isCreativeMode) {
			return false;
		} else {
			return true;
		}
	}

	public CommandResultStats func_175572_n() {
		return field_175575_g;
	}
}
