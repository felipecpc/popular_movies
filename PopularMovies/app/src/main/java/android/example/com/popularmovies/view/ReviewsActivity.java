package android.example.com.popularmovies.view;

import android.example.com.popularmovies.model.ReviewsModel;
import android.example.com.popularmovies.ui.GridRecyclerView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.example.com.popularmovies.R;
import android.support.v7.widget.GridLayoutManager;

import java.util.ArrayList;

public class ReviewsActivity extends AppCompatActivity {


    private GridRecyclerView mMovieReviewList;
    private ReviewsAdapter mReviewAdapter;

    private static ArrayList<ReviewsModel> mReviewList = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);

        mMovieReviewList = (GridRecyclerView) findViewById(R.id.rv_movies_review);

        GridLayoutManager layoutManager = new GridLayoutManager(this,1);
        mMovieReviewList.setLayoutManager(layoutManager);

        mMovieReviewList.setHasFixedSize(true);

        //Creating fake data
        mReviewList = new ArrayList<>();
        mReviewList.add(new ReviewsModel(0,"Felipe Castro","asdfasdfasdfjkhaskljdfhasjkdfhaklsjdfhkjasdhflkajsdhflkjashdfjklashdflkjashdflkajsdfhlaksjdfhalksjdfhalsdfhajslkdfhalksjdfhaklsjdfhalkjsdfhalkjsdhfkaljsdhfklajsdhflkajsdhflkajsdhflkashdflkasjdhflasjdhfasljdfhalsjkdfhalskjdfhalksjdfhasdf"));
        mReviewList.add(new ReviewsModel(1,"Felipe aa","asdfasdfasdfjkhaskljdfhasjkdfhaklsjdfhkjasdhflkajsdhflkjashdfjklashdflkjashdflkajsdfhlaksjdfhalksjdfhalsdfhajslkdfhalksjdfhaklsjdfhalkjsdfhalkjsdhfkaljsdhfklajsdhflkajsdhflkajsdhflkashdflkasjdhflasjdhfasljdfhalsjkdfhalskjdfhalksjdfhasdf"));
        mReviewList.add(new ReviewsModel(2,"Felipe ss","asdfasdfasdfjkhaskljdfhasjkdfhaklsjdfhkjasdhflkajsdhflkjashdfjklashdflkjashdflkajsdfhlaksjdfhalksjdfhalsdfhajslkdfhalksjdfhaklsjdfhalkjsdfhalkjsdhfkaljsdhfklajsdhflkajsdhflkajsdhflkashdflkasjdhflasjdhfasljdfhalsjkdfhalskjdfhalksjdfhasdf"));
        mReviewList.add(new ReviewsModel(3,"Felipe dd","asdfasdfasdfjkhaskljdfhasjkdfhaklsjdfhkjasdhflkajsdhflkjashdfjklashdflkjashdflkajsdfhlaksjdfhalksjdfhalsdfhajslkdfhalksjdfhaklsjdfhalkjsdfhalkjsdhfkaljsdhfklajsdhflkajsdhflkajsdhflkashdflkasjdhflasjdhfasljdfhalsjkdfhalskjdfhalksjdfhasdf"));
        mReviewList.add(new ReviewsModel(4,"Felipe ff","asdfasdfasdfjkhaskljdfhasjkdfhaklsjdfhkjasdhflkajsdhflkjashdfjklashdflkjashdflkajsdfhlaksjdfhalksjdfhalsdfhajslkdfhalksjdfhaklsjdfhalkjsdfhalkjsdhfkaljsdhfklajsdhflkajsdhflkajsdhflkashdflkasjdhflasjdhfasljdfhalsjkdfhalskjdfhalksjdfhasdf"));
        mReviewList.add(new ReviewsModel(5,"Felipe gg","asdfasdfasdfjkhaskljdfhasjkdfhaklsjdfhkjasdhflkajsdhflkjashdfjklashdflkjashdflkajsdfhlaksjdfhalksjdfhalsdfhajslkdfhalksjdfhaklsjdfhalkjsdfhalkjsdhfkaljsdhfklajsdhflkajsdhflkajsdhflkashdflkasjdhflasjdhfasljdfhalsjkdfhalskjdfhalksjdfhasdf"));
        mReviewList.add(new ReviewsModel(6,"Felipe hh","asdfasdfasdfjkhaskljdfhasjkdfhaklsjdfhkjasdhflkajsdhflkjashdfjklashdflkjashdflkajsdfhlaksjdfhalksjdfhalsdfhajslkdfhalksjdfhaklsjdfhalkjsdfhalkjsdhfkaljsdhfklajsdhflkajsdhflkajsdhflkashdflkasjdhflasjdhfasljdfhalsjkdfhalskjdfhalksjdfhasdf"));
        mReviewList.add(new ReviewsModel(7,"Felipe jj","asdfasdfasdfjkhaskljdfhasjkdfhaklsjdfhkjasdhflkajsdhflkjashdfjklashdflkjashdflkajsdfhlaksjdfhalksjdfhalsdfhajslkdfhalksjdfhaklsjdfhalkjsdfhalkjsdhfkaljsdhfklajsdhflkajsdhflkajsdhflkashdflkasjdhflasjdhfasljdfhalsjkdfhalskjdfhalksjdfhasdf"));
        mReviewList.add(new ReviewsModel(8,"Felipe kk","asdfasdfasdfjkhaskljdfhasjkdfhaklsjdfhkjasdhflkajsdhflkjashdfjklashdflkjashdflkajsdfhlaksjdfhalksjdfhalsdfhajslkdfhalksjdfhaklsjdfhalkjsdfhalkjsdhfkaljsdhfklajsdhflkajsdhflkajsdhflkashdflkasjdhflasjdhfasljdfhalsjkdfhalskjdfhalksjdfhasdf"));
        mReviewList.add(new ReviewsModel(9,"Felipe ll","asdfasdfasdfjkhaskljdfhasjkdfhaklsjdfhkjasdhflkajsdhflkjashdfjklashdflkjashdflkajsdfhlaksjdfhalksjdfhalsdfhajslkdfhalksjdfhaklsjdfhalkjsdfhalkjsdhfkaljsdhfklajsdhflkajsdhflkajsdhflkashdflkasjdhflasjdhfasljdfhalsjkdfhalskjdfhalksjdfhasdf"));
        mReviewList.add(new ReviewsModel(10,"Felipe ;;","asdfasdfasdfjkhaskljdfhasjkdfhaklsjdfhkjasdhflkajsdhflkjashdfjklashdflkjashdflkajsdfhlaksjdfhalksjdfhalsdfhajslkdfhalksjdfhaklsjdfhalkjsdfhalkjsdhfkaljsdhfklajsdhflkajsdhflkajsdhflkashdflkasjdhflasjdhfasljdfhalsjkdfhalskjdfhalksjdfhasdf"));

        mReviewAdapter = new ReviewsAdapter();
        mReviewAdapter.setReviewData(mReviewList);
        mMovieReviewList.setAdapter(mReviewAdapter);



        getSupportActionBar().setTitle(getResources().getString(R.string.review_title));

    }

    @Override public void onEnterAnimationComplete() {
        super.onEnterAnimationComplete();
    }

}
