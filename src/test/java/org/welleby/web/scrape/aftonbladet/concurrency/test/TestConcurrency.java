package org.welleby.web.scrape.aftonbladet.concurrency.test;

import org.welleby.web.scrape.aftonbladet.concurrency.AbJob;
import org.welleby.web.scrape.aftonbladet.concurrency.AbJobDistributer;
import org.welleby.web.scrape.aftonbladet.concurrency.JobDistributer;

public class TestConcurrency {
	public static void main(String[] args) {
		JobDistributer<AbJob> jobDistr = new AbJobDistributer();
		for(int i=0; i<10; i++)
			System.out.println("Job: "+jobDistr.getJob().getArticleFrom() );

    }

}
