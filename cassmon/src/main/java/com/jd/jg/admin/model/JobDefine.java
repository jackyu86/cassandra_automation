package com.jd.jg.admin.model;

public class JobDefine {

    String jobName;
    String umpKey;
    int intervalSeconds;

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public int getIntervalSeconds() {
        return intervalSeconds;
    }

    public void setIntervalSeconds(int intervalSeconds) {
        this.intervalSeconds = intervalSeconds;
    }

    public String getUmpKey() {
        return umpKey;
    }

    public void setUmpKey(String umpKey) {
        this.umpKey = umpKey;
    }
}
