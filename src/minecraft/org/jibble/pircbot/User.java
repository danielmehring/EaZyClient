package org.jibble.pircbot;

public class User {

    public static final int EaZy = 1986;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    private final String _prefix;
    private final String _nick;
    private final String _lowerNick;

    User(final String prefix, final String nick) {
        _prefix = prefix;
        _nick = nick;
        _lowerNick = nick.toLowerCase();
    }

    public String getPrefix() {
        return _prefix;
    }

    public boolean isOp() {
        return _prefix.indexOf(64) >= 0;
    }

    public boolean hasVoice() {
        return _prefix.indexOf(43) >= 0;
    }

    public String getNick() {
        return _nick;
    }

    @Override
    public String toString() {
        return String.valueOf(getPrefix()) + getNick();
    }

    public boolean equals(final String nick) {
        return nick.toLowerCase().equals(_lowerNick);
    }

    @Override
    public boolean equals(final Object o) {
        if (o instanceof User) {
            final User other = (User) o;
            return other._lowerNick.equals(_lowerNick);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return _lowerNick.hashCode();
    }

    public int compareTo(final Object o) {
        if (o instanceof User) {
            final User other = (User) o;
            return other._lowerNick.compareTo(_lowerNick);
        }
        return -1;
    }
}
