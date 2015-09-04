package com.vinidsl.navigationviewdemo.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by tlacaelel21 on 1/09/15.
 */
public class EventoModel implements Parcelable {
    private long int_id;
    private String rec_id;
    private String imageEvento;
    private String nombre_evento;
    private String ubicacion_evento;
    private String cat_desc;
    private String fecha_inicio_evento;
    private String fecha_fin_evento;
    private String titulo_evento;
    private String desc_evento;

    public EventoModel(long int_id, String rec_id, String imageEvento, String ubicacion_evento, String cat_desc, String fecha_inicio_evento, String fecha_fin_evento, String titulo_evento, String desc_evento) {
        this.int_id = int_id;
        this.rec_id=rec_id;
        this.imageEvento=imageEvento;
        this.ubicacion_evento=ubicacion_evento;
        this.cat_desc=cat_desc;
        this.fecha_inicio_evento=fecha_inicio_evento;
        this.fecha_fin_evento=fecha_fin_evento;
        this.titulo_evento=titulo_evento;
        this.desc_evento=desc_evento;
    }

    public long getId() {
        return int_id;
    }

    public String getRec_id() {
        return rec_id;
    }

    public String getImageEvento() {
        return imageEvento;
    }

    public String getNombre_evento() {
        return nombre_evento;
    }

    public String getUbicacion_evento() {
        return ubicacion_evento;
    }

    public String getCat_desc() {
        return cat_desc;
    }

    public String getFecha_inicio_evento() {
        return fecha_inicio_evento;
    }

    public String getFecha_fin_evento() {
        return fecha_fin_evento;
    }

    public String getTitulo_evento() {
        return titulo_evento;
    }

    public String getDesc_evento() {
        return desc_evento;
    }

    // Parcelling part
    public EventoModel(Parcel in){
        String[] data = new String[3];
        long idValue = in.readLong();
        in.readStringArray(data);
        this.int_id = idValue;
        this.rec_id=data[0];
        this.imageEvento=data[1];
        this.nombre_evento=data[2];
        this.ubicacion_evento=data[3];
        this.cat_desc=data[4];
        this.fecha_inicio_evento=data[5];
        this.fecha_fin_evento=data[6];
        this.titulo_evento=data[7];
        this.desc_evento=data[8];
    }

    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.int_id);
        dest.writeStringArray(new String[] {
                this.rec_id,
                this.imageEvento,
                this.nombre_evento,
                this.ubicacion_evento,
                this.cat_desc,
                this.fecha_inicio_evento,
                this.fecha_fin_evento,
                this.titulo_evento,
                this.desc_evento});
    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public EventoModel createFromParcel(Parcel in) {
            return new EventoModel(in);
        }

        public EventoModel[] newArray(int size) {
            return new EventoModel[size];
        }
    };

}
