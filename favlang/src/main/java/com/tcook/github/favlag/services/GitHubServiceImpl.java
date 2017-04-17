package com.tcook.github.favlag.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tcook.github.favlag.dal.GitHubRestDAL;
import com.tcook.github.favlag.model.Language;
import com.tcook.github.favlag.model.Repository;

/** Business logic layer implementation
 * 
 * @author Esteban Saenz Caceres
 */

@Service
public class GitHubServiceImpl implements GitHubService {

	@Autowired
	private GitHubRestDAL gitHubRestDal;
	
	@Override
	public List<Repository> getUserRepositories (String username) {
		List<Repository> repositories = gitHubRestDal.findUserRepos(username);
		return repositories;
	}
	
	@Override
	public List<Language> getUserLanguages(String username, List<Repository> repositories) throws JsonProcessingException, IOException {
		List<Language> userLangs = new ArrayList<Language>();
		// We use a hashmap as help container for performance
		Map<String, Integer> langMap = new HashMap<String, Integer>();
		int codeLines;

		// Get all the languages from each the repository of the User and add all code lines of each language
		for (Repository repository : repositories) {		
			List<Language> languages = gitHubRestDal.findUserLanguages(username, repository.getName());			
			
			for (Language language : languages) {
				String langName = language.getName();
				if (langMap.containsKey(langName)) {
					codeLines = langMap.get(langName) + language.getCodeBytes();
				} else {
					codeLines = language.getCodeBytes();
				}
				langMap.put(langName, codeLines);
			}
		}

		for (String lang : langMap.keySet()) {
			userLangs.add(new Language(lang, langMap.get(lang)));
		}
		
		// Sort the list
		Collections.sort(userLangs, (lang1, lang2) -> lang2.getCodeBytes().compareTo(lang1.getCodeBytes()));

		return userLangs;
	}

}
