package android.example.com.popularmovies;

import android.content.Intent;
import android.example.com.popularmovies.model.MovieModel;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MovieDetails extends AppCompatActivity {

    MovieModel mMovieDetails;
    private TextView tvMovieTitle;
    private TextView tvOverview;
    private TextView tvReleaseDate;
    private ImageView ivMovieCover;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        tvMovieTitle = (TextView) findViewById(R.id.tv_original_title_details) ;
        ivMovieCover = (ImageView) findViewById(R.id.iv_movie_cover_details);
        tvOverview = (TextView) findViewById(R.id.tv_overview) ;
        tvReleaseDate = (TextView) findViewById(R.id.tv_releasedate) ;
        Intent receivedIntent = getIntent();

        if (receivedIntent.hasExtra("MOVIE_DETAIL")){
            mMovieDetails = receivedIntent.getParcelableExtra("MOVIE_DETAIL");

            tvMovieTitle.setText(mMovieDetails.getOriginalTitle());
            Picasso.with(ivMovieCover.getContext()).load(mMovieDetails.getCoverLink())
                    .fit()
                    .into(ivMovieCover);
            tvOverview.setText(mMovieDetails.getPlotSynopsis());
            tvReleaseDate.setText(mMovieDetails.getReleaseDate());
        }


    }
}
