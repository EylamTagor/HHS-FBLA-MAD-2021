package com.hhsfbla.hhs_fbla_mad_2021.ui.saved;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SavedViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SavedViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Saved Jobs fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}