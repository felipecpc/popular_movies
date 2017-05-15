package android.example.com.popularmovies.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.example.com.popularmovies.model.MovieModel;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by felipe on 14/05/17.
 */

public class MovieGuideDatabase {

    private static Context mContext;
    private static MovieGuideDatabase ourInstance=null;
    private static final String TAG = MovieGuideDatabase.class.getSimpleName();

    public static MovieGuideDatabase getInstance(Context ctx) {
        mContext = ctx;

        if  (ourInstance == null) {
            ourInstance = new MovieGuideDatabase();
        }


        return ourInstance;
    }

    private MovieGuideDatabase() {

    }

    /**
     * Query the mDb and get all guests from the waitlist table
     *
     * @return Cursor containing the list of guests
     */
    public ArrayList<MovieModel> getAllMovies() {
        ArrayList<MovieModel> modelModelList = new ArrayList<>();
        Cursor cursor;
        try {
            cursor=mContext.getContentResolver().query(MovieGuideDbContract.MovieEntry.CONTENT_URI,
                    null,
                    null,
                    null,
                    MovieGuideDbContract.MovieEntry.COLUMN_ORIGINAL_TITLE);

        } catch (Exception e) {
            Log.e(TAG, "Not able to read data from contentResolver");
            e.printStackTrace();
            return null;
        }


        while(cursor.moveToNext()){
            modelModelList.add(new MovieModel(
                    Integer.parseInt(cursor.getString(cursor.getColumnIndex(MovieGuideDbContract.MovieEntry.COLUMN_MOVIE_ID))),
                    cursor.getString(cursor.getColumnIndex(MovieGuideDbContract.MovieEntry.COLUMN_ORIGINAL_TITLE)),
                    cursor.getString(cursor.getColumnIndex(MovieGuideDbContract.MovieEntry.COLUMN_PLOT_SYNOPSIS)),
                    cursor.getString(cursor.getColumnIndex(MovieGuideDbContract.MovieEntry.COLUMN_USER_RATE)),
                    cursor.getString(cursor.getColumnIndex(MovieGuideDbContract.MovieEntry.COLUMN_RELEASE_DATE)),
                    cursor.getString(cursor.getColumnIndex(MovieGuideDbContract.MovieEntry.COLUMN_COVER_LINK)),
                    cursor.getString(cursor.getColumnIndex(MovieGuideDbContract.MovieEntry.COLUMN_POSTER_LINK)),
                    0
            ));
        }

        return modelModelList;
    }


    public Uri addMovie(MovieModel movieModel) {
        ContentValues cv = new ContentValues();
        cv.put(MovieGuideDbContract.MovieEntry.COLUMN_MOVIE_ID, movieModel.getId());
        cv.put(MovieGuideDbContract.MovieEntry.COLUMN_ORIGINAL_TITLE, movieModel.getOriginalTitle());
        cv.put(MovieGuideDbContract.MovieEntry.COLUMN_PLOT_SYNOPSIS, movieModel.getPlotSynopsis());
        cv.put(MovieGuideDbContract.MovieEntry.COLUMN_USER_RATE, movieModel.getUserRate());
        cv.put(MovieGuideDbContract.MovieEntry.COLUMN_RELEASE_DATE, movieModel.getReleaseDate());
        cv.put(MovieGuideDbContract.MovieEntry.COLUMN_COVER_LINK, movieModel.getCoverLink());
        cv.put(MovieGuideDbContract.MovieEntry.COLUMN_POSTER_LINK, movieModel.getPosterLink());

        Uri uri = mContext.getContentResolver().insert(MovieGuideDbContract.MovieEntry.CONTENT_URI, cv);

        return uri;
    }

    public boolean removeMovie(MovieModel movieModel){
        Uri uri = MovieGuideDbContract.MovieEntry.CONTENT_URI;
        uri = uri.buildUpon().appendPath(String.valueOf(movieModel.getId())).build();

        return mContext.getContentResolver().delete(uri, null, null) > 0;
    }

    public boolean isMovieAtDatabase(MovieModel mMovieDetails) {

        Uri uri = MovieGuideDbContract.MovieEntry.buildWeatherUriWithID(String.valueOf(mMovieDetails.getId()));


        Cursor cursor;
        try {
            cursor=mContext.getContentResolver().query(uri,
                    null,
                    null,
                    null,
                    MovieGuideDbContract.MovieEntry.COLUMN_ORIGINAL_TITLE);

        } catch (Exception e) {
            Log.e(TAG, "Not able to read data from contentResolver");
            e.printStackTrace();
            return false;
        }

        if(cursor.getCount()==0){
            return false;
        }else{
            return true;
        }
    }
}
