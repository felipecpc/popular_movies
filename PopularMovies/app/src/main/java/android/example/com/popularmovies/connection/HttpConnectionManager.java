package android.example.com.popularmovies.connection;

import android.example.com.popularmovies.model.HttpReponseModel;
import android.os.AsyncTask;
import android.util.Log;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by felipe on 14/04/17.
 */

public class HttpConnectionManager {

    private static final String TAG = HttpConnectionManager.class.getSimpleName();

    private String BASE_URL  = "http://api.themoviedb.org/3/";
    private String KEY = "SET YOUR KEY";
    private String QUERY_POPULAR = "movie/popular";
    private String QUERY_TOP_RATED = "movie/top_rated";

    private HttpConnectionInterface mHttpConnectionInterface;

    public HttpConnectionManager(HttpConnectionInterface httpConnectionInterface){
        mHttpConnectionInterface = httpConnectionInterface;
    }

    private void run(String url) {
        Log.d(TAG,url);
        final OkHttpClient client = new OkHttpClient();

        final Request request = new Request.Builder()
                .url(url)
                .build();

        AsyncTask<Void, Void, String> asyncTask = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                try {
                    Response response = client.newCall(request).execute();
                    if (!response.isSuccessful()) {
                        mHttpConnectionInterface.requestResponse(new HttpReponseModel(HttpReponseModel.RequestStatus.FAIL,"Fail"));
                    }
                    return response.body().string();
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if (s != null) {
                    mHttpConnectionInterface.requestResponse(new HttpReponseModel(HttpReponseModel.RequestStatus.SUCCESS,s));

                }
            }
        };

        asyncTask.execute();

    }

    public void queryPopularMovies(){
        run(BASE_URL+QUERY_POPULAR+"?api_key="+KEY);
    }

    public void queryTopRatedMovies(){
        run(BASE_URL+ QUERY_TOP_RATED +"?api_key="+KEY);
    }
}
