package com.ubercode.network.api;

/**
 * contains all the constants related to API interactions
 */
public interface ApiConstants {

    //live
    String FLICKER_BASE_URL = "https://api.flickr.com/";
    String SERVICE_TYPE = "services/rest";
    String METHOD = "method";
    String API_KEY = "api_key";
    String RESPONSE_FORMAT = "format";
    String NO_JSON_CALLABACK = "nojsoncallback";
    String SAFE_SEARCH = "safe_search";
    String TEXT = "text";
    String PER_PAGE_ITEMS = "per_page";
    String PAGE = "page";

    String METHOD_INPUT = "flickr.photos.search";
    String FLICKER_API_KEY = "3e7cc266ae2b0e0d78e279ce8e361736";
    String CALLBACK_FORMAT = "json";
    int NO_JSON_CALLBACK_FLAG = 1;
    int PER_PAGE = 20;
}
