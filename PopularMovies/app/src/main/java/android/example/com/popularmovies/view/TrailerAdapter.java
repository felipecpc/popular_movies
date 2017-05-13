package android.example.com.popularmovies.view;

import android.content.Context;
import android.example.com.popularmovies.R;
import android.example.com.popularmovies.model.MovieModel;
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

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerAdapterViewHolder> {

    ArrayList<TrailerModel> mTrailerItems = new ArrayList<TrailerModel>();
    MovieSelectedInterface mMovieSelectedInterface;

    public TrailerAdapter(MovieSelectedInterface selectedInterface){
        mMovieSelectedInterface = selectedInterface;
    }

    @Override
    public TrailerAdapter.TrailerAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.trailer_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        TrailerAdapter.TrailerAdapterViewHolder viewHolder = new TrailerAdapter.TrailerAdapterViewHolder(view);

        return viewHolder;
    }

    public void setMovieData(ArrayList<TrailerModel> movieData){
        mTrailerItems = movieData;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(TrailerAdapter.TrailerAdapterViewHolder holder, int position) {
        ImageView imgView = holder.movieCover;
        TextView txtView = holder.trailerTitle;
        Picasso.with(imgView.getContext()).load("https://img.youtube.com/vi/"+mTrailerItems.get(position).getKey()+"/hqdefault.jpg")
                .fit()
                .into(imgView);

        txtView.setText(mTrailerItems.get(position).getNome());

    }

    @Override
    public int getItemCount() {
        return mTrailerItems.size();
    }

    class TrailerAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView movieCover;
        TextView trailerTitle;
        public TrailerAdapterViewHolder(View itemView) {
            super(itemView);

            movieCover = (ImageView) itemView.findViewById(R.id.iv_trailer_cover);
            trailerTitle = (TextView) itemView.findViewById(R.id.tv_trailerx_title);
            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mMovieSelectedInterface.movieSelected(clickedPosition);
        }
    }

}
