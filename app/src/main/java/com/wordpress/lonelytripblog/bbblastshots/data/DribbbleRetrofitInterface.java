package com.wordpress.lonelytripblog.bbblastshots.data;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Interface for HTTP API.
 */

public interface DribbbleRetrofitInterface {
    @GET("shots?sort=recent&access_token=90628d4b8c9dd2cc8d2fbca7f61e690926f0fc6fc500da2810cf77b7193660ea")
    Call<List<Shot>> getShots(@Query("page") int page);
}
