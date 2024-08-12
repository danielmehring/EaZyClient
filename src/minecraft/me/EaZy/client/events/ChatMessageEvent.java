package me.EaZy.client.events;

import com.darkmagician6.eventapi.events.callables.EventCancellable;

public class ChatMessageEvent extends EventCancellable {

    public static final int EaZy = 37;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    private String text;

    public ChatMessageEvent(final String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(final String text) {
        this.text = text;
    }
}
