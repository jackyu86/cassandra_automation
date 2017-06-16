/**
 * Copyright (C) 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package controllers;

import com.google.common.base.Strings;
import com.jd.jg.utils.SshExec;
import ninja.Result;
import ninja.Results;

import com.google.inject.Singleton;
import ninja.params.Param;

import java.io.UnsupportedEncodingException;


@Singleton
public class ApplicationController {

    public Result index() {
        return Results.html();
    }

    public Result pogo() {
        return Results.html();
    }
    
    public Result sshrun(@Param("cmd")String cmd,@Param("host")String host) {
        SshExec ssh = new SshExec();
        ssh.login = "upp";
        ssh.password = "1qaz@WSX";
        ssh.host = host;

        try {
            if(Strings.isNullOrEmpty(cmd)){
                System.out.println(cmd);
                return Results.badRequest();
            }
            ssh.connect();
            SshExec.SshResult r = ssh.exec(cmd);

            return Results.json().render("log",r.stdout).render("err",r.stderr);
        }catch (Exception ex){
            ex.printStackTrace();
            return Results.badRequest();
        }
    }

}
