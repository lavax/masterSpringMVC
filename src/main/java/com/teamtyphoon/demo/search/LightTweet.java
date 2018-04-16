package com.teamtyphoon.demo.search;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.TwitterProfile;

public class LightTweet implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String profileImageUrl;
	private String user;
	private String text;
	private LocalDateTime date;
	private String lang;
	private Integer retweetCount;

	public String getProfileImageUrl() {
		return profileImageUrl;
	}

	public void setProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public Integer getRetweetCount() {
		return retweetCount;
	}

	public void setRetweetCount(Integer retweetCount) {
		this.retweetCount = retweetCount;
	}

	public LightTweet(String text) {
		this.text = text;
	}

	public static LightTweet ofTweet(Tweet tweet) {
		LightTweet lightTweet = new LightTweet(tweet.getText());
		Date createdAt = tweet.getCreatedAt();
		if (createdAt != null) {
			lightTweet.date = LocalDateTime.ofInstant(createdAt.toInstant(), ZoneId.systemDefault());
		}
		TwitterProfile tweetUser = tweet.getUser();
		if (tweetUser != null) {
			lightTweet.user = tweetUser.getName();
			lightTweet.profileImageUrl = tweetUser.getProfileImageUrl();
		}
		lightTweet.lang = tweet.getLanguageCode();
		lightTweet.retweetCount = tweet.getRetweetCount();
		return lightTweet;
	}
	// don't forget to generate getters
	// They are used by Jackson to serialize objects
}