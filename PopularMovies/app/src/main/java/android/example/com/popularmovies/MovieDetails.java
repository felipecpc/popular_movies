package android.example.com.popularmovies;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.example.com.popularmovies.databinding.ActivityMovieDetailsBinding;
import android.example.com.popularmovies.model.MovieModel;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class MovieDetails extends AppCompatActivity {

    MovieModel mMovieDetails;
    private TextView tvMovieTitle;
    private TextView tvOverview;
    private TextView tvReleaseDate;
    private TextView tvUserRate;
    private ImageView ivMovieCover;
    CollapsingToolbarLayout collapsingToolbarLayout;

    public static String EXTRA_MOVIE_DETAILS = "MOVIE_DETAILS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        initActivityTransitions();

        ActivityMovieDetailsBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_details);

        ivMovieCover = (ImageView) findViewById(R.id.iv_movie_cover_details);

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

        }


        getSupportActionBar().setTitle(mMovieDetails.getOriginalTitle());



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


}
