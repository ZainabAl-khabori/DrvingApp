package worldontheotherside.wordpress.com.drvingapp.Classes;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

/**
 * Created by زينب on 12/3/2017.
 */

public class Languages {

    private ArrayList<String> languages;

    public Languages()
    {
        //
    }

    public Languages(DataSnapshot dataSnapshot)
    {
        languages = new ArrayList<>();

        for(DataSnapshot snapshot: dataSnapshot.getChildren())
            languages.add(snapshot.getValue(String.class));
    }

    public ArrayList<String> getLanguages() {
        return languages;
    }

    public void setLanguages(ArrayList<String> languages) {
        this.languages = languages;
    }
}
