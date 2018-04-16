package com.teamtyphoon.demo.controller;

import java.util.Arrays;

import org.springframework.mock.web.MockHttpSession;

import com.teamtyphoon.demo.profile.UserProfileSession;

public class SessionBuilder {
	private final MockHttpSession session;
	UserProfileSession sessionBean;

	public SessionBuilder() {
		session = new MockHttpSession();
		sessionBean = new UserProfileSession();
		session.setAttribute("scopedTarget.userProfileSession", sessionBean);
	}

	public SessionBuilder userTastes(String... tastes) {
		sessionBean.setTastes(Arrays.asList(tastes));
		return this;
	}

	public MockHttpSession build() {
		return session;
	}
}
