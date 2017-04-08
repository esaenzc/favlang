package com.tcook.github.favlag.dal;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tcook.github.favlag.model.Language;
import com.tcook.github.favlag.model.Repository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GitHubRestDALTests {
	
	@Autowired 
	private GitHubRestDAL gitHubRestDAL;

	@Test
	public void testGetUserRepos() {
		List<Repository> repositories = gitHubRestDAL.getUserRepos("kitodo");
		assertTrue(!repositories.isEmpty());
	}
	
	@Test
	public void testNotUserRepoFound() {
		List<Repository> repositories = gitHubRestDAL.getUserRepos("thisusernotexist99999");
		assertTrue(repositories.isEmpty());
	}	
	
	@Test
	public void testGetUserLang() throws JsonProcessingException, IOException {
		List<Language> languages = gitHubRestDAL.getUserLanguages("defunkt","ambition");
		assertTrue(!languages.isEmpty());
	}
	
	
	@Test
	public void testNoUserLangFound() throws JsonProcessingException, IOException {
		List<Language> languages = gitHubRestDAL.getUserLanguages("testgithubpua","ambition");
		assertTrue(languages.isEmpty());
	}
	
}
