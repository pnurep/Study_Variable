package com.dev.gold.githubapiservice.http

import com.dev.gold.githubapiservice.Auth
import com.dev.gold.githubapiservice.api.GithubRepoApi
import com.dev.gold.githubapiservice.api.StargazerApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val baseUrl = "https://api.github.com"

    val githubRepoApi: GithubRepoApi
        get() {
            return retrofit!!.create(GithubRepoApi::class.java)
        }

    val stargazerApi: StargazerApi
        get() {
            return retrofit!!.create(StargazerApi::class.java)
        }

    var retrofit: Retrofit? = null
        get() {
            field = field ?: setUpRetrofit()
            return field
        }

    private fun setUpRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(
                OkHttpClient.Builder().addInterceptor {
                    it.proceed(it.request().newBuilder().addHeader("Authorization",
                        Auth.AUTH
                    ).build())
                }.build())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

}