package com.vinidsl.navigationviewdemo.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by JoseRogelio on 01/10/2015.
 */
public class Sala implements Parcelable {

    String id;
    String desc;

    public Sala(String id, String desc) {
        this.id = id;
        this.desc = desc;
    }

    public String getId() {
        return id;
    }

    public String getDesc() {
        return desc;
    }

    // Parcelling part
    public Sala(Parcel in){
        String[] data = new String[5];
        in.readStringArray(data);
        this.id = data[0];
        this.desc = data[1];
    }

    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {
                this.id,
                this.desc,});
    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Sala createFromParcel(Parcel in) {
            return new Sala(in);
        }

        public Sala[] newArray(int size) {
            return new Sala[size];
        }
    };

}
