package me.EaZy.client.utils;

import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.digest.DigestUtils;

public class HWID {

    public static final int EaZy = 218;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    private static String md5(final String sre) {
        MessageDigest m;
        try {
            m = MessageDigest.getInstance("MD5");
            m.update(sre.getBytes(), 0, sre.length());
            return new BigInteger(1, m.digest()).toString(16);
        } catch (final NoSuchAlgorithmException e) {
            return null;
        }
    }

    private static String sha256(final String text) {
        return DigestUtils.sha256Hex(text);
    }

    public static String get() {
        String computername;
        try {
            computername = InetAddress.getLocalHost().getHostName();
            final String user = System.getProperty("user.name");
            return md5(sha256(user + "@" + computername));
        } catch (final UnknownHostException e) {
            final String user = System.getProperty("user.name");
            return md5(sha256(user));
        }
    }

}
