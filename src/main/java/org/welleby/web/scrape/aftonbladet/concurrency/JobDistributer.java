package org.welleby.web.scrape.aftonbladet.concurrency;

public abstract class JobDistributer<T extends Job> {
	public abstract T getJob();
	public abstract boolean reportJob(T job, JobStatus status);
}