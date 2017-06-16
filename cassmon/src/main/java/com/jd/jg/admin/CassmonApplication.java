package com.jd.jg.admin;

import com.jd.jg.admin.cass.AnalysisResult;
import com.jd.jg.admin.cass.CassLogAnalysis;
import com.jd.jg.admin.controller.AlertController;
import com.jd.jg.admin.model.AlertInfo;
import com.jd.ump.profiler.proxy.Profiler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
@EnableScheduling
public class CassmonApplication {

	Set<String> failedJobs = new HashSet<>();

	Logger logger = LoggerFactory.getLogger(CassmonApplication.class);

	@Autowired
	CassLogAnalysis cla;
	@Autowired
	AlertController ac;

	@Scheduled(cron="0 */1 * * * *")
	public void sendAlert() {
		ac.sendAggAlert();
	}

	@Scheduled(cron="0 */5 * * * *")
	public void reportCurrentTime() {
		AnalysisResult ar = cla.analysis();
		if( ar.getAlerts().size() == 0)return;

		Set<String> newFailedJobs = new HashSet<>();
		for(AlertInfo ai : ar.getAlerts()){
			if( ! failedJobs.contains(ai.getJobName())) {
				Profiler.businessAlarm("jinggang.di.cass", ai.alertMessage());
			}
			newFailedJobs.add(ai.getJobName());
			logger.warn("send alert to ump {}",ai.getJobName());
		}

		failedJobs = newFailedJobs;
	}

	public static void main(String[] args) {
		SpringApplication.run(CassmonApplication.class, args);
	}
}
