package com.ubercode.ui.flickerlist;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ubercode.network.repository.Repository;
import com.ubercode.utils.OnScrollListenerWrapper;
import com.ubercode.utils.schedulers.SchedulerProvider;

import com.ubercode.R;
import com.ubercode.databinding.FlickerListSearchActivityBinding;


public class FlickerListSearchActivity extends AppCompatActivity {
    private FlickerListSearchActivityBinding binding;
    private FlickerListSearchPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FlickerListSearchViewModel viewModel = createFlickerListSearchViewModel();
        setupPresenter(viewModel);
        setupBinding(viewModel);
        setupToolbar();
        setupRecyclerView();
    }

    private void setupToolbar() {
        if (binding == null) {
            throw new IllegalStateException("Must initialize binding before calling setupToolbar!");
        }
        presenter.setupSearchEditTextView(binding.searchBar);
    }

    private void setupRecyclerView() {
        if (presenter == null) {
            throw new IllegalStateException("Must initialize presenter before calling Journal setupRecyclerView!");
        }
        if (binding == null)
            throw new IllegalStateException("Must initialize binding before calling Journal setupRecyclerView!");
        FlickerListSearchAdapter adapter = new FlickerListSearchAdapter(this);
        adapter.setOnBottomReachedListener(position -> presenter.getLatestData());
        binding.recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        binding.recyclerView.setAdapter(adapter);
        presenter.viewModel.repoListData.observe(this, adapter::updateRepoListViewModels);
    }

    private void setupBinding(FlickerListSearchViewModel viewModel) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_flicker_list_search);
        binding.setLifecycleOwner(this);
        binding.setViewModel(viewModel);
    }

    private void setupPresenter(FlickerListSearchViewModel viewModel) {
        Repository repository = Repository.getRepository();
        presenter = new FlickerListSearchPresenter(viewModel, repository, new SchedulerProvider());
    }

    private FlickerListSearchViewModel createFlickerListSearchViewModel() {
        FlickerListSearchViewModel viewModel = ViewModelProviders.of(this).get(FlickerListSearchViewModel.class);
        viewModel.pageCount.setValue(0);
        viewModel.totalPageCount.setValue(0);
        return viewModel;
    }

}
