package edu.usc.jobs;

import android.text.Html;
import android.text.Spanned;

public class SearchResults {
	private String name = "";
	private String cityState = "";
	private String phone = "";
	private String link;

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setCityState(String cityState) {
		this.cityState = cityState;
	}

	public String getCityState() {
		return cityState;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPhone() {
		return phone;
	}

	public void setLink(String link) {

		this.link = link;
		// this.link = link;

	}

	public String getLink() {
		return link;
	}

}