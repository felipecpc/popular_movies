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
    private TextView tvUserRate;
    private ImageView ivMovieCover;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        tvMovieTitle = (TextView) findViewById(R.id.tv_original_title_details) ;
        ivMovieCover = (ImageView) findViewById(R.id.iv_movie_cover_details);
        tvOverview = (TextView) findViewById(R.id.tv_overview) ;
        tvReleaseDate = (TextView) findViewById(R.id.tv_releasedate) ;
        tvUserRate = (TextView) findViewById(R.id.tv_user_rate);
        Intent receivedIntent = getIntent();

        if (receivedIntent.hasExtra("MOVIE_DETAIL")){
            mMovieDetails = receivedIntent.getParcelableExtra("MOVIE_DETAIL");

            tvMovieTitle.setText(mMovieDetails.getOriginalTitle());
            Picasso.with(ivMovieCover.getContext()).load(mMovieDetails.getCoverLink())
                    .fit()
                    .error(ivMovieCover.getContext().getResources().getDrawable(R.drawable.ops))
                    .into(ivMovieCover);
            tvOverview.setText(mMovieDetails.getPlotSynopsis());
            tvReleaseDate.setText(mMovieDetails.getReleaseDate());
            tvUserRate.setText(mMovieDetails.getUserRate());
        }


    }
}
