package com.hhsfbla.hhs_fbla_mad_2021.util;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * A special LinearLayoutManager that disables scrolling within the RecyclerView
 */
public class NonScrollingLLM extends LinearLayoutManager {

    /**
     * Creates a new instance of this class with the following paramete, to be attaced to a RecyclerView
     * @param context the activity or fragment that contains the RecyclerView
     */
    public NonScrollingLLM(Context context) {
        super(context);
    }

    /**
     *
     * @return true if scrolling is enabled within the attached RecyclerView, otherwise false
     */
    @Override
    public boolean canScrollVertically() {
        return false;
    }
}
