package com.jd.jg.utils;

import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.common.IOUtils;
import net.schmizz.sshj.connection.channel.direct.Session;
import net.schmizz.sshj.connection.channel.direct.Session.Command;
import net.schmizz.sshj.transport.verification.HostKeyVerifier;
import net.schmizz.sshj.userauth.keyprovider.KeyProvider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.security.PublicKey;

public class SshExec {
    public String host;
    public String login;
    public String password;
    public String pubKeyPath;
    public String passPhrase;
    final SSHClient ssh = new SSHClient();

    public void connect() throws IOException {
        ssh.useCompression();
        ssh.addHostKeyVerifier(new HostKeyVerifier() {
            @Override
            public boolean verify(String s, int i, PublicKey publicKey) {
                return true;
            }
        });

        ssh.connect(host);
        if (password != null) {
            ssh.authPassword(login, password);
        } else if (pubKeyPath != null) {
            KeyProvider kp = ssh.loadKeys(pubKeyPath, passPhrase);
            ssh.authPublickey(login, kp);
        }
    }

    public void close() {
        try {
            ssh.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void exec(String cmd, Writer writer) throws IOException {
        final Session session = ssh.startSession();

        try {
            final Command cmdRes = session.exec(cmd);
            BufferedReader br = new BufferedReader(new InputStreamReader(cmdRes.getInputStream()));
            String line = null;
            while ((line = br.readLine()) != null) {
                writer.write(line);
            }
            cmdRes.join();
        } finally {
            session.close();
        }
    }

    public SshResult exec(String cmd) throws IOException {
        final Session session = ssh.startSession();
        try {
            final Command cmdRes = session.exec(cmd);
            String log = IOUtils.readFully(cmdRes.getInputStream()).toString();
            String err = IOUtils.readFully(cmdRes.getErrorStream()).toString();
            cmdRes.join();
            SshResult sr = new SshResult();
            sr.stderr = err;
            sr.stdout = log;
            return sr;
        } finally {
            session.close();
        }
    }

    public static void main(String[] args) {
        SshExec ssh = new SshExec();
        ssh.login = "upp";
        ssh.password = "1qaz@WSX";
        ssh.host = "192.168.200.56";
        try {
            ssh.connect();
            ssh.exec("/sbin/ifconfig -a 2>&1");
            ssh.exec("java -version 2>&1");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            ssh.close();
        }

    }

    public static class SshResult{
        public String stdout = "";
        public String stderr = "";
    }

}
