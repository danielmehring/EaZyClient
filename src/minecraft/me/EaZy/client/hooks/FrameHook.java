package me.EaZy.client.hooks;

import javax.swing.JFrame;

public class FrameHook {

    public static final int EaZy = 83;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    private static JFrame frame;

    public static void maximize() {
        if (frame != null) {
            frame.setExtendedState(6);
        }
    }

    public static JFrame getFrame() {
        return frame;
    }
}
