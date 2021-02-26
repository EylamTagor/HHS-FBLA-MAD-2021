package com.hhsfbla.hhs_fbla_mad_2021.ui.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyProfileViewModel extends ViewModel {
    private MutableLiveData<String> mText;
    /**
     * Creates the new View model
     */
    public MyProfileViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is MyProfile Jobs fragment");
    }
    /**
     * Returns the text from the model
     * @return the text from the model
     */
    public LiveData<String> getText() {
        return mText;
    }
}