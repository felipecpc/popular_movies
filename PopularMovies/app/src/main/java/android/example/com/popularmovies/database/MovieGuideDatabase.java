package android.example.com.popularmovies.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.example.com.popularmovies.model.MovieModel;

import java.util.ArrayList;

/**
 * Created by felipe on 14/05/17.
 */

public class MovieGuideDatabase {
    private static SQLiteDatabase mDb;
    private static Context mContext;
    private static MovieGuideDatabase ourInstance=null;

    public static MovieGuideDatabase getInstance(Context ctx) {
        mContext = ctx;

        if  (ourInstance == null) {
            ourInstance = new MovieGuideDatabase();
            // Create a DB helper (this will create the DB if run for the first time)
            MoviesGuideDBHelper dbHelper = new MoviesGuideDBHelper(mContext);
            mDb = dbHelper.getWritableDatabase();
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
        Cursor cursor = mDb.query(
                MovieGuideDbContract.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                MovieGuideDbContract.COLUMN_ORIGINAL_TITLE);

        while(cursor.moveToNext()){
            modelModelList.add(new MovieModel(
                    Integer.parseInt(cursor.getString(cursor.getColumnIndex(MovieGuideDbContract.COLUMN_MOVIE_ID))),
                    cursor.getString(cursor.getColumnIndex(MovieGuideDbContract.COLUMN_ORIGINAL_TITLE)),
                    cursor.getString(cursor.getColumnIndex(MovieGuideDbContract.COLUMN_PLOT_SYNOPSIS)),
                    cursor.getString(cursor.getColumnIndex(MovieGuideDbContract.COLUMN_USER_RATE)),
                    cursor.getString(cursor.getColumnIndex(MovieGuideDbContract.COLUMN_RELEASE_DATE)),
                    cursor.getString(cursor.getColumnIndex(MovieGuideDbContract.COLUMN_COVER_LINK)),
                    cursor.getString(cursor.getColumnIndex(MovieGuideDbContract.COLUMN_POSTER_LINK)),
                    0
            ));
        }

        return modelModelList;
    }


    public long addMovie(MovieModel movieModel) {
        ContentValues cv = new ContentValues();
        cv.put(MovieGuideDbContract.COLUMN_MOVIE_ID, movieModel.getId());
        cv.put(MovieGuideDbContract.COLUMN_ORIGINAL_TITLE, movieModel.getOriginalTitle());
        cv.put(MovieGuideDbContract.COLUMN_PLOT_SYNOPSIS, movieModel.getPlotSynopsis());
        cv.put(MovieGuideDbContract.COLUMN_USER_RATE, movieModel.getUserRate());
        cv.put(MovieGuideDbContract.COLUMN_RELEASE_DATE, movieModel.getReleaseDate());
        cv.put(MovieGuideDbContract.COLUMN_COVER_LINK, movieModel.getCoverLink());
        cv.put(MovieGuideDbContract.COLUMN_POSTER_LINK, movieModel.getPosterLink());

        return mDb.insert(MovieGuideDbContract.TABLE_NAME, null, cv);
    }

    public boolean removeMovie(MovieModel movieModel){
        return mDb.delete(MovieGuideDbContract.TABLE_NAME, MovieGuideDbContract.COLUMN_MOVIE_ID + " = " + movieModel.getId(),null) > 0;
    }

    public boolean isMovieAtDatabase(MovieModel mMovieDetails) {
        Cursor cursor = mDb.query(MovieGuideDbContract.TABLE_NAME, null, MovieGuideDbContract.COLUMN_MOVIE_ID + "=?",
                new String[] { String.valueOf(mMovieDetails.getId()) }, null, null, null, null);

        if(cursor.getCount()==0){
            return false;
        }else{
            return true;
        }
    }
}
