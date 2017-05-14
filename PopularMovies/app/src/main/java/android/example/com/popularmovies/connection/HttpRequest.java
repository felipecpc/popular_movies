package android.example.com.popularmovies.connection;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.example.com.popularmovies.BuildConfig;
import android.example.com.popularmovies.parser.MovieListParser;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by felipe on 14/05/17.
 */

public class HttpRequest extends IntentService{

    private String BASE_URL  = "http://api.themoviedb.org/3/";
    private String KEY = BuildConfig.MOVIE_DB_API_TOKEN;
    public final static String QUERY_POPULAR = "movie/popular";
    public final static String QUERY_TOP_RATED = "movie/top_rated";

    public final static String REQUEST = "REQUEST";
    public final static String RESULT_STATUS = "RESULT_STATUS";
    public final static String RESULT_DATA = "RESULT_DATA";


    public final static String SUCCESS = "SUCCESS";
    public final static String FAIL = "FAIL";

    public static String HTTP_CONNECTION_FILTER = "HTTP_CONNECTION";

    public HttpRequest() {
        super("HTTP_REQUEST");
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if(intent.getExtras().containsKey(REQUEST)){
            run(BASE_URL+intent.getStringExtra(REQUEST)+"?api_key="+KEY);
        }
    }


    private void run(String url) {

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
                        Intent intent = new Intent(HttpRequest.HTTP_CONNECTION_FILTER);
                        intent.putExtra(HttpRequest.RESULT_STATUS, FAIL);
                        LocalBroadcastManager.getInstance(HttpRequest.this).sendBroadcast(intent);
                    }
                    return response.body().string();
                } catch (Exception e) {
                    Intent intent = new Intent(HttpRequest.HTTP_CONNECTION_FILTER);
                    intent.putExtra(HttpRequest.RESULT_STATUS, FAIL);
                    LocalBroadcastManager.getInstance(HttpRequest.this).sendBroadcast(intent);
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if (s != null) {
                    Intent intent = new Intent(HttpRequest.HTTP_CONNECTION_FILTER);
                    MovieListParser mvParserList = new MovieListParser();
                    intent.putExtra(HttpRequest.RESULT_STATUS, SUCCESS);
                    intent.putParcelableArrayListExtra(HttpRequest.RESULT_DATA, mvParserList.parseData(s));
                    LocalBroadcastManager.getInstance(HttpRequest.this).sendBroadcast(intent);
                }
            }
        };

        asyncTask.execute();

    }

    public static void query(Context ctx, String type){
        Intent msg = new Intent(ctx, HttpRequest.class);
        msg.putExtra(HttpRequest.REQUEST, type);
        ctx.startService(msg);
    }



}
