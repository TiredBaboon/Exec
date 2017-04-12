package com.website.auto;

public class Credentials {
    private String user;
    private String pswd;

    public Credentials(String host) {
        this.user = findUser(host);
        this.pswd = findPswd(host);
    }

    private String findUser(String host) {
        return "tyler";
    }

    private String findPswd(String host) {
        return "chbs78969";
    }

    public String getUser() {
        return this.user;
    }

    public String getPswd() {
        return this.pswd;
    }
}
