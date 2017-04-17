package com.tcook.github.favlag.dal;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tcook.github.favlag.model.Language;
import com.tcook.github.favlag.model.Repository;

/** Data access layer facade */
public interface GitHubRestDAL {
	/** Get User repositories
	 * 
	 * @param username
	 * @return User repositories
	 */
	public List<Repository> findUserRepos(String username);

	/** Get User Languages from a repository
	 * 
	 * @param username
	 * @param repository
	 * @return Get User Languages
	 * @throws JsonProcessingException
	 * @throws IOException
	 */
	public List<Language> findUserLanguages(String username, String repository)
			throws JsonProcessingException, IOException;

}
