package com.dev.gold.githubapiservice.api

import com.dev.gold.githubapiservice.model.Stargazer
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface StargazerApi {

    //https://api.github.com/repos/googlesamples/android-architecture-components/stargazers?page=1
    @GET("/repos/{user_name}/{repo}/stargazers")
    fun getStargazers(
        @Path("user_name") userName: String,
        @Path("repo") repo: String,
        @Query("page") page: Int
    ): Observable<List<Stargazer>>

}