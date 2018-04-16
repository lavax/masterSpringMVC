package com.teamtyphoon.demo.search;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.SearchParameters;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Service;

@Service
public class SearchService implements TwitterSearch {
//	private Twitter twitter;

//	@Autowired
//	public SearchService(Twitter twitter) {
//		this.twitter = twitter;
//	}

	/* (non-Javadoc)
	 * @see com.teamtyphoon.demo.search.TwitterSearch#search(java.lang.String, java.util.List)
	 */
	@Override
	public List<LightTweet> search(String searchType, List<String> keywords) {

		List<SearchParameters> searches = keywords.stream().map(taste -> createSearchParam(searchType, taste))
				.collect(Collectors.toList());
//		List<Tweet> results = searches.stream().map(params -> twitter.searchOperations().search(params))
//				.flatMap(searchResults -> searchResults.getTweets().stream()).collect(Collectors.toList());
		List<LightTweet> tweets = new ArrayList<LightTweet>();
		long id = 1;
		String text = "text";
		Date createdAt = new Date();
		String fromUser = "fromUser";
		String profileImageUrl = "profileImageUrl";
		Long toUserId = new Long(2);
		long fromUserId = 3;
		String languageCode = "languageCode";
		String source = "source";
		Tweet t = new Tweet(id, text, createdAt, fromUser, profileImageUrl, toUserId, fromUserId, languageCode, source);
		tweets.add(LightTweet.ofTweet(t));
		return tweets;
	}

	private SearchParameters.ResultType getResultType(String searchType) {
		for (SearchParameters.ResultType knownType : SearchParameters.ResultType.values()) {
			if (knownType.name().equalsIgnoreCase(searchType)) {
				return knownType;
			}
		}
		return SearchParameters.ResultType.RECENT;
	}

	private SearchParameters createSearchParam(String searchType, String taste) {
		SearchParameters.ResultType resultType = getResultType(searchType);
		SearchParameters searchParameters = new SearchParameters(taste);
		searchParameters.resultType(resultType);
		searchParameters.count(3);
		return searchParameters;
	}
}