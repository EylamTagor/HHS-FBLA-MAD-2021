package com.hhsfbla.hhs_fbla_mad_2021.util;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;

public class NonScrollingLLM extends LinearLayoutManager {
    public NonScrollingLLM(Context context) {
        super(context);
    }

    @Override
    public boolean canScrollVertically() {
        return false;
    }
}
