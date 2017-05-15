package android.example.com.popularmovies.database;

import android.annotation.TargetApi;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static android.example.com.popularmovies.database.MovieGuideDbContract.MovieEntry.TABLE_NAME;

/**
 * Created by felipe on 15/05/17.
 */

public class MovieGuideContentProvider extends ContentProvider {

    public static final int CODE_FAVORITE = 100;
    public static final int CODE_FAVORITE_WITH_ID = 101;


    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private MoviesGuideDBHelper mOpenHelper;


    public static UriMatcher buildUriMatcher() {

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = MovieGuideDbContract.CONTENT_AUTHORITY;


        matcher.addURI(authority, MovieGuideDbContract.PATH_FAVORITE, CODE_FAVORITE);

        matcher.addURI(authority, MovieGuideDbContract.PATH_FAVORITE + "/#", CODE_FAVORITE_WITH_ID);

        return matcher;
    }


    @Override
    public boolean onCreate() {

        mOpenHelper = new MoviesGuideDBHelper(getContext());
        return true;
    }



    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        Cursor cursor;

        switch (sUriMatcher.match(uri)) {

            case CODE_FAVORITE_WITH_ID: {

                String id = uri.getPathSegments().get(1);
                cursor = mOpenHelper.getReadableDatabase().query(
                        TABLE_NAME,
                        null,
                        MovieGuideDbContract.MovieEntry.COLUMN_MOVIE_ID + "=?",
                        new String[]{id},
                        null,
                        null,
                        null,
                        null);
                break;
            }


            case CODE_FAVORITE: {
                cursor = mOpenHelper.getReadableDatabase().query(
                        TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);

                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }


    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int removedRows = 0;
        switch (sUriMatcher.match(uri)) {

            case CODE_FAVORITE_WITH_ID:
                String id = uri.getPathSegments().get(1);
                removedRows = db.delete(TABLE_NAME, MovieGuideDbContract.MovieEntry.COLUMN_MOVIE_ID+"=?", new String[]{id});
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        return removedRows;
    }


    @Override
    public String getType(@NonNull Uri uri) {
        throw new RuntimeException("We are not implementing getType in this app.");
    }


    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {

        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case CODE_FAVORITE:
                long id = db.insert(TABLE_NAME, null, values);
                if ( id > 0 ) {
                    returnUri = ContentUris.withAppendedId(MovieGuideDbContract.BASE_CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return returnUri;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        throw new RuntimeException("We are not implementing update in this app");
    }


    @Override
    @TargetApi(11)
    public void shutdown() {
        mOpenHelper.close();
        super.shutdown();
    }
}
