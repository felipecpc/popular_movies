package android.example.com.popularmovies.parser;

import android.example.com.popularmovies.model.MovieModel;

import java.util.ArrayList;

/**
 * Created by felipe on 17/04/17.
 */

public abstract class DataParserBase {
    public abstract ArrayList<MovieModel> parseData(String data);
}
