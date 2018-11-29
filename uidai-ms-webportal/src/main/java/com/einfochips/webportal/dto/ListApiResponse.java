package com.einfochips.webportal.dto;

import java.util.List;

/**
 * To return message in API response.
 * 
 * @author asra.shaikh
 *
 */
public class ListApiResponse<T> {

	private String failureMessage;
	private List<T> list;

	public ListApiResponse(String failureMessage, List<T> list) {
		super();

		this.failureMessage = failureMessage;
		this.list = list;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public String getFailureMessage() {
		return failureMessage;
	}

	public void setFailureMessage(String failureMessage) {
		this.failureMessage = failureMessage;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ListApiResponse [failureMessage=").append(failureMessage)
				.append(", list=").append(list).append("]");
		return builder.toString();
	}

}
