package me.EaZy.client.events;

import com.darkmagician6.eventapi.events.callables.EventCancellable;

public class EventClick extends EventCancellable {

	public static final int EaZy = 39;

	public void lambdaStuff() {
		java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {});
	}

	private boolean canceled;

	@Override
	public boolean isCancelled() {
		return canceled;
	}

	@Override
	public void setCancelled(final boolean state) {
		canceled = state;
	}
}
