package com.tcook.github.favlag.dal;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcook.github.favlag.Constants;
import com.tcook.github.favlag.model.Language;
import com.tcook.github.favlag.model.Repository;

/** Layer to consume GitHub API Web services
 * Calls examples:
 * GET https://api.github.com/users/timgrossmann/repos
 * GET https://api.github.com/repos/defunkt/coffee-mode/languages
 * 
 * @author Esteban Saenz Caceres
 */
@Component
public class GitHubRestDALImpl implements GitHubRestDAL {
	
	@Value( "${user.username}" )
	private String gitHubuser;
	@Value( "${user.password}" )
	private String gitHubpass;

	@Override
	@Cacheable("repos")
	public List<Repository> findUserRepos(String username) {
		RestTemplate restTemplate = new RestTemplate();
		String uri = Constants.REPO_URI + username + Constants.REPO;
		List<Repository> repositories = new ArrayList<Repository> ();
		
		try {
			// Consume REST service with basic authentication
			ResponseEntity<Repository[]> repoResponse = restTemplate.exchange(uri, HttpMethod.GET, createAuthHeader(gitHubuser, gitHubpass), Repository[].class);
			repositories = Arrays.asList(repoResponse.getBody());
		} catch (HttpClientErrorException e) {
			// If not found return an empty list
			if(e.getRawStatusCode() != 404) {
				throw e; 
			}	
		}
		return repositories;
	}	

	@Override
	@Cacheable("repos")
	@SuppressWarnings("unchecked")
	public List<Language> findUserLanguages(String username, String repository) throws JsonProcessingException, IOException {
		List<Language> languages = new ArrayList<Language> ();
		RestTemplate restTemplate = new RestTemplate();	
		String uri = Constants.LANG_URI + username + Constants.SLASH + repository + Constants.LANG;
		ResponseEntity<String> response;
		try {
			// Consume REST service with basic authentication
			response = restTemplate.exchange(uri, HttpMethod.GET, createAuthHeader(gitHubuser, gitHubpass), String.class);
			
			// As we do not know how the JSON is built we need to treat it generically		
			ObjectMapper mapper = new ObjectMapper();
			JsonNode jsonNode = null;

			jsonNode = mapper.readTree(response.getBody());
			Map<String, String> result = mapper.convertValue(jsonNode, Map.class);
			for (String key : result.keySet()) {
				Language language = new Language ();			
				language.setName(key);
				language.setCodeBytes(Integer.parseInt(jsonNode.get(key).toString()));
				languages.add(language);
			}	
		} catch (HttpClientErrorException e) {
			// If not found return an empty list
			if (e.getRawStatusCode() != 404) {
				throw e;
			}
		}
		return languages;
	}
	
	
	private HttpEntity<String> createAuthHeader(String username, String password){
		return new HttpEntity<String> (new HttpHeaders() {
			private static final long serialVersionUID = 1L;
			{
				String auth = username + ":" + password;
				byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
				String authHeader = "Basic " + new String(encodedAuth);
				set("Authorization", authHeader);
			}
		});
	}

}
