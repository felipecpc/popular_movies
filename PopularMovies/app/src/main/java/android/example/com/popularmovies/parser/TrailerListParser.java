package android.example.com.popularmovies.parser;

import android.example.com.popularmovies.model.MovieModel;
import android.example.com.popularmovies.model.TrailerModel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by felipe on 17/04/17.
 */

public class TrailerListParser extends DataParserBase{
    @Override
    public ArrayList<Parcelable> parseData(String data) {
        ArrayList<Parcelable> trailerModelArray = new ArrayList<Parcelable>();
        try {
            JSONObject jsonObj = new JSONObject(data);
            JSONArray results = jsonObj.getJSONArray("results");
            for(int x=0;x<results.length();x++){
                JSONObject moviesData = new JSONObject(results.get(x).toString());

                trailerModelArray.add(new TrailerModel(moviesData.getString("id"),
                                                moviesData.getString("key"),
                                                moviesData.getString("name")
                                                ));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return trailerModelArray;
    }
}


