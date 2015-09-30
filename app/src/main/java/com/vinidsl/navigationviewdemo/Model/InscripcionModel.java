package com.vinidsl.navigationviewdemo.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by tlacaelel21 on 30/09/15.
 */
public class InscripcionModel implements Parcelable {
    private long int_id;
    private String costo;

    public InscripcionModel(String costo) {
        this.costo=costo;
    }

    public long getId() {
        return int_id;
    }

    public String getCosto() {
        return costo;
    }

    // Parcelling part
    public InscripcionModel(Parcel in){
        String[] data = new String[2];
        long idValue = in.readLong();
        in.readStringArray(data);
        this.int_id = idValue;
        this.costo=data[0];

    }

    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.int_id);
        dest.writeStringArray(new String[] {
                this.costo});
    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public InscripcionModel createFromParcel(Parcel in) {
            return new InscripcionModel(in);
        }

        public InscripcionModel[] newArray(int size) {
            return new InscripcionModel[size];
        }
    };

}


