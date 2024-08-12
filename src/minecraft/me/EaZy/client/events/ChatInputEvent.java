package me.EaZy.client.events;

import net.minecraft.client.gui.ChatLine;
import net.minecraft.util.IChatComponent;

import java.util.List;

import com.darkmagician6.eventapi.events.callables.EventCancellable;

public class ChatInputEvent extends EventCancellable {

    public static final int EaZy = 36;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    private IChatComponent component;
    private final List<ChatLine> chatLines;

    public ChatInputEvent(final IChatComponent component, final List<ChatLine> chatLines) {
        this.component = component;
        this.chatLines = chatLines;
    }

    public IChatComponent getComponent() {
        return component;
    }

    public void setComponent(final IChatComponent component) {
        this.component = component;
    }

    public List<ChatLine> getChatLines() {
        return chatLines;
    }

    public String getComment() {
        return "Message: `" + component.getUnformattedText() + "`";
    }
}
