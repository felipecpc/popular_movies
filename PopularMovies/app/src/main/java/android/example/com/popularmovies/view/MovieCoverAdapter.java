package android.example.com.popularmovies.view;

import android.content.Context;
import android.example.com.popularmovies.model.MovieModel;
import android.example.com.popularmovies.R;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by felipe on 14/04/17.
 */

public class MovieCoverAdapter extends RecyclerView.Adapter<MovieCoverAdapter.MovieCoverViewHolder> {

    ArrayList<MovieModel> mMovieItems = new ArrayList<MovieModel>();
    MovieSelectedInterface mMovieSelectedInterface;

    public MovieCoverAdapter(MovieSelectedInterface selectedInterface){
        mMovieSelectedInterface = selectedInterface;
    }

    @Override
    public MovieCoverViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.movie_cover;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        MovieCoverViewHolder viewHolder = new MovieCoverViewHolder(view);

        return viewHolder;
    }

    public void setMovieData(ArrayList<MovieModel> movieData){
        mMovieItems = movieData;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(MovieCoverViewHolder holder, int position) {
        ImageView imgView = holder.movieCover;
        Picasso.with(imgView.getContext()).load(mMovieItems.get(position).getPosterLink())
                .fit()
                .into(imgView);
    }

    @Override
    public int getItemCount() {
        return mMovieItems.size();
    }

    class MovieCoverViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView movieCover;

        public MovieCoverViewHolder(View itemView) {
            super(itemView);

            movieCover = (ImageView) itemView.findViewById(R.id.iv_movie_cover);
            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mMovieSelectedInterface.movieSelected(clickedPosition);
        }
    }


}
