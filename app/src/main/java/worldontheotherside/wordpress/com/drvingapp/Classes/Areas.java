package worldontheotherside.wordpress.com.drvingapp.Classes;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

/**
 * Created by زينب on 12/3/2017.
 */

public class Areas {

    private ArrayList<String> areas;

    public Areas()
    {
        // Empty default constructor
    }

    public Areas(DataSnapshot dataSnapshot)
    {
        areas = new ArrayList<>();

        for(DataSnapshot snapshot: dataSnapshot.getChildren())
            areas.add(snapshot.getValue(String.class));
    }

    public ArrayList<String> getAreas() {
        return areas;
    }

    public void setAreas(ArrayList<String> areas) {
        this.areas = areas;
    }
}
