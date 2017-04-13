package com.website.auto;

import java.util.Properties;
import java.io.*;

public class Credentials {
    private String user;
    private String pswd;

    private static String credentialsFile = "/home/toc362_ubuntu/scripts/java/Exec/src/main/resources/credentials/credentials.properties";
    private Properties credentials = new Properties();

    public Credentials(String host) {
        FileInputStream in = null; 

        try {
            in = new FileInputStream(credentialsFile);

            try {
                credentials.load(in);
                in.close();

                this.user = findUser(host);
                this.pswd = findPswd(host);
            } catch (IOException i) {
                System.out.println("IOException: " + i);
            }
        } catch (FileNotFoundException f) {
            System.out.println("FileNotFoundException: " + f);
        }
    }

    private String findUser(String host) {
        return this.credentials.getProperty("user");
    }

    private String findPswd(String host) {
        return this.credentials.getProperty("pswd");
    }

    public String getUser() {
        return this.user;
    }

    public String getPswd() {
        return this.pswd;
    }
}
