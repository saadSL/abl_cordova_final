package com.unikrew.faceoff.ABLPlugin.model;

import java.io.Serializable;

public class FingerprintsItem implements Serializable {
	@Override
	public String toString() {
		return "FingerprintsItem{" +
				"template='" + template + '\'' +
				", index=" + index +
				'}';
	}

	private String template;
	private Long index;

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public Long getIndex() {
		return index;
	}

	public void setIndex(Long index) {
		this.index = index;
	}
}
