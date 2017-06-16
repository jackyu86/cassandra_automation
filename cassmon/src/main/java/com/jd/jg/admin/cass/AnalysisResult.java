package com.jd.jg.admin.cass;

import com.jd.jg.admin.model.AlertInfo;
import com.jd.jg.admin.model.JobInfo;

import java.util.List;

public class AnalysisResult {

    List<AlertInfo> alerts;
    List<JobInfo> jobs;

    public List<AlertInfo> getAlerts() {
        return alerts;
    }

    public void setAlerts(List<AlertInfo> alerts) {
        this.alerts = alerts;
    }

    public List<JobInfo> getJobs() {
        return jobs;
    }

    public void setJobs(List<JobInfo> jobs) {
        this.jobs = jobs;
    }
}
