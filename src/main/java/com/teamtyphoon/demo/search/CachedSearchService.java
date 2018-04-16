package com.teamtyphoon.demo.search;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.util.concurrent.ListenableFuture;

import com.teamtyphoon.demo.cache.SearchCache;

public class CachedSearchService {
	protected final Log logger = LogFactory.getLog(getClass());
	private SearchCache searchCache;

	@Autowired
	public CachedSearchService(SearchCache searchCache) {
		this.searchCache = searchCache;
	}

	@Async
	public ListenableFuture<List<LightTweet>> search(String searchType, String keyword) {
		logger.info(Thread.currentThread().getName() + " - Searching for " + keyword);
		return new AsyncResult<>(searchCache.fetch(searchType, keyword));
	}

}
