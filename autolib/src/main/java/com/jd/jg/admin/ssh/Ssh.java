package com.jd.jg.admin.ssh;

import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.common.IOUtils;
import net.schmizz.sshj.connection.channel.direct.Session;
import net.schmizz.sshj.connection.channel.direct.Session.Command;
import net.schmizz.sshj.transport.verification.HostKeyVerifier;
import net.schmizz.sshj.userauth.keyprovider.KeyProvider;
import net.schmizz.sshj.xfer.FileSystemFile;

import java.io.*;
import java.security.PublicKey;
import java.util.concurrent.TimeUnit;

public class Ssh {
    public String host;
    public String login;
    public String password;
    public String pubKeyPath;
    public String passPhrase;
    final SSHClient ssh = new SSHClient();

    public void connect() {
        try {
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
        } catch (Exception ex) {
            throw new RuntimeException("ssh connection error", ex);
        }
    }

    public void close() {
        try {
            ssh.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void scp(String srcFile, String tgtPath) {
        try {
            ssh.newSCPFileTransfer().upload(new FileSystemFile(srcFile), tgtPath);
        } catch (Exception ex) {
            throw new RuntimeException("failed to scp", ex);
        }

    }

    public void exec(String cmd, Writer writer, int timeout) throws IOException {
        final Session session = ssh.startSession();

        try {
            final Command cmdRes = session.exec(cmd);
            BufferedReader br = new BufferedReader(new InputStreamReader(cmdRes.getInputStream()));
            String line = null;
            while ((line = br.readLine()) != null) {
                writer.write(line);
            }
            cmdRes.join(timeout, TimeUnit.SECONDS);
        } finally {
            session.close();
        }
    }

    public SshResult exec(String cmd, int timeout) {
        try {
            final Session session = ssh.startSession();
            try {
                final Command cmdRes = session.exec(cmd);
                String log = IOUtils.readFully(cmdRes.getInputStream()).toString();
                String err = IOUtils.readFully(cmdRes.getErrorStream()).toString();
                cmdRes.join();
                SshResult sr = new SshResult();
                sr.stderr = err;
                sr.stdout = log;
                System.out.println(log);
                System.out.println(err);
                return sr;
            } finally {
                session.close();
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void main(String[] args) {
        Ssh ssh = new Ssh();
        ssh.login = "admin";
        ssh.pubKeyPath = "k";
        ssh.passPhrase = "cassRSA";
        ssh.host = args[0];
        try {
            ssh.connect();
            ssh.scp("./cassandra-env.sh", "/export/App/jgcass.jd.local/apache-cassandra-2.2.3/conf");
        } finally {
            ssh.close();
        }
    }

}
