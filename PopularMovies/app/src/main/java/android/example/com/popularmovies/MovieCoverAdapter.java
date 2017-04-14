package android.example.com.popularmovies;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by felipe on 14/04/17.
 */

public class MovieCoverAdapter extends RecyclerView.Adapter<MovieCoverAdapter.MovieCoverViewHolder> {
    @Override
    public MovieCoverViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(MovieCoverViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class MovieCoverViewHolder extends RecyclerView.ViewHolder{

        public MovieCoverViewHolder(View itemView) {
            super(itemView);
        }
    }
}
