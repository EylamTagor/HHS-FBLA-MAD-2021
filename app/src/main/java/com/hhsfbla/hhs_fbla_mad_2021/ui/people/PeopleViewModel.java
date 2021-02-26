package com.hhsfbla.hhs_fbla_mad_2021.ui.people;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PeopleViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    /**
     * Creates the new View model
     */
    public PeopleViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }
    /**
     * Returns the text from the model
     * @return the text from the model
     */
    public LiveData<String> getText() {
        return mText;
    }
}