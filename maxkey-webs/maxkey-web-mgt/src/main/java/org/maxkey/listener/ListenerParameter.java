package org.maxkey.listener;

import org.quartz.JobDataMap;

public class ListenerParameter {
	JobDataMap parameters ;

	public ListenerParameter() {
		parameters = new JobDataMap();
	}
	
	public ListenerParameter add(String key , Object value) {
		parameters.put(key, value);
		return this;
	}
	
	public JobDataMap build() {
		return this.parameters;
	}
}
