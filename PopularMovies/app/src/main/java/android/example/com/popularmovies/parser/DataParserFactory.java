package android.example.com.popularmovies.parser;

import android.example.com.popularmovies.connection.HttpRequest;

/**
 * Created by felipe on 14/05/17.
 */

public class DataParserFactory {

    public static DataParserBase getDataParserObj(String command){
        switch (command){
            case HttpRequest.QUERY_POPULAR:
            case HttpRequest.QUERY_TOP_RATED:
                return new MovieListParser();
            case HttpRequest.QUERY_TRAILER:
                return new TrailerListParser();
            case HttpRequest.QUERY_REVIEW:
                return new ReviewListParser();
            default:
                return null;

        }
    }

}
