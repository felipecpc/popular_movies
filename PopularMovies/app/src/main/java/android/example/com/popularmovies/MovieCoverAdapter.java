package android.example.com.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import static android.R.attr.onClick;

/**
 * Created by felipe on 14/04/17.
 */

public class MovieCoverAdapter extends RecyclerView.Adapter<MovieCoverAdapter.MovieCoverViewHolder> {

    MovieModel mMovieItems[];
    MovieSelectedInterface mMovieSelectedInterface;

    public MovieCoverAdapter(MovieSelectedInterface selectedInterface,MovieModel movieData[]){
        mMovieItems = movieData;
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

    @Override
    public void onBindViewHolder(MovieCoverViewHolder holder, int position) {
        holder.movieTitle.setText(mMovieItems[position].getOriginalTitle());
        ImageView imgView = holder.movieCover;
        Picasso.with(imgView.getContext()).load(mMovieItems[position].getCoverLink())
                .fit()
                .into(imgView);
    }

    @Override
    public int getItemCount() {
        return mMovieItems.length;
    }

    class MovieCoverViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView movieTitle;
        ImageView movieCover;

        public MovieCoverViewHolder(View itemView) {
            super(itemView);

            movieTitle = (TextView) itemView.findViewById(R.id.tv_movie_title);
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
