package org.welleby.web.scrape.aftonbladet.concurrency;

import java.util.HashMap;
import java.util.Map;

public class AbJobDistributer extends JobDistributer<AbJob> {
	private Map<AbJob,JobStatus> jobs = new HashMap<AbJob, JobStatus>();
	private static final String BASE_URL = "http://www.aftonbladet.se/article";
	
	public AbJobDistributer(){
		for(int i = 0; i<100; i++){
			AbJob newJob = new AbJob(String.valueOf(i));
			newJob.setArticleFrom(i);
			newJob.setArticleTo(i*7);
			newJob.setBaseUrl(BASE_URL);
			jobs.put(newJob, JobStatus.NOT_STARTED);
		}
	}

	@Override
	public synchronized AbJob getJob() {
		for (Map.Entry<AbJob, JobStatus> job : jobs.entrySet()) {
			if(job.getValue().equals(JobStatus.NOT_STARTED)){
				job.setValue(JobStatus.RUNNING);
				return job.getKey();
			}
		}
		return null;
	}

	@Override
	public synchronized boolean reportJob(AbJob job, JobStatus status) {
		jobs.put(job, status);
		return false;
	}

}
