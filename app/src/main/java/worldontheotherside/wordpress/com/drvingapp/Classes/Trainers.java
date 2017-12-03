package worldontheotherside.wordpress.com.drvingapp.Classes;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

/**
 * Created by زينب on 12/2/2017.
 */

public class Trainers {

    private ArrayList<Trainer> trainers;

    public Trainers()
    {
        // Empty default constructor
    }

    public Trainers(DataSnapshot dataSnapshot)
    {
        trainers = new ArrayList<>();

        for(DataSnapshot snapshot: dataSnapshot.getChildren())
            trainers.add(new Trainer(snapshot));
    }

    public ArrayList<Trainer> getTrainers() {
        return trainers;
    }

    public void setTrainers(ArrayList<Trainer> trainers) {
        this.trainers = trainers;
    }
}
