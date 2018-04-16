package com.teamtyphoon.demo.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import com.teamtyphoon.demo.search.LightTweet;
import com.teamtyphoon.demo.search.TwitterSearch;

@Profile("test")
@Configuration
public class StubTwitterSearchConfig {
	@Primary
	@Bean
	public TwitterSearch twitterSearch() {
		return (searchType, keywords) -> Arrays.asList(new LightTweet("tweetText"), new LightTweet("secondTweet"));
	}
}