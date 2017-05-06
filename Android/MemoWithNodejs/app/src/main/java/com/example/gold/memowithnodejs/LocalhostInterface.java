package com.example.gold.memowithnodejs;

import com.example.gold.memowithnodejs.domain.Data;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Gold on 2017. 3. 24..
 */

public interface LocalhostInterface {

    @GET("bbs")
    Call<Data> getData();

}
