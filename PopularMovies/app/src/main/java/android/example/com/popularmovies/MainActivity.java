package android.example.com.popularmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements MovieSelectedInterface{

    private static final String TAG = MainActivity.class.getSimpleName();

    private RecyclerView mMovieCoverList;
    private MovieCoverAdapter mMovieCoverAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMovieCoverList = (RecyclerView) findViewById(R.id.rv_movies_cover);

        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        mMovieCoverList.setLayoutManager(layoutManager);

        mMovieCoverList.setHasFixedSize(true);

        MovieModel fakeData[] = {
                new MovieModel("The Boss Baby",null,null,null,"http://image.tmdb.org/t/p/w185/bTFeSwh07oX99ofpDI4O2WkiFJ.jpg"),
                new MovieModel("Beauty and the Beast",null,null,null,"http://image.tmdb.org/t/p/w185/6aUWe0GSl69wMTSWWexsorMIvwU.jpg"),
                new MovieModel("Logan",null,null,null,"http://image.tmdb.org/t/p/w185/5pAGnkFYSsFJ99ZxDIYnhQbQFXs.jpg"),
                new MovieModel("Sing",null,null,null,"http://image.tmdb.org/t/p/w185/fxDXp8un4qNY9b1dLd7SH6CKzC.jpg"),
        };

        mMovieCoverAdapter = new MovieCoverAdapter(this,fakeData);
        mMovieCoverList.setAdapter(mMovieCoverAdapter);
    }

    @Override
    public void movieSelected(int selected) {
        Log.d(TAG,"Item selected " + selected);
    }
}
