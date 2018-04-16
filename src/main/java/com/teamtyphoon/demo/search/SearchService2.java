package com.teamtyphoon.demo.search;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.teamtyphoon.demo.cache.SearchCache;

@Profile("!async")
@Service
@Primary
public class SearchService2 implements TwitterSearch {
	private SearchCache searchCache;

	@Autowired
	public SearchService2(SearchCache searchCache) {
		this.searchCache = searchCache;
	}

	@Override
	public List<LightTweet> search(String searchType, List<String> keywords) {
		System.out.println("Service 2 was invoked");
		return keywords.stream().flatMap(keyword -> searchCache.fetch(searchType, keyword).stream())
				.collect(Collectors.toList());
	}
}