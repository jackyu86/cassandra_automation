package com.jd.jg.admin.cass;

import com.jd.jg.admin.model.AlertInfo;
import com.jd.jg.admin.model.JobDefine;
import com.jd.jg.admin.model.JobInfo;
import com.jd.jg.admin.utils.TimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CassLogAnalysis {

    Logger logger = LoggerFactory.getLogger(CassLogAnalysis.class);

    @Autowired
    CassandraClient cass;

    public AnalysisResult analysis(){
        List<AlertInfo> alerts = new ArrayList<>();
        List<JobInfo> jobs = new ArrayList<>();

        Map<String,JobInfo> jobLastSuccess = new HashMap<>();
        Map<String,JobInfo> jobLastRun= new HashMap<>();

        CassTableScan cassTableScan = new CassTableScan(cass.getSession(),"ur.job_info","job_name");
        cassTableScan.load(row -> {
            JobInfo ji = new JobInfo();

            ji.jobName = row.getString("job_name");
            ji.dt = row.getString("dt");
            ji.cassSeed = row.getString("cass_seed");
            ji.startTime = row.getTimestamp("start_time");
            ji.endTime = row.getTimestamp("end_time");
            ji.config = row.getString("config");
            ji.updateRecords = row.getLong("modified");

            if( ji.startTime == null ){
                logger.warn("find {} with null starttime",ji.getJobName());
                return;
            }

            if( ji.endTime != null){
                if( jobLastSuccess.get(ji.getJobName()) != null ){
                    JobInfo j = jobLastSuccess.get(ji.jobName);
                    if( j.startTime == null || ji.startTime == null)return;
                    if( j.startTime.before(ji.startTime)){
                        jobLastSuccess.put(ji.jobName,ji);
                    }
                }
                else{
                    jobLastSuccess.put(ji.jobName,ji);
                }
            }

            if( jobLastRun.get(ji.getJobName()) != null){
                if( jobLastRun.get(ji.getJobName()).getStartTime().before(ji.getStartTime()) ){
                    jobLastRun.put(ji.jobName,ji);
                }
            }
            else {
                jobLastRun.put(ji.jobName,ji);
            }

            jobs.add(ji);
        });

        long now = System.currentTimeMillis();
        for(JobInfo j : jobs){
            if( j.getEndTime() != null){
                j.status = "成功";
            }
            else {
                JobInfo lastSuccessJob = jobLastSuccess.get(j.getJobName());
                if( lastSuccessJob == null){
                    j.status = "尚未成功";
                }
                else if( (now - j.getStartTime().getTime()) < (lastSuccessJob.getEndTime().getTime() - lastSuccessJob.getStartTime().getTime())*2 ){
                    j.status = "运行中";
                }
                else{
                    j.status = "超时失败";
                }
            }
        }

        CassTableScan ctc = new CassTableScan(cass.getSession(),"ur.job_define","job_name");
        ctc.load(row -> {
            JobDefine jd = new JobDefine();
            jd.setJobName(row.getString("job_name"));
            jd.setIntervalSeconds(row.getInt("interval_seconds"));

            JobInfo jobLR = jobLastRun.get(jd.getJobName());
            JobInfo jobLS = jobLastSuccess.get(jd.getJobName());

            if( jobLR == null || jobLS == null || jobLR.getStartTime() == null || jobLS.getStartTime() == null)return;

            AlertInfo ai = new AlertInfo();
            ai.setJobName(jd.getJobName());
            ai.setKey(jd.getUmpKey());
            long startTimeDelay = (now - jobLR.getStartTime().getTime())/1000 - jd.getIntervalSeconds();
            if( startTimeDelay > 5*60 ){ // 允许545分钟的延迟
                ai.setDelayTime(TimeUtils.getReadableTime((int)startTimeDelay));
                ai.setType("未在规定时间开始");
                alerts.add(ai);
            }
            else if( jobLR.getEndTime() == null){
                long currentRuntime = now - jobLR.getStartTime().getTime();
                long lastRuntime = jobLS.getEndTime().getTime() - jobLS.getStartTime().getTime();
                int endTimeDelay = (int)((currentRuntime - lastRuntime*2)/1000);
                if( endTimeDelay > 0 && currentRuntime > 10*60*1000){
                    ai.setDelayTime(TimeUtils.getReadableTime(endTimeDelay));
                    ai.setType("超过上次执行时间2倍仍未完成");
                    alerts.add(ai);
                }
            }
        });

        AnalysisResult ar = new AnalysisResult();
        ar.setAlerts(alerts);
        ar.setJobs(jobs);
        return ar;
    }

}
