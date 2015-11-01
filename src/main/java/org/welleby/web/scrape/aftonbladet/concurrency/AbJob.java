package org.welleby.web.scrape.aftonbladet.concurrency;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class AbJob extends Job {

	private int articleFrom;
	private int articleTo;
	private String baseUrl;
	private AbJobResult jobResult = new AbJobResult();
	
	
	public AbJob(String name) {
		super(name);
		jobResult = new AbJobResult();
	}


	public int getArticleFrom() {
		return articleFrom;
	}

	public void setArticleFrom(int articleFrom) {
		this.articleFrom = articleFrom;
	}

	public int getArticleTo() {
		return articleTo;
	}

	public void setArticleTo(int articleTo) {
		this.articleTo = articleTo;
	}

	@Override
	public boolean execute() {
		try{
	        for (int i = articleFrom + 1; i < articleTo; i++) {
	            URL url = new URL(getBaseUrl() + i + ".ab");
	            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	            connection.setRequestMethod("HEAD");
	            connection.setInstanceFollowRedirects(false);
	            int responseCode = connection.getResponseCode();
	            if (responseCode == 301 || responseCode == 200) {
	                System.out.println(url);
	                jobResult.addResult(url,new Boolean(true));
	            }else {
	                System.out.println(responseCode + " on " + url);
	                jobResult.addResult(url,new Boolean(false));
	            }
	        }
			return true;
		} catch (IOException e){
			return false;
		}
	}


	public String getBaseUrl() {
		return baseUrl;
	}


	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}


	@Override
	public JobResult getResult() {
		return jobResult;
	}
}
