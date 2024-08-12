package me.EaZy.client.utils;

import net.minecraft.util.StringUtils;

public class Friend {

    public static final int EaZy = 213;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    private String username;
    private String nick;

    public Friend(final String username) {
        this.username = username;
        nick = username;
    }

    public Friend(final String username, final String nick) {
        this.username = username;
        this.nick = nick;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(final String nick) {
        this.nick = nick;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public boolean equals(String usernamealias) {
        usernamealias = StringUtils.stripControlCodes(usernamealias);
        return username.equalsIgnoreCase(usernamealias);
    }
}
