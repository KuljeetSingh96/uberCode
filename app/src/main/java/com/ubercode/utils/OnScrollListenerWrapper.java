package com.ubercode.utils;

import android.support.v7.widget.RecyclerView;

public class OnScrollListenerWrapper extends RecyclerView.OnScrollListener {

    public interface OnScrolledListener {
        void onScrolled(RecyclerView recyclerView, int dx, int dy);
    }

    public interface OnScrollStateChangedListener {
        void onScrollStateChanged(RecyclerView recyclerView, int newState);
    }

    private OnScrolledListener onScrolledListener;
    private OnScrollStateChangedListener onScrollStateChangedListener;

    public OnScrollListenerWrapper(OnScrolledListener onScrolledListener,
                                   OnScrollStateChangedListener onScrollStateChangedListener) {
        this.onScrolledListener = onScrolledListener;
        this.onScrollStateChangedListener = onScrollStateChangedListener;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        if (onScrolledListener == null) {
            return;
        }

        onScrolledListener.onScrolled(recyclerView, dx, dy);
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        if (onScrollStateChangedListener == null) {
            return;
        }

        onScrollStateChangedListener.onScrollStateChanged(recyclerView, newState);
    }
}
