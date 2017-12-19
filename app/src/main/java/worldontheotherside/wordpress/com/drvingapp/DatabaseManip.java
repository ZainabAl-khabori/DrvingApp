package worldontheotherside.wordpress.com.drvingapp;

import android.net.Uri;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import worldontheotherside.wordpress.com.drvingapp.Classes.NewTrainee;
import worldontheotherside.wordpress.com.drvingapp.Classes.PreviousTrainee;
import worldontheotherside.wordpress.com.drvingapp.Classes.Trainer;

/**
 * Created by زينب on 11/20/2017.
 */

public class DatabaseManip {

    private static DatabaseReference db;

    public static void getData(String url, ValueEventListener valueEventListener)
    {
        db = FirebaseDatabase.getInstance().getReferenceFromUrl(url);
        db.addValueEventListener(valueEventListener);
    }

    public static void getData(String url, String child, ValueEventListener valueEventListener)
    {
        db = FirebaseDatabase.getInstance().getReferenceFromUrl(url).child(child);
        db.addValueEventListener(valueEventListener);
    }

    public static void findData(String url, String field, String param, ValueEventListener valueEventListener)
    {
        db = FirebaseDatabase.getInstance().getReferenceFromUrl(url);
        Query q = db.orderByChild(field).equalTo(param);
        q.addValueEventListener(valueEventListener);
    }

    public static void findData(String url, String field, int param, ValueEventListener valueEventListener)
    {
        db = FirebaseDatabase.getInstance().getReferenceFromUrl(url);
        Query q = db.orderByChild(field).equalTo(param);
        q.addValueEventListener(valueEventListener);
    }

    public static void findData(String url, String field, double param, ValueEventListener valueEventListener)
    {
        db = FirebaseDatabase.getInstance().getReferenceFromUrl(url);
        Query q = db.orderByChild(field).equalTo(param);
        q.addValueEventListener(valueEventListener);
    }

    public static void findData(String url, String field, boolean param, ValueEventListener valueEventListener)
    {
        db = FirebaseDatabase.getInstance().getReferenceFromUrl(url);
        Query q = db.orderByChild(field).equalTo(param);
        q.addValueEventListener(valueEventListener);
    }

    public static void findData(String url, String field, long param, ValueEventListener valueEventListener)
    {
        db = FirebaseDatabase.getInstance().getReferenceFromUrl(url);
        Query q = db.orderByChild(field).equalTo(param);
        q.addValueEventListener(valueEventListener);
    }

    public static void findData(String parent, HashMap<String, String> params, ValueEventListener valueEventListener)
    {
        db = FirebaseDatabase.getInstance().getReferenceFromUrl(parent);
        Query q = db;

        if(params.containsKey("Area"))
            q = q.orderByChild("places/"+params.get("Area")).equalTo(true);
        if(params.containsKey("From") || params.containsKey("To"))
        {
            q = q.orderByChild("age");
            if(params.containsKey("From"))
                q = q.startAt(Integer.valueOf(params.get("From")));
            if(params.containsKey("To"))
                q = q.endAt(Integer.valueOf(params.get("To")));
        }
        if(params.containsKey("Gender"))
            q = q.orderByChild("gender").equalTo(params.get("Gender"));
        if(params.containsKey("Contract"))
            q = q.orderByChild("price/"+params.get("Contract")).startAt("");
        if(params.containsKey("Language"))
            q = q.orderByChild("languages/"+params.get("Language")).equalTo(true);

        q.addValueEventListener(valueEventListener);
    }

    public static void addData(String url, Object data, DatabaseReference.CompletionListener completionListener)
    {
        db = FirebaseDatabase.getInstance().getReferenceFromUrl(url);

        if(url.equals(AppAPI.TRAINERS) || url.equals(AppAPI.FORMER_TRAINEES) || url.equals(AppAPI.CURRENT_TRAINEES))
        {
            if(url.equals(AppAPI.TRAINERS))
            {
                Trainer trainer = (Trainer) data;
                db.child(String.valueOf(trainer.getCivilNo())).setValue(data, completionListener);
            }
            else if(url.equals(AppAPI.FORMER_TRAINEES))
            {
                PreviousTrainee previousTrainee = (PreviousTrainee) data;
                db.child(String.valueOf(previousTrainee.getCivilNo())).setValue(data, completionListener);
            }
            else
            {
                NewTrainee newTrainee = (NewTrainee) data;
                db.child(String.valueOf(newTrainee.getCivilNo())).setValue(data, completionListener);
            }
        }
        else
            db.push().setValue(data, completionListener);
    }

    public static void updateData(String url, Object data, DatabaseReference.CompletionListener completionListener)
    {
        db = FirebaseDatabase.getInstance().getReferenceFromUrl(url);
        db.setValue(data, completionListener);
    }

    public static void updateData(String url, String field, Object data, DatabaseReference.CompletionListener completionListener)
    {
        db = FirebaseDatabase.getInstance().getReferenceFromUrl(url).child(field);
        db.setValue(data, completionListener);
    }

    public static void deleteData(String url, Object data, DatabaseReference.CompletionListener completionListener)
    {
        db = FirebaseDatabase.getInstance().getReferenceFromUrl(url);
        db.setValue(null, completionListener);
    }

    public static void deleteData(String url, String field, Object data, DatabaseReference.CompletionListener completionListener)
    {
        db = FirebaseDatabase.getInstance().getReferenceFromUrl(url).child(field);
        db.setValue(null, completionListener);
    }

    public static void updateUserProfile(FirebaseUser user, String name, String email, String password, Uri photo, OnCompleteListener onCompleteListener)
    {
        UserProfileChangeRequest.Builder userProfileChangeRequestBuilder = new UserProfileChangeRequest.Builder();

        if(name != null)
            userProfileChangeRequestBuilder.setDisplayName(name);
        if(photo != null)
            userProfileChangeRequestBuilder.setPhotoUri(photo);

        if(email != null)
            user.updateEmail(email);
        if(password != null)
            user.updatePassword(password);

        UserProfileChangeRequest userProfileChangeRequest = userProfileChangeRequestBuilder.build();
        user.updateProfile(userProfileChangeRequest).addOnCompleteListener(onCompleteListener);
    }
}
