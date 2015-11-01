package org.welleby.web.scrape.aftonbladet.concurrency;

public abstract class Job {
	protected String name;
	public Job(String name){
		this.name = name;
	}
	public abstract boolean execute();
	public abstract JobResult getResult();
}
