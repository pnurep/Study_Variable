package gold.dev.com.githubusersearch.repository

import gold.dev.com.githubusersearch.http.HttpClient
import gold.dev.com.githubusersearch.model.Users
import io.reactivex.Observable

object GithubUserSearchRepoImpl: GithubUserSearchRepo {

    override fun getUserSearchInfo(name: String): Observable<Users> {
        return HttpClient.githubSearchApi.getUserSearchInfo(name, "stars", "desc")
    }

}