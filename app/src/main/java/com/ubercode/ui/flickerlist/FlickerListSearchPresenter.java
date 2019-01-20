package com.ubercode.ui.flickerlist;

import android.support.annotation.VisibleForTesting;
import android.view.View;
import android.widget.EditText;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.ubercode.network.model.FlickerListResponse;
import com.ubercode.network.repository.Repository;
import com.ubercode.utils.schedulers.SchedulerProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Action1;

import static com.ubercode.network.api.ApiConstants.CALLBACK_FORMAT;
import static com.ubercode.network.api.ApiConstants.FLICKER_API_KEY;
import static com.ubercode.network.api.ApiConstants.METHOD_INPUT;
import static com.ubercode.network.api.ApiConstants.NO_JSON_CALLBACK_FLAG;
import static com.ubercode.network.api.ApiConstants.PER_PAGE;

/**
 * Created by kuljeetsingh on 01/20/18.
 */

public class FlickerListSearchPresenter {
    private Repository repository;
    protected FlickerListSearchViewModel viewModel;
    private SchedulerProvider schedulerProvider;

    public FlickerListSearchPresenter(FlickerListSearchViewModel viewModel, Repository repository, SchedulerProvider schedulerProvider) {
        this.repository = repository;
        this.viewModel = viewModel;
        this.schedulerProvider = schedulerProvider;
        setupListErrorVisibility(View.GONE, View.GONE, View.VISIBLE);
    }

    @VisibleForTesting
    SchedulerProvider getSchedulerProvider() {
        return schedulerProvider;
    }


    private void setupListErrorVisibility(int errorVisibility, int listVisibility, int loadingVisibility) {

        viewModel.errorMessageVisibility.setValue(errorVisibility);
        viewModel.repoListVisibility.setValue(listVisibility);
        viewModel.loadingVisibility.setValue(loadingVisibility);

    }


    public void getFlickerImages(String query) {
        repository.getFlickerImages(METHOD_INPUT, FLICKER_API_KEY, CALLBACK_FORMAT, NO_JSON_CALLBACK_FLAG, query, PER_PAGE, viewModel.pageCount.getValue() + 1)
                .subscribeOn(getSchedulerProvider().io())
                .map(this::mapFlickerResponseToItems)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(this::onRepoListSuccess, this::onNetworkFailure);
    }

    private List<FlickerListItemViewModel> mapFlickerResponseToItems(FlickerListResponse flickerListResponses) {
        List<FlickerListItemViewModel> flickerItems = new ArrayList<>();
        if (flickerListResponses == null
                || flickerListResponses.getPhotos() == null
                || flickerListResponses.getPhotos().getPhoto() == null) {
            return flickerItems;
        }
        viewModel.pageCount.postValue(flickerListResponses.getPhotos().getPage());
        viewModel.totalPageCount.postValue(flickerListResponses.getPhotos().getPages());
        for (int index = 0; index < flickerListResponses.getPhotos().getPhoto().size(); index++) {
            FlickerListResponse.Photos.PhotoListResult photoListResult = flickerListResponses.getPhotos().getPhoto().get(index);
            String pictureUrl = "http://farm" + photoListResult.getFarm() + ".static.flickr.com/" + photoListResult.getServer() + "/" + photoListResult.getId() + "_" + photoListResult.getSecret() + ".jpg";
            flickerItems.add(new FlickerListItemViewModel(pictureUrl));
        }
        return flickerItems;
    }

    private void onNetworkFailure(Throwable throwable) {
        //you can handle network exceptions here like retry dialog show error messages
        setupListErrorVisibility(View.VISIBLE, View.GONE, View.GONE);
        viewModel.errorMessage.setValue("Network error. Please try again later.");
    }

    private void onRepoListSuccess(List<FlickerListItemViewModel> itemViewModels) {
        if (itemViewModels != null && itemViewModels.size() > 0) {
            List<FlickerListItemViewModel> flickerListItemViewModels = viewModel.repoListData.getValue();
            if (flickerListItemViewModels == null || viewModel.isReset) {
                flickerListItemViewModels = itemViewModels;
            } else {
                flickerListItemViewModels.addAll(itemViewModels);
            }
            setupListErrorVisibility(View.GONE, View.VISIBLE, View.GONE);
            viewModel.repoListData.postValue(flickerListItemViewModels);
            viewModel.isReset = false;
            return;
        }
        if (viewModel.isReset) {
            setupListErrorVisibility(View.VISIBLE, View.GONE, View.GONE);
            viewModel.errorMessage.setValue("No list available.");
        }
    }

    public void getLatestData() {
        if (!hasMoreData()) {
            return;
        }
        getFlickerImages(viewModel.searchQuery);
    }
    private boolean hasMoreData() {
        return viewModel.pageCount.getValue() != viewModel.totalPageCount.getValue();
    }

    public void setupSearchEditTextView(EditText searchBar) {
        Observable<String> obs = RxTextView.textChanges(searchBar).
                debounce(800, TimeUnit.MILLISECONDS).map(charSequence ->  charSequence.toString());
        obs.subscribe(query -> {
            viewModel.isReset=true;
            getFlickerImages(query);
        });
    }
}
