package com.dev.gold.githubapiservice.api

import com.dev.gold.githubapiservice.model.RepoData
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubRepoApi {

    @GET("/repos/{user_name}/{repo}")
    fun getRepoInfo(
        @Path("user_name") userName: String,
        @Path("repo") repo: String
    ): Observable<RepoData>

}