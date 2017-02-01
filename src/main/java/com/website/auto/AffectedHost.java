package com.website.auto;

import com.jcraft.jsch.*;
import java.io.*;
import java.lang.StringBuilder;

public class AffectedHost {
    private String host;
    private String user;
    private String pswd;

    public AffectedHost(String host, String user, String pswd) {
        this.host = host;
        this.user = user;
        this.pswd = pswd;
    }

    private static void cleanUp(Session session, Channel channel) {
        channel.disconnect();
        session.disconnect();
    }

    public int exec(String cmd) {
        JSch jsch = new JSch();
        Session session = null;
        Channel channel = null;
        InputStream stdout = null;
        BufferedReader bufferedReader = null;
        StringBuilder results = new StringBuilder();
        int int_result = 1;

        try {
            session = jsch.getSession(this.user, this.host, 22);
            session.setConfig("StrictHostKeyChecking", "no");
            session.setPassword(this.pswd);
            session.connect();

            channel = session.openChannel("exec");
            ((ChannelExec)channel).setCommand(cmd);

            channel.setInputStream(null);
            ((ChannelExec)channel).setErrStream(System.err);

            channel.connect();
        } catch (JSchException e) {
            cleanUp(session, channel);
            System.out.println("Exception: " + e);

            return 1;
        }


        try {
            stdout = channel.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(stdout));

            while ((int_result = bufferedReader.read()) != -1) {
                results.append((char)int_result);
            }
        } catch (IOException e) {
            cleanUp(session, channel);
            System.out.println("Exception: " + e);

            return 1;
        }

        cleanUp(session, channel);

        System.out.println(results.toString());

        return channel.getExitStatus();
    }
}
