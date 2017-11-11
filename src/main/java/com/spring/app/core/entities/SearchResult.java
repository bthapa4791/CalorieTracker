package com.spring.app.core.entities;

import java.util.List;

public class SearchResult<T> {
	
	private Long resultsCount;
	private List<T> result;
	
	public SearchResult(long resultsCount, List<T> result) {
		this.resultsCount = resultsCount;
        this.result = result;
	}

	public Long getResultsCount() {
		return resultsCount;
	}

	public List<T> getResult() {
		return result;
	}

}
