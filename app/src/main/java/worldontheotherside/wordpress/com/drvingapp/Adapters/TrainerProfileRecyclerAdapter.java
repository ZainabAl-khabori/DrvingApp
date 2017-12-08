package worldontheotherside.wordpress.com.drvingapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import worldontheotherside.wordpress.com.drvingapp.Classes.UserProfile;
import worldontheotherside.wordpress.com.drvingapp.R;

/**
 * Created by wafooy on 07/12/17.
 */

public class TrainerProfileRecyclerAdapter extends RecyclerView.Adapter<TrainerProfileRecyclerHolders> {

/*
    private String name;
    private long civilNo;
    private String email;
    private int age;
    private String phone;
    private String gender;
    private String password;
    private String carNo;
    private HashMap<String, String> places;
    private String vehicleType;
    private HashMap<String, String> languages;
    private TrainerRate rate;
    private Price price;*/

    private List<UserProfile> trainerData;
    protected Context context;

    public TrainerProfileRecyclerAdapter(Context context, List<UserProfile> trainerData) {
        this.trainerData = trainerData;
        this.context = context;
    }

    @Override
    public TrainerProfileRecyclerHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        TrainerProfileRecyclerHolders viewHolder = null;
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.profile_data_list, parent, false);
        viewHolder = new TrainerProfileRecyclerHolders(layoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TrainerProfileRecyclerHolders holder, int position) {
        holder.profileHeader.setText(trainerData.get(position).getHeader());
        holder.headerContent.setText(trainerData.get(position).getProfileContent());
    }



    @Override
    public int getItemCount() {
        return this.trainerData.size();
    }



}
