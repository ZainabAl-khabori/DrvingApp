package worldontheotherside.wordpress.com.drvingapp.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

import worldontheotherside.wordpress.com.drvingapp.Classes.TrainerRate;
import worldontheotherside.wordpress.com.drvingapp.R;

/**
 * Created by wafooy on 29/11/17.
 */

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ViewHolder> {

    private ArrayList<TrainerRate> reviewsList;
    private OnItemClickListener onItemClickListener;
    //private BaseFragment fragment;

    public ReviewsAdapter (ArrayList dataSet)
    {
        reviewsList = dataSet;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        public void OnClick(View view, int position);
    }


    //public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    @Override
    public ReviewsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reviews_tab_contents,
                parent, false);
        return new ReviewsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


       /* Picasso.with(holder.itemView.getContext())
                .load(CartProductsList.get(position).getThumb())
                .into(holder.imageViewProductImage);
        holder.textViewProductName.setText(CartProductsList.get(position).getName());
        if (CartProductsList.get(position).isStock())
            holder.textViewProductStock.setText("1");
        else
            holder.textViewProductStock.setText("0");
        holder.textViewProductPrice.setText(CartProductsList.get(position).getPrice());
        holder.textViewProductQuantity.setText(Integer.toString(CartProductsList.get(position)
                .getQuantity()));*/
    }

    @Override
    public int getItemCount() {
        return reviewsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        RatingBar ratingBarReviews;
        TextView textViewCommentDate;
        TextView textViewTraineeName;
        TextView textViewComments;


        public ViewHolder(View itemView) {
            super(itemView);

            ratingBarReviews = (RatingBar) itemView.findViewById(R.id.ratingBarReviews);
            textViewCommentDate = (TextView) itemView.findViewById(R.id.textViewCommentDate);
            textViewTraineeName = (TextView) itemView.findViewById(R.id.textViewTraineeName);
            textViewComments = (TextView) itemView.findViewById(R.id.textViewComments);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {


            if (onItemClickListener != null)
                onItemClickListener.OnClick(view, getAdapterPosition());
        }
    }
}
