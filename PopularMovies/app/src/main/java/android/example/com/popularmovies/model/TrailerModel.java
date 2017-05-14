package android.example.com.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by felipe on 13/05/17.
 */

public class TrailerModel implements Parcelable{
    String id;
    String key;
    String nome;

    public TrailerModel(String id, String key, String nome){
        this.id= id;
        this.key = key;
        this.nome = nome;
    }

    public String getId(){ return id; }

    public void setId( String id){ this.id=id; }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


    public static final Parcelable.Creator<TrailerModel> CREATOR = new Parcelable.Creator<TrailerModel>() {
        public TrailerModel createFromParcel(Parcel source) {
            TrailerModel movieModel = new TrailerModel(
                    source.readString(),
                    source.readString(),
                    source.readString());

            return movieModel;
        }
        public TrailerModel[] newArray(int size) {
            return new TrailerModel[size];
        }
    };

    public int describeContents() {
        return 0;
    }
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(id);
        parcel.writeString(key);
        parcel.writeString(nome);

    }
}
