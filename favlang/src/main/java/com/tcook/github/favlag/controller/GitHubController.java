package com.tcook.github.favlag.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tcook.github.favlag.Constants;
import com.tcook.github.favlag.model.Language;
import com.tcook.github.favlag.model.Repository;
import com.tcook.github.favlag.services.GitHubService;

/** 
 * Controller to manage the extended GitHub functionalities
 * 
 * @author Esteban Saenz Caceres
 *
 */

@Controller
public class GitHubController {

	private static final String USERNAME_PATTERN = "^[a-zA-Z][a-zA-Z0-9]+{3,15}$";
	private static final String FAVLANG = "/favlang";
	
	@Value("${error.invalid.user}")
	private String invalidUsername;
	@Value("${error.empty.repo}")
	private String emptyRepo;
	@Value("${error.empty.nolanguages}")
	private String nolanguages;

	@Autowired
	private GitHubService gitHubServ;

	/** Render the index page with a list with the programming languages used by an user
	 * sort by number of lines coded 
	 * 
	 * @param username The username
	 * @return The index page with a list the programming languages used by an user 
	 * @throws IOException 
	 * @throws JsonProcessingException 
	 */
	@GetMapping(value = { FAVLANG })
	public String index(@RequestParam(required = false) String username, Model model) throws JsonProcessingException, IOException {
		List<Language> languages = new ArrayList<Language> ();
		String page = Constants.INDEX;
		
		if (username != null) {
			if (validateUsername(username)) {
				List<Repository> repositories = gitHubServ.getUserRepositories(username);
				if (repositories.isEmpty()) {
					model.addAttribute(Constants.ERROR, emptyRepo);
				} else {
					languages = gitHubServ.getUserLanguages(username, repositories);
					if (languages.isEmpty()) {
						model.addAttribute(Constants.ERROR, nolanguages);
					}
				}

			} else {
				model.addAttribute(Constants.ERROR, invalidUsername);
			}
		}
		
		model.addAttribute(Constants.USERNAME, username);
		model.addAttribute(Constants.LANGUAGES, languages);

		return page;
	}

	/** Validate if an username is valid
	 * 
	 * @param username Username to be validated
	 * @return If the username is valid
	 */
	private boolean validateUsername(String username) {
		return username.matches(USERNAME_PATTERN);
	}

}
