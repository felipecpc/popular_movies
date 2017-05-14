package android.example.com.popularmovies.database;

import android.provider.BaseColumns;

/**
 * Created by felipe on 14/05/17.
 */

public class MovieGuideDbContract implements BaseColumns {
    public static final String TABLE_NAME = "moviesguide";
    public static final String COLUMN_MOVIE_ID = "movie_id";
    public static final String COLUMN_ORIGINAL_TITLE = "original_title";
    public static final String COLUMN_PLOT_SYNOPSIS = "plot_synopsis";
    public static final String COLUMN_USER_RATE = "user_rate";
    public static final String COLUMN_RELEASE_DATE = "release_date";
    public static final String COLUMN_COVER_LINK = "cover_link";
    public static final String COLUMN_POSTER_LINK = "poster_link";

}
