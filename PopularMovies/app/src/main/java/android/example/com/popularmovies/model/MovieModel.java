package android.example.com.popularmovies.model;

/**
 * Created by felipe on 14/04/17.
 */

public class MovieModel {
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
}
