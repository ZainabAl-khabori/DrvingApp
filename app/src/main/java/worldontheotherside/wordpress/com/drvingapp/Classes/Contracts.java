package worldontheotherside.wordpress.com.drvingapp.Classes;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

/**
 * Created by زينب on 12/2/2017.
 */

public class Contracts
{
    private ArrayList<Contract> contracts;

    public Contracts()
    {
        // Empty default constructor
    }

    public Contracts(DataSnapshot dataSnapshot)
    {
        contracts = new ArrayList<>();

        for(DataSnapshot snapshot: dataSnapshot.getChildren())
        {
            contracts.add(new Contract(snapshot));
        }
    }

    public ArrayList<Contract> getContracts() {
        return contracts;
    }

    public void setContracts(ArrayList<Contract> contracts) {
        this.contracts = contracts;
    }
}
