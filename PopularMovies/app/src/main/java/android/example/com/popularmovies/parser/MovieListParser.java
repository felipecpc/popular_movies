package android.example.com.popularmovies.parser;

import android.example.com.popularmovies.model.MovieModel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by felipe on 17/04/17.
 */

public class MovieListParser extends DataParserBase{
    @Override
    public ArrayList<Parcelable> parseData(String data) {
        ArrayList<Parcelable> movieModelArray = new ArrayList<Parcelable>();
        try {
            JSONObject jsonObj = new JSONObject(data);
            JSONArray results = jsonObj.getJSONArray("results");
            for(int x=0;x<results.length();x++){
                JSONObject moviesData = new JSONObject(results.get(x).toString());

                movieModelArray.add(new MovieModel(moviesData.getInt("id"),
                                                moviesData.getString("original_title"),
                                                moviesData.getString("overview"),
                                                moviesData.getString("vote_average"),
                                                moviesData.getString("release_date"),
                                                "http://image.tmdb.org/t/p/w185"+moviesData.getString("backdrop_path"),
                                                "http://image.tmdb.org/t/p/w185"+moviesData.getString("poster_path")
                                                ));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return movieModelArray;
    }
}


