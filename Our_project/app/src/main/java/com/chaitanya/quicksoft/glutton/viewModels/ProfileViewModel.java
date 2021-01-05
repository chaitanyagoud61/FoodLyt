package com.chaitanya.quicksoft.glutton.viewModels;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;

public class ProfileViewModel extends AndroidViewModel {
    public Context context;
    public ObservableField<String> name = new ObservableField<>();
    public ObservableField<String> mobile = new ObservableField<>();
    public ObservableField<String> mailId = new ObservableField<>();
    public ObservableField<String> address = new ObservableField<>();

    public ProfileViewModel(@NonNull Application application) {
        super(application);
        this.context = application;
    }
}
