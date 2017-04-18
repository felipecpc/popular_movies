package android.example.com.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by felipe on 14/04/17.
 */

public class MovieModel implements Parcelable{
    String originalTitle;
    String plotSynopsis;
    String userRate;
    String releaseDate;
    String coverLink;

    public MovieModel(String originalTitle, String plotSynopsis, String userRate, String releaseDate, String coverLink){
        this.originalTitle = originalTitle;
        this.plotSynopsis = plotSynopsis;
        this.userRate = userRate;
        this.releaseDate = releaseDate;
        this.coverLink = coverLink;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getPlotSynopsis() {
        return plotSynopsis;
    }

    public void setPlotSynopsis(String plotSynopsis) {
        this.plotSynopsis = plotSynopsis;
    }

    public String getUserRate() {
        return userRate;
    }

    public void setUserRate(String userRate) {
        this.userRate = userRate;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getCoverLink() {
        return coverLink;
    }

    public void setCoverLink(String coverLink) {
        this.coverLink = coverLink;
    }

    public static final Parcelable.Creator<MovieModel> CREATOR = new Parcelable.Creator<MovieModel>() {
        public MovieModel createFromParcel(Parcel source) {
            MovieModel movieModel = new MovieModel(
                    source.readString(),
                    source.readString(),
                    source.readString(),
                    source.readString(),
                    source.readString());

            return movieModel;
        }
        public MovieModel[] newArray(int size) {
            return new MovieModel[size];
        }
    };

    public int describeContents() {
        return 0;
    }
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(originalTitle);
        parcel.writeString(plotSynopsis);
        parcel.writeString(userRate);
        parcel.writeString(releaseDate);
        parcel.writeString(coverLink);
    }

}
