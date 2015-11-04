package org.welleby.web.scrape.aftonbladet.persistence;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;

@Table("workingUrls")
public class WorkingUrlDob extends Model {
	public WorkingUrlDob getName(int i) {
		return WorkingUrlDob.findFirst("id = ?", i);
	}
}
