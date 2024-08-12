package me.EaZy.client.events;

import net.minecraft.network.Packet;

import com.darkmagician6.eventapi.events.callables.EventCancellable;

public class EventSendPacket extends EventCancellable {

    public static final int EaZy = 45;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    private Packet packet;

    public Packet getPacket() {
        return packet;
    }

    public void setPacket(final Packet packet) {
        this.packet = packet;
    }
}
