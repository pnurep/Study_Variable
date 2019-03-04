package gold.dev.com.githubusersearch.http

import android.util.Log
import gold.dev.com.githubusersearch.api.GithubSearchApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object HttpClient {

    private const val BASE_URL = "https://api.github.com"

    val githubSearchApi: GithubSearchApi
        get() {
            return retrofit!!.create(GithubSearchApi::class.java)
        }

    var retrofit: Retrofit? = null
        get() {
            field = field ?: setUpRetrofit()
            return field
        }

    private fun setUpRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor
                    { Log.d("OKHTTP : ", it) }.setLevel(HttpLoggingInterceptor.Level.BODY)
                    ).build()
            )
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

}