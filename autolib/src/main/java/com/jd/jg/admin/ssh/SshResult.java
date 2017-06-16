package com.jd.jg.admin.ssh;

public class SshResult {
    public String stdout = "";
    public String stderr = "";

    public SshResult expect(String match){
        if( ! (stdout+stderr).contains(match) ){
            throw new ExpectException("SSH result do not return:"+match);
        }
        return this;
    }

    public SshResult errorDetect(String match){
        if( (stdout+stderr).contains(match) ){
            throw new RuntimeException("SSH result contains error:"+match);
        }
        return this;
    }

    public static class ExpectException extends RuntimeException{

        public ExpectException(String msg){
            super(msg);
        }

    }

    public static class ErrorDetectException extends RuntimeException{

        public ErrorDetectException(String msg){
            super(msg);
        }

    }
}
