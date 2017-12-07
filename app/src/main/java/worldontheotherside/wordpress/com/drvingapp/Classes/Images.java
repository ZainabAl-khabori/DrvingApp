package worldontheotherside.wordpress.com.drvingapp.Classes;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

/**
 * Created by زينب on 12/7/2017.
 */

public class Images {

    private ArrayList<String> images;

    public Images()
    {
        //
    }

    public Images(DataSnapshot dataSnapshot)
    {
        images = new ArrayList<>();

        for(DataSnapshot snapshot: dataSnapshot.getChildren())
            images.add(snapshot.getValue(String.class));
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }
}
