package com.jd.jg.admin.model;

import com.jd.jg.admin.utils.TimeUtils;

import java.util.Date;
import java.util.Map;

public class JobInfo {

    public String jobName;
    public String dt;
    public String cassSeed;
    public Date startTime;
    public Date endTime;
    public long updateRecords = 0L;
    public String config;
    public Map<String,Long> counters;
    public String status;

    public String getStatus(){
        return status;
    }

    public String getSpendTime(){
        if( endTime == null || startTime == null )return "-";
        int t = (int)((endTime.getTime() - startTime.getTime())/1000);
        return TimeUtils.getReadableTime(t);
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public String getCassSeed() {
        return cassSeed;
    }

    public void setCassSeed(String cassSeed) {
        this.cassSeed = cassSeed;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public long getUpdateRecords() {
        return updateRecords;
    }

    public void setUpdateRecords(long updateRecords) {
        this.updateRecords = updateRecords;
    }

    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }

    public Map<String, Long> getCounters() {
        return counters;
    }

    public void setCounters(Map<String, Long> counters) {
        this.counters = counters;
    }
}
