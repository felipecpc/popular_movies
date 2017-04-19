package android.example.com.popularmovies;

import android.content.Intent;
import android.example.com.popularmovies.connection.HttpConnectionInterface;
import android.example.com.popularmovies.connection.HttpConnectionManager;
import android.example.com.popularmovies.model.HttpReponseModel;
import android.example.com.popularmovies.model.MovieModel;
import android.example.com.popularmovies.parser.MovieListParser;
import android.example.com.popularmovies.view.MovieCoverAdapter;
import android.example.com.popularmovies.view.MovieSelectedInterface;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MovieSelectedInterface, HttpConnectionInterface {

    private static final String TAG = MainActivity.class.getSimpleName();

    private RecyclerView mMovieCoverList;
    private MovieCoverAdapter mMovieCoverAdapter;
    private HttpConnectionManager mHttpConnectionManager;
    private static ArrayList<MovieModel> mMovieList = null;

    private static Bundle mBundleRecyclerViewState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMovieCoverList = (RecyclerView) findViewById(R.id.rv_movies_cover);

        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        mMovieCoverList.setLayoutManager(layoutManager);

        mMovieCoverList.setHasFixedSize(true);

        mMovieCoverAdapter = new MovieCoverAdapter(this);
        mMovieCoverList.setAdapter(mMovieCoverAdapter);

        mHttpConnectionManager = new HttpConnectionManager(this);

        if (mMovieList == null){
            mMovieList = new ArrayList<MovieModel>();
            mHttpConnectionManager.queryPopularMovies();
        }else{
            mMovieCoverAdapter.setMovieData(mMovieList);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        // save RecyclerView state
        mBundleRecyclerViewState = new Bundle();
        Parcelable listState = mMovieCoverList.getLayoutManager().onSaveInstanceState();
        mBundleRecyclerViewState.putParcelable("RV_STATE", listState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // restore RecyclerView state
        if (mBundleRecyclerViewState != null) {
            Parcelable listState = mBundleRecyclerViewState.getParcelable("RV_STATE");
            mMovieCoverList.getLayoutManager().onRestoreInstanceState(listState);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_popular:
                mHttpConnectionManager.queryPopularMovies();
                return true;
            case R.id.menu_top_rated:
                mHttpConnectionManager.queryTopRatedMovies();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void movieSelected(int selected) {
        Intent newIntent = new Intent(this,MovieDetails.class);
        newIntent.putExtra("MOVIE_DETAIL",mMovieList.get(selected));
        startActivity(newIntent);
        Log.d(TAG,"Item selected " + selected);
    }

    @Override
    public void requestResponse(HttpReponseModel response) {
        if (response.getStatus().equals(HttpReponseModel.RequestStatus.SUCCESS)){
            //Log.d(TAG,response.getResponseData());
            MovieListParser mListParser = new MovieListParser();
            mMovieList = mListParser.parseData(response.getResponseData());
            mMovieCoverAdapter.setMovieData(mMovieList);

        }else{
            Log.d(TAG,response.getResponseData());
        }
    }
}
