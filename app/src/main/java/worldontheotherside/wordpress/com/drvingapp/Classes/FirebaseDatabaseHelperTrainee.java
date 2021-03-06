package worldontheotherside.wordpress.com.drvingapp.Classes;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import worldontheotherside.wordpress.com.drvingapp.Adapters.TrainerProfileRecyclerAdapter;

/**
 * Created by wafooy on 19/12/17.
 */

public class FirebaseDatabaseHelperTrainee {


    private static final String TAG = FirebaseDatabaseHelperTrainee.class.getSimpleName();

    private DatabaseReference databaseReference;

    public FirebaseDatabaseHelperTrainee(){
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    public void createUserInFirebaseDatabase(String userId, NewTrainee newTrainee){
        Map<String, NewTrainee> user = new HashMap<String, NewTrainee>();
        user.put(userId, newTrainee);
        databaseReference.child("users").setValue(user);
    }

    public void isUserKeyExist(final String uid, final Context context, final RecyclerView recyclerView){
        databaseReference.child("users").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                java.lang.System.out.println("User login 1 " + dataSnapshot.getKey() + " " + dataSnapshot.getValue());
                List<UserProfile> userData = adapterSourceData(dataSnapshot, uid);
                java.lang.System.out.println("User login Size " + userData.size());
                TrainerProfileRecyclerAdapter recyclerViewAdapter = new TrainerProfileRecyclerAdapter((Activity)context, userData);
                recyclerView.setAdapter(recyclerViewAdapter);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                List<UserProfile> userData = adapterSourceData(dataSnapshot, uid);
                java.lang.System.out.println("User login Size " + userData.size());
                TrainerProfileRecyclerAdapter recyclerViewAdapter = new TrainerProfileRecyclerAdapter((Activity)context, userData);
                recyclerView.setAdapter(recyclerViewAdapter);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private List<UserProfile> adapterSourceData(DataSnapshot dataSnapshot, String uId){
        List<UserProfile> traineeData = new ArrayList<UserProfile>();
        if(dataSnapshot.getKey().equals(uId)){

           /* Trainer trainerInfo = dataSnapshot.getValue(Trainer.class);
            String getAge = String.valueOf(trainerInfo.getAge());
            String getCivilId = String.valueOf(trainerInfo.getCivilId());
            String getHourPrice = String.valueOf(trainerInfo.getHourPrice());
            String getContractPrice = String.valueOf(trainerInfo.getContractPrice());




            traineeData.add(new UserProfile(AppKeys.NAME, trainerInfo.getName()));
            traineeData.add(new UserProfile(AppKeys.EMAIL, trainerInfo.getEmail()));
            traineeData.add(new UserProfile(AppKeys.AGE, getAge));
            traineeData.add(new UserProfile(AppKeys.PHONE_NUMBER, trainerInfo.getPhone()));
            traineeData.add(new UserProfile(AppKeys.CIVIL_NO, getCivilId));
            traineeData.add(new UserProfile(AppKeys.GENDER, trainerInfo.getGender()));
            traineeData.add(new UserProfile(AppKeys.CAR_PLATE, trainerInfo.getCarNo()));
            traineeData.add(new UserProfile(AppKeys.LANGUAGES, trainerInfo.getSpokenLanguage()));
            traineeData.add(new UserProfile(AppKeys.PLACES, trainerInfo.getTrainingAreas()));
            traineeData.add(new UserProfile(AppKeys.VEHICLE_TYPE, trainerInfo.getVehicleType()));
            traineeData.add(new UserProfile(AppKeys.CONTRACT_TYPE, trainerInfo.getContractType()));
            traineeData.add(new UserProfile(AppKeys.TRAINING_TIME, trainerInfo.getTrainingTime()));

               *//* if(getHourPrice!=null)
                    trainerData.add(new UserProfile(AppKeys.HOUR_PRICE, getHourPrice));

                if(getContractPrice!=null)
                    trainerData.add(new UserProfile(AppKeys.CONTRACT_PRICE, getContractPrice));
*//*
            if (!TextUtils.isEmpty(getHourPrice))
                traineeData.add(new UserProfile(AppKeys.HOUR_PRICE, getHourPrice));

            if (!TextUtils.isEmpty(getContractPrice))
                traineeData.add(new UserProfile(AppKeys.CONTRACT_PRICE, getContractPrice));
*/

        }
        return traineeData;
    }

}
