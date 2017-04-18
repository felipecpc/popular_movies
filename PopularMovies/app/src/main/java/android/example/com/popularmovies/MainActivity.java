package android.example.com.popularmovies;

import android.content.Intent;
import android.example.com.popularmovies.connection.HttpConnectionInterface;
import android.example.com.popularmovies.connection.HttpConnectionManager;
import android.example.com.popularmovies.model.HttpReponseModel;
import android.example.com.popularmovies.model.MovieModel;
import android.example.com.popularmovies.parser.MovieListParser;
import android.example.com.popularmovies.view.MovieCoverAdapter;
import android.example.com.popularmovies.view.MovieSelectedInterface;
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
    ArrayList<MovieModel> mMovieList = new ArrayList<MovieModel>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMovieCoverList = (RecyclerView) findViewById(R.id.rv_movies_cover);

        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        mMovieCoverList.setLayoutManager(layoutManager);

        mMovieCoverList.setHasFixedSize(true);

        /*MovieModel fakeData[] = {
                new MovieModel("The Boss Baby",null,null,null,"http://image.tmdb.org/t/p/w185/bTFeSwh07oX99ofpDI4O2WkiFJ.jpg"),
                new MovieModel("Beauty and the Beast",null,null,null,"http://image.tmdb.org/t/p/w185/6aUWe0GSl69wMTSWWexsorMIvwU.jpg"),
                new MovieModel("Logan",null,null,null,"http://image.tmdb.org/t/p/w185/5pAGnkFYSsFJ99ZxDIYnhQbQFXs.jpg"),
                new MovieModel("Sing",null,null,null,"http://image.tmdb.org/t/p/w185/fxDXp8un4qNY9b1dLd7SH6CKzC.jpg"),
        };*/

        mMovieCoverAdapter = new MovieCoverAdapter(this);
        mMovieCoverList.setAdapter(mMovieCoverAdapter);

        mHttpConnectionManager = new HttpConnectionManager(this);
        mHttpConnectionManager.queryPopularMovies();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
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
