package com.tcook.github.favlag.dal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tcook.github.favlag.model.Language;
import com.tcook.github.favlag.model.Repository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GitHubRestDALTests {
	
	@Autowired 
	private GitHubRestDAL gitHubRestDAL;
	@Autowired
	private CacheManager cacheManager;

	@Test
	public void testGetUserRepos() {
		List<Repository> repositories = gitHubRestDAL.findUserRepos("kitodo");
		assertTrue(!repositories.isEmpty());
	}
	
	@Test
	public void testNotUserRepoFound() {
		List<Repository> repositories = gitHubRestDAL.findUserRepos("thisusernotexist99999");
		assertTrue(repositories.isEmpty());
	}	
	
	@Test
	public void testGetUserLang() throws JsonProcessingException, IOException {
		List<Language> languages = gitHubRestDAL.findUserLanguages("defunkt","ambition");
		assertTrue(!languages.isEmpty());
	}
	
	
	@Test
	public void testNoUserLangFound() throws JsonProcessingException, IOException {
		List<Language> languages = gitHubRestDAL.findUserLanguages("testgithubpua","ambition");
		assertTrue(languages.isEmpty());
	}
	
	@Test
	public void validateCache() {
		Cache repoCache = cacheManager.getCache("repos");
		assertThat(repoCache).isNotNull();
		repoCache.clear(); // Simple test assuming the cache is empty
		assertThat(repoCache.get("esaenzc")).isNull();
		List<Repository> repo = gitHubRestDAL.findUserRepos("esaenzc");
		assertThat(repoCache.get("esaenzc").get()).isEqualTo(repo);
	}

	
}
