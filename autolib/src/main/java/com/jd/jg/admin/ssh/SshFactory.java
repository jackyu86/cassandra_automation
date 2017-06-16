package com.jd.jg.admin.ssh;

import java.util.HashMap;
import java.util.Map;

public class SshFactory {

    static Map<String,Ssh> repo = new HashMap<>();

    public static final Ssh getSSH(String host){
        if( ! repo.containsKey(host)){
            Ssh ssh = new Ssh();
            ssh.host = host;
            if( host.startsWith("192")){
                ssh.login = "admin";
                ssh.password = "1qaz@WSX";
            } else if (host.startsWith("10")) { // my local ip
                ssh.login = "bean";
                ssh.password = "youmac";
            }
            else {
                ssh.login = "admin";
                ssh.pubKeyPath = "k";
                ssh.passPhrase = "cassRSA";
            }
            ssh.connect();
            repo.put(host,ssh);
        }
        return repo.get(host);
    }

    public static final Ssh getSSH(String host,String uid, String passwd){
        Ssh ssh = new Ssh();
        ssh.host = host;
        ssh.login = uid;
        ssh.password = passwd;
        ssh.connect();
        return ssh;
    }
}
