package gold.dev.com.githubusersearch.repository

import gold.dev.com.githubusersearch.model.Users
import io.reactivex.Observable

interface GithubUserSearchRepo {

    fun getUserSearchInfo(name: String): Observable<Users>

}