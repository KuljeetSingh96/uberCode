package com.ubercode.network.api;

import com.ubercode.network.model.FlickerListResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by kuljeetsingh on 01/20/19.
 */

public interface RestService {

    //FETCH FLICKER IMAGE  API
    @GET(ApiConstants.SERVICE_TYPE)
    Observable<FlickerListResponse> getFlickerImages(
            @Query(ApiConstants.METHOD) String method,
            @Query(ApiConstants.API_KEY) String apiKey,
            @Query(ApiConstants.RESPONSE_FORMAT) String responseFormat,
            @Query(ApiConstants.NO_JSON_CALLABACK) int noJsonCallBack,
            @Query(ApiConstants.TEXT) String searchQuery,
            @Query(ApiConstants.PER_PAGE_ITEMS) int perPageCount,
            @Query(ApiConstants.PAGE) int pageNumber);
}
