package net.minecraft.entity.ai;

import net.minecraft.profiler.Profiler;

import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.Lists;

public class EntityAITasks {

public static final int EaZy = 1086;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
    }

	private static final Logger logger = LogManager.getLogger();

	/** A list of EntityAITaskEntrys in EntityAITasks. */
	private final List taskEntries = Lists.newArrayList();

	/** A list of EntityAITaskEntrys that are currently being executed. */
	private final List executingTaskEntries = Lists.newArrayList();

	/** Instance of Profiler. */
	private final Profiler theProfiler;
	private int tickCount;
	private final int tickRate = 3;
	// private static final String __OBFID = "http://https://fuckuskid00001588";

	public EntityAITasks(final Profiler p_i1628_1_) {
		theProfiler = p_i1628_1_;
	}

	/**
	 * Add a now AITask. Args : priority, task
	 */
	public void addTask(final int p_75776_1_, final EntityAIBase p_75776_2_) {
		taskEntries.add(new EntityAITasks.EntityAITaskEntry(p_75776_1_, p_75776_2_));
	}

	/**
	 * removes the indicated task from the entity's AI tasks.
	 */
	public void removeTask(final EntityAIBase p_85156_1_) {
		final Iterator var2 = taskEntries.iterator();

		while (var2.hasNext()) {
			final EntityAITasks.EntityAITaskEntry var3 = (EntityAITasks.EntityAITaskEntry) var2.next();
			final EntityAIBase var4 = var3.action;

			if (var4 == p_85156_1_) {
				if (executingTaskEntries.contains(var3)) {
					var4.resetTask();
					executingTaskEntries.remove(var3);
				}

				var2.remove();
			}
		}
	}

	public void onUpdateTasks() {
		theProfiler.startSection("goalSetup");
		Iterator var1;
		EntityAITasks.EntityAITaskEntry var2;

		if (tickCount++ % tickRate == 0) {
			var1 = taskEntries.iterator();

			while (var1.hasNext()) {
				var2 = (EntityAITasks.EntityAITaskEntry) var1.next();
				final boolean var3 = executingTaskEntries.contains(var2);

				if (var3) {
					if (canUse(var2) && canContinue(var2)) {
						continue;
					}

					var2.action.resetTask();
					executingTaskEntries.remove(var2);
				}

				if (canUse(var2) && var2.action.shouldExecute()) {
					var2.action.startExecuting();
					executingTaskEntries.add(var2);
				}
			}
		} else {
			var1 = executingTaskEntries.iterator();

			while (var1.hasNext()) {
				var2 = (EntityAITasks.EntityAITaskEntry) var1.next();

				if (!canContinue(var2)) {
					var2.action.resetTask();
					var1.remove();
				}
			}
		}

		theProfiler.endSection();
		theProfiler.startSection("goalTick");
		var1 = executingTaskEntries.iterator();

		while (var1.hasNext()) {
			var2 = (EntityAITasks.EntityAITaskEntry) var1.next();
			var2.action.updateTask();
		}

		theProfiler.endSection();
	}

	/**
	 * Determine if a specific AI Task should continue being executed.
	 */
	private boolean canContinue(final EntityAITasks.EntityAITaskEntry p_75773_1_) {
		final boolean var2 = p_75773_1_.action.continueExecuting();
		return var2;
	}

	/**
	 * Determine if a specific AI Task can be executed, which means that all
	 * running higher (= lower int value) priority tasks are compatible with it
	 * or all lower priority tasks can be interrupted.
	 */
	private boolean canUse(final EntityAITasks.EntityAITaskEntry p_75775_1_) {
		final Iterator var2 = taskEntries.iterator();

		while (var2.hasNext()) {
			final EntityAITasks.EntityAITaskEntry var3 = (EntityAITasks.EntityAITaskEntry) var2.next();

			if (var3 != p_75775_1_) {
				if (p_75775_1_.priority >= var3.priority) {
					if (!areTasksCompatible(p_75775_1_, var3) && executingTaskEntries.contains(var3)) {
						return false;
					}
				} else if (!var3.action.isInterruptible() && executingTaskEntries.contains(var3)) {
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * Returns whether two EntityAITaskEntries can be executed concurrently
	 */
	private boolean areTasksCompatible(final EntityAITasks.EntityAITaskEntry p_75777_1_,
			final EntityAITasks.EntityAITaskEntry p_75777_2_) {
		return (p_75777_1_.action.getMutexBits() & p_75777_2_.action.getMutexBits()) == 0;
	}

	class EntityAITaskEntry {
		public EntityAIBase action;
		public int priority;
		// private static final String __OBFID =
		// "http://https://fuckuskid00001589";

		public EntityAITaskEntry(final int p_i1627_2_, final EntityAIBase p_i1627_3_) {
			priority = p_i1627_2_;
			action = p_i1627_3_;
		}
	}
}
