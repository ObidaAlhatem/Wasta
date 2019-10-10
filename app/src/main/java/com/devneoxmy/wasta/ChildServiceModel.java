package com.devneoxmy.wasta;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by yasser on 3/10/2019.
 */

public class ChildServiceModel implements Parcelable {

    public final  String name;

    public ChildServiceModel(String name) {
        this.name = name;
    }

    protected ChildServiceModel(Parcel in) {
        name = in.readString();
    }

    public static final Creator<ChildServiceModel> CREATOR = new Creator<ChildServiceModel>() {
        @Override
        public ChildServiceModel createFromParcel(Parcel in) {
            return new ChildServiceModel(in);
        }

        @Override
        public ChildServiceModel[] newArray(int size) {
            return new ChildServiceModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);

    }
}
