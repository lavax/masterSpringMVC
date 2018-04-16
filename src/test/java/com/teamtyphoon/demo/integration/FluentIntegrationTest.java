package com.teamtyphoon.demo.integration;

import static org.assertj.core.api.Assertions.assertThat;

import org.fluentlenium.adapter.FluentTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.teamtyphoon.demo.MasterSpringMvcApplication;
import com.teamtyphoon.demo.config.StubTwitterSearchConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { MasterSpringMvcApplication.class, StubTwitterSearchConfig.class })
//@WebIntegrationTest(randomPort = true)
public class FluentIntegrationTest extends FluentTest {
	@Value("${local.server.port}")
	private int serverPort;

	@Override
	public WebDriver getDefaultDriver() {
		return new PhantomJSDriver();
	}

	public String getDefaultBaseUrl() {
		return "http://localhost:" + serverPort;
	}

	@Test
	public void hasPageTitle() {
		goTo("/");
		assertThat(findFirst("h2").getText()).isEqualTo("Login");
	}
}