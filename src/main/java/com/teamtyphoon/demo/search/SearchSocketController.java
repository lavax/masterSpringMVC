package com.teamtyphoon.demo.search;

import java.util.List;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.social.twitter.api.SearchParameters;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SearchSocketController {
	private CachedSearchService searchService;
	private SimpMessagingTemplate webSocket;

	@Autowired
	public SearchSocketController(CachedSearchService searchService, SimpMessagingTemplate webSocket) {
		this.searchService = searchService;
		this.webSocket = webSocket;
	}

	@MessageMapping("/search")
	public void search(@RequestParam List<String> keywords) throws Exception {
		Consumer<List<LightTweet>> callback = tweet -> webSocket.convertAndSend("/topic/searchResults", tweet);
		twitterSearch(SearchParameters.ResultType.POPULAR, keywords, callback);
	}

	public void twitterSearch(SearchParameters.ResultType resultType, List<String> keywords,
			Consumer<List<LightTweet>> callback) {
		keywords.stream().forEach(keyword -> {
			searchService.search(resultType.toString(), keyword).addCallback(callback::accept, Throwable::printStackTrace);
		});
	}
}
