package worldontheotherside.wordpress.com.drvingapp;

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

    public static void getData(String url, String param, ValueEventListener valueEventListener)
    {
        db = FirebaseDatabase.getInstance().getReferenceFromUrl(url);
        Query q = db.equalTo(param);
        q.addValueEventListener(valueEventListener);
    }

    public static void getData(String url, int param, ValueEventListener valueEventListener)
    {
        db = FirebaseDatabase.getInstance().getReferenceFromUrl(url);
        Query q = db.equalTo(param);
        q.addValueEventListener(valueEventListener);
    }

    public static void getData(String url, double param, ValueEventListener valueEventListener)
    {
        db = FirebaseDatabase.getInstance().getReferenceFromUrl(url);
        Query q = db.equalTo(param);
        q.addValueEventListener(valueEventListener);
    }

    public static void getData(String url, boolean param, ValueEventListener valueEventListener)
    {
        db = FirebaseDatabase.getInstance().getReferenceFromUrl(url);
        Query q = db.equalTo(param);
        q.addValueEventListener(valueEventListener);
    }
    public static void getData(String url, long param, ValueEventListener valueEventListener)
    {
        db = FirebaseDatabase.getInstance().getReferenceFromUrl(url);
        Query q = db.equalTo(param);
        q.addValueEventListener(valueEventListener);
    }

    public static void getData(String url, String param1, String secondField, String param2, ValueEventListener valueEventListener)
    {
        db = FirebaseDatabase.getInstance().getReferenceFromUrl(url);
        Query q = db.equalTo(param1).orderByChild(secondField).equalTo(param2);
        q.addValueEventListener(valueEventListener);
    }

    public static void addData(String url, Object data)
    {
        db = FirebaseDatabase.getInstance().getReferenceFromUrl(url);
        db.push().setValue(data);
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
}
