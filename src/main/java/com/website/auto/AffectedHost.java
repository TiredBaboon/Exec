package com.website.auto;

import com.jcraft.jsch.*;
import java.io.*;
import java.lang.StringBuilder;

public class AffectedHost {
    private String host;
    private String user;
    private String pswd;

    public AffectedHost(String host) {
        this.host = host;
        Credentials creds = new Credentials(host);
        this.user = creds.getUser();
        this.pswd = creds.getPswd();
    }

    public int exec(String cmd) {
        JSch jsch = new JSch();
        Session session = null;

        try {
            session = jsch.getSession(this.user, this.host, 22);
        } catch (JSchException e) {
            System.out.println(e);
        }

        try {
            session.setConfig("StrictHostKeyChecking", "no");
            session.setPassword(this.pswd);
            session.connect();
        } catch (JSchException e) {
            System.out.println(e);
        }

        Channel channel = null;

        try {
            channel = session.openChannel("exec");
        } catch (JSchException e) {
            System.out.println(e);
        }

        try {
            ((ChannelExec)channel).setCommand(cmd);

            channel.setInputStream(null);
            ((ChannelExec)channel).setErrStream(System.err);
            channel.connect();
        } catch (JSchException e) {
            System.out.println(e);
        }

        InputStream stdout = null;

        try {
            stdout = channel.getInputStream();
        } catch (IOException e) {
            System.out.println(e);
        }

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stdout));

        StringBuilder results = new StringBuilder();
        int int_result;

        try {
            while ((int_result = bufferedReader.read()) != -1) {
                results.append((char)int_result);
            }
        } catch (IOException e) {
            System.out.println(e);
        }

        System.out.println(results.toString());

        channel.disconnect();
        session.disconnect();

        return channel.getExitStatus();
    }
}
