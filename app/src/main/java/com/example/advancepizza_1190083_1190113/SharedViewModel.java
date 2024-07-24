package com.example.advancepizza_1190083_1190113;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData<String> userEmail = new MutableLiveData<>();

    public void setUserEmail(String email) {
        userEmail.setValue(email);
    }

    public LiveData<String> getUserEmailLiveData() {
        return userEmail;
    }

    public String getUserEmail() {
        return userEmail.getValue();
    }
}
