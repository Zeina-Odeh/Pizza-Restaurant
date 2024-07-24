package com.example.advancepizza_1190083_1190113.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Craving a slice of perfection?\n\nLook no further than Pizza Piazza. From classic favorites to daring creations, we've got your pizza dreams covered.\n\nDive into flavor with us today!");
    }

    public LiveData<String> getText() {
        return mText;
    }
}