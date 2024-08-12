package me.EaZy.client.alts;

public class Alt {

    public static final int EaZy = 23;

    public void lambdaStuff() {
        java.util.Arrays.asList().stream().filter((m) -> (false)).forEachOrdered((m) -> {
        });
    }

    private final String email;
    private String name;
    private String password;
    private boolean cracked;
    private boolean unchecked;
    private boolean starred;
    /**
     * 0 = loading, 1 = false, 2 = true, 3 = idk cuz itz not checkt
     */
    public int hasOFCape = 0;
    /**
     * 0 = loading, 1 = false, 2 = true, 3 = idk cuz itz not checkt
     */
    public int hasLabyModCape = 0;

    public Alt(final String email, final String password, final String name) {
        this(email, password, name, false);
    }

    public Alt(final String email, final String password, final String name, final boolean starred) {
        this.email = email;
        this.starred = starred;
        if (password == null || password.isEmpty()) {
            cracked = true;
            unchecked = false;
            this.name = email;
            this.password = null;
        } else {
            cracked = false;
            unchecked = name == null || name.isEmpty();
            this.name = name;
            this.password = password;
        }
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getNameOrEmail() {
        return unchecked ? email : name;
    }

    public String getPassword() {
        if (password == null || password.isEmpty()) {
            cracked = true;
            return "";
        }
        return password;
    }

    public boolean isCracked() {
        return cracked;
    }

    public boolean isStarred() {
        return starred;
    }

    public void setStarred(final boolean starred) {
        this.starred = starred;
    }

    public boolean isUnchecked() {
        return unchecked;
    }

    public void setChecked(final String name) {
        this.name = name;
        unchecked = false;
    }
}
