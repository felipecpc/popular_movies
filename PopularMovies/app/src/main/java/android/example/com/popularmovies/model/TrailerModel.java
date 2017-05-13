package android.example.com.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by felipe on 13/05/17.
 */

public class TrailerModel implements Parcelable{
    int id;
    String key;
    String nome;
    String userRate;
    String releaseDate;
    String coverLink;
    String posterLink;

    public TrailerModel(int id, String key, String nome){
        this.id= id;
        this.key = key;
        this.nome = nome;
    }

    public int getId(){ return id; }

    public void setId( int id){ this.id=id; }

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
                    source.readInt(),
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
        parcel.writeInt(id);
        parcel.writeString(key);
        parcel.writeString(nome);

    }
}
