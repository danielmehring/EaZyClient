package me.EaZy.client.utils;

import me.EaZy.client.main.Client;
import me.EaZy.client.modules.NoFriends;
import me.EaZy.client.modules.Team;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public abstract class Friends {

    public static final int EaZy = 214;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    public static List<Friend> friends = new ArrayList<>();

    public static void add(String username) {
        username = StringUtils.stripControlCodes(username);
        Friends.remove(username);
        friends.add(new Friend(username));
    }

    public static void add(String username, final String nick) {
        username = StringUtils.stripControlCodes(username);
        Friends.remove(username);
        friends.add(new Friend(username, nick));
    }

    public static boolean remove(String usernamealias) {
        usernamealias = StringUtils.stripControlCodes(usernamealias);
        for (final Friend f : friends) {
            if (!f.getUsername().equalsIgnoreCase(usernamealias)) {
                continue;
            }
            return friends.remove(f);
        }
        return false;
    }

    public static boolean contains(final EntityPlayer ply) {
        if (NoFriends.mod.isToggled()) {
            return false;
        }
        final String usernamealias = StringUtils.stripControlCodes(ply.getName());
        if (Team.mod.isToggled() && PlayerUtil.isPlayerInTeam(ply)) {
            return true;
        }
        for (final Friend f : friends) {
            if (!f.getUsername().equalsIgnoreCase(usernamealias)
                    && !Minecraft.thePlayer.getName().equalsIgnoreCase(usernamealias)) {
                continue;
            }
            return true;
        }
        return false;
    }

    public static boolean containsName(final String name) {
        for (final Friend f : friends) {
            if (!name.equalsIgnoreCase(f.getUsername()) && !Minecraft.thePlayer.getName().equalsIgnoreCase(name)) {
                continue;
            }
            return true;
        }
        return false;
    }

    public static List<Friend> getFriends() {
        return friends;
    }

}
