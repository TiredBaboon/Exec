package com.website.auto;

import com.jcraft.jsch.*;
import java.io.*;

public class Exec {
    public static void main(String[] args) throws IOException, JSchException {
        String host = args[0];
        String user = args[1];
        String pswd = args[2];
        String cmd = args[3];

        JSch jsch = new JSch();

        Session session = jsch.getSession(user, host, 22);

        session.setConfig("StrictHostKeyChecking", "no");
        session.setPassword(pswd);
        session.connect();

        Channel channel = session.openChannel("exec");
        ((ChannelExec)channel).setCommand(cmd);

        channel.setInputStream(null);
        ((ChannelExec)channel).setErrStream(System.err);
        InputStream in = channel.getInputStream();
        channel.connect();

        byte[] tmp = new byte[1024];

        System.out.println("Session: " + session.isConnected());
        System.out.println("Channel: " + channel.isConnected());

        while (true) {
            while (in.available() > 0) {
                int i = in.read(tmp, 0, 1024);

                if (i < 0) {
                    break;
                }

                //Something is happening where in.available() never goes above 0.  Troubleshooting required.
                //Try verifying connectivity to the host first with something like session.isConnected() and
                //channel.isConnected()

                System.out.println(new String(tmp, 0, i));
            }

            if (channel.isClosed()) {
                if (channel.getExitStatus() == 0) {
                    System.out.println("Command executed successfully.");
                }

                break;
            }
        }

        channel.disconnect();
        session.disconnect();
    }
}
