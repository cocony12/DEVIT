package com.ssafy.devit.model.request;

import java.util.Arrays;

public class SearchFormRequest {
	private int startPage;
	private String[] searchText;

	public SearchFormRequest() {
		super();
	}

	public SearchFormRequest(int startPage, String[] searchText) {
		super();
		this.startPage = startPage;
		this.searchText = searchText;
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public String[] getSearchText() {
		return searchText;
	}

	public void setSearchText(String[] searchText) {
		this.searchText = searchText;
	}

	@Override
	public String toString() {
		return "SearchFormRequest [startPage=" + startPage + ", searchText=" + Arrays.toString(searchText) + "]";
	}

}
