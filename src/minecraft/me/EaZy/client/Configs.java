package me.EaZy.client;

public class Configs {

    public static final int EaZy = 33;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    public static String commandPrefix = "-";
    /**
     * Modes: 0 = Normal, 1 = Color Changing White, 2 = dark button
     */
    public static int buttonMode = 2;
    public static boolean blurredBackground = false;
    public static boolean toggleSounds = false;
    public static boolean noBob = true;
    public static boolean smoothZoom = true;
    public static boolean advancedHotbar = true;
    public static boolean saveEaZyLogin = false;
    public static boolean itemPhysics = false;
    public static boolean betterChat = true;
    public static boolean suffix = false;
    public static boolean otherSwing = false;
    public static boolean customFont = false;
    public static boolean useClientColor = false;
    public static boolean f5PositionXD = true;
    public static boolean invParticles = false;
    public static boolean antiLag = true;
    public static boolean gta5Death = false;
    public static boolean gta5DeathSound = true;
    public static String gta5Text = "wasted";

    public static String bgPath = "";
    public static boolean bgMoving = true;
    
    public static String loginName = "";
    public static String loginPassword = "";

    public static long cookies = 0l;
    public static long tritte = 0l;
    public static long spinns = 0l;

    public static String buttonModeName = "Dark";

    public static void nextButtonMode() {
        switch (buttonMode) {
            case 0:
                buttonMode = 1;
                buttonModeName = "Light";
                break;
            case 1:
                buttonMode = 2;
                buttonModeName = "Dark";
                break;
            case 2:
                buttonMode = 0;
                buttonModeName = "Minecraft";
                break;
            default:
                break;
        }
    }
}
