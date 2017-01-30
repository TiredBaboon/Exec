package com.website.auto;

import com.jcraft.jsch.*;
import java.io.*;
import java.lang.StringBuilder;

public class Exec {
    public static void main(String[] args) throws IOException, JSchException {
        String host = args[0];
        String user = args[1];
        String pswd = args[2];
        String cmd = args[3];

        JSch jsch = new JSch();
        Session session = jsch.getSession(user, host, 22);

        try {
            session.setConfig("StrictHostKeyChecking", "no");
            session.setPassword(pswd);
            session.connect();
        } catch (JSchException e) {
            System.out.println(e);
        }

        Channel channel = session.openChannel("exec");

        try {
            ((ChannelExec)channel).setCommand(cmd);

            channel.setInputStream(null);
            ((ChannelExec)channel).setErrStream(System.err);
            channel.connect();
        } catch (JSchException e) {
            System.out.println(e);
        }

        InputStream stdout = channel.getInputStream();
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
        System.out.printf("Exit status: %d%n", channel.getExitStatus());

        channel.disconnect();
        session.disconnect();
    }
}
