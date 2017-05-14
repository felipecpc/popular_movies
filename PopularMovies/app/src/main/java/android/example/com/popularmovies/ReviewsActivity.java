package android.example.com.popularmovies;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.example.com.popularmovies.connection.HttpRequest;
import android.example.com.popularmovies.model.ReviewsModel;
import android.example.com.popularmovies.model.TrailerModel;
import android.example.com.popularmovies.ui.GridRecyclerView;
import android.example.com.popularmovies.view.ReviewsAdapter;
import android.os.Handler;
import android.os.Looper;
import android.os.PersistableBundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.example.com.popularmovies.R;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class ReviewsActivity extends AppCompatActivity {


    private GridRecyclerView mMovieReviewList;
    private ReviewsAdapter mReviewAdapter;

    private CardView cardViewStatus;

    private static ArrayList<ReviewsModel> mReviewList = null;
    private String mId;
    public static final String STATE_ID = "id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);

        mMovieReviewList = (GridRecyclerView) findViewById(R.id.rv_movies_review);
        cardViewStatus = (CardView) findViewById(R.id.card_view_loading);
        mMovieReviewList.setVisibility(View.VISIBLE);
        cardViewStatus.setVisibility(View.GONE);

        GridLayoutManager layoutManager = new GridLayoutManager(this,1);
        mMovieReviewList.setLayoutManager(layoutManager);

        if(getIntent().hasExtra(HttpRequest.ID)){
            mId = getIntent().getStringExtra(HttpRequest.ID);
        } else if(savedInstanceState!=null){
            mId = savedInstanceState.getString(STATE_ID);
        }

        mMovieReviewList.setHasFixedSize(true);



        mReviewAdapter = new ReviewsAdapter();
        mMovieReviewList.setAdapter(mReviewAdapter);


        getSupportActionBar().setTitle(getResources().getString(R.string.review_title));

        mReviewList = new ArrayList<ReviewsModel>();
        HttpRequest.query(this,HttpRequest.QUERY_REVIEW,mId);


    }

    @Override
    protected void onResume() {
        super.onResume();

        LocalBroadcastManager.getInstance(this).registerReceiver(
                mMessageReceiver, new IntentFilter(HttpRequest.HTTP_CONNECTION_FILTER));


    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(STATE_ID,mId);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);


    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(
                mMessageReceiver);
    }

    @Override
    protected void onStop() {
        super.onStop();

        LocalBroadcastManager.getInstance(this).unregisterReceiver(
                mMessageReceiver);
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if(intent.hasExtra(HttpRequest.RESULT_STATUS)){
                if(intent.getStringExtra(HttpRequest.RESULT_STATUS).equals(HttpRequest.SUCCESS)){
                    mReviewList = intent.getParcelableArrayListExtra(HttpRequest.RESULT_DATA);

                    if(mReviewList.isEmpty()){
                        mMovieReviewList.setVisibility(View.GONE);
                        cardViewStatus.setVisibility(View.VISIBLE);
                    }

                    mReviewAdapter.setReviewData(mReviewList);
                }else{
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ReviewsActivity.this,getResources().getText(R.string.error),Toast.LENGTH_LONG).show();
                        }
                    });
                }

            }

        }
    };

    @Override public void onEnterAnimationComplete() {
        super.onEnterAnimationComplete();
    }
}
