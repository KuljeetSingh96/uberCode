package com.ubercode.ui.flickerlist;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

/**
 * Created by kuljeetsingh on 01/20/19.
 */


public class FlickerListSearchViewModel extends ViewModel {
    public MutableLiveData<List<FlickerListItemViewModel>> repoListData = new MutableLiveData<>();
    public MutableLiveData<Integer> errorMessageVisibility = new MutableLiveData<>();
    public MutableLiveData<Integer> loadingVisibility = new MutableLiveData<>();
    public MutableLiveData<Integer> repoListVisibility = new MutableLiveData<>();
    public MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public MutableLiveData<Integer> pageCount = new MutableLiveData<>();
    public MutableLiveData<Integer> totalPageCount = new MutableLiveData<>();

    protected boolean isReset = true;
    public String searchQuery="";
}
