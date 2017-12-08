package worldontheotherside.wordpress.com.drvingapp.Classes;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import worldontheotherside.wordpress.com.drvingapp.Adapters.TrainerProfileRecyclerAdapter;
import worldontheotherside.wordpress.com.drvingapp.AppAPI;
import worldontheotherside.wordpress.com.drvingapp.AppKeys;

/**
 * Created by wafooy on 08/12/17.
 */

public class FirebaseDatabaseHelper {

        private static final String TAG = FirebaseDatabaseHelper.class.getSimpleName();

        private DatabaseReference databaseReference;

        public FirebaseDatabaseHelper(){
            databaseReference = FirebaseDatabase.getInstance().getReference();
        }

        public void createUserInFirebaseDatabase(String userId, Trainer firebaseUserEntity){
            Map<String, Trainer> user = new HashMap<String, Trainer>();
            user.put(userId, firebaseUserEntity);
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
            List<UserProfile> trainerData = new ArrayList<UserProfile>();
            if(dataSnapshot.getKey().equals(uId)){
                Trainer trainerInfo = dataSnapshot.getValue(Trainer.class);
                String getAge = String.valueOf(trainerInfo.getAge());
                String getCivilId = String.valueOf(trainerInfo.getCivilId());

                trainerData.add(new UserProfile(AppKeys.NAME, trainerInfo.getName()));
                trainerData.add(new UserProfile(AppKeys.EMAIL, trainerInfo.getEmail()));
                trainerData.add(new UserProfile(AppKeys.AGE, getAge));
                trainerData.add(new UserProfile(AppKeys.PHONE_NUMBER, trainerInfo.getPhone()));
                trainerData.add(new UserProfile(AppKeys.CIVIL_NO, getCivilId));
                trainerData.add(new UserProfile(AppKeys.GENDER, trainerInfo.getGender()));
            }
            return trainerData;
        }
}