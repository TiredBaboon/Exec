package com.website.auto;

public class Remote {
    public static void main(String[] args) {
        String host = args[0]; 
        String user = args[1];
        String pswd = args[2];
        String cmd = args[3];

        AffectedHost remoteHost = new AffectedHost(host, user, pswd);

        int ret_code = remoteHost.exec(cmd);

        System.out.printf("Exit code: %d%n", ret_code);
    }
}
