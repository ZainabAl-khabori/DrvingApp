package worldontheotherside.wordpress.com.drvingapp;

import android.net.Uri;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

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

    public static void getData(String url, String param1, String secondField, String param2, ValueEventListener valueEventListener)
    {
        db = FirebaseDatabase.getInstance().getReferenceFromUrl(url);
        Query q = db.equalTo(param1).orderByChild(secondField).equalTo(param2);
        q.addValueEventListener(valueEventListener);
    }

    public static void addData(String url, Object data, DatabaseReference.CompletionListener completionListener)
    {
        db = FirebaseDatabase.getInstance().getReferenceFromUrl(url);
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
