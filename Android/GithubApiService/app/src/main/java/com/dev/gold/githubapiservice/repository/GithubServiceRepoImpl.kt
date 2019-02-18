package com.dev.gold.githubapiservice.repository

import com.dev.gold.githubapiservice.RetrofitClient
import com.dev.gold.githubapiservice.model.RepoData
import com.dev.gold.githubapiservice.model.Stargazer
import io.reactivex.Observable

class GithubServiceRepoImpl: GithubServiceRepository {

    override fun getGithubRepo(): Observable<RepoData> {
        return RetrofitClient.githubRepoApi.getRepoInfo("googlesamples", "android-architecture-components")
    }

    override fun getStargazers(page: Int): Observable<List<Stargazer>> {
        return RetrofitClient.stargazerApi.getStargazers("googlesamples", "android-architecture-components", page)
    }

}