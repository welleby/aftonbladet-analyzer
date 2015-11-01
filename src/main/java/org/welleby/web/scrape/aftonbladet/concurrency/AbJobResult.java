package org.welleby.web.scrape.aftonbladet.concurrency;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class AbJobResult extends JobResult {
	private Map<URL, Boolean> result = new HashMap<URL, Boolean>();

	public Map<URL, Boolean> getResult() {
		return result;
	}

	public void addResult(URL key, Boolean value) {
		result.put(key, value);
	}

}
