package worldontheotherside.wordpress.com.drvingapp.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;

import worldontheotherside.wordpress.com.drvingapp.AppAPI;
import worldontheotherside.wordpress.com.drvingapp.Classes.Contract;
import worldontheotherside.wordpress.com.drvingapp.Classes.Trainer;
import worldontheotherside.wordpress.com.drvingapp.DatabaseManip;
import worldontheotherside.wordpress.com.drvingapp.R;

/**
 * Created by زينب on 12/19/2017.
 */

public class ContractInfoFragment extends DialogFragment {

    public ContractInfoFragment()
    {
        //
    }

    public static ContractInfoFragment newContractInfoFragment(Contract contract)
    {
        ContractInfoFragment infoFragment = new ContractInfoFragment();
        Bundle args = new Bundle();
        args.putString("Contract", new Gson().toJson(contract));
        infoFragment.setArguments(args);
        return infoFragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final Contract contract = new Gson().fromJson(getArguments().getString("Contract"), Contract.class);
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_contract_details, null);

        builder.setView(view);

        TextView textViewStartVal = (TextView) view.findViewById(R.id.textViewStartVal);
        TextView textViewEndVal = (TextView) view.findViewById(R.id.textViewEndVal);
        TextView textViewPriceVal = (TextView) view.findViewById(R.id.textViewPriceVal);
        TextView textViewTypeVal = (TextView) view.findViewById(R.id.textViewTypeVal);
        final TextView textViewTraineeVal = (TextView) view.findViewById(R.id.textViewTraineeVal);
        final TextView textViewInstructorVal = (TextView) view.findViewById(R.id.textViewInstructorVal);
        TextView textViewDrumsVal = (TextView) view.findViewById(R.id.textViewDrumsVal);
        TextView textViewSlopeVal = (TextView) view.findViewById(R.id.textViewSlopeVal);
        TextView textViewRoadVal = (TextView) view.findViewById(R.id.textViewRoadVal);
        TextView textViewCancelledVal = (TextView) view.findViewById(R.id.textViewCancelledVal);

        textViewStartVal.setText(contract.getStart());
        if(contract.getEnd().equals(""))
            textViewEndVal.setText("Ongoing");
        else
            textViewEndVal.setText(contract.getEnd());
        textViewPriceVal.setText(String.valueOf(contract.getPrice())+" OR");
        textViewTypeVal.setText(contract.getType());

        DatabaseManip.getData(AppAPI.TRAINEES, new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<DataSnapshot> snapshots = new ArrayList<>();
                for(DataSnapshot snapshot: dataSnapshot.getChildren())
                    snapshots.add(snapshot);

                ArrayList<Long> ids = new ArrayList<>();
                ArrayList<String> names = new ArrayList<>();
                for(int i = 0; i < snapshots.size(); i++)
                {
                    for(DataSnapshot snapshot: snapshots.get(i).getChildren())
                    {
                        ids.add(snapshot.child("civilNo").getValue(Long.class));
                        names.add(snapshot.child("username").getValue(String.class));
                    }
                }

                if(ids.contains(contract.getTraineeId()))
                    textViewTraineeVal.setText(names.get(ids.indexOf(contract.getTraineeId())));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.v("TRAINEE_FINDING_ERROR", databaseError.getMessage());
            }
        });

        DatabaseManip.findData(AppAPI.TRAINER_BY_ID, "civilNo", contract.getTrainerId(),
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Trainer trainer = new Trainer(dataSnapshot);

                        textViewInstructorVal.setText(trainer.getName());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.v("TRAINER_FINDING_ERROR", databaseError.getMessage());
                    }
                });

        if(contract.isDrumsPass())
            textViewDrumsVal.setText("Yes");
        else
            textViewDrumsVal.setText("");

        if(contract.isSlopePass())
            textViewSlopeVal.setText("Yes");
        else
            textViewSlopeVal.setText("");

        if(contract.isRoadPass())
            textViewRoadVal.setText("Yes");
        else
            textViewRoadVal.setText("");

        if(contract.isDrumsPass())
            textViewDrumsVal.setText("Yes");
        else
            textViewDrumsVal.setText("");

        if(contract.isCancelled())
            textViewCancelledVal.setText("Yes");
        else
            textViewCancelledVal.setText("");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dismiss();
            }
        });

        return builder.create();
    }
}
