package com.ubercode.network.repository;

import com.ubercode.network.api.RestService;
import com.ubercode.network.model.FlickerListResponse;

import io.reactivex.Observable;


/**
 * Created by kuljeetsingh on 01/20/19.
 * <p>
 * Repository class is (will be) our single source of truth for all data.
 * The repository is essentially a proxy / collection of all data sources.
 * The repository contains business logic to determine which source of data should be used - network, database or cache
 * (as the data consumers should not need to be concerned where data comes from save for perhaps forcing
 * a refresh from the server.)
 * The repository may be broken down into separate components if the class becomes too large.
 */
public class Repository {
    private static volatile Repository repository;
    private RestService restApi;

    private Repository() {
        if (repository != null) {
            throw new RuntimeException("Use getRepository() method to get the single instance of this class.");
        }
        restApi = RestApiProvider.getRestApi();
    }

    public static Repository getRepository() {
        if (repository == null) {
            synchronized (Repository.class) {
                if (repository == null) repository = new Repository();
            }
        }

        return repository;
    }

    //Get TOP WEEKLY FROM REPO
    public Observable<FlickerListResponse> getFlickerImages(String method, String apiKey,
                                                                  String responseFormat, int noJsonCallback,
                                                                  String searchQuery, int perPageCount, int pageCount) {
        return restApi.getFlickerImages(method, apiKey, responseFormat, noJsonCallback, searchQuery, perPageCount, pageCount);
    }
}
