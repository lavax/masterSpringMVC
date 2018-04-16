package com.teamtyphoon.demo.config;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.google.common.cache.CacheBuilder;

@Configuration
@EnableCaching
public class CacheConfiguration {
//	@Bean
//	public CacheManager cacheManager() {
//		SimpleCacheManager simpleCacheManager = new SimpleCacheManager();
//		simpleCacheManager.setCaches(Arrays.asList(new ConcurrentMapCache("searches")));
//		return simpleCacheManager;
//	}
	
	@Bean
	public CacheManager cacheManager() {
		GuavaCacheManager cacheManager = new GuavaCacheManager("searches");
		cacheManager.setCacheBuilder(CacheBuilder.newBuilder().softValues().expireAfterWrite(10, TimeUnit.MINUTES));
		return cacheManager;
	}
}