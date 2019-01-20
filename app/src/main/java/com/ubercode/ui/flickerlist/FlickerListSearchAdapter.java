package com.ubercode.ui.flickerlist;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import com.ubercode.databinding.FlickerListSearchItemBinding;

public class FlickerListSearchAdapter extends RecyclerView.Adapter<FlickerListSearchAdapter.ViewHolder> {
    private List<FlickerListItemViewModel> repoListViewModels = new ArrayList<>();
    private FlickerListSearchActivity clickHandler;
    OnBottomReachedListener onBottomReachedListener;
    public FlickerListSearchAdapter(FlickerListSearchActivity clickHandler) {
        this.clickHandler = clickHandler;
    }
    public interface OnBottomReachedListener {
        void onBottomReached(int position);
    }
    public void setOnBottomReachedListener(OnBottomReachedListener onBottomReachedListener){

        this.onBottomReachedListener = onBottomReachedListener;
    }
    public void updateRepoListViewModels(List<FlickerListItemViewModel> repoListViewModels) {
        this.repoListViewModels.clear();
        this.repoListViewModels.addAll(repoListViewModels);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return ViewHolder.createViewHolder(viewGroup, clickHandler);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        FlickerListItemViewModel repoListViewModel = repoListViewModels.get(position);
        viewHolder.bind(repoListViewModel);
        if (onBottomReachedListener!=null&&position == repoListViewModels.size() - 1){
            onBottomReachedListener.onBottomReached(position);
        }
    }

    @Override
    public int getItemCount() {
        return repoListViewModels.size();
    }

    @Override
    public long getItemId(int position) {
        return repoListViewModels.get(position).flickerImageUrl.hashCode();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        public static ViewHolder createViewHolder(ViewGroup parent, FlickerListSearchActivity clickHandler) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            FlickerListSearchItemBinding binding = FlickerListSearchItemBinding.inflate(inflater, parent, false);
            return new ViewHolder(binding, clickHandler);
        }

        private FlickerListSearchItemBinding binding;

        public ViewHolder(FlickerListSearchItemBinding binding, FlickerListSearchActivity clickHandler) {
            super(binding.getRoot());
            this.binding = binding;
            // Set in constructor since these do not change
        }

        public void bind(FlickerListItemViewModel repoListViewModel) {
            binding.setViewModel(repoListViewModel);
        }
    }
}
