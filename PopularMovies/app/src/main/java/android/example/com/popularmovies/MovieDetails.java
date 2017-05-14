package android.example.com.popularmovies;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.example.com.popularmovies.connection.HttpRequest;
import android.example.com.popularmovies.databinding.ActivityMovieDetailsBinding;
import android.example.com.popularmovies.model.MovieModel;
import android.example.com.popularmovies.model.TrailerModel;
import android.example.com.popularmovies.view.MovieSelectedInterface;
import android.example.com.popularmovies.view.ReviewsActivity;
import android.example.com.popularmovies.view.TrailerAdapter;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Slide;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieDetails extends AppCompatActivity implements MovieSelectedInterface {

    private ImageView ivMovieCover;
    private RecyclerView rcTrailer;
    private CardView cvReview;
    CollapsingToolbarLayout collapsingToolbarLayout;
    private TrailerAdapter mTrailerAdapter;
    public static String EXTRA_MOVIE_DETAILS = "MOVIE_DETAILS";
    public static final String STATE_TRAILERS = "trailers";
    public static final String STATE_MOVIE_DETAIL = "movie_details";


    MovieModel mMovieDetails;
    ArrayList<TrailerModel> trailersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        initActivityTransitions();

        ActivityMovieDetailsBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_details);

        mTrailerAdapter = new TrailerAdapter(this);



        ivMovieCover = (ImageView) findViewById(R.id.iv_movie_cover_details);
        rcTrailer = (RecyclerView) findViewById(R.id.rc_trailer);
        cvReview = (CardView) findViewById(R.id.cardView_reviews);

        rcTrailer.setAdapter(mTrailerAdapter);
        rcTrailer.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        if (isPortraint())
            collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);


        Intent receivedIntent = getIntent();

        if (receivedIntent.hasExtra(EXTRA_MOVIE_DETAILS)){
            mMovieDetails = receivedIntent.getParcelableExtra(EXTRA_MOVIE_DETAILS);

            binding.setMovie(mMovieDetails);
            Picasso.with(ivMovieCover.getContext()).load(mMovieDetails.getCoverLink())
                    .fit()
                    .error(ivMovieCover.getContext().getResources().getDrawable(R.drawable.ops))
                    .into(ivMovieCover, new Callback() {
                @Override
                public void onSuccess() {
                    Bitmap bitmap = ((BitmapDrawable) ivMovieCover.getDrawable()).getBitmap();
                    Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                        public void onGenerated(Palette palette) {
                            if (isPortraint())
                                applyPalette(palette);
                        }
                    });
                }

                @Override
                public void onError() {

                }
            });

            getSupportActionBar().setTitle(mMovieDetails.getOriginalTitle());
        }





        cvReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reviews = new Intent(MovieDetails.this, ReviewsActivity.class);
                reviews.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(reviews);
            }
        });


        if (trailersList == null){
            trailersList = new ArrayList<TrailerModel>();

            HttpRequest.query(this,HttpRequest.QUERY_TRAILER,mMovieDetails.getId());

        }else{
            mTrailerAdapter.setMovieData(trailersList);
        }

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putParcelableArrayList(STATE_TRAILERS,trailersList);
        outState.putParcelable(STATE_MOVIE_DETAIL,mMovieDetails);
        super.onSaveInstanceState(outState);


    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {

        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();

        LocalBroadcastManager.getInstance(this).registerReceiver(
                mMessageReceiver, new IntentFilter(HttpRequest.HTTP_CONNECTION_FILTER));


    }

    @Override
    protected void onStop() {
        super.onStop();

        LocalBroadcastManager.getInstance(this).unregisterReceiver(
                mMessageReceiver);
    }

    private void initActivityTransitions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Slide transition = new Slide();
            transition.excludeTarget(android.R.id.statusBarBackground, true);
            getWindow().setEnterTransition(transition);
            getWindow().setReturnTransition(transition);
        }
    }

    private void applyPalette(Palette palette) {
        int primaryDark = getResources().getColor(R.color.colorPrimaryDark);
        int primary = getResources().getColor(R.color.colorPrimary);
        collapsingToolbarLayout.setContentScrimColor(palette.getMutedColor(primary));
        collapsingToolbarLayout.setStatusBarScrimColor(palette.getDarkMutedColor(primaryDark));
        supportStartPostponedEnterTransition();
    }

    private boolean isPortraint (){
        Configuration config = getResources().getConfiguration();
        return (config.getLayoutDirection()==config.ORIENTATION_PORTRAIT ? true:false);
    }


    @Override
    public void movieSelected(int selected) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v="+trailersList.get(selected).getKey())));
    }


    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if(intent.hasExtra(HttpRequest.RESULT_STATUS)){
                if(intent.getStringExtra(HttpRequest.RESULT_STATUS).equals(HttpRequest.SUCCESS)){
                    trailersList = intent.getParcelableArrayListExtra(HttpRequest.RESULT_DATA);
                    mTrailerAdapter.setMovieData(trailersList);
                }else{
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MovieDetails.this,getResources().getText(R.string.error),Toast.LENGTH_LONG).show();
                        }
                    });
                }

            }

        }
    };
}
