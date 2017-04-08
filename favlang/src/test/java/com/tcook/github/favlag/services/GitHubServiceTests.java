package com.tcook.github.favlag.services;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tcook.github.favlag.dal.GitHubRestDAL;
import com.tcook.github.favlag.model.Language;
import com.tcook.github.favlag.model.Repository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GitHubServiceTests {

	@InjectMocks
	private GitHubServiceImpl gitHubServ;
	@Mock
	private GitHubRestDAL gitHubRestDALMock;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	// Dummies
	
	private static final List<Language> LANGUAGES_DUMMY = new ArrayList<Language>() {
		private static final long serialVersionUID = 5314424276319438408L;
		{
	        add(new Language("lang1",32));
	        add(new Language("lang2",21));
	    }
	};
	
	private static final List<Repository> REPOSITORIES_DUMMY = new ArrayList<Repository>() {
		private static final long serialVersionUID = 5314424276319438408L;
		{
	        add(new Repository("Repo1", "1"));
	        add(new Repository("Repo2", "2"));
	    }
	};
	
	// Tests

	@Test
	public void testGetTotalBytesCodedByLang() throws JsonProcessingException, IOException {	
		Mockito.when(gitHubRestDALMock.getUserLanguages(Mockito.any(String.class),Mockito.any(String.class))).thenReturn(LANGUAGES_DUMMY);
		
		List<Language> languages = gitHubServ.getUserLanguages("user", REPOSITORIES_DUMMY);
		
		
		assertTrue(languages.size() == 2);
		// Repo1 and repo2 has the same languages with the same bytes coded so
		// the result will be the add of both of them. The list is sort by code bytes
		assertTrue(languages.get(0).getName().equals("lang1"));
		assertTrue(languages.get(0).getCodeBytes() == (32 + 32));
		assertTrue(languages.get(1).getName().equals("lang2"));
		assertTrue(languages.get(1).getCodeBytes() == (21 + 21));
		
		Mockito.verify(gitHubRestDALMock, VerificationModeFactory.times(2)).getUserLanguages(Mockito.any(String.class),Mockito.any(String.class));
		Mockito.verifyNoMoreInteractions(gitHubRestDALMock);		
	}
	
	@Test
	public void testGetRepos() throws JsonProcessingException, IOException {	
		Mockito.when(gitHubRestDALMock.getUserRepos(Mockito.any(String.class))).thenReturn(REPOSITORIES_DUMMY);

		List<Repository> repos = gitHubServ.getUserRepositories("user");
		
		assertTrue(repos.size() == 2);
		assertTrue(repos.get(0).getName().equals("Repo1"));
		assertTrue(repos.get(0).getId().equals("1"));
		assertTrue(repos.get(1).getName().equals("Repo2"));
		assertTrue(repos.get(1).getId().equals("2"));
		
		Mockito.verify(gitHubRestDALMock, VerificationModeFactory.times(1)).getUserRepos(Mockito.any(String.class));
		Mockito.verifyNoMoreInteractions(gitHubRestDALMock);
	}
	
	@Test
	public void testNoRepos() throws JsonProcessingException, IOException {	
		Mockito.when(gitHubRestDALMock.getUserRepos(Mockito.any(String.class))).thenReturn(new ArrayList<Repository> ());
		assertTrue(gitHubServ.getUserRepositories("user").isEmpty());
	}
	
}
