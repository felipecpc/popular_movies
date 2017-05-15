package android.example.com.popularmovies.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by felipe on 14/05/17.
 */

public class MoviesGuideDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "waitlist.db";

    private static final int DATABASE_VERSION = 1;

    public MoviesGuideDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        // Create a table to hold waitlist data
        final String SQL_CREATE_MOVIEGUIDE_TABLE = "CREATE TABLE " + MovieGuideDbContract.MovieEntry.TABLE_NAME + " (" +
                MovieGuideDbContract.MovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                MovieGuideDbContract.MovieEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL, " +
                MovieGuideDbContract.MovieEntry.COLUMN_ORIGINAL_TITLE + " TEXT NOT NULL, " +
                MovieGuideDbContract.MovieEntry.COLUMN_PLOT_SYNOPSIS + " TEXT NOT NULL, " +
                MovieGuideDbContract.MovieEntry.COLUMN_USER_RATE + " TEXT NOT NULL, " +
                MovieGuideDbContract.MovieEntry.COLUMN_RELEASE_DATE + " TEXT NOT NULL, " +
                MovieGuideDbContract.MovieEntry.COLUMN_COVER_LINK + " TEXT NOT NULL, " +
                MovieGuideDbContract.MovieEntry.COLUMN_POSTER_LINK + " TEXT NOT NULL);";

        sqLiteDatabase.execSQL(SQL_CREATE_MOVIEGUIDE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MovieGuideDbContract.MovieEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
