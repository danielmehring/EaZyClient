package me.EaZy.client.utils;

public class JumpThread extends Thread {

    public static final int EaZy = 221;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    private boolean isJumping = false;

    @Override
    public void run() {
        isJumping = true;
        PlayerUtil.packetJump();
        try {
            Thread.sleep(400);
        } catch (final InterruptedException e) {
            e.printStackTrace();
        }
        isJumping = false;
    }

    public boolean isJumping() {
        return isJumping;
    }
}
