package io.bxbxbai.zhuanlan.bean;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import io.bxbxbai.zhuanlan.BR;

/**
 * Created by xuebin on 16/3/31.
 */
public class Person extends BaseObservable {

    private String firstName;
    private String lastName;

    @Bindable
    public String getFirstName() {
        return firstName;
    }

    @Bindable
    public String getLastName() {
        return lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
        notifyPropertyChanged(BR.firstName);
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
        notifyPropertyChanged(BR.lastName);
    }
}
