package com.tcook.github.favlag.services;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tcook.github.favlag.model.Language;
import com.tcook.github.favlag.model.Repository;


/** Business logic layer
 * 
 * @author Esteban Saenz Caceres
 */
public interface GitHubService {

	/** Returns a list of the programming languages used by an User in GitHub
	 * 
	 * @param username The username
	 * @return The user's favorite language
	 * @throws IOException 
	 * @throws JsonProcessingException 
	 */
	public List<Language> getUserLanguages(String username, List<Repository> repositories) throws JsonProcessingException, IOException;
	/** Return the a list of the user repositories or an
	 * empty if has no repositories or the user does not exist in GitHub
	 * 
	 * @param username
	 * @return The list of the user repositories
	 */
	public List<Repository> getUserRepositories(String username);


}
