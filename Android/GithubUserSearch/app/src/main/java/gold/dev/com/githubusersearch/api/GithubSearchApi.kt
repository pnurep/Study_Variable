package gold.dev.com.githubusersearch.api

import gold.dev.com.githubusersearch.model.Users
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubSearchApi {

    // https://api.github.com/search/users?q=followers:>=1000&sort=stars&order=desc
    @GET("/search/users")
    fun getUserSearchInfo(
        @Query("q") query: String,
        @Query("sort") sort: String,
        @Query("order") order: String
    ): Observable<Users>

}