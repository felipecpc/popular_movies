package android.example.com.popularmovies.view;

import android.content.Context;
import android.example.com.popularmovies.R;
import android.example.com.popularmovies.model.ReviewsModel;
import android.example.com.popularmovies.model.TrailerModel;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by felipe on 13/05/17.
 */

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewAdapterViewHolder> {

    ArrayList<ReviewsModel> mReviewItens = new ArrayList<ReviewsModel>();


    public ReviewsAdapter(){

    }

    @Override
    public ReviewsAdapter.ReviewAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.review_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        ReviewsAdapter.ReviewAdapterViewHolder viewHolder = new ReviewsAdapter.ReviewAdapterViewHolder(view);

        return viewHolder;
    }

    public void setReviewData(ArrayList<ReviewsModel> movieData){
        mReviewItens = movieData;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(ReviewsAdapter.ReviewAdapterViewHolder holder, int position) {
        TextView author = holder.author;
        TextView content = holder.content;

        author.setText(mReviewItens.get(position).getAuthor());
        content.setText("\""+mReviewItens.get(position).getContent()+"\"");

    }

    @Override
    public int getItemCount() {
        return mReviewItens.size();
    }

    class ReviewAdapterViewHolder extends RecyclerView.ViewHolder  {

        TextView author;
        TextView content;
        public ReviewAdapterViewHolder(View itemView) {
            super(itemView);

            author = (TextView) itemView.findViewById(R.id.tv_review_author);
            content = (TextView) itemView.findViewById(R.id.tv_review_content);


        }

    }

}
