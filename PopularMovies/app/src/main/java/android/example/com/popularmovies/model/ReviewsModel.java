package android.example.com.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by felipe on 13/05/17.
 */

public class ReviewsModel implements Parcelable{
    int id;
    String author;
    String content;

    public ReviewsModel(int id, String author, String content){
        this.id= id;
        this.author = author;
        this.content = content;
    }

    public int getId(){ return id; }

    public void setId( int id){ this.id=id; }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.author = content;
    }


    public static final Creator<ReviewsModel> CREATOR = new Creator<ReviewsModel>() {
        public ReviewsModel createFromParcel(Parcel source) {
            ReviewsModel movieModel = new ReviewsModel(
                    source.readInt(),
                    source.readString(),
                    source.readString());

            return movieModel;
        }
        public ReviewsModel[] newArray(int size) {
            return new ReviewsModel[size];
        }
    };

    public int describeContents() {
        return 0;
    }
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(id);
        parcel.writeString(author);
        parcel.writeString(content);

    }
}
