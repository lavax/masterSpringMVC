package com.teamtyphoon.demo.controller;

import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.teamtyphoon.demo.MasterSpringMvcApplication;
import com.teamtyphoon.demo.search.LightTweet;
import com.teamtyphoon.demo.search.SearchController;
import com.teamtyphoon.demo.search.SearchService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MasterSpringMvcApplication.class)
@WebAppConfiguration
public class SearchControllerMockTest {
	@Mock
	private SearchService searchService;
	@InjectMocks
	private SearchController searchController;
	private MockMvc mockMvc;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(searchController).setRemoveSemicolonContent(false).build();
	}

	@Test
	public void should_search() throws Exception {
		when(searchService.search(anyString(), anyListOf(String.class)))
				.thenReturn(Arrays.asList(new LightTweet("tweetText")));
		this.mockMvc.perform(get("/search/mixed;keywords=spring")).andExpect(status().isOk())
				.andExpect(view().name("resultPage"))
				.andExpect(model().attribute("tweets", everyItem(hasProperty("text", is("tweetText")))));
		verify(searchService, times(1)).search(anyString(), anyListOf(String.class));
	}
}