package worldontheotherside.wordpress.com.drvingapp.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

import worldontheotherside.wordpress.com.drvingapp.Classes.Trainer;
import worldontheotherside.wordpress.com.drvingapp.R;

/**
 * Created by زينب on 12/4/2017.
 */

public class InstructorsRecyclerAdapter extends RecyclerView.Adapter<InstructorsRecyclerAdapter.ViewHolder> {

    private ArrayList<Trainer> list;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener { public void onClick(View view, int position); }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private TextView textViewName;
        private RatingBar ratingBarRating;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewName = (TextView) itemView.findViewById(R.id.textViewName);
            ratingBarRating = (RatingBar) itemView.findViewById(R.id.ratingBarRating);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(onItemClickListener != null)
                onItemClickListener.onClick(view, getAdapterPosition());
        }
    }

    public InstructorsRecyclerAdapter(ArrayList<Trainer> data) { list = data; }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) { this.onItemClickListener = onItemClickListener; }

    @Override
    public InstructorsRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.instructors_viewholder, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textViewName.setText(list.get(position).getName());
        holder.ratingBarRating.setRating((float)list.get(position).getRate().getRatingAverage());
    }

    @Override
    public int getItemCount() { return list.size(); }
}
