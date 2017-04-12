package com.website.auto;

public class Remote {
    public static void main(String[] args) {
        String host = args[0]; 
        String cmd = args[1];

        AffectedHost remoteHost = new AffectedHost(host);

        int ret_code = remoteHost.exec(cmd);

        System.out.printf("Exit code: %d%n", ret_code);
    }
}
