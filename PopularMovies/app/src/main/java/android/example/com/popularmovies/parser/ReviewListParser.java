package android.example.com.popularmovies.parser;

import android.example.com.popularmovies.model.ReviewsModel;
import android.example.com.popularmovies.model.TrailerModel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by felipe on 17/04/17.
 */

public class ReviewListParser extends DataParserBase{
    private final String TAG = ReviewListParser.class.getSimpleName();
    @Override
    public ArrayList<Parcelable> parseData(String data) {
        ArrayList<Parcelable> reviewModelArray = new ArrayList<Parcelable>();
        try {
            JSONObject jsonObj = new JSONObject(data);
            JSONArray results = jsonObj.getJSONArray("results");
            for(int x=0;x<results.length();x++){
                JSONObject moviesData = new JSONObject(results.get(x).toString());
                Log.d(TAG,"Parsing > " + moviesData);
                reviewModelArray.add(new ReviewsModel(moviesData.getString("id"),
                                                moviesData.getString("author"),
                                                moviesData.getString("content")
                                                ));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d(TAG,"Parsed Items > " + reviewModelArray.size());
        return reviewModelArray;
    }
}


