package worldontheotherside.wordpress.com.drvingapp.Adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import worldontheotherside.wordpress.com.drvingapp.AppAPI;
import worldontheotherside.wordpress.com.drvingapp.Classes.Contract;
import worldontheotherside.wordpress.com.drvingapp.Classes.NewTrainee;
import worldontheotherside.wordpress.com.drvingapp.Classes.PreviousTrainee;
import worldontheotherside.wordpress.com.drvingapp.Classes.Trainer;
import worldontheotherside.wordpress.com.drvingapp.DatabaseManip;
import worldontheotherside.wordpress.com.drvingapp.R;

/**
 * Created by زينب on 12/1/2017.
 */

public class MyInstructorsRecyclerAdapter extends RecyclerView.Adapter<MyInstructorsRecyclerAdapter.ViewHolder> {

    private ArrayList<Contract> contractsList;
    private Object user;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener { public void OnClick(View view, int position); }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView textViewInstructorName;
        private ImageView imageViewDrumsTest;
        private ImageView imageViewSlopeTest;
        private ImageView imageViewRoadTest;
        private LinearLayout linearLayoutInfo;
        private LinearLayout linearLayoutRate;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewInstructorName = (TextView) itemView.findViewById(R.id.textViewInstructorName);
            imageViewDrumsTest = (ImageView) itemView.findViewById(R.id.imageViewDrumsTest);
            imageViewSlopeTest = (ImageView) itemView.findViewById(R.id.imageViewSlopeTest);
            imageViewRoadTest = (ImageView) itemView.findViewById(R.id.imageViewRoadTest);
            linearLayoutInfo = (LinearLayout) itemView.findViewById(R.id.linearLayoutInfo);
            linearLayoutRate = (LinearLayout) itemView.findViewById(R.id.linearLayoutRate);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(onItemClickListener != null)
                onItemClickListener.OnClick(view, getAdapterPosition());
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public MyInstructorsRecyclerAdapter(ArrayList<Contract> data, Object object) {
        contractsList = data;
        user = object;
    }

    @Override
    public MyInstructorsRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_instructors_viewholder, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        DatabaseManip.findData(AppAPI.TRAINER_BY_ID, "civilNo", contractsList.get(position).getTrainerId(), new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Trainer trainer = new Trainer(dataSnapshot);

                holder.textViewInstructorName.setText(trainer.getName());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.v("TRAINER_FINDING_ERROR", databaseError.getMessage());
            }
        });

        if(contractsList.get(position).getDrumsPass())
            holder.imageViewDrumsTest.setVisibility(View.VISIBLE);
        if(contractsList.get(position).getSlopePass())
            holder.imageViewSlopeTest.setVisibility(View.VISIBLE);
        if(contractsList.get(position).getRoadPass())
            holder.imageViewRoadTest.setVisibility(View.VISIBLE);

        if(user instanceof PreviousTrainee)
            holder.linearLayoutRate.setVisibility(View.VISIBLE);
        else if(!(user instanceof NewTrainee))
            Toast.makeText(holder.itemView.getContext(), "Error: not prev or new trainee", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount() { return contractsList.size(); }
}
