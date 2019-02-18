package com.dev.gold.githubapiservice.repository

import com.dev.gold.githubapiservice.model.RepoData
import com.dev.gold.githubapiservice.model.Stargazer
import io.reactivex.Observable

interface GithubServiceRepository {

    fun getGithubRepo(): Observable<RepoData>

    fun getStargazers(page: Int): Observable<List<Stargazer>>

}