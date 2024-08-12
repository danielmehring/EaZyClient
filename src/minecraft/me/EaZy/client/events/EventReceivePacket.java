package me.EaZy.client.events;

import com.darkmagician6.eventapi.events.callables.EventCancellable;

import net.minecraft.network.Packet;

public class EventReceivePacket extends EventCancellable {

    public static final int EaZy = 43;

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
