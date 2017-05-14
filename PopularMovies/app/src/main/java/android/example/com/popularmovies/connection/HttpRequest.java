package android.example.com.popularmovies.connection;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.example.com.popularmovies.BuildConfig;
import android.example.com.popularmovies.parser.DataParserBase;
import android.example.com.popularmovies.parser.DataParserFactory;

import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by felipe on 14/05/17.
 */

public class HttpRequest extends IntentService{

    private static final String TAG = HttpRequest.class.getSimpleName();

    private String BASE_URL  = "http://api.themoviedb.org/3/";
    private String KEY = BuildConfig.MOVIE_DB_API_TOKEN;
    public final static String QUERY_POPULAR = "popular";
    public final static String QUERY_TOP_RATED = "top_rated";
    public final static String QUERY_TRAILER = "videos";
    public final static String QUERY_REVIEW = "reviews";


    public final static String REQUEST = "REQUEST";
    public final static String ID = "ID";
    public final static String RESULT_STATUS = "RESULT_STATUS";
    public final static String RESULT_DATA = "RESULT_DATA";


    public final static String SUCCESS = "SUCCESS";
    public final static String FAIL = "FAIL";
    private String mRequest=null;

    public static String HTTP_CONNECTION_FILTER = "HTTP_CONNECTION";

    public HttpRequest() {
        super("HTTP_REQUEST");
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if(intent.getExtras().containsKey(REQUEST)){
            mRequest=intent.getStringExtra(REQUEST);
            if(!intent.getExtras().containsKey(HttpRequest.ID)) {
                Log.d(TAG,BASE_URL + "movie/" + mRequest + "?api_key=" + KEY);
                run(BASE_URL + "movie/" + mRequest + "?api_key=" + KEY);
            }else{
                String id = intent.getStringExtra(HttpRequest.ID);
                Log.d(TAG,BASE_URL + "movie/" + id + "/"+ mRequest + "?api_key=" + KEY);
                run(BASE_URL + "movie/" + id + "/"+ mRequest + "?api_key=" + KEY);
            }
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
                    DataParserBase dataParser = DataParserFactory.getDataParserObj(mRequest);
                    intent.putExtra(HttpRequest.RESULT_STATUS, SUCCESS);
                    intent.putParcelableArrayListExtra(HttpRequest.RESULT_DATA, dataParser.parseData(s));
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

    public static void query(Context ctx, String type, String id){
        Intent msg = new Intent(ctx, HttpRequest.class);
        msg.putExtra(HttpRequest.ID, id);
        msg.putExtra(HttpRequest.REQUEST, type);
        ctx.startService(msg);
    }


}
